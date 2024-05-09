package cz.asen.fridge.persistence.mapper;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.domain.enums.ClaimState;
import cz.asen.fridge.persistence.entity.AppUserEntity;
import cz.asen.fridge.persistence.entity.FoodListingClaimEntity;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class FoodListingDomainMapper {

    @Contract("_, _ -> new")
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
                Collections.emptySet(),
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
                        .map(blob -> convertBlobToBase64(blob))
                        .collect(Collectors.toSet())
        );
    }

    private static String convertBlobToBase64(@NotNull Blob blob)  {
        try {
            InputStream inputStream = blob.getBinaryStream();
            byte[] bytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
