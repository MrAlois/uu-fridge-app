package cz.asen.unicorn.fridge.persistence.repository;

import cz.asen.unicorn.fridge.persistence.entity.FoodListingClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodListingClaimRepository extends JpaRepository<FoodListingClaimEntity, Integer> {
}