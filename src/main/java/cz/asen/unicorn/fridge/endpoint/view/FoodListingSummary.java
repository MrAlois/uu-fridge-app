package cz.asen.unicorn.fridge.endpoint.view;

import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import dev.hilla.Nonnull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public record FoodListingSummary(
        @Nonnull Integer id,
        @Nonnull String shortDescription,
        @Nonnull LocalDateTime expiryDate,
        @Nonnull String pickupLocation,
        @Nonnull LocalDateTime created,
        @Nonnull String donorName,
        @Nonnull ClaimState currentState,
        @Nonnull Set<String> base64Images
) implements Serializable {
}
