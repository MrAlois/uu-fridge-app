package cz.asen.fridge.persistence.repository;

import cz.asen.fridge.persistence.entity.FoodListingClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodListingClaimRepository extends JpaRepository<FoodListingClaimEntity, Integer> {
}