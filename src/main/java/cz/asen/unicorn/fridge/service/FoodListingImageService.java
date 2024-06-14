package cz.asen.unicorn.fridge.service;

import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingPhotoEntity;
import cz.asen.unicorn.fridge.persistence.repository.FoodListingPhotoRepository;
import cz.asen.unicorn.fridge.utils.ImageCompressionUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class FoodListingImageService {
    private static final float BASE64_COMPRESSION_RATIO = 0.75F;
    private static final long SIZE_THRESHOLD_FOR_COMPRESSION = (long) (350_000L * BASE64_COMPRESSION_RATIO);
    private static final long IDEAL_SIZE_FOR_IMAGE = (long) (120_000L * BASE64_COMPRESSION_RATIO);
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
    public void saveAllBase64lImages(Integer listingId, @NotNull List<String> base64Images){
        final var foodListing = new FoodListingEntity();
        foodListing.setListingId(listingId);

        final var imageEntities = base64Images.stream()
                .map(imageBase64 -> saveImage(foodListing, imageBase64))
                .toList();

        foodListingPhotoRepository.saveAll(imageEntities);
    }

    private @NotNull FoodListingPhotoEntity saveImage(FoodListingEntity listingEntity, @NotNull String base64Image){
        final var foodListingPhoto = new FoodListingPhotoEntity();

        // Compress the image, if it's too large
        if(base64Image.length() >= SIZE_THRESHOLD_FOR_COMPRESSION){
            final var idealRatio = IDEAL_SIZE_FOR_IMAGE / base64Image.length();
            final var compressedBytes = ImageCompressionUtils.compressImage(base64Image, idealRatio);
            final var compressedBase64Image = Base64.getEncoder().encodeToString(compressedBytes);

            log.warn("Image too large [{}], compressed to [{}] with ratio of {}.", base64Image.length(), compressedBase64Image.length(), idealRatio);

            base64Image = compressedBase64Image;
        }

        foodListingPhoto.setListing(listingEntity);
        foodListingPhoto.setData(base64Image);

        return foodListingPhotoRepository.save(foodListingPhoto);
    }
}
