import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;



public interface CustomerDatabase {
    File file = new File("customerData.txt");

    default File getFile(){
        return file;
    }

    public void fillArrayList();
    public void appendDatabase();
}
