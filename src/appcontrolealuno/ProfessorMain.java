/*
 * TiposDocsMain.java
 * 
 * Created on 10 de Agosto de 2008, 17:39
 */
package appcontrolealuno;

import DAO.ProfessorDAO;
import TO.ProfessorTO;


import db.DaoException;
import exceptions.ValidacaoException;
import geral.Geral;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import static jdk.nashorn.internal.runtime.Debug.id;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Sergio Damiao
 */
public class ProfessorMain extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = -2594306776637231202L;

    String fluxo = "", quemChamou = "";
    boolean retornaDados = false;
    NumberFormat duasDecimais = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.ENGLISH));

    /**
     * Creates new form Editora
     */
    public ProfessorMain() {
        initComponents();
        lblResult.setText("");
        this.setEnabled(true);
        jInternalFrame1.setVisible(false);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                jTextPesquisar.requestFocus();
            }
        });
    }

    ProfessorMain(String quemChamouExterno, String descricaoTD) {
        initComponents();
        lblResult.setText("");
        this.setEnabled(true);
        retornaDados = true;
        quemChamou = quemChamouExterno;
        jInternalFrame1.setVisible(false);
        jTextPesquisar.setText(descricaoTD);
        botaoPesquisarPressionado();
    }

    private void bloqueiaBotoes() {
        btnIncluir.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnRelatorio.setEnabled(false);
        btnExcluir.setEnabled(false);
        jbtnOk.setVisible(true);
    }

    private void desBloqueiaBotoes(String fluxo) {
        btnIncluir.setEnabled(true);
        if (!fluxo.equals("INCLUIR")) {
            btnAlterar.setEnabled(true);
            btnRelatorio.setEnabled(false);
            btnExcluir.setEnabled(true);
        }
    }

    private String getCodigoSelecionado() {
        if (tblResult.getSelectedRow() < 0) {
            return "";
        }
        return tblResult.getValueAt(tblResult.getSelectedRow(), 0).toString();
    }


    private void botaoPesquisarPressionado() {

        Collection<ProfessorTO> lista = new ArrayList<>();
        try {
            lista = ProfessorDAO.buscaProfessor(jTextPesquisar.getText().trim());
        } catch (DaoException ex) {
            Logger.getLogger(ProfessorMain.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro consulta ao BD.");
        }
        getTableModel().setRowCount(0);  // Limpa quadro
        if (lista != null && lista.size() > 0) {
            lblResult.setText(lista.size() + " registro(s) encontrado(s)");
            for (ProfessorTO v : lista) {
                String[] rowData = new String[3];
                rowData[0] = String.valueOf(v.getIdprofessor());
                rowData[1] = v.getNome();
                rowData[2] = v.getMateria();
                getTableModel().addRow(rowData);
            }
            btnAlterar.setEnabled(true);
            btnExcluir.setEnabled(true);
            btnRelatorio.setEnabled(true);
        } else {
            lblResult.setText("nenhum registro encontrado");
            btnAlterar.setEnabled(false);
            btnExcluir.setEnabled(false);
            btnRelatorio.setEnabled(true);
        }
    }

    private DefaultTableModel getTableModel() {
        return (DefaultTableModel) tblResult.getModel();
    }

    private void botaoOKPressionado() throws HeadlessException {

        ProfessorTO idProf;

        if (fluxo.equals("INCLUIR")) {
            idProf = parseTO(fluxo, 0);
            try {
                ProfessorDAO.inserirProfessor(idProf);
                JOptionPane.showMessageDialog(this, "Os dados foram incluidos com sucesso.");
                Geral.limpaCampos(jInternalFrame1);
                jtxtNome.requestFocus();
            } catch (DaoException ex) {
                Logger.getLogger(ProfessorMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na validacao");
            }
            
        }
        if (fluxo.equals("ALTERAR")) {
            idProf = parseTO(fluxo, Integer.valueOf(jtxtIdProfessor.getText()));
            try {
                ProfessorDAO.alterarProfessor(idProf);
                JOptionPane.showMessageDialog(this, "Os dados foram alterados com sucesso.");
                Geral.limpaCampos(jInternalFrame1);
                btnSair1ActionPerformed(null);
            } catch (DaoException ex) {
                Logger.getLogger(ProfessorMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na validacao");
            }
        }

    }

    private ProfessorTO parseTO(String fluxoAux, int id) {
        ProfessorTO idProf = new ProfessorTO();
        if (!fluxoAux.equals("INCLUIR")) {
            idProf.setIdprofessor(id);
        }
        idProf.setNome(jtxtNome.getText());
        idProf.setMateria(jtxtMateria.getText());

        return idProf;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jTextPesquisar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnRelatorio = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnIncluir = new javax.swing.JButton();
        lblResult = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel10 = new javax.swing.JLabel();
        jtxtIdProfessor = new javax.swing.JTextField();
        jtxtNome = new javax.swing.JTextField();
        jbtnOk = new javax.swing.JButton();
        btnSair1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jtxtMateria = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Professores");

        tblResult.setAutoCreateRowSorter(true);
        tblResult.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idProfessor", "Nome", "Matéria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblResult);
        if (tblResult.getColumnModel().getColumnCount() > 0) {
            tblResult.getColumnModel().getColumn(0).setMinWidth(90);
            tblResult.getColumnModel().getColumn(0).setPreferredWidth(90);
            tblResult.getColumnModel().getColumn(0).setMaxWidth(90);
        }

        jTextPesquisar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextPesquisarActionPerformed(evt);
            }
        });
        jTextPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPesquisarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextPesquisarKeyTyped(evt);
            }
        });

        btnPesquisar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        btnPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPesquisarKeyPressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Nome:");

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSair.setText("Sair");
        btnSair.setName("btnSair"); // NOI18N
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        btnSair.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSairKeyPressed(evt);
            }
        });

        btnRelatorio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRelatorio.setText("Relatorio");
        btnRelatorio.setEnabled(false);
        btnRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioActionPerformed(evt);
            }
        });
        btnRelatorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRelatorioKeyPressed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setEnabled(false);
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        btnAlterar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAlterarKeyPressed(evt);
            }
        });

        btnIncluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnIncluir.setText("Incluir");
        btnIncluir.setName("btnIncluir"); // NOI18N
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });
        btnIncluir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnIncluirKeyPressed(evt);
            }
        });

        lblResult.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblResult.setText("999  registros");

        btnExcluir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        btnExcluir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExcluirKeyPressed(evt);
            }
        });

        jInternalFrame1.setVisible(true);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("idProfessor:");

        jtxtIdProfessor.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtxtIdProfessor.setEnabled(false);

        jtxtNome.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtxtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNomeActionPerformed(evt);
            }
        });

        jbtnOk.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbtnOk.setText("Ok");
        jbtnOk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jbtnOkFocusGained(evt);
            }
        });
        jbtnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOkActionPerformed(evt);
            }
        });
        jbtnOk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnOkKeyPressed(evt);
            }
        });

        btnSair1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSair1.setText("Sair");
        btnSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair1ActionPerformed(evt);
            }
        });
        btnSair1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSair1KeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Nome.........:");

        jtxtMateria.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtxtMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtMateriaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Matéria.......:");

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(jtxtIdProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(jtxtNome)
                                .addContainerGap())))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtMateria)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair1)
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtIdProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSair1)
                    .addComponent(jbtnOk))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblResult, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnIncluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRelatorio)
                                .addGap(7, 7, 7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextPesquisar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jInternalFrame1)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblResult)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPesquisar)
                    .addComponent(jTextPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair)
                    .addComponent(btnRelatorio)
                    .addComponent(btnAlterar)
                    .addComponent(btnIncluir)
                    .addComponent(btnExcluir))
                .addGap(18, 18, 18)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextPesquisarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPesquisarKeyTyped
        if (jTextPesquisar.getText().length() >= 19) {
            btnPesquisar.requestFocus();
        }
    }//GEN-LAST:event_jTextPesquisarKeyTyped

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        botaoPesquisarPressionado();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed
        if (evt.getKeyCode() == 10) {
            botaoPesquisarPressionado();
        }
        if (evt.getKeyCode() == 27) {
            btnSairActionPerformed(null);
        }
    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioActionPerformed
 // Pede confirmação para emissao do relatório
        Object[] options = {"Sim", "Não"};
        int q = JOptionPane.showOptionDialog(null,
                "Tem certeza que deseja emitir relatório?", "Saída",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
        if (q == JOptionPane.YES_OPTION) {
            fluxo = "RELATORIO";
            // Relatório de usuarios cadastrados e mostrados na tela
            // array com o que está na tela
            ArrayList<ProfessorTO> relProfGerado;

            try {
                relProfGerado = (ArrayList<ProfessorTO>) DAO.ProfessorDAO.buscaProfessor(jTextPesquisar.getText());
                if (relProfGerado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Professor(es) " + jTextPesquisar.getText() + " não encontrado(s)!");
                    return;
                }
                // Pergunta se imprime ou gera PDF
                Object[] options1 = {"Impressora", "Tela"};
                int q1 = JOptionPane.showOptionDialog(null,
                        "O relatório será?", "Saída",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options1, options1[1]);
                if (q1 == JOptionPane.YES_OPTION) {

                    JasperReport report = JasperCompileManager.compileReport(geral.Geral.getDiretorioAtual() + "\\Relatorios\\RelProfessores.jrxml");
                    JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(relProfGerado));
                    JasperPrintManager.printPage(print, 0, false);
                } else {
                    JasperReport report = JasperCompileManager.compileReport(geral.Geral.getDiretorioAtual() + "\\Relatorios\\RelProfessores.jrxml");
                    JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(relProfGerado));
                    // exportacao do relatorio para outro formato, no caso PDF 
                    JasperExportManager.exportReportToPdfFile(print, geral.Geral.getDiretorioAtual() + "\\Relatorios\\RelProfessores.pdf");
                    JasperViewer.viewReport(print, false);
                }
            } catch (JRException | ValidacaoException | DaoException  ex) {
                Logger.getLogger(ProfessorMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na geração relatório!");
            } 
        }
                
    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        try {
            if (tblResult.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione Professor.");
                return;
            }
            bloqueiaBotoes();
            jInternalFrame1.setTitle("ALTERAR");
            jbtnOk.setText("Alterar");
            jbtnOk.setVisible(true);
            jInternalFrame1.setVisible(true);
            fluxo = "ALTERAR";
            // Busca Cliente
            ProfessorTO to;
            to = ProfessorDAO.detalharProfessor(Integer.valueOf(tblResult.getValueAt(tblResult.getSelectedRow(), 0).toString()));

            if (to != null) {
                jtxtIdProfessor.setText(String.valueOf(to.getIdprofessor()));
                jtxtNome.setText(to.getNome());
                jtxtMateria.setText(to.getMateria());

            }
            jtxtNome.requestFocus();

        } catch (DaoException ex) {
            Logger.getLogger(ProfessorMain.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na aplicacao");
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        bloqueiaBotoes();
        jbtnOk.setText("Incluir");
        fluxo = "INCLUIR";
        jtxtNome.requestFocus();
        jInternalFrame1.setTitle(fluxo);
        jtxtIdProfessor.setText("");
        jtxtNome.setText("");
        jtxtMateria.setText("");
        jbtnOk.setVisible(true);
        jInternalFrame1.setVisible(true);
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void jTextPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextPesquisarActionPerformed
        // TODO add your handling code here:
        btnPesquisar.requestFocus();
    }//GEN-LAST:event_jTextPesquisarActionPerformed

    private void jTextPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPesquisarKeyPressed
        if (evt.getKeyCode() == 10) {
            btnPesquisar.requestFocus();
        }
        if (evt.getKeyCode() == 27) {
            btnSairActionPerformed(null);
        }
    }//GEN-LAST:event_jTextPesquisarKeyPressed

    private void btnIncluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnIncluirKeyPressed
        if (evt.getKeyCode() == 10) {
            btnIncluirActionPerformed(null);
        }
    }//GEN-LAST:event_btnIncluirKeyPressed

    private void btnAlterarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAlterarKeyPressed
        if (evt.getKeyCode() == 10) {
            btnAlterarActionPerformed(null);
        }
    }//GEN-LAST:event_btnAlterarKeyPressed

    private void btnRelatorioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRelatorioKeyPressed
        if (evt.getKeyCode() == 10) {
            btnRelatorioActionPerformed(null);
        }
    }//GEN-LAST:event_btnRelatorioKeyPressed

    private void btnSairKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSairKeyPressed
        if (evt.getKeyCode() == 10) {
            btnSairActionPerformed(null);
        }
    }//GEN-LAST:event_btnSairKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (tblResult.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Item");
            return;
        }
        bloqueiaBotoes();
        ProfessorTO e;
        e = new ProfessorTO();
        try {
            e = ProfessorDAO.detalharProfessor(Integer.valueOf(getCodigoSelecionado()));
        } catch (DaoException ex) {
            Logger.getLogger(ProfessorMain.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na aplicacao");
        }
        Object[] options = {"Sim", "Não"};
        int q = JOptionPane.showOptionDialog(null,
                "Tem certeza que deseja excluir o Item \n" + e.getNome(), "Saída",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
        if (q == JOptionPane.YES_OPTION) {
            try {
                ProfessorDAO.deletarProfessor(e);
            } catch (DaoException ex) {
                Logger.getLogger(ProfessorMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na aplicacao");
            }
        }
        desBloqueiaBotoes("EXCLUIR");
        botaoPesquisarPressionado();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnSair1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSair1KeyPressed
        if (evt.getKeyCode() == 10) {
            desBloqueiaBotoes(fluxo);
            jInternalFrame1.setVisible(false);
            botaoPesquisarPressionado();
        }
    }//GEN-LAST:event_btnSair1KeyPressed

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed
        desBloqueiaBotoes(fluxo);
        jInternalFrame1.setVisible(false);
        botaoPesquisarPressionado();
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void jbtnOkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnOkKeyPressed
        if (evt.getKeyCode() == 10) {
            botaoOKPressionado();
        }
    }//GEN-LAST:event_jbtnOkKeyPressed

    private void jbtnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOkActionPerformed
        botaoOKPressionado();
    }//GEN-LAST:event_jbtnOkActionPerformed

    private void jbtnOkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jbtnOkFocusGained
        Geral.Corta(jtxtNome, 30);
    }//GEN-LAST:event_jbtnOkFocusGained

    private void jtxtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNomeActionPerformed
        Geral.testaFocaCorta(jtxtNome, 45, jbtnOk);
    }//GEN-LAST:event_jtxtNomeActionPerformed

    private void jtxtMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtMateriaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSair1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextPesquisar;
    private javax.swing.JButton jbtnOk;
    private javax.swing.JTextField jtxtIdProfessor;
    private javax.swing.JTextField jtxtMateria;
    private javax.swing.JTextField jtxtNome;
    private javax.swing.JLabel lblResult;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables

}
