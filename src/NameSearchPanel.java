import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//inherits GenericPanel for navigation buttons
public class NameSearchPanel extends GenericPanel{
    //label to ask user for input
    private JLabel questionLabel = new JLabel("What is your name?", JLabel.CENTER);
    private JLabel warningLabel = new JLabel("", JLabel.CENTER);
    private JTextField searchField = new JTextField( 30);
    private JPanel warningSearchCont = new JPanel();

    NameSearchPanel(){
        setLayout(new BorderLayout());

        //JPanel containers to contain navigation buttons
        //for easier and cleaner management
        btnCont.add(backBtn);
        btnCont.add(nextBtn);

        //add search text field and warning label
        //to container for easier and cleaner management
        warningSearchCont.add(searchField);
        warningSearchCont.add(warningLabel);

        //set size of search text field to preferred size, which
        //is size of text font, so that text field
        //does not take up all the available vertical space
        //also aligns text input in the center of the field
        searchField.setMaximumSize(searchField.getPreferredSize());
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);

        //add labels, text field, and navigation buttons
        //to main container and set their positions
        add(questionLabel, BorderLayout.PAGE_START);
        add(warningSearchCont, BorderLayout.CENTER);
        add(btnCont, BorderLayout.PAGE_END); 

    }

    public String getTextFieldValue(){
        return searchField.getText();
    }

    //sets warning message if customer name could not be found
    public void setWarningLabel(){
        warningLabel.setText("Could not find this name.  Please try again.");
    }


}
