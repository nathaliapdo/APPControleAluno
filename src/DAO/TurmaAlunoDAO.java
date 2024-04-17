package DAO;

import TO.TurmaAlunoTO;
import TO.TurmaTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public class TurmaAlunoDAO {

    java.util.Date hoje = new java.util.Date();

    private static final String INSERT_SQL_TURMA_ALUNO = "INSERT INTO turmaaluno "
        + "(idturma, serie, idaluno, nome, datanascimento, sexo )"
        + "VALUES(?,?,?,?,?,?)";

    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }

    public static void inserirTurmaAluno(ArrayList<TurmaAlunoTO> ta) throws DaoException, SQLException {
        Connection con;
        PreparedStatement ps;
        try {
            for (int ii = 0; ta.size() > ii; ii++) {
                int i = 1;
                con = getConnection();
                ps = con.prepareStatement(INSERT_SQL_TURMA_ALUNO);
                ps.setInt(i++, ta.get(ii).getIdturma());
                ps.setString(i++, ta.get(ii).getSerie());
                ps.setInt(i++, ta.get(ii).getIdaluno());
                ps.setString(i++, ta.get(ii).getNome());
                ps.setString(i++, ta.get(ii).getSexo());
                ps.setString(i++, ta.get(ii).getDatanascimento());
                ps.executeUpdate();
                con.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
