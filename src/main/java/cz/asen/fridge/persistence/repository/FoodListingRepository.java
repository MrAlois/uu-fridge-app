package cz.asen.fridge.persistence.repository;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodListingRepository extends JpaRepository<FoodListingEntity, Integer> {
    List<FoodListingEntity> findByShortDescriptionContainsOrPickupLocationContainsIgnoreCase(String shortDescription, String location);
}