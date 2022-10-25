package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.EmptyStackException;
import java.util.Map;

public class UserDao {
    ConnectonMaker connectonMaker;
    public UserDao(){this.connectonMaker=new AwsConnectionMaker();}
    public UserDao(ConnectonMaker connectonMaker){this.connectonMaker=connectonMaker;}

    public void deleteAll(){
        try{
            Connection c = connectonMaker.makeConnection();

            PreparedStatement ps=c.prepareStatement("delete from users");
            ps.executeUpdate();

            ps.close();
            c.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public int getCount(){
        try{
            Connection c= connectonMaker.makeConnection();
            PreparedStatement ps=c.prepareStatement("select count(*) from users");
            ResultSet result=ps.executeQuery();
            result.next();

            int count=result.getInt(1);

            result.close();
            ps.close();
            c.close();

            return count;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public void add(User user) {
        try {
            // DB접속 (ex sql workbeanch실행)
            Connection c = connectonMaker.makeConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        User user=null;
        try {
            // DB접속 (ex sql workbeanch실행)
            Connection c = connectonMaker.makeConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            c.close();
            if(user==null){
                throw new EmptyResultDataAccessException(1);
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}