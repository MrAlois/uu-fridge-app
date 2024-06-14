package cz.asen.unicorn.fridge.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.service.UserService;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;

import java.util.List;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {
    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @Nonnull
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @Nonnull
    public User getUserById(Integer id){
        return userService.findByIdOrThrow(id);
    }

    @Nonnull
    public User updateUser(User user) {
        if(user == null || user.id() == null)
            throw new IllegalArgumentException("To update, UserId cannot be empty!");

        return userService.saveUser(user);
    }
}
