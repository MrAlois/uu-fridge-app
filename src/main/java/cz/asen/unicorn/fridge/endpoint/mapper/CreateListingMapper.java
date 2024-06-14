package cz.asen.unicorn.fridge.endpoint.mapper;

import cz.asen.unicorn.fridge.domain.FoodListing;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import cz.asen.unicorn.fridge.endpoint.operation.CreateListing;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

@UtilityClass
public class CreateListingMapper {
    @Contract("_, _ -> new")
    public static @NotNull FoodListing createNewFromView(@NotNull CreateListing createListing, Integer listingId){
        return new FoodListing(
                listingId,
                createListing.donor(),
                createListing.shortDescription(),
                createListing.description(),
                createListing.expiryDate().atStartOfDay(),
                createListing.pickupLocation().locationName(),
                createListing.pickupLocation().latitude(),
                createListing.pickupLocation().longitude(),
                LocalDateTime.now(), // Set created to now
                createListing.allergens(),
                ClaimState.UNCLAIMED,
                Optional.empty(),
                Optional.empty(),
                createListing.base64Images()
        );
    }

    /**
     * Converts a {@link CreateListing} object and a {@link FoodListing} object into a {@link FoodListing} object. Used for update!
     *
     * @param listingId      the ID of the listing
     * @param createListing  the {@link CreateListing} object to convert
     * @param foodListing    the {@link FoodListing} object to convert
     * @return a new {@link FoodListing} object converted from the given parameters
     */
    @Contract("_, _ -> new")
    public static @NotNull FoodListing updateFromViewAndDomain(Integer listingId, @NotNull CreateListing createListing, @NotNull FoodListing foodListing){
        return new FoodListing(
                listingId,
                createListing.donor(),
                createListing.shortDescription(),
                createListing.description(),
                createListing.expiryDate().atStartOfDay(),
                createListing.pickupLocation().locationName(),
                createListing.pickupLocation().latitude(),
                createListing.pickupLocation().longitude(),
                foodListing.created(),
                createListing.allergens(),
                foodListing.currentState(),
                foodListing.currentClaimingUser(),
                foodListing.claimTime(),
                createListing.base64Images()
        );
    }

    @Contract("_ -> new")
    public static @NotNull CreateListing toView(@NotNull FoodListing foodListing){
        return new CreateListing(
                foodListing.shortDescription(),
                foodListing.description(),
                foodListing.expiryDate().toLocalDate(), // LocalDateTime to LocalDate conversion
                new CreateListing.Location(
                        foodListing.pickupLocation(),
                        foodListing.pickupLatitude(),
                        foodListing.pickupLongitude()
                ),
                foodListing.donor(),
                foodListing.allergens(),
                foodListing.base64Images()
        );
    }
}
