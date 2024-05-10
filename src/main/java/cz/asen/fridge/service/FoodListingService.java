package cz.asen.fridge.service;

import cz.asen.fridge.domain.FoodListing;
import cz.asen.fridge.persistence.entity.FoodListingEntity;
import cz.asen.fridge.persistence.entity.FoodListingPhotoEntity;
import cz.asen.fridge.persistence.mapper.DomainFoodListingMapper;
import cz.asen.fridge.persistence.repository.FoodListingClaimRepository;
import cz.asen.fridge.persistence.repository.FoodListingPhotoRepository;
import cz.asen.fridge.persistence.repository.FoodListingRepository;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

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

    public FoodListing getListingById(int listingId) throws NoSuchElementException{
        return foodListingRepository.findById(listingId)
                .map(this::findAndFillListingMetadata)
                .orElseThrow(() -> new NoSuchElementException("Listing of id " + listingId + " not found"));
    }

    public List<FoodListing> getAllFoodListings() {
        return foodListingRepository.findAll().stream()
                .map(this::findAndFillListingMetadata)
                .toList();
    }

    public FoodListing createNewListing(FoodListing foodListing){
        val entity = DomainFoodListingMapper.fromDomain(foodListing, null);

        return Optional.of(foodListingRepository.save(entity))
                .map(DomainFoodListingMapper::toDomain)
                .orElseThrow();
    }

    /**
     * Finds and fills the metadata of a FoodListing based on the given FoodListingEntity.
     *
     * @param foodListing The FoodListingEntity object to retrieve metadata from.
     * @return The filled FoodListing object.
     */
    private @NotNull FoodListing findAndFillListingMetadata(@NotNull FoodListingEntity foodListing){
        final var foodListingClaimEntity = foodListingClaimRepository.findById(foodListing.getId()).orElse(null);
        final var foodListingPhotoEntities = foodListingPhotoRepository.findByListing_Id(foodListing.getId());
        return DomainFoodListingMapper.toDomain(foodListing, foodListingClaimEntity, foodListingPhotoEntities);
    }


}
