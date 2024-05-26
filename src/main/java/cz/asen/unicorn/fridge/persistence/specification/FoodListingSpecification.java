package cz.asen.unicorn.fridge.persistence.specification;

import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.domain.enums.Allergen;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Set;

public class FoodListingSpecification {

    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> nameContains(String name) {
        return (listing, cq, cb) -> cb.like(cb.lower(listing.get("name")), "%" + name.toLowerCase() + "%");
    }

    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> upToExpiryDate(LocalDate expiryDate) {
        return (root, query, cb) -> expiryDate == null
                ? cb.isTrue(cb.literal(true))  // always true = no filtering
                : cb.lessThanOrEqualTo(root.get("expiryDate"), expiryDate);
    }

    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> hasState(ClaimState state) {
        return (listing, cq, cb) -> state == null
                ? cb.conjunction()
                : cb.equal(listing.get("state"), state);
    }

    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> ownedBy(User user) {
        return (listing, cq, cb) -> user == null
                ? cb.conjunction()
                : cb.equal(listing.get("user"), user);
    }


    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> hasAllergens(Set<Allergen> allergens) {
        return (root, query, cb) -> {
            if (allergens == null || allergens.isEmpty()) {
                return cb.isTrue(cb.literal(true)); // always true = no filtering
            } else {
                CriteriaBuilder.In<String> inClause = cb.in(root.get("allergen"));
                for (Allergen allergen : allergens) {
                    inClause.value(allergen.name());
                }
                return inClause;
            }
        };
    }
}