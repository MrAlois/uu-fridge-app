package cz.asen.fridge.endpoint;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import cz.asen.fridge.persistence.repository.FoodListingPhotoRepository;
import cz.asen.fridge.service.FoodListingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mgmt")
public class TestingController {
    private final FoodListingService foodListingService;
    private final FoodListingPhotoRepository foodListingPhotoRepository;

    public TestingController(FoodListingService foodListingService, FoodListingPhotoRepository foodListingPhotoRepository) {
        this.foodListingService = foodListingService;
        this.foodListingPhotoRepository = foodListingPhotoRepository;
    }

    @GetMapping("/listings")
    public List<FoodListing> getListings() {
        return foodListingService.getAllFoodListings();
    }

    @GetMapping("/images")
    public List<FoodListingPhotoEntity> getPhotos(){
        return foodListingPhotoRepository.findAll();
    }
}
