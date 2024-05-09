package cz.asen.fridge.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.service.FoodListingService;
import dev.hilla.Endpoint;

import java.util.List;

@Endpoint
@AnonymousAllowed //FIXME Remove after security impl
public class FoodListingEndpoint {
    private final FoodListingService foodListingService;

    public FoodListingEndpoint(FoodListingService foodListingService) {
        this.foodListingService = foodListingService;
    }

    public List<FoodListing> getAllListings(){
        return foodListingService.getAllFoodListings();
    }
}
