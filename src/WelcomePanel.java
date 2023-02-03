import javax.swing.BoxLayout;
import javax.swing.*;

public class WelcomePanel extends JPanel{
    //label to greet customer
    private JLabel welcomeLabel = new JLabel("Welcome.  Are you a new or existing customer?");
    //container to contain navigation buttons
    private JPanel btnCont = new JPanel();
    //create "New" button
    private JButton newBtn = new JButton("New");
    //create "Existing" button
    private JButton existBtn = new JButton("Existing");

    WelcomePanel(){
        //set layout as box layout and vertically align items
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //add buttons to container
        //for easier and cleaner management
        btnCont.add(newBtn);
        btnCont.add(existBtn);
        //add welcome label and buttons
        //to main panel
        add(welcomeLabel);
        add(btnCont);

    }

    public JButton getNewButton(){
        return newBtn;
    }

    public JButton getExistButton(){
        return existBtn;
    }

}