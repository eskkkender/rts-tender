package utilities;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static PropertyManager instance;
    private static final Object lock = new Object();
    private static String propertyFilePath = System.getProperty("user.dir")+
            "\\src\\test\\java\\resources\\config.properties";
    private static String beginDateNotice;
    private static String endDateNotice;

    //Create a Singleton instance. We need only one instance of Property Manager.
    public static PropertyManager getInstance () {
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertyManager();
                instance.loadData();
            }
        }
        return instance;
    }

    //Get all configuration data and assign to related fields.
    private void loadData() {
        //Declare a properties object
        Properties prop = new Properties();

        //Read configuration.properties file
        try {
            prop.load(new FileInputStream(propertyFilePath));
            //prop.load(this.getClass().getClassLoader().getResourceAsStream("configuration.properties"));
        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found");
        }

        //Get properties from configuration.properties
        beginDateNotice= prop.getProperty("beginDateNotice");
        endDateNotice = prop.getProperty("endDateNotice");
    }



    public String getBeginDateNotice (){ return beginDateNotice;}
    public String getEndDateNotice () {return endDateNotice;}
}
