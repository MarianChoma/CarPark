package sk.stuba.fei.uim.vsa.pr1a;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1a.Floor;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-04-06T23:15:40")
@StaticMetamodel(ParkingHouse.class)
public class ParkingHouse_ { 

    public static volatile SingularAttribute<ParkingHouse, String> address;
    public static volatile ListAttribute<ParkingHouse, Floor> floors;
    public static volatile SingularAttribute<ParkingHouse, String> name;
    public static volatile SingularAttribute<ParkingHouse, Integer> pricePerHour;
    public static volatile SingularAttribute<ParkingHouse, Long> id;

}