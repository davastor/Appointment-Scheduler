import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

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
