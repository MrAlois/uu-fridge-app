package cz.asen.unicorn.fridge.domain;

import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

public record FoodListingClaim(
        @NotNull
        ClaimState currentState,

        @NotNull
        Optional<User> currentClaimingUser,

        @NotNull
        Optional<LocalDateTime> claimTime
) {
}
