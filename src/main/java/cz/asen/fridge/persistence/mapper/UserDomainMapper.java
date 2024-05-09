package cz.asen.fridge.persistence.mapper;

import cz.asen.fridge.domain.User;
import cz.asen.fridge.domain.enums.DietaryRestriction;
import cz.asen.fridge.persistence.entity.AppUserEntity;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UserDomainMapper {
    @Contract("_ -> new")
    public static @NotNull User toDomain(@NotNull AppUserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getDefaultLocation(),
                parseDatabaseDietaryRestrictions(userEntity.getDietaryRestrictions()),
                userEntity.getEmail(),
                userEntity.getPhone()
        );
    }

    static @NotNull AppUserEntity fromDomain(@NotNull User user) {
        final AppUserEntity appUserEntity = new AppUserEntity();

        appUserEntity.setId(user.id());
        appUserEntity.setName(user.name());
        appUserEntity.setDefaultLocation(user.defaultLocation());
        appUserEntity.setDietaryRestrictions(parseDomainDietaryRestrictions(user.dietaryRestrictions()));
        appUserEntity.setEmail(user.email());
        appUserEntity.setPhone(user.phone());

        return appUserEntity;
    }

    private static @NotNull Set<DietaryRestriction> parseDatabaseDietaryRestrictions(String dietaryRestrictions) {
        if (dietaryRestrictions == null || dietaryRestrictions.isEmpty())
            return Collections.emptySet();

        final var restrictions = new HashSet<DietaryRestriction>();
        for (String restriction : dietaryRestrictions.split(","))
            restrictions.add(DietaryRestriction.valueOf(restriction.trim()));

        return restrictions;
    }

    private static String parseDomainDietaryRestrictions(Set<DietaryRestriction> dietaryRestrictions) {
        if (dietaryRestrictions == null || dietaryRestrictions.isEmpty())
            return "";

        return dietaryRestrictions.stream()
                .map(DietaryRestriction::name)
                .collect(Collectors.joining(","));
    }
}
