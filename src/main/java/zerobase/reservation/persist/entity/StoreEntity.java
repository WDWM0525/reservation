package zerobase.reservation.persist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.reservation.model.Store;

import javax.persistence.*;

@Entity(name = "STORE")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID")
    private Long id;

    @Column(unique = true)
    private String name;        // 상점명

    private String location;    // 상점 위치

    private String description; // 상점 설명

    private Long manager_id;    // 매니저 id

    public StoreEntity(Store store) {
        this.name = store.getName();
        this.location = store.getLocation();
        this.description = store.getDescription();
        this.manager_id = store.getManager_id();
    }

}
