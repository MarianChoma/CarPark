package sk.stuba.fei.uim.vsa.pr1a;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1a.Customer;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-04-06T23:15:40")
@StaticMetamodel(Car.class)
public class Car_ { 

    public static volatile SingularAttribute<Car, String> color;
    public static volatile SingularAttribute<Car, String> model;
    public static volatile SingularAttribute<Car, Long> id;
    public static volatile SingularAttribute<Car, String> vehicleRegistrationPlate;
    public static volatile SingularAttribute<Car, String> brand;
    public static volatile SingularAttribute<Car, Customer> user;

}