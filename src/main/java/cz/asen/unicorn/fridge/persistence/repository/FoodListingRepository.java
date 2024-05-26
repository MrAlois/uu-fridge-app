package cz.asen.unicorn.fridge.persistence.repository;

import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FoodListingRepository extends JpaRepository<FoodListingEntity, Integer>, JpaSpecificationExecutor<FoodListingEntity> {
    List<FoodListingEntity> findByShortDescriptionContainsIgnoreCaseOrPickupLocationContainsIgnoreCase(String shortDescription, String location);
}