package sk.stuba.fei.uim.vsa.pr1a;

import javax.persistence.*;

@Entity(name = "CAR")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String color;

    @Column(unique = true, nullable = false)
    private String vehicleRegistrationPlate;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Customer user;

    public Car() {
    }

    public Car(Long id, String brand, String model, String color, String vehicleRegistrationPlate) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    public Car(String brand, String model, String color, String vehicleRegistrationPlate) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVehicleRegistrationPlate() {
        return vehicleRegistrationPlate;
    }

    public void setVehicleRegistrationPlate(String vehicleRegistrationPlate) {
        this.vehicleRegistrationPlate = vehicleRegistrationPlate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", vehicleRegistrationPlate='" + vehicleRegistrationPlate + '\'' +
                ", user=" + user +
                '}';
    }
}
