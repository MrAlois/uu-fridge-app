package cz.asen.fridge.persistence.mapper;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.domain.enums.ClaimState;
import cz.asen.fridge.domain.enums.DietaryRestriction;
import cz.asen.fridge.persistence.entity.FoodListingClaimEntity;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class FoodListingDomainMapper {
    private static final String DIETARY_RESTRICTION_DELIMITER = ",";

    public static @NotNull FoodListing toDomain(
            @NotNull FoodListingEntity entity,
            FoodListingClaimEntity foodListingClaimEntity,
            @NotNull List<FoodListingPhotoEntity> foodListingPhotoEntity
    ) {
        final var maybeClaimEntity = Optional.ofNullable(foodListingClaimEntity);
        return new FoodListing(
                entity.getId(),
                UserDomainMapper.toDomain(entity.getDonor()),
                entity.getShortDescription(),
                entity.getDescription(),
                entity.getQuantity(),
                LocalDateTime.ofInstant(entity.getExpiryDate(), ZoneId.systemDefault()),
                entity.getPickupLocation(),
                LocalDateTime.ofInstant(entity.getCreated(), ZoneId.systemDefault()),
                parseDietaryRestriction(entity.getDietaryInfo()),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getState)
                        .map(ClaimState::valueOf)
                        .orElse(ClaimState.WAITING),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getUser)
                        .map(UserDomainMapper::toDomain),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getClaimed)
                        .map(instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault())),
                foodListingPhotoEntity.stream()
                        .map(FoodListingPhotoEntity::getData)
                        .collect(Collectors.toSet())
        );
    }

    /**
     * Parses a string representation of dietary restrictions into a set of DietaryRestriction objects.
     *
     * @param dietaryRestrictions the string representation of dietary restrictions to parse
     * @return the set of DietaryRestriction objects parsed from the input string
     */
    private static @NotNull Set<DietaryRestriction> parseDietaryRestriction(@NotNull String dietaryRestrictions){
        return Arrays.stream(dietaryRestrictions.split(DIETARY_RESTRICTION_DELIMITER))
                .filter(Objects::nonNull)
                .map(String::strip)
                .map(DietaryRestriction::valueOf)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Parses the set of dietary restrictions into a comma-separated string.
     *
     * @param dietaryRestrictions the set of dietary restrictions to parse
     * @return the comma-separated string of dietary restrictions
     */
    @Contract("_ -> new")
    private static @NotNull String parseDietaryRestriction(@NotNull Set<DietaryRestriction> dietaryRestrictions){
        return String.join(DIETARY_RESTRICTION_DELIMITER, dietaryRestrictions.stream().map(DietaryRestriction::name).toList());
    }
}
