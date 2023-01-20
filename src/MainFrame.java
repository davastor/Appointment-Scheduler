import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener{
    JPanel panelCont = new JPanel();
    FormPanel formPanel = new FormPanel();
    WelcomePanel welcomePanel = new WelcomePanel();
    AppointmentPanel appointPanel = new AppointmentPanel();
    CardLayout cl = new CardLayout();
    boolean existingCust;
    Customer customer = new Customer();

    MainFrame(){
        existingCust = false;
        this.setSize(500,500);
        setResizable(false);
        panelCont.setLayout(cl);
        panelCont.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(15, 15, 15, 15),  new EtchedBorder()));

        panelCont.add("welcome", welcomePanel);
        panelCont.add("form", formPanel);
        panelCont.add("appoint", appointPanel);
        cl.show(panelCont, "welcome");

        welcomePanel.getNewButton().addActionListener(this);
        welcomePanel.getExistButton().addActionListener(this);
        formPanel.getBackButton().addActionListener(this);
        formPanel.getSubmitButton().addActionListener(this);
        appointPanel.getBackButton().addActionListener(this);
        appointPanel.getNextButton().addActionListener(this);


        add(panelCont);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == welcomePanel.getNewButton()){
            cl.show(panelCont, "form"); 
        }
        else if(e.getSource() == welcomePanel.getExistButton()){
            cl.show(panelCont, "appoint");
            existingCust = true;
        }
        else if(e.getSource() == formPanel.getSubmitButton()){
            submitFormData();
            cl.next(panelCont);
        }
        else if(e.getSource() == formPanel.getBackButton()){
            cl.previous(panelCont);
        }
        else if(e.getSource() == appointPanel.getNextButton()){
            submitAppointment();
        }
        else if(e.getSource() == appointPanel.getBackButton()){
            if(existingCust){
                cl.show(panelCont, "welcome");
                existingCust = false;
            }
            else
                cl.previous(panelCont);
        }
    }

    private void submitFormData(){
        JTextField fields[] = formPanel.getTextFields();

        //checks to see if any fields are empty, sets warning message if true
        for(JTextField field: fields){
            if(field.getText().isEmpty()){
                formPanel.setFormWarning();
                return;
            }
        }

        customer.setData(fields);
    }

    private void submitAppointment(){
        if(appointPanel.getDatePicker().getComponentDateTextField().getText().isEmpty()||
        appointPanel.getTimePicker().getComponentTimeTextField().getText().isEmpty()){
            appointPanel.setAppointWarning();
            return;
        } 

        customer.setData(appointPanel.getDatePicker().getComponentDateTextField().getText(),
                        appointPanel.getTimePicker().getComponentTimeTextField().getText());
    }

   

}