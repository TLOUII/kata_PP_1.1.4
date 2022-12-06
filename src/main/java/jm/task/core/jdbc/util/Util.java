package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    private static  final Logger log = Logger.getLogger(Util.class.getName());
    private final static Properties properties = new Properties();
    private static Connection connection;

    public static Connection getConnection() {
        try (InputStream is = new FileInputStream("src/main/resources/database.properties")) {
            properties.load(is);
            connection = DriverManager.getConnection(properties.getProperty("datasource.url"),
                    properties.getProperty("datasource.username"),
                    properties.getProperty("datasource.password"));
            log.fine("Connection complete");
        } catch (Exception e) {
            log.warning("Нет соединения с БД что-то с пропертями");
        }
        return connection;
    }
    public static void closeConnection (){
        try {
            connection.close();
            log.info("Соединение с бд закрыто");
        } catch (SQLException e) {
            log.warning("Соединение с БД не закрылось");
        }
    } // Закрытие соединения.

}

    // реализуйте настройку соеденения с БД

