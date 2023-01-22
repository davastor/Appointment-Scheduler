import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ConfirmationPanel extends JPanel{
    //use TextPane instead of JLabel for wrapping and styling purposes
    JTextPane confirmMsg = new JTextPane();
    JButton confirmBtn = new JButton("Confirm");
    JButton backBtn = new JButton("Back");
    JPanel btnCont = new JPanel();

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


        btnCont.add(backBtn);
        btnCont.add(confirmBtn);
        add(confirmMsg);
        add(btnCont);
    }

    public void setLabel(Customer cust){
        confirmMsg.setText("Hello " + cust.getName() + ", your service will see you on " + cust.getDate() + " at " + cust.getTime() + ".\n"
                            + "Please confirm this appointment.");
    }

    public JButton getConfirmButton(){
        return confirmBtn;
    }

    public JButton getBackButton(){
        return backBtn;
    }

    
}
