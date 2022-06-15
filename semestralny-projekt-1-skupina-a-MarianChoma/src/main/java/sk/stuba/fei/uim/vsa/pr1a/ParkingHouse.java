package sk.stuba.fei.uim.vsa.pr1a;

import org.eclipse.persistence.annotations.CascadeOnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "CAR_PARK")
public class ParkingHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Integer pricePerHour;

    @OneToMany(mappedBy = "carParkId")
    @CascadeOnDelete
    private List<Floor> floors;


    public ParkingHouse() {
    }

    public ParkingHouse(String name, String address, Integer pricePerHour) {
        this.name = name;
        this.address = address;
        this.pricePerHour = pricePerHour;
    }

    public ParkingHouse(Long id, String name, String address, Integer pricePerHour) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.pricePerHour = pricePerHour;
    }

    public ParkingHouse(Long id, String name, String address, Integer pricePerHour, List<Floor> floors) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.pricePerHour = pricePerHour;
        this.floors = floors;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Integer pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public String toString() {
        return "ParkingHouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}
