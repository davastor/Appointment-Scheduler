import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.*;

public class Customer{
    private int id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNum;
    private String date;
    private String time;

    Customer(){
    }

    Customer(int id, String name, String street, String city, String state, String zip, String phoneNum, String date, String time){
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNum = phoneNum;
        this.date = date;
        this.time = time;
    }

    void setData(JTextField []fields){
        name = fields[0].getText();
        street = fields[1].getText();
        city = fields[2].getText();
        state = fields[3].getText();
        zip = fields[4].getText();
        phoneNum = fields[5].getText();
    }

    void setData(String name, String street, String city, String state, String zip, String phoneNum, String date, String time){
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNum = phoneNum;
        this.date = date;
        this.time = time;
    }

    void setAppointment(String date, String time){
        this.date = date;
        this.time = time;
    }

    public int getID(){
        return id;
    }

    public boolean equals(Customer cust){
        return name.equals(cust.name) &&
                street.equals(cust.street) &&
                city.equals(cust.city) &&
                state.equals(cust.state) &&
                zip.equals(cust.zip) &&
                phoneNum.equals(cust.phoneNum);
    }

    public void setID(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public void setAppointmentNull(){
        date = null;
        time = null;
    }

    public void writeToDataBase(BufferedWriter fileWriter){
        try{
            
            fileWriter.write("\n"+ name);
            fileWriter.write("\n"+ street);
            fileWriter.write("\n"+ city);
            fileWriter.write("\n"+ state);
            fileWriter.write("\n"+ zip);
            fileWriter.write("\n"+ phoneNum);
            fileWriter.write("\n"+ date );
            fileWriter.write("\n"+ time);

        
        }catch (IOException e){
            System.out.println("File not found.");
        }
  


    }

    
}