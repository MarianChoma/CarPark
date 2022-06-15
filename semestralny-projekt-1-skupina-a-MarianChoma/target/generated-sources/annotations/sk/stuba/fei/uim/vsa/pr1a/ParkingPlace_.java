package sk.stuba.fei.uim.vsa.pr1a;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1a.Floor;
import sk.stuba.fei.uim.vsa.pr1a.ParkingHouse;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-04-06T23:15:40")
@StaticMetamodel(ParkingPlace.class)
public class ParkingPlace_ { 

    public static volatile SingularAttribute<ParkingPlace, Floor> floorIdentifier;
    public static volatile SingularAttribute<ParkingPlace, ParkingHouse> carPark;
    public static volatile SingularAttribute<ParkingPlace, Boolean> available;
    public static volatile SingularAttribute<ParkingPlace, Long> id;
    public static volatile SingularAttribute<ParkingPlace, String> spotIdentifier;

}