package cz.asen.fridge.config;

import cz.asen.fridge.service.FoodListingImageService;
import cz.asen.fridge.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.*;

@Slf4j
@Configuration
public class InitializationConfig {
    private static final Map<Integer, List<String>> RESOURCE_MAP = Map.of(
            1, List.of("food_listing_images/salad_1.png", "food_listing_images/salad_2.png", "food_listing_images/salad_3.png"),
            2, List.of("food_listing_images/bread_1.png", "food_listing_images/bread_2.png", "food_listing_images/bread_3.png"),
            3, List.of("food_listing_images/pie_1.png"),
            4, List.of("food_listing_images/pasta_1.png", "food_listing_images/pasta_2.png"),
            5, List.of("food_listing_images/cookie_1.png", "food_listing_images/cookie_2.png")
    );

    @Bean
    public ApplicationRunner applicationRunner(FoodListingImageService imageService){
        return args -> getBase64ImagesFromResources()
                .forEach((listingId, imageList) -> {
                    log.info("Loaded {} images of listingId=[{}] (~{}B)",
                            imageList.size(),
                            listingId,
                            imageList.stream().map(String::length).reduce(0, Integer::sum)
                    );

                    imageService.saveAllBase64lImages(listingId, imageList);
                });
    }

    private @NotNull Map<Integer, List<String>> getBase64ImagesFromResources() {
        final var base64ImagesMap = new HashMap<Integer, List<String>>();

        RESOURCE_MAP.forEach((listingId, imagePaths) -> {
            final var base64Images = imagePaths.stream()
                    .map(imagePath -> convertResourceToBase64(new ClassPathResource(imagePath)))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            if (!base64Images.isEmpty()) {
                base64ImagesMap.put(listingId, base64Images);
            }
        });

        return base64ImagesMap;
    }

    private static Optional<String> convertResourceToBase64(@NotNull ClassPathResource resource) {
        try {
            final var base64Image = FileUtils.fileToBase64Data(resource.getFile());
            final var format = Objects.requireNonNull(resource.getFilename()).substring(resource.getFilename().lastIndexOf(".") + 1); // extract file extension

            return Optional.of("data:image/" + format + ";base64," + base64Image);
        } catch (IOException e) {
            log.error("Couldn't convert resource into image. {}", resource.getPath());
            return Optional.empty(); // or, log the exception and return empty
        }
    }
}
