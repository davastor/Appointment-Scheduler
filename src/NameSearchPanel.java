import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NameSearchPanel extends GenericPanel{
    JLabel questionLabel = new JLabel("What is your name?", JLabel.CENTER);
    JLabel warningLabel = new JLabel("", JLabel.CENTER);
    JTextField searchField = new JTextField( 30);
    JPanel warningSearchCont = new JPanel();

    NameSearchPanel(){
        setLayout(new BorderLayout());

        btnCont.add(backBtn);
        btnCont.add(nextBtn);


        
    
        //warningSearchCont.setLayout(new BoxLayout(warningSearchCont, BoxLayout.PAGE_AXIS));
        warningSearchCont.add(searchField);
        warningSearchCont.add(warningLabel);

        searchField.setMaximumSize(searchField.getPreferredSize());
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(questionLabel, BorderLayout.PAGE_START);
        add(warningSearchCont, BorderLayout.CENTER);
        add(btnCont, BorderLayout.PAGE_END);

    }

    public String getTextFieldValue(){
        return searchField.getText();
    }

    public void setWarningLabel(){
        warningLabel.setText("Could not find this name.  Please try again.");
    }


}
