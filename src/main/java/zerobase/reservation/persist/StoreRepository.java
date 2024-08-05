package zerobase.reservation.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.reservation.persist.entity.StoreEntity;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    boolean existsByName(String name);

    Optional<StoreEntity> findByName(String name);
}
