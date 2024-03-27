/*
 * TiposDocsMain.java
 * 
 * Created on 10 de Agosto de 2008, 17:39
 */
package appcontrolealuno;


import DAO.TurmaDAO;
import TO.TurmaTO;
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
public class TurmaMain extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = -2594306776637231202L;

    String fluxo = "", quemChamou = "";
    boolean retornaDados = false;
    NumberFormat duasDecimais = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.ENGLISH));

    /**
     * Creates new form Editora
     */
    public TurmaMain() {
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

    TurmaMain(String quemChamouExterno, String descricaoTD) {
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

        Collection<TurmaTO> lista = new ArrayList<>();
        try {
            lista = TurmaDAO.buscaTurma(jTextPesquisar.getText().trim());
        } catch (DaoException ex) {
            Logger.getLogger(TurmaMain.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro consulta ao BD.");
        }
        getTableModel().setRowCount(0);  // Limpa quadro
        if (lista != null && lista.size() > 0) {
            lblResult.setText(lista.size() + " registro(s) encontrado(s)");
            for (TurmaTO v : lista) {
                String[] rowData = new String[3];
                rowData[0] = String.valueOf(v.getIdturma());
                rowData[1] = v.getSerie();
                rowData[2] = v.getNumeroturma();
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

        TurmaTO idTurma;

        if (fluxo.equals("INCLUIR")) {
            idTurma = parseTO(fluxo, 0);
            try {
                TurmaDAO.inserirTurma(idTurma);
                JOptionPane.showMessageDialog(this, "Os dados foram incluidos com sucesso.");
                Geral.limpaCampos(jInternalFrame1);
                jtxtTurma.requestFocus();
            } catch (DaoException ex) {
                Logger.getLogger(TurmaMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na validacao");
            }
            
        }
        if (fluxo.equals("ALTERAR")) {
            idTurma = parseTO(fluxo, Integer.valueOf(jtxtIdTurma.getText()));
            try {
                TurmaDAO.alterarTurma(idTurma);
                JOptionPane.showMessageDialog(this, "Os dados foram alterados com sucesso.");
                Geral.limpaCampos(jInternalFrame1);
                btnSair1ActionPerformed(null);
            } catch (DaoException ex) {
                Logger.getLogger(TurmaMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na validacao");
            }
        }

    }

    private TurmaTO parseTO(String fluxoAux, int id) {
        TurmaTO idTurma = new TurmaTO();
        if (!fluxoAux.equals("INCLUIR")) {
            idTurma.setIdturma(id);
        }
        idTurma.setNumeroturma(jtxtTurma.getText());
        idTurma.setSerie(jtxtSérie.getText());

        return idTurma;
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
        jtxtIdTurma = new javax.swing.JTextField();
        jtxtTurma = new javax.swing.JTextField();
        jbtnOk = new javax.swing.JButton();
        btnSair1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtxtSérie = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro Turmas");

        tblResult.setAutoCreateRowSorter(true);
        tblResult.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idTurma", "Número Turma", "Série"
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
        jLabel4.setText("Turma:");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnIncluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRelatorio))
                            .addComponent(jTextPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPesquisar))
                        .addContainerGap())))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame1.setVisible(true);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("idTurma:");

        jtxtIdTurma.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtxtIdTurma.setEnabled(false);

        jtxtTurma.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtxtTurma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTurmaActionPerformed(evt);
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
        jLabel11.setText("Turma...:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Série:");

        jtxtSérie.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtxtSérie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtSérieActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtIdTurma, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jbtnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtSérie, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtIdTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtxtTurma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jtxtSérie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 23, Short.MAX_VALUE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair1)
                    .addComponent(jbtnOk))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
            ArrayList<TurmaTO> relTurmaGerado;

            try {
                relTurmaGerado = (ArrayList<TurmaTO>) DAO.TurmaDAO.buscaTurma(jTextPesquisar.getText());
                if (relTurmaGerado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Turma(s) " + jTextPesquisar.getText() + " não encontrada(s)!");
                    return;
                }
                // Pergunta se imprime ou gera PDF
                Object[] options1 = {"Impressora", "Tela"};
                int q1 = JOptionPane.showOptionDialog(null,
                        "O relatório será?", "Saída",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options1, options1[1]);
                if (q1 == JOptionPane.YES_OPTION) {

                    JasperReport report = JasperCompileManager.compileReport(geral.Geral.getDiretorioAtual() + "\\Relatorios\\RelTurmas.jrxml");
                    JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(relTurmaGerado));
                    JasperPrintManager.printPage(print, 0, false);
                } else {
                    JasperReport report = JasperCompileManager.compileReport(geral.Geral.getDiretorioAtual() + "\\Relatorios\\RelTurmas.jrxml");
                    JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(relTurmaGerado));
                    // exportacao do relatorio para outro formato, no caso PDF 
                    JasperExportManager.exportReportToPdfFile(print, geral.Geral.getDiretorioAtual() + "\\Relatorios\\RelTurma.pdf");
                    JasperViewer.viewReport(print, false);
                }
            } catch (JRException | ValidacaoException | DaoException  ex) {
                Logger.getLogger(TurmaMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na geração relatório!");
            } 
        }
                
    }//GEN-LAST:event_btnRelatorioActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        try {
            if (tblResult.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione Turma.");
                return;
            }
            bloqueiaBotoes();
            jInternalFrame1.setTitle("ALTERAR");
            jbtnOk.setText("Alterar");
            jbtnOk.setVisible(true);
            jInternalFrame1.setVisible(true);
            fluxo = "ALTERAR";
            // Busca Cliente
            TurmaTO to;
            to = TurmaDAO.detalharTurma(Integer.valueOf(tblResult.getValueAt(tblResult.getSelectedRow(), 0).toString()));

            if (to != null) {
                jtxtIdTurma.setText(String.valueOf(to.getIdturma()));
                jtxtTurma.setText(to.getNumeroturma());
                jtxtSérie.setText(to.getSerie());

            }
            jtxtTurma.requestFocus();

        } catch (DaoException ex) {
            Logger.getLogger(TurmaMain.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na aplicacao");
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        bloqueiaBotoes();
        jbtnOk.setText("Incluir");
        fluxo = "INCLUIR";
        jtxtTurma.requestFocus();
        jInternalFrame1.setTitle(fluxo);
        jtxtIdTurma.setText("");
        jtxtTurma.setText("");
        jtxtSérie.setText("");
        jbtnOk.setVisible(true);
        jInternalFrame1.setVisible(true);
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void jtxtTurmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTurmaActionPerformed
        Geral.testaFocaCorta(jtxtTurma, 45, jbtnOk);
    }//GEN-LAST:event_jtxtTurmaActionPerformed

    private void jbtnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOkActionPerformed
        botaoOKPressionado();
    }//GEN-LAST:event_jbtnOkActionPerformed

    private void jbtnOkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jbtnOkFocusGained
        Geral.Corta(jtxtTurma, 30);
    }//GEN-LAST:event_jbtnOkFocusGained

    private void jbtnOkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnOkKeyPressed
        if (evt.getKeyCode() == 10) {
            botaoOKPressionado();
        }
    }//GEN-LAST:event_jbtnOkKeyPressed

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed
        desBloqueiaBotoes(fluxo);
        jInternalFrame1.setVisible(false);
        botaoPesquisarPressionado();
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void btnSair1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSair1KeyPressed
        if (evt.getKeyCode() == 10) {
            desBloqueiaBotoes(fluxo);
            jInternalFrame1.setVisible(false);
            botaoPesquisarPressionado();
        }
    }//GEN-LAST:event_btnSair1KeyPressed

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
        TurmaTO e;
        e = new TurmaTO();
        try {
            e = TurmaDAO.detalharTurma(Integer.valueOf(getCodigoSelecionado()));
        } catch (DaoException ex) {
            Logger.getLogger(TurmaMain.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na aplicacao");
        }
        Object[] options = {"Sim", "Não"};
        int q = JOptionPane.showOptionDialog(null,
                "Tem certeza que deseja excluir o Item \n" + e.getNumeroturma(), "Saída",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[1]);
        if (q == JOptionPane.YES_OPTION) {
            try {
                TurmaDAO.deletarTurma(e);
            } catch (DaoException ex) {
                Logger.getLogger(TurmaMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage() + " \nErro na aplicacao");
            }
        }
        desBloqueiaBotoes("EXCLUIR");
        botaoPesquisarPressionado();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void jtxtSérieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtSérieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtSérieActionPerformed

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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextPesquisar;
    private javax.swing.JButton jbtnOk;
    private javax.swing.JTextField jtxtIdTurma;
    private javax.swing.JTextField jtxtSérie;
    private javax.swing.JTextField jtxtTurma;
    private javax.swing.JLabel lblResult;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables

}
