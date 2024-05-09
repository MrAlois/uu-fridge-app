package cz.asen.fridge.service;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.persistence.entity.FoodListingClaimEntity;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import cz.asen.fridge.persistence.mapper.FoodListingDomainMapper;
import cz.asen.fridge.persistence.repository.FoodListingClaimRepository;
import cz.asen.fridge.persistence.repository.FoodListingPhotoRepository;
import cz.asen.fridge.persistence.repository.FoodListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodListingService {
    private final FoodListingRepository foodListingRepository;
    private final FoodListingClaimRepository foodListingClaimRepository;
    private final FoodListingPhotoRepository foodListingPhotoRepository;

    public FoodListingService(FoodListingRepository foodListingRepository, FoodListingClaimRepository foodListingClaimRepository, FoodListingPhotoRepository foodListingPhotoRepository) {
        this.foodListingRepository = foodListingRepository;
        this.foodListingClaimRepository = foodListingClaimRepository;
        this.foodListingPhotoRepository = foodListingPhotoRepository;
    }

    public List<FoodListing> getAllFoodListings() {
        return foodListingRepository.findAll().stream()
                .map(foodListing -> {
                    final var foodListingClaimEntity = foodListingClaimRepository.findById(foodListing.getId()).orElse(null);
                    final var foodListingPhotoEntities = foodListingPhotoRepository.findByListing_Id(foodListing.getId());
                    return FoodListingDomainMapper.toDomain(foodListing, foodListingClaimEntity, foodListingPhotoEntities);
                })
                .toList();
    }
}
