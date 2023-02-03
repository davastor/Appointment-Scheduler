import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.*;

//Contains all data for each customer
public class Customer{
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNum;
    private String date;
    private String time;
    private String aquariumType;
    private int gallons;
    private double cost;

    //constructor to do nothing upon object instantiation
    Customer(){
    }

    //constructor to copy data from another Customer object
    Customer(Customer cust){
        name = cust.name;
        street = cust.street;
        city = cust.city;
        state = cust.state;
        zip = cust.zip;
        phoneNum = cust.phoneNum;
        aquariumType = cust.aquariumType;
        gallons = cust.gallons;
        cost = cust.cost;
    }

    //sets Customer data using JTextFields and JRadioButton values
    void setData(JTextField []fields, String type){
        name = fields[0].getText();
        street = fields[1].getText();
        city = fields[2].getText();
        state = fields[3].getText();
        zip = fields[4].getText();
        phoneNum = fields[5].getText();
        gallons = Integer.parseInt(fields[6].getText());
        aquariumType = type;
        calculateCost();
    }

    //sets Customer data using array of Strings
    //with each element being read from one line of
    //the text file
    void setData(String [] strings){
        this.name = strings[0];
        this.street = strings[1];
        this.city = strings[2];
        this.state = strings[3];
        this.zip = strings[4];
        this.phoneNum = strings[5];
        this.date = strings[6];
        this.time = strings[7];
        this.aquariumType = strings[8];
        this.gallons = Integer.parseInt(strings[9]);
        this.cost = Double.parseDouble(strings[10]);
    }

    //set appointment date and time, called inside MainFrame
    void setAppointment(String date, String time){
        this.date = date;
        this.time = time;
    }
    
    //checks to see if two Customer objects have the same contact information
    //returns true if everything is the same, false
    //if even one piece of data is different
    public boolean equals(Customer cust){
        return name.equals(cust.name) &&
                street.equals(cust.street) &&
                city.equals(cust.city) &&
                state.equals(cust.state) &&
                zip.equals(cust.zip) &&
                phoneNum.equals(cust.phoneNum);
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

    //set date and time to null if customer presses back on Appointment screen
    //to invalidate their previous selection
    public void setAppointmentNull(){
        date = null;
        time = null;
    }

    //use BufferedWriter object to write Customer data to 
    //textfile on each subsequent line
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
            fileWriter.write("\n" + aquariumType);
            fileWriter.write("\n" + gallons);

            DecimalFormat dc = new DecimalFormat(".00");
            fileWriter.write("\n" + dc.format(cost));

        //prints out error message in console if textfile cannot be located
        }catch (IOException e){
            System.out.println("File not found.");
        }

    }

    //multiply gallons with 1.25 to get cost for service
    //round cost to two decimal places
    public void calculateCost(){
        cost = gallons * 1.25;
        cost =  Math.round(cost * 100) / 100;
    }

    public double getCost(){
        return cost;
    }
    
    
}