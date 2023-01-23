import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;

public class FormPanel extends GenericPanel{
    String lineNames[] = {"Name:", "Street:", "City:", "State:", "Zipcode:", "Phone Number:"};
    JPanel buttonCont = new JPanel();
    JLabel warningLabel = new JLabel(" ");
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
        btnCont.add(backBtn);
        btnCont.add(nextBtn); 

        add(warningLabel);
        add(Box.createVerticalGlue());
        add(btnCont);
    }

    public JTextField[] getTextFields(){
        return fields;
    }

    public void setFormWarning(){
        warningLabel.setText("Please fill out every field.");
    }

    public void setExistWarning(){
        warningLabel.setText("You are already an existing customer.  Here is a reminder of your ID: ");
    }
} 