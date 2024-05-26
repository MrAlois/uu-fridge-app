package cz.asen.unicorn.fridge.endpoint.operation;

import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.domain.enums.Allergen;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public record CreateListing(
        @NotBlank
        String shortDescription,

        @NotBlank
        String description,

        @NotNull
        LocalDate expiryDate,

        @NotNull
        Location pickupLocation,

        User donor,
        Set<Allergen> allergens,
        Set<String> base64Images
) implements Serializable {

        public static record Location(
                @NotNull
                String locationName,
                Double latitude,
                Double longitude
        ){}
}

