package cz.asen.fridge.persistence.repository;

import cz.asen.fridge.persistence.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Integer> {
    Optional<AppUserEntity> findByNameIgnoreCase(String name);
}