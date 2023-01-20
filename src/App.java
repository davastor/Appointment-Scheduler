import javax.swing.SwingUtilities;
class App{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MainFrame();
            }
        });
    }
}
