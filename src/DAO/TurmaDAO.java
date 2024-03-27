package DAO;

import TO.TurmaTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class TurmaDAO {

    java.util.Date hoje = new java.util.Date();


    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirTurma(TurmaTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO turma "
                + "(serie, numeroturma) "
                + "VALUES('" + to.getSerie()+ "',"
                + " '" + to.getNumeroturma()+ "') ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM turma");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                con.close();
                ps.close();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }
    }

    

    public static void deletarTurma(TurmaTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM turma WHERE idturma = '" + to.getIdturma()+ "'";
        try {
            con = getConnection();
            ps = con.prepareStatement(DELETE_SQL);
            ps.execute();
            con.close();
            ps.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static void alterarTurma (TurmaTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE turma SET  "
                + "serie = '" + to.getSerie()+ "', "
                + "numeroturma = '" + to.getNumeroturma()+ "' "
                + "WHERE idturma  = " + to.getIdturma();
        try {
            con = getConnection();
            ps = con.prepareStatement(UPDATE_SQL);
            ps.executeUpdate();
            con.close();
            ps.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static ArrayList<TurmaTO> buscaTurma(String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM turma WHERE (UPPER(numeroturma) LIKE UPPER('%" + parteNome + "%')) ORDER BY numeroturma";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<TurmaTO> td = new ArrayList<>();

            while (rs.next()) {
                TurmaTO to = new TurmaTO();
                toset(rs, to);
                td.add(to);
            }
            con.close();
            ps.close();
            return td;
        } catch (SQLException e) {
            System.out.println("++++++" + e.getMessage());
            throw new DaoException(e);
        }
    }

    public static TurmaTO detalharTurma(int idTurma) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM turma WHERE BINARY idturma = " + idTurma ;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TurmaTO to = new TurmaTO();
                toset(rs, to);
                con.close();
                ps.close();
                return to;
            }
            con.close();
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    
    public static TurmaTO detalharTurmaNumero(String numeroturma) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM turma WHERE BINARY numeroturma = '" + numeroturma + "'" ;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TurmaTO to = new TurmaTO();
                toset(rs, to);
                con.close();
                ps.close();
                return to;
            }
            con.close();
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static void toset(ResultSet rs, TurmaTO to) throws DaoException, SQLException {
        to.setIdturma(rs.getInt("idturma"));
        to.setSerie(rs.getString("serie"));
        to.setNumeroturma(rs.getString("numeroturma"));
    }


}
