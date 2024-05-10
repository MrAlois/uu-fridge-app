package cz.asen.fridge.domain;

import cz.asen.fridge.domain.enums.Allergens;
import cz.asen.fridge.domain.enums.ClaimState;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

/**
 * DTO for {@link FoodListingEntity}
 */
public record FoodListing(
        Integer id,

        @NotNull
        User donor,

        @NotNull
        @Size(max = 64)
        String shortDescription,

        @NotNull
        @Size(max = 256)
        String description,

        @NotNull
        Integer quantity,

        @NotNull
        LocalDateTime expiryDate,

        @NotNull
        @Size(max = 128)
        String pickupLocation,

        @NotNull
        LocalDateTime created,

        @NotNull
        Set<Allergens> allergens,

//      Claim section
        @NotNull
        ClaimState currentState,

        @NotNull
        Optional<User> currentClaimingUser,

        @NotNull
        Optional<LocalDateTime> claimTime,

//      Image section
        @NotNull
        Set<String> base64Images

) implements Serializable {
}