package sk.stuba.fei.uim.vsa.pr1a;


import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "CAR_PARK_FLOOR")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"CARPARKID_ID", "FLOORIDENTIFIER"})
})
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String floorIdentifier;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ParkingHouse carParkId;

    @OneToMany(mappedBy = "floorIdentifier")
    @CascadeOnDelete
    private List<ParkingPlace> places;

    public Floor() {
    }

    public Floor(Long id, String floorIdentifier) {
        this.id = id;
        this.floorIdentifier = floorIdentifier;
    }

    public Floor(Long id, String floorIdentifier, ParkingHouse carParkId, List<ParkingPlace> places) {
        this.id = id;
        this.floorIdentifier = floorIdentifier;
        this.carParkId = carParkId;
        this.places = places;
    }

    public Floor(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorIdentifier() {
        return floorIdentifier;
    }

    public void setFloorIdentifier(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    public ParkingHouse getCarParkId() {
        return carParkId;
    }

    public void setCarParkId(ParkingHouse carParkId) {
        this.carParkId = carParkId;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", floorIdentifier='" + floorIdentifier + '\'' +
                ", carParkId=" + carParkId +
                '}';
    }
}
