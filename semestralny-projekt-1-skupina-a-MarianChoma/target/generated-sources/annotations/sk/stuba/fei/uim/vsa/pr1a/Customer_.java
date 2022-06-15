package sk.stuba.fei.uim.vsa.pr1a;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1a.Car;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-04-06T23:15:40")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile ListAttribute<Customer, Car> cars;
    public static volatile SingularAttribute<Customer, String> firstname;
    public static volatile SingularAttribute<Customer, Long> id;
    public static volatile SingularAttribute<Customer, String> email;
    public static volatile SingularAttribute<Customer, String> lastname;

}