package cz.asen.fridge.persistence.mapper;

import cz.asen.fridge.domain.User;
import cz.asen.fridge.persistence.entity.AppUserEntity;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static cz.asen.fridge.persistence.mapper.DomainAllergenMapper.parseAllergens;

@UtilityClass
public class DomainUserMapper {
    @Contract("_ -> new")
    public static @NotNull User toDomain(@NotNull AppUserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getDefaultLocation(),
                parseAllergens(userEntity.getAllergicTo()),
                userEntity.getEmail(),
                userEntity.getPhone()
        );
    }

    static @NotNull AppUserEntity fromDomain(@NotNull User user) {
        final AppUserEntity appUserEntity = new AppUserEntity();

        appUserEntity.setId(user.id());
        appUserEntity.setName(user.name());
        appUserEntity.setDefaultLocation(user.defaultLocation());
        appUserEntity.setAllergicTo(parseAllergens(user.allergens()));
        appUserEntity.setEmail(user.email());
        appUserEntity.setPhone(user.phone());

        return appUserEntity;
    }
}
