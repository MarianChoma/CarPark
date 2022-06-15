package sk.stuba.fei.uim.vsa.pr1a;


import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;

@Entity(name = "PARKING_SPOT")
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"CARPARK_ID", "SPOTIDENTIFIER"})
})
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String spotIdentifier;

    private boolean available = true;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Floor floorIdentifier;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ParkingHouse carPark;



    public ParkingPlace() {
    }


    public ParkingPlace(Long id, String spotIdentifier, Floor floorIdentifier, ParkingHouse carPark) {
        this.id = id;
        this.spotIdentifier = spotIdentifier;
        this.floorIdentifier = floorIdentifier;
        this.carPark = carPark;
    }

    public ParkingPlace(Long id, String spotIdentifier) {
        this.id = id;
        this.spotIdentifier = spotIdentifier;
    }

    public ParkingPlace(Long id, String spotIdentifier, boolean available) {
        this.id = id;
        this.spotIdentifier = spotIdentifier;
        this.available = available;
    }

    public ParkingPlace(String spotIdentifier, Floor floorIdentifier, ParkingHouse carPark) {
        this.spotIdentifier = spotIdentifier;
        this.floorIdentifier = floorIdentifier;
        this.carPark = carPark;
    }

    public ParkingHouse getCarPark() {
        return carPark;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCarPark(ParkingHouse carPark) {
        this.carPark = carPark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Floor getFloorIdentifier() {
        return floorIdentifier;
    }

    public void setFloorIdentifier(Floor floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    public String getSpotIdentifier() {
        return spotIdentifier;
    }

    public void setSpotIdentifier(String spotIdentifier) {
        this.spotIdentifier = spotIdentifier;
    }

    @Override
    public String toString() {
        return "ParkingPlace{" +
                "id=" + id +
                ", spotIdentifier='" + spotIdentifier + '\'' +
                ", available=" + available +
                '}';
    }
}
