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


public class AppointmentPanel extends GenericPanel implements DateChangeListener{
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusYears(1);

    TimeIncrement inc = TimeIncrement.OneHour;
    LocalTime startTime = LocalTime.of(9, 0, 0,0);
    LocalTime endTime = LocalTime.of(17, 0, 0, 0);

    private ArrayList<Customer>customers;
    private DatePicker datePicker = new DatePicker();
    private TimePicker timePicker = new TimePicker();
    private JPanel pickerCont = new JPanel();
    private JPanel btnCont = new JPanel();
    private JLabel greetLabel = new JLabel("When would you like to have your appointment?", JLabel.CENTER);
    private JLabel warningLabel = new JLabel("");
    private JPanel pickerLabelCont = new JPanel();

    AppointmentPanel(){
        //uses TimePicker methods to generate custom list of times from 9AM to 5PM with one hour increments
        timePicker.getSettings().generatePotentialMenuTimes(inc, startTime, endTime);

        //uses DatePicker methods to set date range from tomorrow to one year later
        datePicker.getSettings().setDateRangeLimits(startDate, endDate);
        


        setLayout(new BorderLayout());
        pickerCont.add(datePicker);
        pickerCont.add(timePicker);
        datePicker.addDateChangeListener(this);
        btnCont.add(backBtn);
        btnCont.add(nextBtn);
        pickerLabelCont.add(pickerCont);
        pickerLabelCont.add(warningLabel);
        pickerLabelCont.setLayout(new BoxLayout(pickerLabelCont, BoxLayout.PAGE_AXIS));

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

   public void setAppointWarning(){
        warningLabel.setText("Please select a date/time.");
   }

   public void fillDates(ArrayList<Customer> cust){
        customers = cust;
         
   }


   public void removeTimes(DateChangeEvent event){
    timePicker.getSettings().setVetoPolicy(new TimeVetoPolicy() {
        
        String chosenDate = event.getNewDate().toString();
    
        public boolean isTimeAllowed(LocalTime time){
            String formattedTime = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
  
            for(Customer customer: customers){
        
                if((chosenDate.equals(customer.getDate())) &&
                    (formattedTime.equals(customer.getTime()))){

                        return false;
                    }          
            }

            if(time.getMinute() != 0)
                return false;
            if(time.getSecond() != 0)
                return false;
            if(time.getNano() != 0)
                return false;
            
            return true;
        }
    });
   }

@Override
    public void dateChanged(DateChangeEvent event) {
        timePicker.clear();
        removeTimes(event);
    
    }
}