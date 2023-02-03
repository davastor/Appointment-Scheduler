import java.io.File;


//interface that opens the customerData.txt file

public interface CustomerDatabase {
    File file = new File("customerData.txt");

    default File getFile(){
        return file;
    }

    public void fillArrayList();
    public void appendDatabase();
}
