package cz.asen.unicorn.fridge.domain;

import cz.asen.unicorn.fridge.domain.enums.Allergen;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

/**
 * DTO for {@link FoodListingEntity}
 */
@Builder(toBuilder = true)
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
        LocalDateTime expiryDate,

        @NotNull
        @Size(max = 128)
        String pickupLocation,

        Double pickupLatitude,

        Double pickupLongitude,

        @NotNull
        LocalDateTime created,

        @NotNull
        Set<Allergen> allergens,

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