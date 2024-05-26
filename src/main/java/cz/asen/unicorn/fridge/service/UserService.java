package cz.asen.unicorn.fridge.service;

import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.persistence.mapper.DomainUserMapper;
import cz.asen.unicorn.fridge.persistence.repository.AppUserRepository;
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
