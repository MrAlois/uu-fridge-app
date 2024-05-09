package cz.asen.fridge.persistence.repository;

import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodListingPhotoRepository extends JpaRepository<FoodListingPhotoEntity, Integer> {
    List<FoodListingPhotoEntity> findByListing_Id(Integer id);
}