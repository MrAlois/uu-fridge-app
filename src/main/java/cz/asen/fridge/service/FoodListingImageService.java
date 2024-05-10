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
    private static final String BASE64_IMAGE_HEADER_REGEX = "^data:image/\\w+;base64,";
    private final FoodListingPhotoRepository foodListingPhotoRepository;

    public FoodListingImageService(FoodListingPhotoRepository foodListingPhotoRepository) {
        this.foodListingPhotoRepository = foodListingPhotoRepository;
    }

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
