package cz.asen.unicorn.fridge.persistence.repository;

import cz.asen.unicorn.fridge.persistence.entity.FoodListingPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FoodListingPhotoRepository extends JpaRepository<FoodListingPhotoEntity, Integer>, JpaSpecificationExecutor<FoodListingPhotoEntity> {
    List<FoodListingPhotoEntity> findByListing_ListingId(Integer id);
}