package cz.asen.unicorn.fridge.endpoint.request;

import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.domain.enums.Allergens;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateListingRequest(
        @NotBlank
        String shortDescription,

        @NotBlank
        String description,

        @NotNull
        LocalDate expiryDate,

        @NotBlank
        String pickupLocation,

        User donor,
        Set<Allergens> allergens,
        Set<String> base64Images
) {
}
