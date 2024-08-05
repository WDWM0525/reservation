package zerobase.reservation.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.persist.entity.ReservationEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    boolean existsByStoreIdAndDate(Long storeId, LocalDateTime date);
    boolean existsByIdAndUserId(Long id, Long userId);
    boolean existsByIdAndVisitYn(Long id, String visitYn);

    ReservationEntity findByStoreId(Long storeId);
    Page<ReservationEntity> findByUserId(Long userId, Pageable pageable);
    Optional<ReservationEntity> findByUserId(Long userId);
}
