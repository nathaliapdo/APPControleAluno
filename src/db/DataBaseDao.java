package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBaseDao {

    protected abstract Connection getConnection() throws SQLException;

    protected void close(Connection con) throws DaoException {
        this.close(con, null, null);
    }

    protected void close(Connection con, Statement stmt) throws DaoException {
        this.close(con, stmt, null);
    }

    protected void close(Statement stmt, ResultSet rs) throws DaoException {
        this.close(null, stmt, rs);
    }

    protected void close(Connection con, Statement stmt, ResultSet rs)
            throws DaoException {
        try {

            if (rs != null) {
                rs.close();
            }

        } catch (SQLException e) {
            throw new DaoException("problema ao fechar ResultSet: ["
                    + e.getMessage() + "].", e);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new DaoException("problema ao fechar Statement: ["
                    + e.getMessage() + "].", e);
        }

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new DaoException("problema ao fechar Connection: ["
                    + e.getMessage() + "].", e);
        }
    }
}
