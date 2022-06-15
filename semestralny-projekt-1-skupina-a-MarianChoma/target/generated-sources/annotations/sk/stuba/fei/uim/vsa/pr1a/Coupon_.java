package sk.stuba.fei.uim.vsa.pr1a;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.stuba.fei.uim.vsa.pr1a.Customer;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2022-04-06T23:15:40")
@StaticMetamodel(Coupon.class)
public class Coupon_ { 

    public static volatile SingularAttribute<Coupon, String> name;
    public static volatile SingularAttribute<Coupon, Double> discount;
    public static volatile SingularAttribute<Coupon, Long> id;
    public static volatile SingularAttribute<Coupon, Customer> user;

}