package cz.asen.fridge.endpoint.request;

import cz.asen.fridge.domain.User;
import cz.asen.fridge.domain.enums.Allergens;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateListingRequest(
        @NotBlank
        String shortDescription,

        @NotBlank
        String description,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        LocalDate expiryDate,

        @NotBlank
        String pickupLocation,

        User donor,
        Set<Allergens> allergens,
        Set<String> base64Images
) {
}
