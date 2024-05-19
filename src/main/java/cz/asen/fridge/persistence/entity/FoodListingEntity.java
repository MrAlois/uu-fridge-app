package cz.asen.fridge.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "FOOD_LISTING")
public class FoodListingEntity {
    @Id
    @SequenceGenerator(name = "generator", initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @Column(name = "LISTING_ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "DONOR_ID", nullable = false)
    private AppUserEntity donor;

    @Size(max = 64)
    @NotNull
    @Column(name = "SHORT_DESCRIPTION", nullable = false, length = 64)
    private String shortDescription;

    @Size(max = 256)
    @NotNull
    @Column(name = "DESCRIPTION", nullable = false, length = 512)
    private String description;

    @NotNull
    @Column(name = "EXPIRY_DATE", nullable = false)
    private Instant expiryDate;

    @Size(max = 128)
    @NotNull
    @Column(name = "PICKUP_LOCATION", nullable = false, length = 128)
    private String pickupLocation;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED", nullable = false)
    private Instant created;

    @Size(max = 64)
    @Column(name = "ALLERGENS", length = 64)
    private String allergens;
}