package sk.stuba.fei.uim.vsa.pr1a;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Project1A {

    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(new Date().getTime()));
       // CarParkService parkService= new CarParkService();
        //parkService.startReservation();
    }
}
