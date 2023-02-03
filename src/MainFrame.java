import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


//Inherits JFrame to contain JPanels
//All other panels are contained and navigated via this class
//Implements action listener to handle events such as button presses
//Implements CustomerDatabase interface to read/write to text file
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
    BufferedReader fileReader;
    BufferedWriter fileWriter;

    MainFrame(){
        //fill customer ArrayList with existing customers
        fillArrayList();
        //fill occupied dates/times using information from existing customers
        appointPanel.fillCustomers(customers);

        
        existingCust = false;

        //set window size to 500x500 
        //prevent window from being resized to maintain proper layout
        this.setSize(500,500);
        setResizable(false);

        //set layout for main JPanel container to CardLayout,
        //as this will be the method for navigating through the
        //different panels
        panelCont.setLayout(cl);
        panelCont.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(15, 15, 15, 15),  new EtchedBorder()));

        //add each panel object to the main container
        panelCont.add("welcome", welcomePanel);
        panelCont.add("search", searchPanel);
        panelCont.add("form", formPanel);
        panelCont.add("appoint", appointPanel);
        panelCont.add("confirm", confirmPanel);
        //sets first panel shown as the welcome panel
        cl.show(panelCont, "welcome");

        //Add test customer to ArrayList
        //customers.add(testCust);

        //add action listeners for all navigation buttons
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

        //add main JPanel container to JFrame
        add(panelCont);
        //set JFrame to be visible
        setVisible(true);
        //close JFrame upon pressing X in top right corner
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //function to handle ActionEvents
    public void actionPerformed(ActionEvent e){
        //if customer presses "New" button, go to Form Panel
        if(e.getSource() == welcomePanel.getNewButton()){
            cl.show(panelCont, "form");
        }
        //if customer presses "Existing" button, go to Search Panel
        //set existingCust to true so that "existing" customer
        //cannot navigate to the wrong panels, i.e. Form Panel
        else if(e.getSource() == welcomePanel.getExistButton()){
            existingCust = true;
            cl.show(panelCont, "search");
        }
        //if customer presses "Back" button on search panel, go back
        //to welcome panel, set existingCust to false
        //to re-enable other panels for navigation
        else if(e.getSource() == searchPanel.getBackBtn()){
            existingCust = false;
            cl.show(panelCont, "welcome");
        }
        //if customer pressed "Next" buttonn on search panel, calls
        //appendExistingCustomer method to check for existing customer
        //if so, create copy of existing customer
        //and append it to the end of the ArrayList of Customers
        //for entry of unique date/time values but with existing customer
        //contact information and address to the database
        //if appending is successful, go to appointment panel
        //if not successful, set warning label to indicate unsuccessful search
        //and return to prevent further action
        else if(e.getSource() == searchPanel.getNextBtn()){
            if(appendExistingCustomer()){
                cl.show(panelCont, "appoint");
            }
            else{
                searchPanel.setWarningLabel();
                return;
            }
         
        //if customer presses "Back" button of Form panel,
        //go back to Welcome panel
        }        
        else if(e.getSource() == formPanel.getBackBtn()){
            cl.show(panelCont, "welcome");
        }
        //if customer pressed "Next" button of form panel,
        //call submitFormData method to substantiate
        //new Customer object with JTextField values
        //if a field is empty or customer information already
        //exists in textfile, warning message will be set
        //then method will return to prevent further action
        //if data submit is successful, go to appointment panel
        else if(e.getSource() == formPanel.getNextBtn()){
            if(!submitFormData())
                return;
            else
                cl.show(panelCont, "appoint");
        }
        //if customer presses "Next" button, calls
        //submitAppointment method to submit date and time
        //to customer object, set confirmation message
        //for confirmation panel, then navigate to
        //confirmation panel
        else if(e.getSource() == appointPanel.getNextBtn()){
            submitAppointment();
            confirmPanel.setLabel(customers.get(customers.size()-1));
            cl.next(panelCont);
        }
        //if customer pressed "Back" button of appointment panel,
        //go back to search panel if existingCust is true,
        //otherwise go back to form panel
        else if(e.getSource() == appointPanel.getBackBtn()){
            if(existingCust){
                cl.show(panelCont, "search");
                existingCust = false;
            }
            else{
                cl.show(panelCont, "form");
                //remove new customer object in case customer decides to resubmit the form or search up a different name
                //prevents extra Customer objects from being added to the ArrayList
                customers.remove(customers.size()-1);
            }
        }
        //if customer presses "Back" button on confirmation panel,
        //go back to appointment panel and set previous date/time selections
        //for customer to null, so that the date/time can be free'd up on the
        //date/time pickers
        else if(e.getSource() == confirmPanel.getBackButton()){
            //undo customer's previous selection of date and time
            customers.get(customers.size()-1).setAppointmentNull();
            cl.previous(panelCont);
        }
        //if customer presses "Confirm" button of confirmation panel,
        //call appendDatabse method to
        //write new Customer information to textfile 'database'
        //and close the window
        else{
            appendDatabase();
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    //submits data in text fields, returns false if fields 
    //are incomplete or if customer already exists, 
    //otherwise return true 
    //to determine if layout should go to next panel
    private boolean submitFormData(){

        //gets the text field object array from formPanel and sets it to local variable
        JTextField fields[] = formPanel.getTextFields();

        //runs through object array
        //checks to see if any fields are empty, sets warning message if true
        for(JTextField field: fields){
            if(field.getText().isEmpty()){
                formPanel.setFormWarning("Empty");
                return false;
            }
        }

        //checks to see if gallons text field has invalid values, 
        //if so, set warning message and return false
        //so panel does not navigate to next screen
        if(!checkGallons(fields[6].getText())){
            formPanel.setFormWarning("Gallons");
            return false;
        }

        //create new customer object and sets its data members equal to JTextField values
        //and JRadioButton selection
        Customer newCustomer = new Customer();
        newCustomer.setData(fields, formPanel.getTypeSelection());

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
        //and returns void method to prevent further actions until fields are filled
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

    //method to read data from textfile to account for already existing customer accounts
    //fills Customer ArrayList with new Customer objects that contain data
    //read from textfile
    public void fillArrayList(){

        //create string array to temporarily store data read from text file
        String customerData[] = new String[11];
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
                existingCustomer.setData(customerData);

                //add newly instantiated Customer object to Customers ArrayList
                customers.add(existingCustomer);
            }
            fileReader.close();

        } catch (IOException e){
            System.out.println("File not found.");
        }
    }

    //appends new customer to text file database
    public void appendDatabase(){
        try{
            fileWriter = new BufferedWriter(new FileWriter(getFile(), true));
            //uses writeToDatabse method within Customer class and passes fileWriter
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
            String searchValue = searchPanel.getTextFieldValue().toLowerCase();
            String customerName = customer.getName().toLowerCase();

            System.out.println(searchValue);
            System.out.println(customerName);
            if(searchValue.equals(customerName)){
                Customer existCust = new Customer(customer);
                customers.add(existCust);
                return true;
            }
        }
        return false;
    }


    //checks to see if text fields for Gallons is a valid integer
    //between 1 to 200, returns true if so, false if not
    //must parse String into an Int as JTextField values are strings
    //try and catch block to catch exception if String cannot be
    //parsed into an integer
    public boolean checkGallons(String string) {
        int intValue;
        try {
            intValue = Integer.parseInt(string);
            if(intValue < 1 || intValue > 200)
                return false;
            else
                return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            return false;
        }
    }
}