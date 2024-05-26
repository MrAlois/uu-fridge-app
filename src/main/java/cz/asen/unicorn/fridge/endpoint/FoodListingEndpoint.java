package cz.asen.unicorn.fridge.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.asen.unicorn.fridge.domain.FoodListing;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import cz.asen.unicorn.fridge.domain.enums.Distance;
import cz.asen.unicorn.fridge.endpoint.request.CreateListingRequest;
import cz.asen.unicorn.fridge.service.FoodListingService;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class FoodListingEndpoint {
    private final FoodListingService foodListingService;

    public FoodListingEndpoint(FoodListingService foodListingService) {
        this.foodListingService = foodListingService;
    }

    @Nonnull
    public List<FoodListing> getAllListings(){
        return foodListingService.getAllFoodListings();
    }

    @Nonnull
    public FoodListing getListing(int listingId) {
        return foodListingService.getListingById(listingId);
    }

    @Nonnull
    public List<FoodListing> searchListings(@Nonnull String name, @Nullable Distance distance){
        return foodListingService.searchForListings(name);
    }

    @Nonnull
    public FoodListing createListing(@Nonnull @NotNull CreateListingRequest request){
        return updateListing(request, null);
    }

    @Nonnull
    public FoodListing updateListing(@Nonnull @NotNull CreateListingRequest request, Integer listingId){
        val foodListing = new FoodListing(
                listingId,
                request.donor(),
                request.shortDescription(),
                request.description(),
                request.expiryDate().atStartOfDay(),
                request.pickupLocation(),
                LocalDateTime.now(), // Set created to now
                request.allergens(),
                ClaimState.UNCLAIMED,
                Optional.empty(),
                Optional.empty(),
                request.base64Images()
        );

        return foodListingService.saveListing(foodListing);
    }

    public void removeListing(@Nonnull @NotNull Integer listingId){
        foodListingService.deleteListingById(listingId);
    }
}
