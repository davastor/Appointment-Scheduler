import javax.swing.JButton;
import javax.swing.*;

//class that defines Back and Next buttons
//use as generic template for all panels
//that employ use of "Back" and "Next"
//use to reduce similar/same code
public class GenericPanel extends JPanel{
    JButton nextBtn = new JButton("Next");
    JButton backBtn = new JButton("Back");
    JPanel btnCont = new JPanel();

    public JButton getNextBtn() {
        return nextBtn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }
}
