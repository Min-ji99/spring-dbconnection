package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    public void addAndGet(){
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
        userDao.add(user1);
        User user=userDao.findById("1");
        assertEquals("minji", user1.getName());
        assertEquals(1, userDao.getCount());
    }

    @Test
    @DisplayName("deleteAll getCount 테스트")
    public void deleteAllTeat(){
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
    }

}