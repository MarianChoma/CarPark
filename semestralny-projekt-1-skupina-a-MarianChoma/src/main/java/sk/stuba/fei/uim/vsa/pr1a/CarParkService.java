package sk.stuba.fei.uim.vsa.pr1a;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CarParkService extends AbstractCarParkService {


    @Override
    public Object createCarPark(String name, String address, Integer pricePerHour) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            ParkingHouse p = new ParkingHouse(name, address, pricePerHour);
            manager.persist(p);

            transaction.commit();
            return p;
        }
        catch (RollbackException e){
            return null;
        }
    }

    @Override
    public Object getCarPark(Long carParkId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT cp from CAR_PARK cp WHERE cp.id= ?1");
            q.setParameter(1, carParkId);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }

    }

    @Override
    public Object getCarPark(String carParkName) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT cp from CAR_PARK cp WHERE cp.name LIKE ?1");
            q.setParameter(1, carParkName);
            return q.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Object> getCarParks() {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT cp from CAR_PARK cp");
            return Arrays.asList(q.getResultList().toArray());
        }
        catch (NoResultException e){
            return null;
        }
    }

    @Override
    public Object updateCarPark(Object carPark) {
        ParkingHouse cp = (ParkingHouse) carPark;
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        if (((ParkingHouse) carPark).getId() != null) {
            Query q = manager.createQuery("UPDATE CAR_PARK SET name= ?1, address= ?2, pricePerHour= ?3 WHERE id=?4");
            q.setParameter(1, cp.getName());
            q.setParameter(2, cp.getAddress());
            q.setParameter(3, cp.getPricePerHour());
            q.setParameter(4, cp.getId());

            q.executeUpdate();
            q = manager.createQuery("SELECT cp from CAR_PARK cp WHERE cp.id=?1");
            q.setParameter(1, cp.getId());
            transaction.commit();
            return q.getSingleResult();
        }
        return null;
    }

    @Override
    public Object deleteCarPark(Long carParkId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Query q = manager.createNativeQuery("DELETE FROM CAR_PARK WHERE id=?");
            q.setParameter(1, carParkId);
            q.executeUpdate();
            transaction.commit();
            return getCarPark(carParkId);
        }
        catch(NoResultException e){
            return null;
        }

    }

    @Override
    public Object createCarParkFloor(Long carParkId, String floorIdentifier) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();

            Floor f = new Floor(floorIdentifier);
            f.setCarParkId((ParkingHouse) getCarPark(carParkId));
            manager.persist(f);

            transaction.commit();
            return f;
        }catch(RollbackException e){
            return null;
        }
    }

    @Override
    public Object getCarParkFloor(Long carParkFloorId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT f from CAR_PARK_FLOOR f WHERE f.id= ?1");
            q.setParameter(1, carParkFloorId);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Object> getCarParkFloors(Long carParkId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT f from CAR_PARK_FLOOR f WHERE f.carParkId.id= ?1");
            q.setParameter(1, carParkId);
            return Arrays.asList(q.getResultList().toArray());
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Object updateCarParkFloor(Object carParkFloor) {
        Floor parkFloor = (Floor) carParkFloor;
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        if (parkFloor.getId() != null) {
            Query q = manager.createQuery("UPDATE CAR_PARK_FLOOR SET floorIdentifier= ?1 WHERE id=?2");
            q.setParameter(1, parkFloor.getFloorIdentifier());
            q.setParameter(2, parkFloor.getId());

            q.executeUpdate();
            q = manager.createQuery("SELECT f from CAR_PARK_FLOOR f WHERE f.id=?1");
            q.setParameter(1, parkFloor.getId());
            transaction.commit();
            return q.getSingleResult();
        }
        return null;
    }

    @Override
    public Object deleteCarParkFloor(Long carParkFloorId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            Object deletedFloor = getCarParkFloor(carParkFloorId);
            Query q = manager.createNativeQuery("DELETE FROM car_park_floor WHERE id=?");
            q.setParameter(1, carParkFloorId);
            q.executeUpdate();
            transaction.commit();
            return deletedFloor;
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Object createParkingSpot(Long carParkId, String floorIdentifier, String spotIdentifier) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            ParkingPlace p = new ParkingPlace();
            p.setSpotIdentifier(spotIdentifier);
            p.setCarPark((ParkingHouse) getCarPark(carParkId));
            Query q = manager.createQuery("SELECT f from CAR_PARK_FLOOR f where f.floorIdentifier LIKE ?1 AND f.carParkId.id=?2");
            q.setParameter(1, floorIdentifier);
            q.setParameter(2, carParkId);

            Floor carFloor = (Floor) q.getSingleResult();
            p.setFloorIdentifier(carFloor);
            manager.persist(p);
            transaction.commit();
            return p;
        }catch(RollbackException | NoResultException e){
            return null;
        }

    }

    @Override
    public Object getParkingSpot(Long parkingSpotId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT p from PARKING_SPOT p WHERE p.id= ?1");
            q.setParameter(1, parkingSpotId);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Object> getParkingSpots(Long carParkId, String floorIdentifier) {
        try {
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT p from PARKING_SPOT p WHERE p.carPark.id= ?1 AND p.floorIdentifier.floorIdentifier LIKE ?2");
            q.setParameter(1, carParkId);
            q.setParameter(2, floorIdentifier);
            return Arrays.asList(q.getResultList().toArray());
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Map<String, List<Object>> getParkingSpots(Long carParkId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Map<String, List<Object>> map = new HashMap<>();
            Query q = manager.createQuery("SELECT cp from CAR_PARK cp WHERE cp.id=?1");
            q.setParameter(1, carParkId);

            ParkingHouse carPark = (ParkingHouse) q.getSingleResult();
            q = manager.createQuery("SELECT cf.floorIdentifier from CAR_PARK_FLOOR cf WHERE cf.carParkId.id= ?1");
            q.setParameter(1, carPark.getId());
            List<String> floorIdentifier = (List<String>) q.getResultList();

            for (String s : floorIdentifier) {
                q = manager.createQuery("SELECT p from PARKING_SPOT p WHERE p.floorIdentifier.floorIdentifier LIKE ?1");
                q.setParameter(1, s);
                map.put(s, q.getResultList());
            }
            return map;
        }catch(RollbackException e){
            return null;
        }

    }

    @Override
    public Map<String, List<Object>> getAvailableParkingSpots(String carParkName) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            ParkingHouse carPark = (ParkingHouse) getCarPark(carParkName);
            List<Object> floorIdentifiers = getCarParkFloors(carPark.getId());

            for (Object o : floorIdentifiers) {
                Query q = manager.createQuery("SELECT p from PARKING_SPOT p WHERE p.floorIdentifier.id=?1 AND p.available=true");
                q.setParameter(1, ((Floor) o).getId());
                map.put(((Floor) o).getFloorIdentifier(), q.getResultList());
            }
            return map;
        }catch(NoResultException | NullPointerException e){
            return null;
        }

    }

    @Override
    public Map<String, List<Object>> getOccupiedParkingSpots(String carParkName) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            ParkingHouse carPark = (ParkingHouse) getCarPark(carParkName);

            List<Object> floorIdentifiers = getCarParkFloors(carPark.getId());
            for (Object o : floorIdentifiers) {
                Query q = manager.createQuery("SELECT p from PARKING_SPOT p WHERE p.floorIdentifier.id=?1 AND p.available=false");
                q.setParameter(1, ((Floor) o).getId());
                map.put(((Floor) o).getFloorIdentifier(), q.getResultList());
            }
            return map;
        }catch(NoResultException | NullPointerException e){
            return null;
        }

    }

    @Override
    public Object updateParkingSpot(Object parkingSpot) {
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        Query q = manager.createNativeQuery("UPDATE parking_spot ps SET ps.SPOTIDENTIFIER=?, ps.AVAILABLE=? WHERE ps.ID=?");
        q.setParameter(1, ((ParkingPlace) parkingSpot).getSpotIdentifier());
        q.setParameter(2, ((ParkingPlace) parkingSpot).isAvailable());
        q.setParameter(3, ((ParkingPlace) parkingSpot).getId());
        q.executeUpdate();
        transaction.commit();
        return getParkingSpot(((ParkingPlace) parkingSpot).getId());
    }

    @Override
    public Object deleteParkingSpot(Long parkingSpotId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Object deletedSpot = getParkingSpot(parkingSpotId);

            Query q=manager.createNativeQuery("DELETE FROM RESERVATION r WHERE PARKINGSPOT_ID=?1");
            q.setParameter(1, parkingSpotId);
            q.executeUpdate();
            q = manager.createNativeQuery("DELETE FROM parking_spot WHERE id=?1");
            q.setParameter(1, parkingSpotId);
            q.executeUpdate();
            transaction.commit();
            return deletedSpot;
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Object createCar(Long userId, String brand, String model, String colour, String vehicleRegistrationPlate) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Car car = new Car(brand, model, colour, vehicleRegistrationPlate);
            car.setUser((Customer) getUser(userId));
            manager.persist(car);
            transaction.commit();
            return car;
        }catch(RollbackException e){
            return null;
        }
    }

    @Override
    public Object getCar(Long carId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT car from CAR car WHERE car.id= ?1");
            q.setParameter(1, carId);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }

    }

    @Override
    public Object getCar(String vehicleRegistrationPlate) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT car from CAR car WHERE car.vehicleRegistrationPlate= ?1");
            q.setParameter(1, vehicleRegistrationPlate);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Object> getCars(Long userId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT c from CAR c WHERE c.user.id= ?1");
            q.setParameter(1, userId);
            return Arrays.asList(q.getResultList().toArray());
        }catch(NoResultException r){
            return null;
        }
    }

    @Override
    public Object updateCar(Object car) {
        Car c = (Car) car;
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        if (c.getId() != null) {
            Query q = manager.createQuery("UPDATE CAR SET brand= ?1, model= ?2, color= ?3, vehicleRegistrationPlate=?4 WHERE CAR.id=?5");
            q.setParameter(1, c.getBrand());
            q.setParameter(2, c.getModel());
            q.setParameter(3, c.getColor());
            q.setParameter(4, c.getVehicleRegistrationPlate());
            q.setParameter(5, c.getId());
            q.executeUpdate();
            transaction.commit();

            return getUser(c.getId());
        }
        return null;
    }

    @Override
    public Object deleteCar(Long carId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Object deletedCar = getCar(carId);
            Query q = manager.createNativeQuery("DELETE FROM car WHERE id=?");
            q.setParameter(1, carId);
            q.executeUpdate();
            transaction.commit();
            return deletedCar;
        }catch(NoResultException e){
            return null;
        }

    }

    @Override
    public Object createUser(String firstname, String lastname, String email) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Customer user = new Customer(firstname, lastname, email);
            manager.persist(user);
            transaction.commit();
            return user;
        }catch(RollbackException e){
            return null;
        }
    }

    @Override
    public Object getUser(Long userId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT u from USER u WHERE u.id= ?1");
            q.setParameter(1, userId);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Object getUser(String email) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT u from USER u WHERE u.email LIKE ?1");
            q.setParameter(1, email);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }

    }

    @Override
    public List<Object> getUsers() {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT u from USER u");
            return Arrays.asList(q.getResultList().toArray());
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Object updateUser(Object user) {
        Customer c = (Customer) user;
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        if (c.getId() != null) {
            Query q = manager.createQuery("UPDATE USER SET firstname= ?1, lastname= ?2, email= ?3 WHERE id=?4");
            q.setParameter(1, c.getFirstname());
            q.setParameter(2, c.getLastname());
            q.setParameter(3, c.getEmail());
            q.setParameter(4, c.getId());
            q.executeUpdate();
            transaction.commit();

            return getUser(c.getId());
        }
        return null;
    }

    @Override
    public Object deleteUser(Long userId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Query q = manager.createNativeQuery("DELETE FROM car WHERE USER_ID=?");
            q.setParameter(1, userId);
            q.executeUpdate();

            q = manager.createNativeQuery("DELETE FROM user WHERE id=? ");
            q.setParameter(1, userId);
            q.executeUpdate();

            transaction.commit();
            return getUser(userId);
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Object createReservation(Long parkingSpotId, Long carId) {
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        boolean available=false;
        try{
            ParkingPlace parkingSpot = (ParkingPlace) getParkingSpot(parkingSpotId);
            available=parkingSpot.isAvailable();
        }catch(NullPointerException | NoResultException e){
            return null;
        }

        if (available) {
            Query q = manager.createQuery("SELECT r FROM RESERVATION r WHERE r.car.id=?1");
            q.setParameter(1, carId);
            boolean canCreate = true;
            try {
                for (int i = 0; i < q.getResultList().size(); i++) {
                    if (((Reservation) q.getResultList().get(i)).getEndOfReservation() == null) {
                        canCreate = false;
                    }
                }
            } catch (NoResultException ignored) {
                return null;
            }
            if (canCreate) {

                Reservation r = new Reservation();
                r.setCar((Car) getCar(carId));
                r.setBeginOfReservation(new Date());

                r.setParkingSpot((ParkingPlace) getParkingSpot(parkingSpotId));
                manager.persist(r);

                q = manager.createQuery("UPDATE PARKING_SPOT ps SET ps.available=false WHERE ps.id=?1");
                q.setParameter(1, parkingSpotId);
                q.executeUpdate();
                transaction.commit();
                return r;
            }
        }
        return null;
    }

    @Override
    public Object endReservation(Long reservationId) {
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        Reservation r = null;
        Query q;
        try{
            q = manager.createQuery("SELECT r FROM RESERVATION r WHERE r.id= ?1");
            q.setParameter(1, reservationId);
            r = (Reservation) q.getSingleResult();
        }catch(NullPointerException | NoResultException e){
            return null;
        }
        if(r.getEndOfReservation()==null){
            Date end = new Date();

            Long hoursToPay = TimeUnit.MILLISECONDS.toHours(end.getTime() - r.getBeginOfReservation().getTime()) + 1;

            q = manager.createQuery("SELECT cp FROM CAR_PARK cp " +
                    "JOIN PARKING_SPOT ps ON ps.carPark.id=cp.id " +
                    "JOIN RESERVATION r ON r.parkingSpot.id=ps.id " +
                    "WHERE r.id=?1");
            q.setParameter(1, reservationId);
            ParkingHouse carPark = (ParkingHouse) q.getSingleResult();

            double price = hoursToPay * carPark.getPricePerHour();

            transaction.begin();
            q = manager.createQuery("UPDATE RESERVATION r SET r.endOfReservation= ?1, r.price=?2 WHERE r.id= ?3");
            q.setParameter(1, end);
            q.setParameter(2, price);
            q.setParameter(3, reservationId);
            q.executeUpdate();

            q = manager.createQuery("UPDATE PARKING_SPOT p SET p.available=true WHERE p.id= ?1");
            q.setParameter(1, r.getParkingSpot().getId());
            q.executeUpdate();
            transaction.commit();
            return r;
        }
        else{
            return null;
        }
    }

    @Override
    public List<Object> getReservations(Long parkingSpotId, Date date) {
        try{
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            LocalDateTime localDay = localDateTime.with(LocalTime.MAX);
            Date endOfDay = Date.from(localDay.atZone(ZoneId.systemDefault()).toInstant());

            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT r FROM RESERVATION r WHERE r.beginOfReservation < ?1 AND r.parkingSpot.id =?2");
            q.setParameter(1, endOfDay);
            q.setParameter(2, parkingSpotId);

            return Arrays.asList(q.getResultList().toArray());
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<Object> getMyReservations(Long userId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT r from RESERVATION r JOIN CAR c ON c.id=r.car.id WHERE c.user.id=?1 AND r.endOfReservation IS NULL");
            q.setParameter(1, userId);
            return Arrays.asList(q.getResultList().toArray());
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public Object updateReservation(Object reservation) {
        return null;
    }


    @Override
    public Object createDiscountCoupon(String name, Integer discount) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Coupon c = new Coupon(name, discount);
            manager.persist(c);
            transaction.commit();
            return c;
        }catch(RollbackException e){
            return null;
        }
    }

    @Override
    public void giveCouponToUser(Long couponId, Long userId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            Query q = manager.createQuery("Update COUPON c SET c.user=?1 where c.id=?2 AND c.user IS NULL");
            q.setParameter(1, getUser(userId));
            q.setParameter(2, couponId);
            q.executeUpdate();
            transaction.commit();
        }catch (NoResultException ignored){

        }
    }

    @Override
    public Object getCoupon(Long couponId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT c from COUPON c WHERE c.id= ?1");
            q.setParameter(1, couponId);
            return q.getSingleResult();
        }catch(NoResultException e){
            return null;
        }

    }

    @Override
    public List<Object> getCoupons(Long userId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            Query q = manager.createQuery("SELECT c from COUPON c JOIN USER u ON c.user.id=u.id WHERE c.user.id=?1");
            q.setParameter(1, userId);
            return Arrays.asList(q.getResultList().toArray());
        }catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public Object endReservation(Long reservationId, Long couponId) {
        Reservation reservation = (Reservation) endReservation(reservationId);
        EntityManager manager = this.emf.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        Query q = manager.createQuery("SELECT r From RESERVATION r WHERE r.id=?1 AND r.usingCoupon=false");
        q.setParameter(1, reservationId);
        double price;
        try{
            price = ((Reservation) q.getSingleResult()).getPrice();
        }catch(NoResultException e){
            return null;
        }
        price = price - (price * ((Coupon) getCoupon(couponId)).getDiscount()) / 100;
        q = manager.createQuery("UPDATE RESERVATION r SET r.usingCoupon= true, r.price=?1 WHERE r.id=?2");
        q.setParameter(1, price);
        q.setParameter(2, reservationId);
        q.executeUpdate();
        transaction.commit();
        deleteCoupon(couponId);
        return reservation;
    }

    @Override
    public Object deleteCoupon(Long couponId) {
        try{
            EntityManager manager = this.emf.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();
            Object deletedCoupon = getCoupon(couponId);
            Query q = manager.createNativeQuery("DELETE FROM coupon WHERE id=?");
            q.setParameter(1, couponId);
            q.executeUpdate();
            transaction.commit();
            return deletedCoupon;
        }catch(NoResultException e){
            return null;
        }
    }


    public int startReservation() {
        System.out.println("Vitajte v rezervačnom systéme parkovacích domov. Vyberte si z ponuky čo chcete vykonať:");
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Menu:");
            System.out.println("CarPark: 1\nParkFloor: 2\nParkingSpot: 3\nUser: 4\nCar: 5\nCoupon: 6\nReservation: 7");
            System.out.println("Zadajte číslo z ponuky: ");
            int output = inputValidation(1, 7);
            switch (output) {
                case 1:
                    System.out.println("*+*+*CarPark*+*+*");
                    System.out.println("CreateCarPark: 1\nGetCarPark: 2\nUpdateCarPark :3\nDeleteCarPar: 4\nGetCarParks: 5");
                    output = inputValidation(1, 5) + 10;
                    break;
                case 2:
                    System.out.println("*+*+*ParkFloor*+*+*");
                    System.out.println("CreateParkFloor: 1\nGetParkFloor: 2\nUpdateParkFloor :3\nDeleteParkFloor: 4\nGetParkingFloors: 5");
                    output = inputValidation(1, 5) + 20;
                    break;
                case 3:
                    System.out.println("*+*+*ParkingSpot*+*+*");
                    System.out.println("CreateParkingSpot: 1\nGetParkingSpot: 2\nUpdateParkingSpot :3\nDeleteParkingSpot: 4\nGetParkingSpots: 5");
                    output = inputValidation(1, 5) + 30;
                    break;
                case 4:
                    System.out.println("*+*+*User*+*+*");
                    System.out.println("CreateUser: 1\nGetUser: 2\nUpdateUser :3\nDeleteUser: 4\nGetUsers: 5");
                    output = inputValidation(1, 5) + 40;
                    break;
                case 5:
                    System.out.println("*+*+*Car*+*+*");
                    System.out.println("CreateCar: 1\nGetCar: 2\nUpdateCar :3\nDeleteCar: 4\nGetCars 5");
                    output = inputValidation(1, 5) + 50;
                    break;
                case 6:
                    System.out.println("*+*+*Coupon*+*+*");
                    System.out.println("CreateCoupon: 1\nGiveCouponToUser: 2\nGetCoupon :3\nDeleteCoupon: 4\nGetCoupons 5\n EndReservationWithCoupon: 6");
                    output = inputValidation(1, 6) + 60;
                    break;
                case 7:
                    System.out.println("*+*+*Reservation*+*+*");
                    System.out.println("CreateReservation: 1\nEndReservation: 2\nGetReservation :3\ngetMyReservations: 4");
                    output = inputValidation(1, 4) + 70;
                    break;
                default:
                    System.out.println("Chybný input");
                    break;
            }

            switch (output) {
                case 11:
                    System.out.println("*+-*+-CreateCarPark-+*-+*");
                    System.out.println("Zadajte názov:");
                    String name = stringInput();

                    System.out.println("Zadajte adresu:");
                    String adress = stringInput();

                    System.out.println("Zadajte cenu za parkovanie:");
                    Integer price = integerValidation();

                    createCarPark(name, adress, price);
                    break;
                case 12:
                    System.out.println("*+-*+-GetCarPark-+*-+*");
                    System.out.println("Nájsť parkovací dom podľa Id: 1");
                    System.out.println("Nájsť parkovací dom podľa mena: 2");
                    System.out.println("Zadajte číslo: ");
                    carParkInput();
                    break;
                case 13:
                    System.out.println("*+-*+-UpdateCarPark-+*-+*");
                    printCarParks();
                    System.out.println("Zadajte Id parkovacieho domu:");
                    Long updateCarParkId = idValidation();
                    System.out.println("Zadajte nový názov parkovacieho domu:");
                    String updateCPName = stringInput();
                    System.out.println("Zadajte novú adresu parkovacieho domu:");
                    String newAdress = stringInput();
                    System.out.println("Zadajte novú cenu za parkovanie parkovacieho domu:");
                    int newPrice = integerValidation();

                    ParkingHouse newCarPark = new ParkingHouse(updateCarParkId, updateCPName, newAdress, newPrice);
                    updateCarPark(newCarPark);
                    break;
                case 14:
                    System.out.println("*+-*+-DeleteCarPark-+*-+*");
                    printCarParks();
                    System.out.println("Zadajte Id parkovacieho domu:");
                    Long carParkId = idValidation();

                    deleteCarPark(carParkId);
                    break;
                case 15:
                    System.out.println("*+-*+-GetCarParks-+*-+*");
                    System.out.println(getCarParks());
                    break;
                case 21:
                    System.out.println("*+-*+-CreateParkFloor-+*-+*");
                    System.out.println("Zadajte názov ktorým identifikujete parkovacie podlažie");
                    String stringIdentifier = stringInput();
                    printCarParks();
                    System.out.println("Zadajte Id parkovacieho domu");
                    Long id = idValidation();

                    createCarParkFloor(id, stringIdentifier);
                    break;
                case 22:
                    System.out.println("*+-*+-GetCarParkFloor-+*-+*");
                    printParkFloor();
                    System.out.println("Zadajte Id parkovacieho podlažia: ");
                    Long floorId = idValidation();
                    System.out.println(getCarParkFloor(floorId));
                    break;
                case 23:
                    System.out.println("*+-*+-UpdateCarParkFloor-+*-+*");
                    printParkFloor();
                    System.out.println("Zadajte Id parkovacieho podlažia:");
                    Long newFloorId = idValidation();
                    System.out.println("Zadajte nový identifikátor parkovacieho podlažia:");
                    String newIdentifikator = stringInput();
                    Floor newFloor = new Floor(newFloorId, newIdentifikator);

                    updateCarParkFloor(newFloor);
                    break;
                case 24:
                    System.out.println("*+-*+-DeleteCarParkFloor-+*-+*");
                    printParkFloor();
                    System.out.println("Zadajte Id parkovacieho podlažia:");
                    Long deleteFloorId = idValidation();

                    deleteCarParkFloor(deleteFloorId);
                    break;
                case 25:
                    System.out.println("*+-*+-GetParkFloors-+*-+*");
                    printCarParks();
                    System.out.println("Zadajte Id parkovacieho domu:");
                    Long idOfCarPark = idValidation();

                    System.out.println(getCarParkFloors(idOfCarPark));
                    break;
                case 31:
                    System.out.println("*+-*+-CreateParkingSpot-+*-+*");
                    printCarParks();
                    System.out.println("Zadajte ID parkovacieho domu");
                    Long parkingHouseId = idValidation();
                    System.out.println(getCarParkFloors(parkingHouseId));
                    System.out.println("Zadajte identifikátor parkovacieho podlažia: ");
                    String floorIdentificator = stringInput();
                    System.out.println("Zadajte názov ktorým identifikujete parkovacie miesto");
                    String spotIdentifier = stringInput();

                    createParkingSpot(parkingHouseId, floorIdentificator, spotIdentifier);
                    break;
                case 32:
                    System.out.println("*+-*+-GetParkingSpot-+*-+*");
                    printSpots();
                    System.out.println("Zadajte ID parkovacieho miesta: ");
                    Long spotId=idValidation();
                    System.out.println(getParkingSpot(spotId));
                    break;
                case 33:
                    System.out.println("*+-*+-UpdateParkingSpot-+*-+*");
                    printSpots();
                    System.out.println("Zadajte Id parkovacieho miesta:");
                    Long newSpotId = idValidation();
                    System.out.println("Zadajte nový identifikátor parkovacieho miesta:");
                    String newSpotIdentificator = stringInput();
                    System.out.println("Zadajte true alebo false pre obsadenosť miesta:");
                    boolean newAvailable = sc.nextBoolean();
                    ParkingPlace newSpot = new ParkingPlace(newSpotId,newSpotIdentificator,newAvailable);
                    updateParkingSpot(newSpot);
                    break;
                case 34:
                    System.out.println("*+-*+-DeleteParkingSpot-+*-+*");
                    printSpots();
                    System.out.println("Zadajte Id parkovacieho miesta:");
                    Long deleteSpotId = idValidation();
                    deleteParkingSpot(deleteSpotId);
                    break;
                case 35:
                    System.out.println("*+-*+-GetParkingSpots-+*-+*");
                    System.out.println("Nájsť parkovacie miesta podľa carParkId a FloorIdentifier: 1");
                    System.out.println("Nájsť všetky parkovacie miesta podľa carParkId: 2");
                    System.out.println("Nájsť všetky voľné parkovacie miesta podľa carParkName: 3");
                    System.out.println("Nájsť všetky obsadené parkovacie miesta podľa carParkName: 4");
                    System.out.println("Zadajte číslo: ");
                    spotInput();
                    break;
                case 41:
                    System.out.println("*+-*+-CreateUser-+*-+*");
                    System.out.println("Zadajte krstné meno:");
                    String firstName=stringInput();
                    System.out.println("Zadajte prizvisko:");
                    String lastName=stringInput();
                    System.out.println("Zadajte email:");
                    String email=stringInput();
                    createUser(firstName,lastName,email);
                    break;
                case 42:
                    System.out.println("*+-*+-GetUser-+*-+*");
                    System.out.println("Pre vyhľadanie používateľa podľa ID: 1");
                    System.out.println("Pre vyhľadanie používateľa podľa meno: 2");
                    userInput();
                    break;
                case 43:
                    System.out.println("*+-*+-UpdateUser-+*-+*");
                    printUser();
                    System.out.println("Zadajte ID používateľa: ");
                    Long newUserId=idValidation();
                    System.out.println("Zadajte krstné meno používateľa:");
                    String newName=stringInput();
                    System.out.println("Zadajte priezvisko používateľa:");
                    String newLastName=stringInput();
                    System.out.println("Zadajte email používateľa:");
                    String newEmail=stringInput();
                    Customer updateUser=new Customer(newUserId,newName,newLastName,newEmail);
                    updateUser(updateUser);
                    break;
                case 44:
                    System.out.println("*+-*+-DeleteUser-+*-+*");
                    printUser();
                    System.out.println("Zadajte Id zákazníka:");
                    Long userCarId=idValidation();
                    deleteUser(userCarId);
                    break;
                case 45:
                    System.out.println("*+-*+-GetUsers-+*-+*");
                    System.out.println(getUsers());
                    break;
                case 51:
                    System.out.println("*+-*+-CreateCar-+*-+*");
                    System.out.println("Zadajte značku:");
                    String brand=stringInput();
                    System.out.println("Zadajte model:");
                    String model=stringInput();
                    System.out.println("Zadajte farbu:");
                    String color=stringInput();
                    System.out.println("Zadajte EVČ:");
                    String evc=stringInput();
                    printUser();
                    System.out.println("Zadajte ID majiteľa:");
                    Long owner=idValidation();
                    createCar(owner,brand,model,color,evc);
                    break;
                case 52:
                    System.out.println("*+-*+-GetCar-+*-+*");
                    System.out.println("Vyhľadanie auta podľa ID: 1");
                    System.out.println("Vyhľadanie auta podľa EVČ: 2");
                    carInput();
                    break;
                case 53:
                    System.out.println("*+-*+-UpdateCar-+*-+*");
                    printCars();
                    System.out.println("Zadajte Id auto");
                    Long newCarId=idValidation();
                    System.out.println("Zadajte novú značku auta:");
                    String newBrand=stringInput();
                    System.out.println("Zadajte nový model auta:");
                    String newModel=stringInput();
                    System.out.println("Zadajte novú farbu auta:");
                    String newColor=stringInput();
                    System.out.println("Zadajte novú EVČ auta:");
                    String newEVC=stringInput();
                    Car newCar= new Car(newCarId,newBrand,newModel,newColor,newEVC);
                    updateCar(newCar);
                    break;
                case 54:
                    System.out.println("*+-*+-DeleteCar-+*-+*");
                    printCars();
                    System.out.println("Zadajte ID auta:");
                    Long carId=idValidation();
                    deleteCar(carId);
                    break;
                case 55:
                    System.out.println("*+-*+-GetCars-+*-+*");
                    printUser();
                    System.out.println("Zadajte ID používateľa");
                    Long carsUserId=idValidation();
                    System.out.println(getCars(carsUserId));
                    break;
                case 61:
                    System.out.println("*+-*+-CreateCoupon-+*-+*");
                    System.out.println("Zadajte názov kupónu:");
                    String couponName = stringInput();
                    System.out.println("Zadajte výšku zľavy:");
                    Integer discount = inputValidation(0, 100);
                    System.out.println(createDiscountCoupon(couponName, discount));
                    break;
                case 62:
                    System.out.println("*+-*+-GiveCouponToUser-+*-+*");
                    printCoupons();
                    System.out.println("Zadajte Id kupónu:");
                    Long couponId = idValidation();
                    printUsers();
                    System.out.println("Zadajte Id usera:");
                    Long userId = idValidation();
                    giveCouponToUser(couponId, userId);
                    break;
                case 63:
                    System.out.println("*+-*+-GetCoupons-+*-+*");
                    printCoupons();
                    System.out.println("Zadajte Id kupónu:");
                    Long getCouponId = idValidation();
                    System.out.println(getCoupon(getCouponId));
                    break;
                case 64:
                    System.out.println("*+-*+-DeleteCoupon-+*-+*");
                    printCoupons();
                    System.out.println("Zadajte Id kupónu:");
                    Long deleteCouponId = idValidation();
                    System.out.println(deleteCoupon(deleteCouponId));
                    break;
                case 65:
                    System.out.println("*+-*+-GetCoupons-+*-+*");
                    printUsers();
                    System.out.println("Zadajte Id používateľa:");
                    Long couponUserId = idValidation();
                    System.out.println(getCoupons(couponUserId));
                    break;
                case 66:
                    System.out.println("*+-*+-EndReservationWithCoupon-+*-+*");
                    printReservations();
                    System.out.println("Zadajte Id rezervácie ktorú chcete ukončiť: ");
                    Long endReservationId=idValidation();
                    printCoupons();
                    System.out.println("Zadajt Id kupónu: ");
                    Long idCoupon=idValidation();
                    endReservation(endReservationId, idCoupon);
                    break;
                case 71:
                    System.out.println("*+-*+-CreateReservation-+*-+*");
                    printSpots();
                    System.out.println("Zadajte ID parkovacieho miesta: ");
                    Long parkSpotReservation=idValidation();
                    printCars();
                    System.out.println("Zadajte ID auta: ");
                    Long reservationCar=idValidation();

                    createReservation(parkSpotReservation,reservationCar);
                    break;
                case 72:
                    System.out.println("*+-*+-EndReservation-+*-+*");
                    printReservations();
                    System.out.println("Zadajte ID rezervácie: ");
                    Long endReservation=idValidation();
                    endReservation(endReservation);
                    break;
                case 73:
                    System.out.println("*+-*+-GetReservation-+*-+*");
                    printSpots();
                    System.out.println("Zadajte ID parkovacieho miesta: ");
                    Long getSpotId=idValidation();
                    Date date=inputDate();
                    System.out.println(getReservations(getSpotId,date));
                    break;
                case 74:
                    System.out.println("*+-*+-GetMyReservations-+*-+*");
                    printUser();
                    System.out.println("Zadajte ID používateľa: ");
                    Long userIdReservation=idValidation();
                    System.out.println(getMyReservations(userIdReservation));
                    break;
            }
            System.out.println("Zadajte x pre ukončnie, ak chcete pokračovať zadajte ľubovoľný znak");
            if (sc.nextLine().equals("x")) {
                break;
            }
        }
        return 0;
    }

    public int inputValidation(int start, int end) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            try {
                int s = sc.nextInt();
                if (s >= start && s <= end) {
                    return s;
                }
            } catch (InputMismatchException e) {
                System.out.println("Zadali si zle číslo");
            }
        }
    }

    public String stringInput() {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        while (name.trim().length() <= 0) {
            System.out.println("Zlá hodnota, zadajte inú hodnotu:");
            name = sc.nextLine();
        }
        return name;
    }

    public int integerValidation() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            try {
                int number = sc.nextInt();
                if (number > 0) {
                    return number;
                }

            } catch (InputMismatchException e) {
                System.out.println("Zadali ste zle číslo, skúste to ešte raz:");
            }
        }
    }

    public Long idValidation() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Zadajte Id:");
            try {
                return sc.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Zadali ste zle Id, skúste to ešte raz:");
            }
        }
    }

    public void printCarParks() {
        List<Object> parkingHouses = getCarParks();
        for (Object carPark : parkingHouses) {
            System.out.println("Názov parkovacieho domu:" + ((ParkingHouse) carPark).getName() + " Id: " + ((ParkingHouse) carPark).getId());
        }
    }

    public void printParkFloor() {
        EntityManager manager = this.emf.createEntityManager();
        Query q = manager.createQuery("SELECT c FROM CAR_PARK_FLOOR c");
        List<Object> parkFloors = q.getResultList();
        for (Object floor : parkFloors) {
            System.out.println("Id parkovacieho domu: " + ((Floor) floor).getCarParkId().getId() + " Identifikátor parkovacieho miesta:" + ((Floor) floor).getFloorIdentifier() + " Id: " + ((Floor) floor).getId());
        }
    }

    public void carParkInput() {
        int s = 0;
        while (true) {
            Scanner sc = new Scanner(System.in);

            try {
                s = sc.nextInt();
                if (s == 1 || s == 2) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Zadali si zle číslo");
            }
        }
        printCarParks();
        if (s == 1) {
            System.out.println("Zadajte ID parkovacieho domu");
            Long carParkId = idValidation();
            System.out.println(getCarPark(carParkId));
        }
        if (s == 2) {
            System.out.println("Zadajte názov parkovacieho domu");
            String name = stringInput();
            System.out.println(getCarPark(name));
        }
    }
    public void spotInput(){
        int choiceNumber=inputValidation(1,4);
        switch (choiceNumber){
            case 1:
                System.out.println("*+-*+-GetParkingSpots-+*-+*");
                printCarParks();
                System.out.println("Zadajte ID parkovacieho domu:");
                Long carParkId= idValidation();
                printParkFloor();
                System.out.println("Zadajte identifikátor parkovacieho podlažia:");
                String parkFloor=stringInput();
                System.out.println(getParkingSpots(carParkId,parkFloor));
                break;
            case 2:
                System.out.println("*+-*+-GetParkingSpots-+*-+*");
                printCarParks();
                System.out.println("Zadajte ID parkovacieho domu:");
                Long carPark= idValidation();
                System.out.println(getParkingSpots(carPark));
                break;
            case 3:
                System.out.println("*+-*+-GetParkingSpots-+*-+*");
                printCarParks();
                System.out.println("Zadajte meno parkovacieho domu:");
                String nameCarPark=stringInput();
                System.out.println(getAvailableParkingSpots(nameCarPark));
                break;
            case 4:
                System.out.println("*+-*+-GetParkingSpots-+*-+*");
                printCarParks();
                System.out.println("Zadajte meno parkovacieho domu:");
                String occupiedCarPark=stringInput();
                System.out.println(getOccupiedParkingSpots(occupiedCarPark));
                break;
        }
    }
    public void userInput(){
        int choiceNumber=inputValidation(1,2);
        switch (choiceNumber){
            case 1:
                System.out.println("*+-*+-GetUser-+*-+*");
                printUser();
                System.out.println("Zadajte Id používateľa");
                Long userId=idValidation();
                System.out.println(getUser(userId));
                break;
            case 2:
                System.out.println("*+-*+-GetUser-+*-+*");
                printUser();
                System.out.println("Zadajte email používateľa");
                String userEmail=stringInput();
                System.out.println(getUser(userEmail));
                break;
        }
    }
    public void carInput(){
        int choiceNumber=inputValidation(1,2);
        switch (choiceNumber){
            case 1:
                System.out.println("*+-*+-GetCar-+*-+*");
                printCars();
                System.out.println("Zadajte Id auta: ");
                Long carId=idValidation();
                System.out.println(getCar(carId));
                break;
            case 2:
                System.out.println("*+-*+-GetCar-+*-+*");
                printUser();
                System.out.println("Zadajte EVČ auta");
                String carEVC=stringInput();
                System.out.println(getCar(carEVC));
                break;
        }
    }

    public void printCoupons() {
        EntityManager manager = this.emf.createEntityManager();
        Query q = manager.createQuery("SELECT c FROM COUPON c");
        List<Object> coupons = q.getResultList();
        for (Object c : coupons) {
            System.out.println("ID: " + ((Coupon) c).getId() + " Názov: " + ((Coupon) c).getName());
        }
    }

    public void printUsers() {
        List<Object> users = getUsers();
        for (Object u : users) {
            System.out.println("Email:" + ((Customer) u).getEmail() + " Id: " + ((Customer) u).getId());
        }
    }
    public void printSpots() {
        EntityManager manager = this.emf.createEntityManager();
        Query q = manager.createQuery("SELECT s FROM PARKING_SPOT s");
        List<Object> spots = q.getResultList();
        for (Object p : spots) {
            System.out.println("ID: " + ((ParkingPlace) p).getId() + " Názov: " + ((ParkingPlace) p).getSpotIdentifier());
        }
    }
    public void printUser(){
        List<Object> users=getUsers();
        for (Object u : users) {
            System.out.println("ID: " + ((Customer) u).getId() + " Email: " + ((Customer) u).getEmail());
        }
    }
    public void printCars(){
        EntityManager manager = this.emf.createEntityManager();
        Query q = manager.createQuery("SELECT c FROM CAR c");
        List<Object> cars=q.getResultList();
        for (Object c : cars) {
            System.out.println("ID: " + ((Car) c).getId() + " EVČ: " + ((Car) c).getVehicleRegistrationPlate());
        }
    }
    public void printReservations(){
        EntityManager manager = this.emf.createEntityManager();
        Query q = manager.createQuery("SELECT r FROM RESERVATION r");
        List<Object> reservations=q.getResultList();
        for (Object r : reservations) {
            System.out.println("ID: " + ((Reservation) r).getId() + " Začiatok: " + ((Reservation) r).getBeginOfReservation() +" Koniec: "+ ((Reservation) r).getEndOfReservation());
        }
    }
    public Date inputDate(){
        Date date;
        SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            System.out.println("Zadajte dátum vo formáte yyyy-MM-dd HH:mm:ss");
            Scanner sc = new Scanner(System.in);
            String strDate = sc.nextLine();

            try
            {
                date = dateInput.parse(strDate);
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                return date;
            }
            catch (ParseException e)
            {
                System.out.println("Zadali ste zlý dátum");
            }
        }
    }
}


