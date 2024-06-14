package cz.asen.unicorn.fridge.service;

import cz.asen.unicorn.fridge.domain.FoodListing;
import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.domain.enums.Allergen;
import cz.asen.unicorn.fridge.domain.enums.DistanceType;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingPhotoEntity;
import cz.asen.unicorn.fridge.persistence.mapper.DomainFoodListingMapper;
import cz.asen.unicorn.fridge.persistence.mapper.DomainFoodListingPhotoMapper;
import cz.asen.unicorn.fridge.persistence.repository.FoodListingPhotoRepository;
import cz.asen.unicorn.fridge.persistence.repository.FoodListingRepository;
import cz.asen.unicorn.fridge.persistence.specification.FoodListingSpecification;
import cz.asen.unicorn.fridge.utils.GeoUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static cz.asen.unicorn.fridge.domain.enums.ClaimState.CLAIMED;

@Slf4j
@Service
public class FoodListingService {
    private final FoodListingRepository foodListingRepository;
    private final FoodListingPhotoRepository foodListingPhotoRepository;

    public FoodListingService(FoodListingRepository foodListingRepository, FoodListingPhotoRepository foodListingPhotoRepository) {
        this.foodListingRepository = foodListingRepository;
        this.foodListingPhotoRepository = foodListingPhotoRepository;
    }

    public Optional<FoodListing> findListingById(int listingId) {
        return foodListingRepository.findById(listingId)
                .map(this::fillImagesFromRepository);
    }

    public FoodListing findListingByIdOrThrow(int listingId) throws NoSuchElementException {
        return findListingById(listingId)
                .orElseThrow(() -> new NoSuchElementException("Listing of id " + listingId + " not found"));
    }

    public List<FoodListing> findAllFoodListings() {
        return foodListingRepository.findAll().stream()
                .map(this::fillImagesFromRepository)
                .toList();
    }

    /**
     * Creates a new FoodListing and saves it to the database.
     *
     * @param foodListing The FoodListing to be created and saved
     * @return The created FoodListing
     */
    public FoodListing saveListing(FoodListing foodListing){
        val entity = DomainFoodListingMapper.fromDomain(foodListing);

        return Optional.of(foodListingRepository.save(entity))
                .map(savedEntity -> {
                    final var mappedFoodListing = DomainFoodListingMapper.toDomain(savedEntity, foodListing.base64Images());
                    final var mappedFoodPhoto = DomainFoodListingPhotoMapper.fromDomain(mappedFoodListing);
                    foodListingPhotoRepository.saveAll(mappedFoodPhoto);
                    return mappedFoodListing;
                })
                .orElseThrow();
    }

    public void deleteListingById(int listingId) {
        foodListingRepository.deleteById(listingId);
    }

    /**
     * Retrieves a list of all FoodListings related to the specified owner based on certain criteria. Ordered by ownership and state.
     *
     * @param owner         The User object representing the owner.
     * @param namePattern   Optional parameter specifying a name pattern to filter the FoodListings by.
     * @param upToExpiryDate   Optional parameter specifying an expiry date to filter the FoodListings by.
     * @param allergens     Optional set of allergens to filter the FoodListings by.
     * @param distanceType  Optional parameter specifying a distance type to filter the FoodListings by location.
     * @return A list of FoodListings related to the owner, filtered by the given criteria.
     */
    public List<FoodListing> findAllListingsRelatedToOwner(
            @NotNull User owner,
            @Nullable String namePattern,
            @Nullable LocalDate upToExpiryDate,
            @Nullable Set<Allergen> allergens,
            @Nullable DistanceType distanceType
    ) {
        final var allListings = foodListingRepository.findAll(
                Specification.where(
                        FoodListingSpecification.ownedOrClaimedByOrUnclaimed(owner)
                                .and(FoodListingSpecification.nameContains(namePattern))
                                .and(FoodListingSpecification.upToExpiryDate(upToExpiryDate))
                                .and(FoodListingSpecification.hasAllergens(allergens))
                )
        );

        return allListings.stream()
                .filter(listing -> distanceType == null || GeoUtils.withinRadius(listing.getPickupLatitude(), listing.getPickupLongitude(), listing.getPickupLatitude(), listing.getPickupLongitude(), distanceType.getValueInMeter()))
                .sorted(((o1, o2) -> sortListingsBasedOnPriority(o1, o2, owner)))
                .map(this::fillImagesFromRepository)
                .toList();
    }

    /**
     * Fills the images with a FoodListing from the repository.
     *
     * @param foodListing The FoodListingEntity to fill the images from.
     * @return The FoodListing with the images filled.
     */
    private @NotNull FoodListing fillImagesFromRepository(@NotNull FoodListingEntity foodListing){
        final var foodListingPhotoEntities = foodListingPhotoRepository.findByListing_ListingId(foodListing.getListingId()).stream()
                .map(FoodListingPhotoEntity::getData)
                .toList();

        return DomainFoodListingMapper.toDomain(foodListing, foodListingPhotoEntities);
    }

    /**
     * Compares two FoodListingEntity objects based on priority.
     * <p>
     * Priority is determined by the following criteria:
     * - Listings belonging to the owner have a higher priority.
     * - Among the owner's listings, claimed listings have a higher priority.
     *
     * @param o1    The first FoodListingEntity object.
     * @param o2    The second FoodListingEntity object.
     * @param owner The User object representing the owner.
     * @return -1 if o1 has a higher priority than o2.
     * 0 if both listings have the same priority.
     * 1 if o2 has a higher priority than o1.
     */
    private static int sortListingsBasedOnPriority(@NotNull FoodListingEntity o1, FoodListingEntity o2, @NotNull User owner) {

        // Compare owner's listings
        boolean isFirstListingOwner = o1.getDonor().getUserId().equals(owner.id());
        boolean isSecondListingOwner = o2.getDonor().getUserId().equals(owner.id());

        if (isFirstListingOwner && !isSecondListingOwner) return -1;
        if (!isFirstListingOwner && isSecondListingOwner) return 1;

        // If both listings belong to the owner or both don't belong to the owner,
        // compare the claim status.
        boolean isFirstListingClaimed = o1.getState().equals(CLAIMED.name());
        boolean isSecondListingClaimed = o2.getState().equals(CLAIMED.name());

        if (isFirstListingClaimed && !isSecondListingClaimed) return -1;
        if (!isFirstListingClaimed && isSecondListingClaimed) return 1;

        // If no prioritization could be determined, consider both listings as having equal priority.
        return 0;
    }
}
