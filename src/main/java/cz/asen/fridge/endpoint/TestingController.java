package cz.asen.fridge.endpoint;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.service.FoodListingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mgmt")
public class TestingController {
    private final FoodListingService foodListingService;

    public TestingController(FoodListingService foodListingService) {
        this.foodListingService = foodListingService;
    }

    @GetMapping("/listings")
    public List<FoodListing> getListings() {
        return foodListingService.getAllFoodListings();
    }
}
