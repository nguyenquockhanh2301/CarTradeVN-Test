package com.cartradevn.cartradevn.services.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private Long userId; 
    private String brand;
    private String model;
    private String year;
    private String color;
    private String condition; // Corrected spelling
    private String fuelType; // Petrol/Electric/... 
    private Double price;
    private String city;
    private String description;
    private String status; // pending/approved/sold
    private String createdAt; // LocalDateTime as String for simplicity in DTO

    //Validation
    @AssertTrue(message = "Brand không được để trống")
    public boolean isBrandValid() {
        return brand != null && !brand.trim().isEmpty();
    }

    @AssertTrue(message = "Model không được để trống")
    public boolean isModelValid() {
        return model != null && !model.trim().isEmpty();
    }

    @AssertTrue(message = "Giá xe phải lớn hơn 0")
    public boolean isPriceValid() {
        return price != null && price > 0;
    }

    @AssertTrue(message = "City không được để trống")
    public boolean isCityValid() {
        return city != null && !city.trim().isEmpty();
    }

    @AssertTrue(message = "Condition chỉ được là 'new' hoặc 'used'")
    public boolean isConditionValid() {
        if (condition == null) return true; // optional field
        return condition.equalsIgnoreCase("new") || 
               condition.equalsIgnoreCase("used");
    }

    @AssertTrue(message = "FuelType không hợp lệ")
    public boolean isFuelTypeValid() {
        if (fuelType == null) return true; // optional field
        String fuel = fuelType.toLowerCase();
        return fuel.equals("petrol") || 
               fuel.equals("diesel") || 
               fuel.equals("electric") || 
               fuel.equals("hybrid");
    }
}

