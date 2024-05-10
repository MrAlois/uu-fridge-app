package cz.asen.fridge.service;

import cz.asen.fridge.domain.User;
import cz.asen.fridge.persistence.mapper.DomainUserMapper;
import cz.asen.fridge.persistence.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final AppUserRepository appUserRepository;

    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Optional<User> findById(Integer id) {
        return appUserRepository.findById(id)
                .map(DomainUserMapper::toDomain);
    }

    public Optional<User> findByName(String name) {
        return appUserRepository.findByNameIgnoreCase(name)
                .map(DomainUserMapper::toDomain);
    }
}
