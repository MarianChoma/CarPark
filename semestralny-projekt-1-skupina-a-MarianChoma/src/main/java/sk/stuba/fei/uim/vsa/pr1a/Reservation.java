package sk.stuba.fei.uim.vsa.pr1a;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private ParkingPlace parkingSpot;

    @OneToOne
    private Car car;

    private double price=0;
    private Date beginOfReservation;
    private Date endOfReservation;
    private boolean usingCoupon=false;

    public Reservation() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ParkingPlace getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingPlace parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getBeginOfReservation() {
        return beginOfReservation;
    }

    public void setBeginOfReservation(Date beginOfReservation) {
        this.beginOfReservation = beginOfReservation;
    }

    public Date getEndOfReservation() {
        return endOfReservation;
    }

    public void setEndOfReservation(Date endOfReservation) {
        this.endOfReservation = endOfReservation;
    }

    public boolean isUsingCoupon() {
        return usingCoupon;
    }

    public void setUsingCoupon(boolean usingCoupon) {
        this.usingCoupon = usingCoupon;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", parkingSpot=" + parkingSpot +
                ", car=" + car +
                ", price=" + price +
                ", beginOfReservation=" + beginOfReservation +
                ", endOfReservation=" + endOfReservation +
                '}';
    }
}
