package cz.asen.fridge.persistence.mapper;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.domain.User;
import cz.asen.fridge.domain.enums.ClaimState;
import cz.asen.fridge.persistence.entity.FoodListingClaimEntity;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static cz.asen.fridge.persistence.mapper.DomainAllergenMapper.parseAllergens;

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
            @NotNull List<FoodListingPhotoEntity> foodListingPhotoEntity
    ) {
        final var maybeClaimEntity = Optional.ofNullable(foodListingClaimEntity);
        return new FoodListing(
                entity.getId(),
                DomainUserMapper.toDomain(entity.getDonor()),
                entity.getShortDescription(),
                entity.getDescription(),
                entity.getQuantity(),
                LocalDateTime.ofInstant(entity.getExpiryDate(), ZoneId.systemDefault()),
                entity.getPickupLocation(),
                LocalDateTime.ofInstant(entity.getCreated(), ZoneId.systemDefault()),
                parseAllergens(entity.getAllergens()),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getState)
                        .map(ClaimState::valueOf)
                        .orElse(ClaimState.WAITING),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getUser)
                        .map(DomainUserMapper::toDomain),
                maybeClaimEntity
                        .map(FoodListingClaimEntity::getClaimed)
                        .map(instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault())),
                foodListingPhotoEntity.stream()
                        .map(FoodListingPhotoEntity::getData)
                        .collect(Collectors.toSet())
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
        entity.setQuantity(domain.quantity());
        entity.setExpiryDate(domain.expiryDate().toInstant(ZoneOffset.UTC));
        entity.setPickupLocation(domain.pickupLocation());
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
