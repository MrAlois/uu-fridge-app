package cz.asen.fridge.persistence.entity;

import cz.asen.fridge.domain.User;
import cz.asen.fridge.domain.enums.DietaryRestriction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "APP_USER")
public class AppUserEntity {
    @Id
    @SequenceGenerator(name = "generator", initialValue = 10)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name = "USER_ID", nullable = false)
    private Integer id;

    @Size(max = 64)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 64)
    private String name;

    @Size(max = 64)
    @Column(name = "DEFAULT_LOCATION", length = 64)
    private String defaultLocation;

    @Size(max = 128)
    @Column(name = "DIETARY_RESTRICTIONS", length = 128)
    private String dietaryRestrictions;

    @Size(max = 64)
    @NotNull
    @Email
    @Column(name = "EMAIL", nullable = false, length = 64)
    private String email;

    @Size(max = 64)
    @Column(name = "PHONE", length = 64)
    private String phone;
}