package cz.asen.unicorn.fridge.endpoint.operation;

import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.domain.enums.Allergen;
import cz.asen.unicorn.fridge.domain.enums.DistanceType;
import dev.hilla.Nullable;

import java.time.LocalDate;
import java.util.Set;

public record ListingSearchParameters(
        @Nullable String namePattern,
        @Nullable User owner,
        @Nullable LocalDate upToExpiryDate,
        @Nullable Set<Allergen> allergens,
        @Nullable DistanceType distanceType
) {
}
