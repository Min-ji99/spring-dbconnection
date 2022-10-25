package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;

public class UserDao {
    //ConnectonMaker connectonMaker;
    //public UserDao(){this.connectonMaker=new AwsConnectionMaker();}
    //public UserDao(ConnectonMaker connectonMaker){this.connectonMaker=connectonMaker;}
    private DataSource dataSource;
    private JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;
    public UserDao(DataSource dataSource){this.jdbcTemplate=new JdbcTemplate(dataSource);}

    public RowMapper<User> rowMapper=new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user=new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            return user;
        }
    };
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);

        this.dataSource=dataSource;
    }
    public void setJdbcContext(JdbcContext jdbcContext){
        this.jdbcContext=jdbcContext;
    }


    public void deleteAll() throws SQLException {
        //this.jdbcContext.executeSql("delete from users");
        this.jdbcTemplate.update("delete from users");
    }
    public int getCount(){
        return this.jdbcTemplate.queryForObject("select count(*) from users;", Integer.class);
    }
    public void add(final User user) throws SQLException {
        /*
        this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps=c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });*/
        this.jdbcTemplate.update("Insert into users(id, name, password) values (?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());

    }

    public User getById(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id=?", rowMapper, id);
    }

    public List<User> getAll(){
        return this.jdbcTemplate.query("select * from users order by id", rowMapper);
    }
}