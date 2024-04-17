package DAO;

import TO.ProfessorTurmaTO;
import TO.ProfessorTO;



import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public class ProfessorTurmaDAO {

    java.util.Date hoje = new java.util.Date();

    private static final String INSERT_SQL_PROFESSOR_TURMA = "INSERT INTO professorturma "
        + "(idprofessor, nome, materia, idturma, numeroturma, serie )"
        + "VALUES(?,?,?,?,?,?)";

    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }

    public static void inserirProfessorTurma(ArrayList<ProfessorTurmaTO> ta) throws DaoException, SQLException {
        Connection con;
        PreparedStatement ps;
        try {
            for (int ii = 0; ta.size() > ii; ii++) {
                int i = 1;
                con = getConnection();
                ps = con.prepareStatement(INSERT_SQL_PROFESSOR_TURMA);
                ps.setInt(i++, ta.get(ii).getIdprofessor());
                ps.setString(i++, ta.get(ii).getNome());
                ps.setString(i++, ta.get(ii).getMateria());
                ps.setInt(i++, ta.get(ii).getIdturma());
                ps.setString(i++, ta.get(ii).getNumeroturma());
                ps.setString(i++, ta.get(ii).getSerie());
                ps.executeUpdate();
                con.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
