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
@Table(name = "FOOD_LISTING_CLAIM")
public class FoodListingClaimEntity {
    @Id
    @SequenceGenerator(name = "generator", initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @Column(name = "CLAIM_ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "USER_ID", nullable = false)
    private AppUserEntity user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "LISTING_ID", nullable = false)
    private FoodListingEntity listing;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CLAIMED", nullable = false)
    private Instant claimed;

    @Size(max = 32)
    @NotNull
    @ColumnDefault("'WAITING'")
    @Column(name = "STATE", nullable = false, length = 32)
    private String state;

}