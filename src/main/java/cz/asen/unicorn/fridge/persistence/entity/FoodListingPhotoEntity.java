package cz.asen.unicorn.fridge.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "FOOD_LISTING_PHOTO")
public class FoodListingPhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    @SequenceGenerator(name = "idgenerator", initialValue = 10)
    @Column(name = "PHOTO_ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "LISTING_ID", nullable = false)
    private FoodListingEntity listing;

    @Lob
    @Column(name = "DATA", columnDefinition = "BINARY LARGE OBJECT not null")
    private String data;
}