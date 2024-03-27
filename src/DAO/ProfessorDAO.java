package DAO;

import TO.AlunoTO;
import TO.ProfessorTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ProfessorDAO {

    java.util.Date hoje = new java.util.Date();


    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirProfessor(ProfessorTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO professor "
                + "(nome, materia) "
                + "VALUES('" + to.getNome()+ "',"
                + " '" + to.getMateria()+ "') ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM professor");
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

    

    public static void deletarProfessor(ProfessorTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM professor WHERE idprofessor = '" + to.getIdprofessor()+ "'";
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

    public static void alterarProfessor (ProfessorTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE professor SET  "
                + "nome = '" + to.getNome()+ "', "
                + "materia = '" + to.getMateria()+ "' "
                + "WHERE idprofessor  = " + to.getIdprofessor();
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

    public static ArrayList<ProfessorTO> buscaProfessor(String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM professor WHERE (UPPER(nome) LIKE UPPER('%" + parteNome + "%')) ORDER BY nome";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<ProfessorTO> td = new ArrayList<>();

            while (rs.next()) {
                ProfessorTO to = new ProfessorTO();
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

    public static ProfessorTO detalharProfessor(int idProf) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM professor WHERE BINARY idprofessor = " + idProf ;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProfessorTO to = new ProfessorTO();
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

    private static void toset(ResultSet rs, ProfessorTO to) throws DaoException, SQLException {
        to.setIdprofessor(rs.getInt("idprofessor"));
        to.setNome(rs.getString("nome"));
        to.setMateria(rs.getString("materia"));
    }


}
