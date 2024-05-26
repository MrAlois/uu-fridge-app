package cz.asen.unicorn.fridge.persistence.mapper;

import cz.asen.unicorn.fridge.domain.FoodListing;
import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingClaimEntity;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
@UtilityClass
public class DomainFoodListingMapper {

    public static @NotNull FoodListing toDomain(@NotNull FoodListingEntity entity) {
        return toDomain(entity, null, Collections.emptyList());
    }

    public static @NotNull FoodListing toDomain(@NotNull FoodListingEntity entity, FoodListingClaimEntity foodClaim) {
        return toDomain(entity, foodClaim, Collections.emptyList());
    }

    public static @NotNull FoodListing toDomain(
            @NotNull FoodListingEntity entity,
            FoodListingClaimEntity foodListingClaimEntity,
            Collection<String> foodListingPhotos
    ) {
        final var maybeClaimEntity = Optional.ofNullable(foodListingClaimEntity);
        return new FoodListing(
                entity.getId(),
                DomainUserMapper.toDomain(entity.getDonor()),
                entity.getShortDescription(),
                entity.getDescription(),
                LocalDateTime.ofInstant(entity.getExpiryDate(), ZoneId.systemDefault()),
                entity.getPickupLocation(),
                entity.getPickupLatitude(),
                entity.getPickupLongitude(),
                LocalDateTime.ofInstant(entity.getCreated(), ZoneId.systemDefault()),
                DomainAllergenMapper.parseAllergens(entity.getAllergens()),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getState)
                        .map(ClaimState::valueOf)
                        .orElse(ClaimState.UNCLAIMED),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getUser)
                        .map(DomainUserMapper::toDomain),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getClaimed)
                        .map(instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault())),
                foodListingPhotos != null ? new HashSet<>(foodListingPhotos) : Collections.emptySet()
        );
    }

    public static @NotNull FoodListingEntity fromDomain(
            @NotNull FoodListing domain,
            FoodListingClaimEntity foodListingClaimEntity
    ) {
        FoodListingEntity entity = new FoodListingEntity();
        entity.setId(domain.id());
        //entity.setDonor(DomainUserMapper.fromDomain(domain.donor()));
        entity.setDonor(DomainUserMapper.fromDomain(new User(1, null, null, null, null, null)));
        entity.setShortDescription(domain.shortDescription());
        entity.setDescription(domain.description());
        entity.setExpiryDate(domain.expiryDate().toInstant(ZoneOffset.UTC));
        entity.setPickupLocation(domain.pickupLocation());
        entity.setPickupLatitude(domain.pickupLatitude());
        entity.setPickupLongitude(domain.pickupLongitude());
        entity.setCreated(domain.created().toInstant(ZoneOffset.UTC));
        entity.setAllergens(DomainAllergenMapper.parseAllergens(domain.allergens()));

        if(foodListingClaimEntity != null) {
            foodListingClaimEntity.setState(domain.currentState().toString());
            domain.currentClaimingUser()
                    .map(DomainUserMapper::fromDomain)
                    .ifPresent(foodListingClaimEntity::setUser);

            domain.claimTime()
                    .map(time -> time.toInstant(ZoneOffset.UTC))
                    .ifPresent(foodListingClaimEntity::setClaimed);
        }

        return entity;
    }
}
