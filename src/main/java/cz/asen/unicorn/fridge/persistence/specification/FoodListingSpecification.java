package cz.asen.unicorn.fridge.persistence.specification;

import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.domain.enums.Allergen;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingClaimEntity;
import cz.asen.unicorn.fridge.persistence.entity.FoodListingEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Set;

@UtilityClass
public class FoodListingSpecification {

    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> nameContains(String name) {
        return (listing, cq, cb) -> name == null
            ? cb.conjunction()
            : cb.like(cb.lower(listing.get("shortDescription")), "%" + name.toLowerCase() + "%");
    }

    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> upToExpiryDate(LocalDate expiryDate) {
        return (root, query, cb) -> expiryDate == null
                ? cb.isTrue(cb.literal(true))  // always true = no filtering
                : cb.lessThanOrEqualTo(root.get("expiryDate"), expiryDate);
    }

    @Contract(pure = true)
    public static @NotNull Specification<FoodListingEntity> hasState(Set<ClaimState> states) {
        return (root, query, cb) -> {
            if (states == null || states.isEmpty()) {
                return cb.isTrue(cb.literal(true));
            } else {
                Join<FoodListingEntity, FoodListingClaimEntity> join = root.join("claims", JoinType.LEFT);
                CriteriaBuilder.In<Object> inClause = cb.in(cb.coalesce(join.get("state"), ClaimState.UNCLAIMED.name()));
                for (ClaimState state : states) {
                    inClause.value(state.toString());
                }
                return inClause;
            }
        };
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