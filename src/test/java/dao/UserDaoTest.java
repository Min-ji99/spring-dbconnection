package dao;

import domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    public void addAndGet(){
        UserDao userDao=new UserDao();
        userDao.add(new User("4", "nana", "1234"));
        User user=userDao.findById("4");
        assertEquals("nana", user.getName());
    }

}