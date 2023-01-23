import javax.swing.*;
import javax.swing.border.*;

import org.xml.sax.HandlerBase;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.io.File;


public class MainFrame extends JFrame implements ActionListener, CustomerDatabase{
    private JPanel panelCont = new JPanel();
    private FormPanel formPanel = new FormPanel();
    private WelcomePanel welcomePanel = new WelcomePanel();
    private NameSearchPanel searchPanel = new NameSearchPanel();
    private AppointmentPanel appointPanel = new AppointmentPanel();
    private ConfirmationPanel confirmPanel = new ConfirmationPanel();
    private CardLayout cl = new CardLayout();
    private boolean existingCust;
    private ArrayList <Customer> customers = new ArrayList<Customer>();
    //private Customer testCust = new Customer(1, "Danh", "1234 Test Street", "Test City", "Test State", "00000", "1234567890", "February 3, 2023", "9:00AM");
    BufferedReader fileReader;
    BufferedWriter fileWriter;

    MainFrame(){
        fillArrayList();
        appointPanel.fillDates(customers);

        
        existingCust = false;
        this.setSize(500,500);
        setResizable(false);
        panelCont.setLayout(cl);
        panelCont.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(15, 15, 15, 15),  new EtchedBorder()));

        panelCont.add("welcome", welcomePanel);
        panelCont.add("search", searchPanel);
        panelCont.add("form", formPanel);
        panelCont.add("appoint", appointPanel);
        panelCont.add("confirm", confirmPanel);
        cl.show(panelCont, "welcome");

        //Add test customer to ArrayList
        //customers.add(testCust);

        welcomePanel.getNewButton().addActionListener(this);
        welcomePanel.getExistButton().addActionListener(this);
        formPanel.getBackBtn().addActionListener(this);
        formPanel.getNextBtn().addActionListener(this);
        appointPanel.getBackBtn().addActionListener(this);
        appointPanel.getNextBtn().addActionListener(this);
        confirmPanel.getBackButton().addActionListener(this);
        confirmPanel.getConfirmButton().addActionListener(this);
        searchPanel.getBackBtn().addActionListener(this);
        searchPanel.getNextBtn().addActionListener(this);

        add(panelCont);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == welcomePanel.getNewButton()){
            cl.show(panelCont, "form");
        }
        else if(e.getSource() == welcomePanel.getExistButton()){
            existingCust = true;
            cl.show(panelCont, "search");
        }
        else if(e.getSource() == searchPanel.getBackBtn()){
            existingCust = false;
            cl.previous(panelCont);
        }
        else if(e.getSource() == searchPanel.getNextBtn()){
            if(appendExistingCustomer()){
                cl.show(panelCont, "appoint");
            }
            else{
                return;
            }
         
        }        
        else if(e.getSource() == formPanel.getBackBtn()){
            cl.show(panelCont, "welcome");
        }
        else if(e.getSource() == formPanel.getNextBtn()){
            if(!submitFormData())
                return;
            else
                cl.show(panelCont, "appoint");
        }
        else if(e.getSource() == appointPanel.getNextBtn()){
            submitAppointment();
            confirmPanel.setLabel(customers.get(customers.size()-1));
            cl.next(panelCont);
        }
        else if(e.getSource() == appointPanel.getBackBtn()){
            if(existingCust){
                cl.show(panelCont, "search");
                existingCust = false;
            }
            else
                cl.previous(panelCont);

            //remove new customer object in case customer decides to resubmit the form or search up a different name
            //prevents extra objects from being added to the ArrayList
            customers.remove(customers.size()-1);
        }
        else if(e.getSource() == confirmPanel.getBackButton()){
            //undo customer's previous selection of date and time
            customers.get(customers.size()-1).setAppointmentNull();
            cl.previous(panelCont);
        }
        else{
            appendDatabase();
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    //submits data in text fields, returns false if fields are incomplete or if customer already exists, otherwise return true 
    //to determine if layout should go to next panel
    private boolean submitFormData(){

        //gets the text field object array from formPanel and sets it to local variable
        JTextField fields[] = formPanel.getTextFields();

        //runs through object array
        //checks to see if any fields are empty, sets warning message if true
        for(JTextField field: fields){
            if(field.getText().isEmpty()){
                formPanel.setFormWarning();
                return false;
            }
        }

        //create new customer object and sets it data members equal to JTextField values
        Customer newCustomer = new Customer();
        newCustomer.setData(fields);

        //checks to see if new customer already has an existing account
        //if yes, set warning label at the bottom
        //return false so that layout knows to NOT go to the next panel
        for(Customer cust: customers){
            if(newCustomer.equals(cust)){
                formPanel.setExistWarning();
                return false;
            }
        }

        //adds new customer to ArrayList of customers, return true
        //so that layout knows to go to next panel
        customers.add(newCustomer);
        return true;
    }

    private void submitAppointment(){
        //checks to see if date or time fields are empty, sets warning message if true
        if(appointPanel.getDatePicker().getComponentDateTextField().getText().isEmpty()||
        appointPanel.getTimePicker().getComponentTimeTextField().getText().isEmpty()){
            appointPanel.setAppointWarning();
            return;
        } 

        //takes the text values from the date and time pickers and sets new customer's chosen date and time
        String date = appointPanel.getDatePicker().toString();
        String time = appointPanel.getTimePicker().getTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        customers.get(customers.size()-1).setAppointment(date, time);
    }


    public void fillArrayList(){

        //create string array to temporarily store data read from text file
        String customerData[] = new String[8];
        //create Customer reference variable to temporarily store existing customers from text file
        Customer existingCustomer;
        //String variable used to store each line from readLine()
        String line;

        //uses FileReader to read each line from text file and store into String array
        try{
            fileReader = new BufferedReader(new FileReader(getFile()));     
            while ((line = fileReader.readLine()) != null){
                customerData[0] = line;
                //instantiates new Customer object to be inserted into Customers ArrayList
                existingCustomer = new Customer();
                for(int i = 1; i < customerData.length; i++){
                    customerData[i] = fileReader.readLine();
                }

                //uses String array to populate customer's data members
                existingCustomer.setData(customerData[0],
                                            customerData[1],
                                            customerData[2],
                                            customerData[3],
                                            customerData[4],
                                            customerData[5],
                                            customerData[6],
                                            customerData[7]);

                //add newly instantiated Customer object to Customers ArrayList
                customers.add(existingCustomer);
            }
            fileReader.close();

        } catch (IOException e){
            System.out.println("File not found.");
        }
    }


    public void appendDatabase(){
        try{
            fileWriter = new BufferedWriter(new FileWriter(getFile(), true));
            customers.get(customers.size()-1).writeToDataBase(fileWriter);
            fileWriter.close();
        
        }catch (IOException e){
            System.out.println("File not found.");
        }
    }

    //searches through Customer ArrayList to find a customer by name
    //creates a copy of that customer and adds it to the end of the ArrayList
    //returns true if customer was found, false if customer was not found

    public boolean appendExistingCustomer(){

        for(Customer customer: customers){

            System.out.println(searchPanel.getTextFieldValue());
            System.out.println(customer.getName());
            if(searchPanel.getTextFieldValue().equals(customer.getName())){
                System.out.println("TEST");
                Customer existCust = new Customer(customer);
                customers.add(existCust);
                return true;
            }
        }

        searchPanel.setWarningLabel();
        return false;
    }
}

