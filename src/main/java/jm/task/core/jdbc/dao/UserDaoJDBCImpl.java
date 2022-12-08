package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private  List<User> userList = new ArrayList<>();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "Users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(45) NOT NULL," +
                    "last_name VARCHAR(45) NOT NULL," +
                    "age TINYINT NULL) ");
            Util.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    } // Создание таблиц

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE users");
            Util.closeConnection();
        } catch (SQLException e) {
            logger.warning("Ошибка в методе dropUserTable ");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepState = Util.getConnection().prepareStatement(" INSERT INTO kata_pp.users (name,last_name,age) VALUES (?, ?, ? )")) {
            prepState.setString(1, name);
            prepState.setString(2, lastName);
            prepState.setByte(3, age);
            prepState.execute();
            logger.info("Добавлен пользователь " + "[Name = " + name + ", lastName = " + lastName + ", age = " + age + "]");
            Util.closeConnection();
        } catch (SQLException e) {
            logger.warning("Данные об обекте не добавились в методе saveUser");
        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement prepState = Util.getConnection().prepareStatement(" DELETE FROM users WHERE id = (?);")) {
            prepState.setLong(1, id);
            prepState.execute();
            Util.closeConnection();
        } catch (SQLException e) {
            logger.warning("Удаление пошло не по плану в методе removeUserByID");
        }
    }

    public List<User> getAllUsers() {
        try (Statement stmt = Util.getConnection().createStatement();
             ResultSet rSet = stmt.executeQuery("SELECT * FROM kata_pp.users")) {
            while (rSet.next()) {
                long id = rSet.getLong(1);
                String name = rSet.getString(2);
                String lastName = rSet.getString(3);
                byte age = rSet.getByte(4);
                User use = new User(name, lastName, age);
                use.setId(id);
                userList.add(use);
            }
            Util.closeConnection();
        } catch (SQLException e) {
            logger.warning("При добавлении студентов в список что-то пошло не по плану в методе getAllUsers");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DELETE FROM kata_pp.users ");
            logger.fine("Table is cleaned");
            Util.closeConnection();
        } catch (SQLException e) {
            logger.warning("Удаление не получилось в методе cleanUsersTable ");
        }

    }
}
