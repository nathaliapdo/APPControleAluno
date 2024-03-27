package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseDaoFactory {

    static public Connection getConnection() throws SQLException {

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (java.lang.ClassNotFoundException e) {
                System.out.println("************************************************");
                System.out.println("Erro Class Nao Encontrada - " + e);
                Logger.getLogger(DataBaseDaoFactory.class.getName()).log(Level.SEVERE, null, e);
            } catch (InstantiationException ex) {
                System.out.println("------------------------------------------------");
                System.out.println("Erro Class Nao Encontrada - " + ex);
                Logger.getLogger(DataBaseDaoFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                System.out.println("================================================");
                System.out.println("Erro Class Nao Encontrada - " + ex);
                Logger.getLogger(DataBaseDaoFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            com.mysql.jdbc.Connection conn = null;

            String urlPg = "jdbc:mysql://localhost:3306/appcontrolealuno";
            try {
                    conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(urlPg, "root", "");     
            } catch (SQLException ex) {
                System.out.println("Erro Servidor MySQL." + ex);
            }
            
            return conn;
  
    }
    
}