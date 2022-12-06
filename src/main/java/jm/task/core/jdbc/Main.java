package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    private static UserServiceImpl userService = new UserServiceImpl();

    public static void main(String[] args) {

        userService.createUsersTable();

        userService.saveUser("Steven", "Johnson", (byte) 27);
        userService.saveUser("Eduard", "Malik", (byte) 29);
        userService.saveUser("Valeriy", "Abramovich", (byte) 21);
        userService.saveUser("Nikolai", "Vasilievich", (byte) 32);

        userService.removeUserById(2);
        for ( User all : userService.getAllUsers()) {
            System.out.println(all);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}


