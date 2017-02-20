package Lab2.Helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Ирина on 20.02.2017.
 */
public class ConnectionSingleton {
    private static ConnectionSingleton connection;

    private ConnectionSingleton() {
    }

    public static ConnectionSingleton getInstance() {
        if (connection == null) {
            connection = new ConnectionSingleton();
        }
        return connection;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Properties property = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

            Class.forName(property.getProperty("driver"));
            String url = property.getProperty("url");
            String user = property.getProperty("user");
            String pass = property.getProperty("pass");
            con = DriverManager.getConnection(url, user, pass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }

}
