package zerobase.reservation.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.Store;
import zerobase.reservation.persist.entity.MemberEntity;
import zerobase.reservation.persist.entity.StoreEntity;
import zerobase.reservation.service.StoreService;

@RestController
@RequestMapping("/store")
@AllArgsConstructor
public class StoreController {
    private StoreService storeService;

    /* **************************************************************************************
     * GET /store
     * searchStore : 상점 리스트 조회
     * @param pageable
     * ************************************************************************************** */
    @GetMapping
    public ResponseEntity<?> searchStoreList(final Pageable pageable) {
        Page<StoreEntity> storeList = this.storeService.getAllStore(pageable);
        return ResponseEntity.ok(storeList);
    }

    /* **************************************************************************************
     * GET /store/{name}
     * searchStore : 상점 조회
     * @param name
     * ************************************************************************************** */
    @GetMapping("/{name}")
    public ResponseEntity<?> searchStore(@PathVariable String name) {
        if (ObjectUtils.isEmpty(name)) {
            throw new RuntimeException("name is empty");
        }

        StoreEntity store = this.storeService.getStore(name);

        return ResponseEntity.ok(store);
    }

    /* **************************************************************************************
     * Post /store
     * insertStore : 상점 정보 추가
     * @param requset, member
     * ************************************************************************************** */
    @PostMapping
    public ResponseEntity<?> insertStore(  @RequestBody Store request
                                         , @AuthenticationPrincipal MemberEntity member) {
        String name = request.getName();
        if (ObjectUtils.isEmpty(name)) {
            throw new RuntimeException("name is empty");
        }

        String location = request.getLocation();
        if (ObjectUtils.isEmpty(location)) {
            throw new RuntimeException("location is empty");
        }

        Long manager_id = member.getId();
        System.out.println("manager_id >>> " + manager_id);

        Store store = this.storeService.insertStore(name, location, request.getDescription(), manager_id);
        return ResponseEntity.ok(store);
    }

    /* **************************************************************************************
     * Delete /store/{name}
     * deleteStore : 상점 정보 삭제
     * @param name
     * ************************************************************************************** */
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteStore(@PathVariable String name) {
        String storeName = this.storeService.deleteStore(name);
        return ResponseEntity.ok(storeName + " 삭제 성공");
    }

    /* **************************************************************************************
     * Put /store/update
     * updateStore : 상점 정보 수정
     * @param request
     * ************************************************************************************** */
    @PutMapping("/update")
    public ResponseEntity<?> updateStore(@RequestBody Store request) {
        String name = request.getName();
        if (ObjectUtils.isEmpty(name)) {
            throw new RuntimeException("name is empty");
        }

        String location = request.getLocation();
        if (ObjectUtils.isEmpty(location)) {
            throw new RuntimeException("location is empty");
        }

        String storeName = this.storeService.updateStore(request);
        return ResponseEntity.ok(storeName + " 수정 성공");
    }
}
