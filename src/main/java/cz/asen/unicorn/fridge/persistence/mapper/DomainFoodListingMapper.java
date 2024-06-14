package cz.asen.unicorn.fridge.persistence.mapper;

import cz.asen.unicorn.fridge.domain.FoodListing;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
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
        return toDomain(entity, Collections.emptyList());
    }

    public static @NotNull FoodListing toDomain(@NotNull FoodListingEntity entity,
                                                Collection<String> foodListingPhotos) {
        var claimState = Optional.ofNullable(entity.getState())
                .map(ClaimState::valueOf)
                .orElse(ClaimState.UNCLAIMED);

        var claimUser = Optional.ofNullable(entity.getClaimer())
                .map(DomainUserMapper::toDomain);

        var claimTime = Optional.ofNullable(entity.getClaimed())
                .map(claimed -> LocalDateTime.ofInstant(claimed, ZoneId.systemDefault()));

        return new FoodListing(
                entity.getListingId(),
                DomainUserMapper.toDomain(entity.getDonor()),
                entity.getShortDescription(),
                entity.getDescription(),
                LocalDateTime.ofInstant(entity.getExpiryDate(), ZoneId.systemDefault()),
                entity.getPickupLocation(),
                entity.getPickupLatitude(),
                entity.getPickupLongitude(),
                LocalDateTime.ofInstant(entity.getCreated(), ZoneId.systemDefault()),
                DomainAllergenMapper.parseAllergens(entity.getAllergens()),
                claimState,
                claimUser,
                claimTime,
                foodListingPhotos != null ? new HashSet<>(foodListingPhotos) : Collections.emptySet()
        );
    }

    public static @NotNull FoodListingEntity fromDomain(@NotNull FoodListing domain) {
        FoodListingEntity entity = new FoodListingEntity();
        entity.setListingId(domain.id());
        entity.setDonor(DomainUserMapper.fromDomain(domain.donor()));
        entity.setShortDescription(domain.shortDescription());
        entity.setDescription(domain.description());
        entity.setExpiryDate(domain.expiryDate().toInstant(ZoneOffset.UTC));
        entity.setPickupLocation(domain.pickupLocation());
        entity.setPickupLatitude(domain.pickupLatitude());
        entity.setPickupLongitude(domain.pickupLongitude());
        entity.setCreated(domain.created().toInstant(ZoneOffset.UTC));
        entity.setAllergens(DomainAllergenMapper.parseAllergens(domain.allergens()));

        entity.setState(domain.currentState().toString());
        domain.currentClaimingUser()
                .map(DomainUserMapper::fromDomain)
                .ifPresent(entity::setClaimer);

        domain.claimTime()
                .map(time -> time.toInstant(ZoneOffset.UTC))
                .ifPresent(entity::setClaimed);

        return entity;
    }
}