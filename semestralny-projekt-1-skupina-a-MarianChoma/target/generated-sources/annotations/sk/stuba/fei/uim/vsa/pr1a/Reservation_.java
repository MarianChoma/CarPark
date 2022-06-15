package sk.stuba.fei.uim.vsa.pr1a;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1a.Car;
import sk.stuba.fei.uim.vsa.pr1a.ParkingPlace;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-04-06T23:15:40")
@StaticMetamodel(Reservation.class)
public class Reservation_ { 

    public static volatile SingularAttribute<Reservation, Date> beginOfReservation;
    public static volatile SingularAttribute<Reservation, ParkingPlace> parkingSpot;
    public static volatile SingularAttribute<Reservation, Car> car;
    public static volatile SingularAttribute<Reservation, Boolean> usingCoupon;
    public static volatile SingularAttribute<Reservation, Double> price;
    public static volatile SingularAttribute<Reservation, Date> endOfReservation;
    public static volatile SingularAttribute<Reservation, Long> id;

}