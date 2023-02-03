import javax.swing.*;
import java.awt.event.*;

//inherits GenericPanel for back and next button and methods
//implements action listener to handle events for radio buttons
public class FormPanel extends GenericPanel implements ActionListener{
    //array of String Labels to populate JTextFields
    private String lineNames[] = {"Name:", "Street:", "City:", "State:", "Zipcode:", "Phone Number:", "Gallons: "};
    //Panel to contain back and next button for navigation
    private JPanel buttonCont = new JPanel();
    //label to set warning message if any fields are left empty
    private JLabel warningLabel = new JLabel(" ");
    private JLabel labels[] = new JLabel[7];
    private JTextField fields[] = new JTextField[7];
    //array of JPanel containers to contain JLabel and respective JTextField
    private JPanel containers[] = new JPanel[7];
    //radio buttons and respective container for customer to select type of aquarium
    private JRadioButton saltRadio = new JRadioButton("Saltwater");
    private JRadioButton freshRadio = new JRadioButton("Freshwater");
    //special container to allow only one radio button to be selected
    private ButtonGroup btnGroup = new ButtonGroup();
    //Panel container to contain radio buttons for display
    private JPanel radioCont = new JPanel();
    private String aquariumTypeSelection;

    FormPanel(){
        //set layout of main container to be Box layout laid out vertically
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Create JTextFields using the lineNames array
        //Use for loop to reduce lines of code
        for(int i = 0; i < lineNames.length; i++){
            //create new JPanel object for each element in containers array
            containers[i] = new JPanel();
            //set the layout for each container to be box Layout horizontally
            containers[i].setLayout(new BoxLayout(containers[i], BoxLayout.LINE_AXIS));
            //grab strings from lineNames to create new JLabel objects
            labels[i] = new JLabel(lineNames[i]);
            //set character width for JTextFields
            fields[i] = new JTextField(30);
            //limit size of JTextField to prevent from taking up exra vertical/horizontal space
            fields[i].setMaximumSize(fields[i].getPreferredSize());
            //add JLabel and JTextField to JPanel container and use createHorizontalGlue
            //to spread them apart
            containers[i].add(labels[i]);
            containers[i].add(Box.createHorizontalGlue());
            containers[i].add(fields[i]);
            //add JPanel container to main container for this page
            add(containers[i]);
        }
        //add each button to button JPanel container for 
        //cleaner layout management
        btnCont.add(backBtn);
        btnCont.add(nextBtn); 
        
        //add each radio button to button group so that
        //only one radio button can be selected at a time
        btnGroup.add(saltRadio);
        btnGroup.add(freshRadio);
        //add each radio button to JPanel container for 
        //cleaner layout management
        radioCont.add(saltRadio);
        radioCont.add(freshRadio);
        //radioMenu.set

        //add containers and warning label to main container
        add(radioCont);
        add(warningLabel);
        //create vertical space bewtween fields/radiobuttons
        //and navigationbuttons
        add(Box.createVerticalGlue());
    
        add(btnCont);

        //add action listeners for radio buttons
        //to set type of aquarium
        saltRadio.addActionListener(this);
        freshRadio.addActionListener(this);
        //set saltwater radiobutton and type as default
        saltRadio.setSelected(true);
        aquariumTypeSelection = "salt";
    }

    public JTextField[] getTextFields(){
        return fields;
    }

    //sets warning message if any JTextField is empty
    //or if gallons field has invalid value
    public void setFormWarning(String warning){

        if (warning == "Empty")
            warningLabel.setText("Please fill out every field.");
        if (warning == "Gallons")
            warningLabel.setText("Please enter in a whole number between 1 and 200.");
    }

    //sets warning message if customer tries to submit form data
    //for an already existing customer
    public void setExistWarning(){
        warningLabel.setText("You are already an existing customer.");
    }

    //implements action performed when actionevent is received
    //if source of event is freshwater Radio button,
    //set aquarium type to fresh water, and vice versa
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == freshRadio){
            aquariumTypeSelection = "fresh";
        }
        else{
            aquariumTypeSelection = "salt";
        }
    }

    public String getTypeSelection(){
        return aquariumTypeSelection;
    }
} 