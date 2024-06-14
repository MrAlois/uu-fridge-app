package cz.asen.unicorn.fridge.persistence.repository;

import cz.asen.unicorn.fridge.persistence.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Integer>, JpaSpecificationExecutor<AppUserEntity> {
    Optional<AppUserEntity> findByNameIgnoreCase(String name);
}