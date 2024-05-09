package cz.asen.fridge.persistence.repository;

import cz.asen.fridge.persistence.entity.FoodListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodListingRepository extends JpaRepository<FoodListingEntity, Integer> {
}