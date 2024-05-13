package cz.asen.fridge.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.domain.enums.Allergens;
import cz.asen.fridge.domain.enums.ClaimState;
import cz.asen.fridge.endpoint.request.CreateListingRequest;
import cz.asen.fridge.service.FoodListingService;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Endpoint
@AnonymousAllowed //FIXME Remove after security impl
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
    public Set<@Nonnull Allergens> getAllergens(){
        return Arrays.stream(Allergens.values())
                .collect(Collectors.toSet());
    }

    @Nonnull
    public List<FoodListing> searchListings(@Nonnull String query){
        return foodListingService.searchForListings(query);
    }

    public void createListing(@Nonnull @NotNull CreateListingRequest request){
        val foodListing = new FoodListing(
                null,
                request.donor(),
                request.shortDescription(),
                request.description(),
                request.quantity(),
                request.expiryDate() != null ? request.expiryDate() .atStartOfDay() : LocalDateTime.now(),
                request.pickupLocation(),
                LocalDateTime.now(), // Set created to now
                request.allergens(),
                ClaimState.UNCLAIMED,
                Optional.empty(),
                Optional.empty(),
                request.base64Images()
        );

        foodListingService.createNewListing(foodListing);
    }
}
