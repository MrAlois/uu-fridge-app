package cz.asen.unicorn.fridge.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.asen.unicorn.fridge.domain.enums.Allergen;
import cz.asen.unicorn.fridge.domain.enums.ClaimState;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Endpoint
@AnonymousAllowed
public class EnumerationEndpoint {
    @Nonnull
    public Set<@Nonnull Allergen> getAllergens(){
        return Arrays.stream(Allergen.values())
                .collect(Collectors.toSet());
    }

    @Nonnull
    public Set<@Nonnull ClaimState> getClaimStates(){
        return Arrays.stream(ClaimState.values())
                .collect(Collectors.toSet());
    }
}
