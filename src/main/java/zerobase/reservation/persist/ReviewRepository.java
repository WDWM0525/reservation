package zerobase.reservation.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.persist.entity.ReviewEntity;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByIdAndUserId(Long id, Long userId);

    Optional<ReviewEntity> findById(Long id);
    Optional<ReviewEntity> findByIdAndUserId(Long id, Long userId);
    Page<ReviewEntity> findByStoreId(Long storeId, Pageable pageable);
    Page<ReviewEntity> findByUserId(Long userId, Pageable pageable);
}
