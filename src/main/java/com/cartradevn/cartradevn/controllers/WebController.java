package com.cartradevn.cartradevn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cartradevn.cartradevn.administration.Enum.UserRole;
import com.cartradevn.cartradevn.administration.controller.UserResponseDTO;
import com.cartradevn.cartradevn.administration.dto.UserUpdateDTO;
import com.cartradevn.cartradevn.administration.entity.User;
import com.cartradevn.cartradevn.administration.respository.UserRepo;
import com.cartradevn.cartradevn.services.repository.VehicleRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VehicleRepo vehicleRepo;

    @GetMapping({ "/", "/index-9" })
    public String index(HttpSession session) {
        return "index-9";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        // Nếu đã đăng nhập thì redirect về trang chủ
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        return "dashboard";
    }

    @GetMapping("/admin-dashboard")
    public String admindashboard(Model model, HttpSession session) {
        // Đếm số lượng users
        long userCount = userRepo.count();
        model.addAttribute("userCount", userCount);
        // Đếm số lượng xe
        long vehicleCount = vehicleRepo.count();
        model.addAttribute("vehicleCount", vehicleCount);
        return "admin-dashboard";
    }

    @GetMapping("/users-list")
    public String listUsers(Model model,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<User> userPage;

        if (search != null && !search.isEmpty()) {
            userPage = userRepo.findByUsernameOrEmail(search, search, pageable);
        } else {
            userPage = userRepo.findAll(pageable);
        }

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("search", search);

        return "users-list";
    }

    @GetMapping("/admin-profile")
    public String profile(HttpSession session, Model model) {
        return "redirect:/login";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        try {
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("editUser", user);
            return "admin-profile";
        } catch (Exception e) {
            return "redirect:/users-list?error=" + e.getMessage();
        }
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updateUser,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            user.setUsername(updateUser.getUsername());
            user.setEmail(updateUser.getEmail());
            user.setRole(updateUser.getRole());
            userRepo.save(user);
            redirectAttributes.addFlashAttribute("S", "Cập nhật thành công");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("E", e.getMessage());
        }
        return "redirect:/users-list";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        try {
            UserResponseDTO userSession = (UserResponseDTO) session.getAttribute("user");
            if (userSession == null) {
                return "redirect:/login";
            }
    
            User user = userRepo.findById(userSession.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Add debug logging
            System.out.println("Found user: " + user.getUsername());
            
            model.addAttribute("user", user);
            return "profile";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    @PostMapping("/profile/update")
public String updateProfile(@ModelAttribute UserUpdateDTO userUpdateDTO, 
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
    try {
        UserResponseDTO userSession = (UserResponseDTO) session.getAttribute("user");
        User user = userRepo.findById(userSession.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password if it's being changed
        if (!userUpdateDTO.getNewPassword().isEmpty()) {
            if (!passwordEncoder.matches(userUpdateDTO.getCurrentPassword(), user.getPasswordHash())) {
                throw new RuntimeException("Current password is incorrect");
            }
            if (!userUpdateDTO.getNewPassword().equals(userUpdateDTO.getConfirmPassword())) {
                throw new RuntimeException("New passwords don't match");
            }
            user.setPasswordHash(passwordEncoder.encode(userUpdateDTO.getNewPassword()));
        }

        user.setUsername(userUpdateDTO.getUsername());
        user.setEmail(userUpdateDTO.getEmail());
        
        userRepo.save(user);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/profile";
}
}