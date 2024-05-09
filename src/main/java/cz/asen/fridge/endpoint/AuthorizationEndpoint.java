package cz.asen.fridge.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.asen.fridge.service.UserService;
import dev.hilla.Endpoint;

@Endpoint
@AnonymousAllowed
public class AuthorizationEndpoint {
    private final UserService userService;

    public AuthorizationEndpoint(UserService userService) {
        this.userService = userService;
    }
}
