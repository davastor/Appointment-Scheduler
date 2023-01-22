import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.time.LocalDate;
import java.awt.BorderLayout;
import com.github.lgooddatepicker.components.*;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;


public class AppointmentPanel extends JPanel{
    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = LocalDate.now().plusYears(1);

    TimeIncrement inc = TimeIncrement.OneHour;
    LocalTime startTime = LocalTime.of(9, 0, 0,0);
    LocalTime endTime = LocalTime.of(17, 0, 0, 0);
    LocalDate specifiedDate = LocalDate.of(2023, 01, 26);
    LocalTime specifiedTime = LocalTime.of(10, 0, 0, 0);

    private DatePicker datePicker = new DatePicker();
    private TimePicker timePicker = new TimePicker();
    private JPanel pickerCont = new JPanel();
    private JPanel btnCont = new JPanel();
    private JLabel greetLabel = new JLabel("When would you like to have your appointment?", JLabel.CENTER);
    private JLabel warningLabel = new JLabel("");
    private JButton nextBtn = new JButton("Next");
    private JButton backBtn = new JButton("Back");

    AppointmentPanel(){
        //uses TimePicker methods to generate custom list of times from 9AM to 5PM with one hour increments
        timePicker.getSettings().generatePotentialMenuTimes(inc, startTime, endTime);

        setLayout(new BorderLayout());
        pickerCont.add(datePicker);
        pickerCont.add(timePicker);
        btnCont.add(backBtn);
        btnCont.add(nextBtn);

        add(greetLabel, BorderLayout.PAGE_START);
        add(pickerCont, BorderLayout.CENTER);
        add(btnCont, BorderLayout.PAGE_END);
    }
    
   public JButton getBackButton(){
        return backBtn;
   }

   public JButton getNextButton(){
        return nextBtn;
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

   public void removeDatesAndTimes(ArrayList<Customer> customers){

        datePicker.getSettings().setVetoPolicy(new DateVetoPolicy() {
            public boolean isDateAllowed(LocalDate date){

                for(Customer customer: customers){

                    if(date.toString().equals(customer.getDate())){ 
                        
                        return false;
                    }
            
                }
                if(date.isBefore(startDate))
                    return false;
                if(date.isAfter(endDate))
                    return false;
                
                return true;
            }
        });

        timePicker.getSettings().setVetoPolicy(new TimeVetoPolicy() {
            public boolean isTimeAllowed(LocalTime time){
                String formattedTime = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
      
                for(Customer customer: customers){
                    System.out.println(formattedTime);
                    System.out.println(customer.getTime());
                    if(formattedTime.equals(customer.getTime()))
                        return false;
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
}