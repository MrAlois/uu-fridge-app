package cz.asen.fridge.domain;

import cz.asen.fridge.domain.enums.DietaryRestriction;
import cz.asen.fridge.persistence.entity.AppUserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link AppUserEntity}
 */
public record User(
        Integer id,

        @NotNull
        @Size(max = 64)
        String name,

        @Size(max = 64)
        String defaultLocation,

        Set<DietaryRestriction> dietaryRestrictions,

        @NotNull
        @Size(max = 64)
        @Email
        String email,

        @Size(max = 64)
        String phone
) implements Serializable {
}