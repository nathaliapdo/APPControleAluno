package db;

import java.sql.SQLException;

public class DaoException extends Exception {
    private static final long serialVersionUID = -1666238201989677392L;
    
    public DaoException(Throwable e) {
        super(e);
    }
    
    public DaoException(String msg, SQLException e) {
        super(msg, e);
    }
    
    public DaoException() {
    }
    
}
