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
    private String url;
    private String user;
    private String pass;

    private ConnectionSingleton() {
        FileInputStream fis = null;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
            Class.forName(property.getProperty("driver"));
            this.url = property.getProperty("url");
            this.user = property.getProperty("user");
            this.pass = property.getProperty("pass");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static ConnectionSingleton getInstance() {
        if (connection == null) {
            connection = new ConnectionSingleton();
        }
        return connection;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = DriverManager.getConnection(url, user, pass);
        return con;
    }
}
