package cz.asen.fridge.service;

import cz.asen.fridge.persistence.entity.FoodListingEntity;
import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import cz.asen.fridge.persistence.repository.FoodListingPhotoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class FoodListingImageService {
    private final FoodListingPhotoRepository foodListingPhotoRepository;

    public FoodListingImageService(FoodListingPhotoRepository foodListingPhotoRepository) {
        this.foodListingPhotoRepository = foodListingPhotoRepository;
    }

    /**
     * Saves a list of images associated with a food listing.
     *
     * @param listingId The ID of the food listing.
     * @param base64Images The list of base64-encoded images.
     */
    public void saveAllImages(Integer listingId, @NotNull List<String> base64Images){
        final var foodListing = new FoodListingEntity();
        foodListing.setId(listingId);

        final var imageEntities = base64Images.stream()
                .map(imageBase64 -> saveImage(foodListing, imageBase64))
                .toList();

        foodListingPhotoRepository.saveAll(imageEntities);
    }

    private @NotNull FoodListingPhotoEntity saveImage(FoodListingEntity listingEntity, @NotNull String base64Image){
        final var foodListingPhoto = new FoodListingPhotoEntity();
        foodListingPhoto.setListing(listingEntity);
        foodListingPhoto.setData(base64Image);

        return foodListingPhotoRepository.save(foodListingPhoto);
    }
}
