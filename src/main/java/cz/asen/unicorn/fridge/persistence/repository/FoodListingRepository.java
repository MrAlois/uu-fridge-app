package cz.asen.unicorn.fridge.persistence.repository;

import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodListingRepository extends JpaRepository<FoodListingEntity, Integer> {
    List<FoodListingEntity> findByShortDescriptionContainsIgnoreCaseOrPickupLocationContainsIgnoreCase(String shortDescription, String location);
}