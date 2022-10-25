package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.EmptyStackException;
import java.util.Map;

public class UserDao {
    //ConnectonMaker connectonMaker;
    //public UserDao(){this.connectonMaker=new AwsConnectionMaker();}
    //public UserDao(ConnectonMaker connectonMaker){this.connectonMaker=connectonMaker;}
    private DataSource dataSource;
    private JdbcContext jdbcContext;
    public UserDao(DataSource dataSource){this.dataSource=dataSource;}

    public void setJdbcContext(JdbcContext jdbcContext){
        this.jdbcContext=jdbcContext;
    }


    public void deleteAll() throws SQLException {
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                return c.prepareStatement("delete from users");
            }
        });
    }
    public int getCount(){
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        int count=0;
        try{
            c= dataSource.getConnection();
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
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps=c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });

    }

    public User findById(String id) {
        User user=null;
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            // DB접속 (ex sql workbeanch실행)
            c = dataSource.getConnection();

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