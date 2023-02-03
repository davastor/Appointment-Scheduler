import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.time.LocalDate;
import java.awt.BorderLayout;
import com.github.lgooddatepicker.components.*;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

//inherits genericpanel for next and back button and methods
//implements DateChangeListener, a custom interface from a public resource
//to listen for changes to the Date Picker and Time picker
public class AppointmentPanel extends GenericPanel implements DateChangeListener{
    //set tomorrow as first available date in Datepicker
    private LocalDate startDate = LocalDate.now().plusDays(1);
    //set one year later from now as last available date in Datepicker
    private LocalDate endDate = LocalDate.now().plusYears(1);

    //set increments of time for Time picker
    private TimeIncrement inc = TimeIncrement.OneHour;
    //set first available time for TimePicker
    private LocalTime startTime = LocalTime.of(9, 0, 0,0);
    //set last available time for TimePicker
    private LocalTime endTime = LocalTime.of(17, 0, 0, 0);

    private ArrayList<Customer>customers;

    //Date and Time pickers from public resource
    private DatePicker datePicker = new DatePicker();
    private TimePicker timePicker = new TimePicker();
    //container to contain Date and Time picker for
    //easier layout management
    private JPanel pickerCont = new JPanel();
    private JPanel btnCont = new JPanel();
    //label for appointment panel to ask for user input
    private JLabel greetLabel = new JLabel("When would you like to have your appointment?", JLabel.CENTER);
    private JLabel warningLabel = new JLabel("");
    private JPanel pickerLabelCont = new JPanel();

    AppointmentPanel(){
        //uses TimePicker methods to generate custom list of times from 9AM to 5PM with one hour increments
        timePicker.getSettings().generatePotentialMenuTimes(inc, startTime, endTime);

        //uses DatePicker methods to set date range from tomorrow to one year later
        datePicker.getSettings().setDateRangeLimits(startDate, endDate);
        

        //set layout as Border
        setLayout(new BorderLayout());
        //add date time pickers to JPanel container
        pickerCont.add(datePicker);
        pickerCont.add(timePicker);
        //add actionlistener to listen for change events
        datePicker.addDateChangeListener(this);
        //add back and next buttons to button container
        //for cleaner layout management
        btnCont.add(backBtn);
        btnCont.add(nextBtn);
        //add picker and warning label to another container for
        //easier and cleaner layout
        pickerLabelCont.add(pickerCont);
        pickerLabelCont.add(warningLabel);
        pickerLabelCont.setLayout(new BoxLayout(pickerLabelCont, BoxLayout.PAGE_AXIS));

        //add greeting message, date/time picker, warning
        //message and navigation buttons to main panel
        add(greetLabel, BorderLayout.PAGE_START);
        add(pickerLabelCont, BorderLayout.CENTER);
        add(btnCont, BorderLayout.PAGE_END);
    }
    
   public DatePicker getDatePicker(){
        return datePicker;
   }

   public TimePicker getTimePicker(){
        return timePicker;
   }

   //set warning message if customer sets invalid or incomplete fields
   public void setAppointWarning(){
        warningLabel.setText("Please select a date/time.");
   }

   //fills arrayList with existing customers to evaluate
   //occupied dates/times
   public void fillCustomers(ArrayList<Customer> cust){
        customers = cust;
         
   }

   //removes times from each date if it is already
   //chosen by another customer
   public void removeTimes(DateChangeEvent event){
    timePicker.getSettings().setVetoPolicy(new TimeVetoPolicy() {
        
        //converts LocalDate object to string
        String chosenDate = event.getNewDate().toString();
    
        //returns false if time is already occupied for the specified date
        public boolean isTimeAllowed(LocalTime time){
            String formattedTime = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
  
            for(Customer customer: customers){
        
                if((chosenDate.equals(customer.getDate())) &&
                    (formattedTime.equals(customer.getTime()))){
                        return false;
                    }          
            }
            //returns true if time is allowed, i.e. no other customer
            //is occupying that appointment time
            return true;
        }
    });
   }

   //clears time picker field upon changing the date
   //calls removeTimes to remove times for specified date
    public void dateChanged(DateChangeEvent event) {
        timePicker.clear();
        removeTimes(event);
    
    }
}