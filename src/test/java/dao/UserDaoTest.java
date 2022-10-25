package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    UserDao userDao;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setup(){
        userDao=context.getBean("awsUserDao", UserDao.class);
        user1=new User("1", "minji", "1234");
        user2=new User("2", "nana", "5678");
        user3=new User("3", "mimi", "1111");
    }
    @Test
    @DisplayName("insert 확인")
    public void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
        userDao.add(user1);
        User user=userDao.getById("1");
        assertEquals("minji", user1.getName());
        assertEquals(1, userDao.getCount());

        userDao.add(user2);
        user=userDao.getById("2");
        assertEquals("nana", user2.getName());
        assertEquals(2, userDao.getCount());

        userDao.add(user3);
        user=userDao.getById("3");
        assertEquals("mimi", user3.getName());
        assertEquals(3, userDao.getCount());
    }
    @Test
    @DisplayName("deleteAll getCount 테스트")
    public void deleteAllTeat() throws SQLException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
    }
    @Test
    @DisplayName("getAll 테스트")
    public void getAllTest() throws SQLException {
        userDao.deleteAll();
        List<User> users=new ArrayList<>();
        assertEquals(0, userDao.getCount());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
        users=userDao.getAll();
        assertEquals(3, users.size());
    }
    @Test
    void getById(){
        assertThrows(EmptyResultDataAccessException.class, ()->{
            userDao.getById("20");
        });
    }


}