package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectonMaker {
    public Connection makeConnection() throws SQLException;
}
