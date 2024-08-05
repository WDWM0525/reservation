package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.reservation.exception.NoStoreException;
import zerobase.reservation.model.Store;
import zerobase.reservation.persist.StoreRepository;
import zerobase.reservation.persist.entity.StoreEntity;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    /* **************************************************************************************
     * getAllStore : 모든 상점 조회
     * @param pageable
     * ************************************************************************************** */
    public Page<StoreEntity> getAllStore(Pageable pageable) {
        return this.storeRepository.findAll(pageable);
    }

    /* **************************************************************************************
     * getStore : 특정 상점 조회
     * @param name
     * ************************************************************************************** */
    public StoreEntity getStore(String name) {
        var store = this.storeRepository.findByName(name)
                    .orElseThrow(() -> new NoStoreException());

        return store;
    }

    /* **************************************************************************************
     * insertStore : 상점 정보 추가
     * @param name, location, description
     * ************************************************************************************** */
    public Store insertStore(String name, String location, String description, Long manager_id ) {
        boolean exists = this.storeRepository.existsByName(name);
        if (exists) {
            throw new RuntimeException("already exists name -> " + name);
        }

        Store newStore = new Store();
        newStore.setName(name);
        newStore.setLocation(location);
        newStore.setDescription(description);
        newStore.setManager_id(manager_id);
        this.storeRepository.save(new StoreEntity(newStore));

        return newStore;
    }

    /* **************************************************************************************
     * deleteStore : 상점 정보 삭제
     * @param name
     * ************************************************************************************** */
    public String deleteStore(String name) {
        var store = this.storeRepository.findByName(name)
                                        .orElseThrow(() -> new NoStoreException());

        this.storeRepository.delete(store);

        return store.getName();
    }

    /* **************************************************************************************
     * updateStore : 상점 정보 수정
     * @param updateStore
     * ************************************************************************************** */
    public String updateStore(Store updateStore) {
        var store = this.storeRepository.findByName(updateStore.getName())
                                            .orElseThrow(() -> new NoStoreException());

        store.setLocation(updateStore.getLocation());
        store.setDescription(updateStore.getDescription());
        this.storeRepository.save(store);

        return store.getName();
    }
}
