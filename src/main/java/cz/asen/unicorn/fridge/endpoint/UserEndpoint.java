package cz.asen.unicorn.fridge.endpoint;

import cz.asen.unicorn.fridge.domain.FoodListing;
import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.endpoint.view.UserProfile;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;

import java.util.List;

@Endpoint
public class UserEndpoint {
    @Nonnull
    public User getUserById(Integer id){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Nonnull
    public User updateUser(Integer id, User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Nonnull
    public UserProfile getUserProfileById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Nonnull
    public User updateUserProfile(UserProfile userProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Nonnull
    public List<FoodListing> getClaimedFoodListingsByUserId(Integer userId){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
