package cz.asen.unicorn.fridge.domain;

import cz.asen.unicorn.fridge.domain.enums.Allergen;
import cz.asen.unicorn.fridge.persistence.entity.AppUserEntity;
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

        @Size(max = 128)
        String defaultLocation,

        Set<Allergen> allergens,

        @NotNull
        @Size(max = 64)
        @Email
        String email,

        @Size(max = 64)
        String phone
) implements Serializable {
}