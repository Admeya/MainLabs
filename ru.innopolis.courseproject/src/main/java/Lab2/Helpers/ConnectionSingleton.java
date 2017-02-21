package Lab2.Helpers;

import org.apache.log4j.Logger;

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
    static Logger logger = Logger.getLogger(ConnectionSingleton.class);

    private static ConnectionSingleton connection;
    private String url;
    private String user;
    private String pass;
    private String driver;

    private ConnectionSingleton() {
        FileInputStream fis = null;
        Properties property = new Properties();
        String path = "src/main/resources/config.properties";
        try {
            fis = new FileInputStream(path);
            property.load(fis);
            this.driver = property.getProperty("driver");
            this.url = property.getProperty("url");
            this.user = property.getProperty("user");
            this.pass = property.getProperty("pass");
            Class.forName(driver);
        } catch (FileNotFoundException e) {
            logger.error("Конфигурационный файл " + path + " не найден", e);
        } catch (IOException e) {
            logger.error("Невозможно прочитать данные из файла " + path, e);
        } catch (ClassNotFoundException e) {
            logger.error("Не найден класс " + driver, e);
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
