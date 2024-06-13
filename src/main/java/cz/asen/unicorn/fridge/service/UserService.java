package cz.asen.unicorn.fridge.service;

import cz.asen.unicorn.fridge.domain.User;
import cz.asen.unicorn.fridge.persistence.mapper.DomainUserMapper;
import cz.asen.unicorn.fridge.persistence.repository.AppUserRepository;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    public User findByIdOrThrow(Integer id) throws NoSuchElementException {
        return this.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public List<User> findAll() {
        return appUserRepository.findAll().stream()
                .map(DomainUserMapper::toDomain)
                .toList();
    }

    public Optional<User> findByName(String name) {
        return appUserRepository.findByNameIgnoreCase(name)
                .map(DomainUserMapper::toDomain);
    }

    public User saveUser(User user) throws NoSuchElementException {
        val entity = DomainUserMapper.fromDomain(user);

        return Optional.of(appUserRepository.save(entity))
                .map(DomainUserMapper::toDomain)
                .orElseThrow(() -> new NoSuchElementException("User doesn't exist found"));
    }
}
