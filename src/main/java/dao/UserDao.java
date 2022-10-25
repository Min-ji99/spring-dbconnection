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

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException{
        Connection c=null;
        PreparedStatement ps=null;
        try{
            c = connectonMaker.makeConnection();

            ps=stmt.makePreparedStatement(c);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){}
            }
            if(c!=null){
                try{
                    c.close();
                }catch(SQLException e){}
            }
        }
    }
    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy(new DeleteAllStrategy());
    }
    public int getCount(){
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int count=0;
        try{
            c= connectonMaker.makeConnection();
            ps=c.prepareStatement("select count(*) from users");
            rs=ps.executeQuery();
            rs.next();

            count=rs.getInt(1);

            return count;
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){}
            }
            if(c!=null){
                try{
                    c.close();
                }catch(SQLException e){}
            }
            if(rs!=null){
                try{
                    rs.close();
                }catch (SQLException e){}
            }
        }
        return count;
    }
    public void add(User user) throws SQLException {
        jdbcContextWithStatementStrategy(new AddStrategy(user));

    }

    public User findById(String id) {
        User user=null;
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            // DB접속 (ex sql workbeanch실행)
            c = connectonMaker.makeConnection();

            // Query문 작성
            ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setString(1, id);

            // Query문 실행
            rs = ps.executeQuery();
            rs.next();
            user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));
            if(user==null){
                throw new EmptyResultDataAccessException(1);
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            if(ps!=null){
                try{
                    ps.close();
                }catch (SQLException e){}
            }
            if(c!=null){
                try{
                    c.close();
                }catch(SQLException e){}
            }
            if(rs!=null){
                try{
                    rs.close();
                }catch (SQLException e){}
            }
        }
    }
}