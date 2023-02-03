import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ConfirmationPanel extends JPanel{
    //use TextPane instead of JLabel for wrapping and styling purposes
    private JTextPane confirmMsg = new JTextPane();
    private JButton confirmBtn = new JButton("Confirm");
    private JButton backBtn = new JButton("Back");
    private JPanel btnCont = new JPanel();

    ConfirmationPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //allows confirmation message to wrap and align in the center
        StyledDocument doc = confirmMsg.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        confirmMsg.setOpaque(false);
        confirmMsg.setEditable(false);
        confirmMsg.setFocusable(false);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        //add btns to container for easier layout management
        btnCont.add(backBtn);
        btnCont.add(confirmBtn);
        //add confirmation Label and button container to main panel
        add(confirmMsg);
        add(btnCont);
    }

    //set confirmation message detailing customer's choice of appointment
    //and respective fee
    public void setLabel(Customer cust){
        DecimalFormat dc = new DecimalFormat(".00");
        confirmMsg.setText("Hello " + cust.getName() + ", your aquarium service technician will see you on " + cust.getDate() + " at " + cust.getTime() + ".\n"
                            + "Your fee will be $" + dc.format(cust.getCost()) + ". \nPlease confirm this appointment." );
    }

    public JButton getConfirmButton(){
        return confirmBtn;
    }

    public JButton getBackButton(){
        return backBtn;
    }

    
}
