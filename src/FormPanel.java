import javax.swing.*;
import java.awt.event.*;

public class FormPanel extends JPanel{
    JButton submitBtn = new JButton("Submit");
    JButton backBtn = new JButton("Back");
    JPanel buttonCont = new JPanel();
    String lineNames[] = {"Name:", "Street:", "City:", "State:", "Zipcode:", "Phone Number:"};
    JLabel warningLabel = new JLabel(" ");
    JLabel existWarningLabel = new JLabel(" ");
    JLabel labels[] = new JLabel[6];
    JTextField fields[] = new JTextField[6];
    JPanel containers[] = new JPanel[6];

    FormPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        for(int i = 0; i < lineNames.length; i++){
            containers[i] = new JPanel();
            containers[i].setLayout(new BoxLayout(containers[i], BoxLayout.LINE_AXIS));
            labels[i] = new JLabel(lineNames[i]);
            fields[i] = new JTextField(30);
            fields[i].setMaximumSize(fields[i].getPreferredSize());
            containers[i].add(labels[i]);
            containers[i].add(Box.createHorizontalGlue());
            containers[i].add(fields[i]);
            add(containers[i]);
        }
        buttonCont.add(backBtn);
        buttonCont.add(submitBtn); 

        add(warningLabel);
        add(buttonCont);
        add(existWarningLabel);
    }

    public JButton getBackButton() {
        return backBtn;
    }

    public JButton getSubmitButton(){
        return submitBtn;
    }

    public JTextField[] getTextFields(){
        return fields;
    }

    public void setFormWarning(){
        warningLabel.setText("Please fill out every field.");
    }

    public void setExistWarning(){
        existWarningLabel.setText("You are already an existing customer.  Here is a reminder of your ID: ");
    }
} 