package cz.asen.unicorn.fridge.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "APP_USER")
public class AppUserEntity {
    @Id
    @SequenceGenerator(name = "generator", initialValue = 10)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Size(max = 64)
    @NotNull
    @Column(name = "NAME", nullable = false, length = 64)
    private String name;

    @Size(max = 128)
    @Column(name = "DEFAULT_LOCATION", length = 64)
    private String defaultLocation;

    @Column(name = "DEFAULT_LAT")
    private Double defaultLatitude;

    @Column(name = "DEFAULT_LNG")
    private Double defaultLongitude;

    @Size(max = 64)
    @Column(name = "ALLERGIC_TO", length = 128)
    private String allergicTo;

    @Size(max = 64)
    @NotNull
    @Email
    @Column(name = "EMAIL", nullable = false, length = 64)
    private String email;

    @Size(max = 64)
    @Column(name = "PHONE", length = 64)
    private String phone;
}