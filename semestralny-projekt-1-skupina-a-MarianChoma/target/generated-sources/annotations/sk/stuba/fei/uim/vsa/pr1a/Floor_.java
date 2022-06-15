package sk.stuba.fei.uim.vsa.pr1a;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1a.ParkingHouse;
import sk.stuba.fei.uim.vsa.pr1a.ParkingPlace;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-04-06T23:15:40")
@StaticMetamodel(Floor.class)
public class Floor_ { 

    public static volatile ListAttribute<Floor, ParkingPlace> places;
    public static volatile SingularAttribute<Floor, String> floorIdentifier;
    public static volatile SingularAttribute<Floor, ParkingHouse> carParkId;
    public static volatile SingularAttribute<Floor, Long> id;

}