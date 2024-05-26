package cz.asen.unicorn.fridge.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.asen.unicorn.fridge.domain.FoodListing;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import cz.asen.unicorn.fridge.domain.enums.DistanceType;
import cz.asen.unicorn.fridge.endpoint.operation.CreateListing;
import cz.asen.unicorn.fridge.endpoint.view.FoodListingSummary;
import cz.asen.unicorn.fridge.service.FoodListingService;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class FoodListingEndpoint {
    private final FoodListingService foodListingService;

    public FoodListingEndpoint(FoodListingService foodListingService) {
        this.foodListingService = foodListingService;
    }

    @Deprecated
    @Nonnull
    public List<FoodListingSummary> getAllFoodListingSummaries(){
        return foodListingService.getAllFoodListings().stream()
                .map(FoodListingEndpoint::transformToFoodListingSummaryView)
                .toList();
    }

    @Nonnull
    public List<FoodListingSummary> searchFoodListings(@Nonnull String name, @Nullable DistanceType distanceType){
        return foodListingService.searchForListings(name).stream()
                .map(FoodListingEndpoint::transformToFoodListingSummaryView)
                .toList();
    }

    @Nonnull
    public FoodListing getFoodListingById(int listingId) throws NoSuchElementException {
        return foodListingService.getListingByIdOrThrow(listingId);
    }

    @Nonnull
    public FoodListing createFoodListing(@Nonnull @NotNull CreateListing request){
        return foodListingService.saveListing(
                buildFromCreateRequest(request, null)
        );
    }

    @Nonnull
    public FoodListing updateFoodListing(@Nonnull @NotNull CreateListing request, Integer listingId){
        return foodListingService.saveListing(
                buildFromCreateRequest(request, listingId)
        );
    }

    public void deleteFoodListing(@Nonnull @NotNull Integer listingId) throws NoSuchElementException {
        foodListingService.deleteListingById(listingId);
    }

    @Contract("_, _ -> new")
    private static @NotNull FoodListing buildFromCreateRequest(@NotNull CreateListing request, Integer listingId){
        return new FoodListing(
                listingId,
                request.donor(),
                request.shortDescription(),
                request.description(),
                request.expiryDate().atStartOfDay(),
                request.pickupLocation().locationName(),
                request.pickupLocation().latitude(),
                request.pickupLocation().longitude(),
                LocalDateTime.now(), // Set created to now
                request.allergens(),
                ClaimState.UNCLAIMED,
                Optional.empty(),
                Optional.empty(),
                request.base64Images()
        );
    }

    private static @NotNull FoodListingSummary transformToFoodListingSummaryView(@NotNull FoodListing foodListing) {
        return new FoodListingSummary(
                foodListing.id(),
                foodListing.shortDescription(),
                foodListing.expiryDate(),
                foodListing.pickupLocation(),
                foodListing.created(),
                foodListing.donor().name(),
                foodListing.currentState(),
                foodListing.base64Images()
        );
    }
}
