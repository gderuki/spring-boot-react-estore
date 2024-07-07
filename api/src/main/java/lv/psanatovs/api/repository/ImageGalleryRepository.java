package lv.psanatovs.api.repository;

import lv.psanatovs.api.entity.ImageGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageGalleryRepository extends JpaRepository<ImageGallery, Long> {
    List<ImageGallery> findByProductId(Long productId);
}
