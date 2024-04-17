/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TO;

/**
 *
 * @author natha
 */
public class ProfessorTurmaTO {
    private int idprofessor;
    private String nome = "";
    private String materia = "";
    private int idturma;
    private String serie = "";
    private String numeroturma = "";

    public int getIdprofessor() {
        return idprofessor;
    }

    public void setIdprofessor(int idprofessor) {
        this.idprofessor = idprofessor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getIdturma() {
        return idturma;
    }

    public void setIdturma(int idturma) {
        this.idturma = idturma;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumeroturma() {
        return numeroturma;
    }

    public void setNumeroturma(String numeroturma) {
        this.numeroturma = numeroturma;
    }

    
}
