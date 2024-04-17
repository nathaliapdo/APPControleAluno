
package DAO;

import TO.ComentarioTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ComentarioDAO {

    java.util.Date hoje = new java.util.Date();


    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirComentario (ComentarioTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO comentario "
                + "(idAluno,nome, data, comentario) "
                + "VALUES('" + to.getIdAluno()+ "',"
                + " '" + to.getNome()+ "'," 
                + " '" + to.getData()+ "'," 
                + " '" + to.getComentario()+ "') ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM comentario");
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

    

    public static void deletarComentario(ComentarioTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM comentario WHERE idcomentario = '" + to.getIdcomentario()+ "'";
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

    public static void alterarComentario (ComentarioTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE comentario SET  "
                + "idAluno = " + to.getIdAluno()+ ", "
                + "nome = '" + to.getNome()+ "', "
                + "data = '" + to.getData()+ "', "
                + "comentario = '" + to.getComentario()+ "' "                
                + "WHERE idcomentario  = " + to.getIdcomentario();

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

    public static ArrayList<ComentarioTO> buscaComentario(String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM comentario WHERE (UPPER(nome) LIKE UPPER('%" + parteNome + "%')) ORDER BY nome";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<ComentarioTO> td = new ArrayList<>();

            while (rs.next()) {
                ComentarioTO to = new ComentarioTO();
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

    public static ComentarioTO detalharComentario(int idComent) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM comentario WHERE BINARY idcomentario = " + idComent ;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ComentarioTO to = new ComentarioTO();
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
    public static ComentarioTO detalharComentarioNome(String nome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM comentario WHERE BINARY nome LIKE '%" + nome + "%'";
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ComentarioTO to = new ComentarioTO();
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
    private static void toset(ResultSet rs, ComentarioTO to) throws DaoException, SQLException {
        to.setIdcomentario(rs.getInt("idcomentario"));
        to.setIdAluno(rs.getInt("idAluno"));
        to.setNome(rs.getString("nome"));
        to.setData(rs.getString("data"));
        to.setComentario(rs.getString("Comentario"));
    }


}
