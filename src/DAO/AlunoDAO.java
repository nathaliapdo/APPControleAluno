
package DAO;

import TO.AlunoTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AlunoDAO {

    java.util.Date hoje = new java.util.Date();


    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirAluno (AlunoTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO aluno "
                + "(nome, datanascimento, sexo) "
                + "VALUES('" + to.getNome()+ "',"
                + " '" + to.getDatanascimento()+ "'," 
                + " '" + to.getSexo()+ "') ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM aluno");
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

    

    public static void deletarAluno(AlunoTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM aluno WHERE idaluno = '" + to.getIdaluno()+ "'";
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

    public static void alterarAluno (AlunoTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE aluno SET  "
                + "nome = '" + to.getNome()+ "', "
                + "datanascimento = '" + to.getDatanascimento()+ "', "
                + "sexo = '" + to.getSexo()+ "' "
                + "WHERE idaluno  = " + to.getIdaluno();
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

    public static ArrayList<AlunoTO> buscaAluno (String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM aluno WHERE (UPPER(nome) LIKE UPPER('%" + parteNome + "%')) ORDER BY nome";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<AlunoTO> td = new ArrayList<>();

            while (rs.next()) {
                AlunoTO to = new AlunoTO();
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

    public static AlunoTO detalharAluno(int idAlun) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM aluno WHERE BINARY idaluno = " + idAlun ;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AlunoTO to = new AlunoTO();
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
    public static AlunoTO detalharAlunoNome(String nome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM aluno WHERE BINARY nome LIKE '%" + nome + "%'";
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AlunoTO to = new AlunoTO();
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
    private static void toset(ResultSet rs, AlunoTO to) throws DaoException, SQLException {
        to.setIdaluno(rs.getInt("idaluno"));
        to.setNome(rs.getString("nome"));
        to.setDatanascimento(rs.getString("datanascimento"));
        to.setSexo(rs.getString("sexo"));
    }


}
