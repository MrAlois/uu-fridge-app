package cz.asen.unicorn.fridge.persistence.mapper;

import cz.asen.unicorn.fridge.domain.enums.Allergens;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class DomainAllergenMapper {
    private static final String DIETARY_RESTRICTION_DELIMITER = ",";

    /**
     * Parses a string representation of dietary restrictions into a set of Allergens objects.
     *
     * @param allergens the string representation of dietary restrictions to parse
     * @return the set of Allergens objects parsed from the input string
     */
    public static @NotNull Set<Allergens> parseAllergens(String allergens){
        if(allergens == null || allergens.isBlank()) return Collections.emptySet();
        return Arrays.stream(allergens.split(DIETARY_RESTRICTION_DELIMITER))
                .filter(Objects::nonNull)
                .map(String::strip)
                .map(allergen -> {
                    try {
                        return Allergens.valueOf(allergen);
                    } catch (IllegalStateException e){
                        log.warn("Can't parse Allergen of type '{}'", allergen);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Parses the set of dietary restrictions into a comma-separated string.
     *
     * @param allergens the set of dietary restrictions to parse
     * @return the comma-separated string of dietary restrictions
     */
    @Contract("_ -> new")
    public static @NotNull String parseAllergens(Set<Allergens> allergens){
        if(allergens == null) return "";
        return String.join(DIETARY_RESTRICTION_DELIMITER, allergens.stream().map(Allergens::name).toList());
    }
}
