package pl.coderslab.entity;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User();
//        user.setUserName("Paulina");
//        user.setEmail("paulina@gmail.com");
//        user.setPassword("haslo");
//        userDao.create(user);
//        userDao.read(3);
//        userDao.delete(1);
//        User updateUser = new User();
//        updateUser.setUserName("Paulina");
//        updateUser.setEmail("Paulina@gmail.com");
//        updateUser.setPassword("pass1");
//        updateUser.setId(3);
//        userDao.update(updateUser);
//        User user1 = new User();
//        user1.setUserName("Kornelia");
//        user1.setEmail("Kornelia@op.pl");
//        user1.setPassword("asd");
//        userDao.create(user1);
        User[] all = userDao.findAll();
        for (User u : all) {
            System.out.println(u);
        }






    }
}
