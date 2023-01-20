import javax.swing.*;

public class Customer{
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNum;
    private String date;
    private String time;

    void setData(JTextField []fields){
        name = fields[0].getText();
        street = fields[1].getText();
        city = fields[2].getText();
        state = fields[3].getText();
        zip = fields[4].getText();
        phoneNum = fields[5].getText();
    }

    void setData(String date, String time){
        this.date = date;
        this.time = time;
    }
}