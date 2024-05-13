package cz.asen.fridge.persistence.mapper;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class DomainFoodListingPhotoMapper {
    public static List<FoodListingPhotoEntity> fromDomain(FoodListing foodListing){
        final var listingEntity = new FoodListingEntity();
        listingEntity.setId(foodListing.id());

        return foodListing.base64Images().stream()
                .map(imageData -> {
                    final var photoEntity = new FoodListingPhotoEntity();
                    photoEntity.setListing(listingEntity);
                    photoEntity.setData(imageData);

                    return photoEntity;
                })
                .toList();


    }
}
