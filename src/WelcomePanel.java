import javax.swing.BoxLayout;
import javax.swing.*;
import java.awt.event.*;

public class WelcomePanel extends JPanel{

    private JLabel welcomeLabel = new JLabel("Welcome.  Are you a new or existing customer?");
    private JPanel btnCont = new JPanel();
    private JButton newBtn = new JButton("New");
    private JButton existBtn = new JButton("Existing");

    WelcomePanel(){
        btnCont.add(newBtn);
        btnCont.add(existBtn);
        add(welcomeLabel);
        add(btnCont);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    }

    public JButton getNewButton(){
        return newBtn;
    }

    public JButton getExistButton(){
        return existBtn;
    }

}