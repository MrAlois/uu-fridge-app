package cz.asen.unicorn.fridge.persistence.repository;

import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodListingRepository extends JpaRepository<FoodListingEntity, Integer>, JpaSpecificationExecutor<FoodListingEntity> {
    List<FoodListingEntity> findByShortDescriptionContainsIgnoreCaseOrPickupLocationContainsIgnoreCase(String shortDescription, String location);

    @Query("SELECT fl FROM FoodListingEntity fl WHERE fl.donor.userId = :ownerId OR fl.claimer.userId = :ownerId OR fl.state = 'UNCLAIMED'")
    List<FoodListingEntity> findAllRelevantListingsByOwner(Integer ownerId);
}