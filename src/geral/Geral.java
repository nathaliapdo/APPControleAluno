/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geral;

import exceptions.ValidacaoException;
import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Sérgio Damião
 */
public class Geral {

    static NumberFormat duasDecimais = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.ENGLISH));

    public static void desbloqueiaCampos(Container container, String fluxo) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setEditable(true);
                if (fluxo.equals("INCLUIR")) {
                    field.setText(null);
                }
            } else if (component instanceof JPasswordField) {
                JPasswordField field = (JPasswordField) component;
                field.setEditable(true);
                if (fluxo.equals("INCLUIR")) {
                    field.setText(null);
                }
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setEditable(true);
                if (fluxo.equals("INCLUIR")) {
                    field.setText("");
                }
            } else if (component instanceof JRadioButton) {
                JRadioButton field = (JRadioButton) component;
                field.setEnabled(true);
                if (fluxo.equals("INCLUIR")) {
                    field.setSelected(false);
                }
            } else if (component instanceof JComboBox) {
                JComboBox field = (JComboBox) component;
                field.setEnabled(true);
            } else if (component instanceof JButton) {
                JButton field = (JButton) component;
                field.setEnabled(true);
            } else if (component instanceof Container) {
                desbloqueiaCampos((Container) component, fluxo);
            }
        }
    }

    public static void bloqueiaCampos(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setEditable(false);
            } else if (component instanceof JPasswordField) {
                JPasswordField field = (JPasswordField) component;
                field.setEditable(false);
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setEditable(false);
            } else if (component instanceof JRadioButton) {
                JRadioButton field = (JRadioButton) component;
                field.setEnabled(false);
            } else if (component instanceof JComboBox) {
                JComboBox field = (JComboBox) component;
                field.setEnabled(false);
            } else if (component instanceof JButton) {
                JButton field = (JButton) component;
                field.setEnabled(false);
            } else if (component instanceof Container) {
                bloqueiaCampos((Container) component);
            }
        }
    }

    public static void bloqueiaCamposJTextField(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setEditable(false);
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setEditable(false);
            } else if (component instanceof JComboBox) {
                JComboBox field = (JComboBox) component;
                field.setEnabled(false);
            } else if (component instanceof Container) {
                bloqueiaCamposJTextField((Container) component);
            }
        }
    }

    public static void limpaCampos(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setText("");
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setText("");
            } else if (component instanceof JLabel) {
                JLabel field = (JLabel) component;
                field.setIcon(null);
            } else if (component instanceof Container) {
                limpaCampos((Container) component);
            } else if (component instanceof JTextArea) {
                JTextArea field = (JTextArea) component;
                field.setText("");
            }
        }
    }

    public static void testaFocaCorta(JTextField atual, int tamanho, JRadioButton proximo) {
        //Retira acentos
        atual.setText(removerAcentos(atual.getText()));
        if (atual.getText().length() > tamanho) {
            atual.setText(atual.getText().substring(0, tamanho));
        }
        proximo.requestFocus();
    }

    public static void testaFocaCorta(JTextField atual, int tamanho, JComboBox proximo) {
        //Retira acentos
        atual.setText(removerAcentos(atual.getText()));
        if (atual.getText().length() > tamanho) {
            atual.setText(atual.getText().substring(0, tamanho));
        }
        proximo.requestFocus();
    }

    public static void testaFocaCorta(JTextField atual, int tamanho, JButton proximo) {
        //Retira acentos
        atual.setText(removerAcentos(atual.getText()));
        if (atual.getText().length() > tamanho) {
            atual.setText(atual.getText().substring(0, tamanho));
        }
        proximo.requestFocus();
    }

    public static void testaFocaCorta(JTextField atual, int tamanho, JTextField proximo) {
        //Retira acentos
        atual.setText(removerAcentos(atual.getText()));
        if (atual.getText().length() > tamanho) {
            atual.setText(atual.getText().substring(0, tamanho));
        }
        proximo.requestFocus();
    }

    public static void testaFocaNCM(JTextField atual, JTextField proximo) throws ValidacaoException {
        if (atual.getText().length() > 8) {
            atual.setText(atual.getText().substring(0, 8));
        }
        if (atual.getText().length() < 8 && !atual.getText().equals("")) {
            throw new ValidacaoException("Falta número, NCM 8 numeros.");
        }
        soNumeros(atual.getText());
        proximo.requestFocus();
    }

    public static void Corta(JTextField atual, int tamanho) {
        //Retira acentos
        atual.setText(removerAcentos(atual.getText()));
        if (atual.getText().length() > tamanho) {
            atual.setText(atual.getText().substring(0, tamanho));
        }
    }

    public static String CortaString(String atual, int tamanho) {
        String substring;

        if (atual == null) {
            substring = "";
        } else {
            if (atual.length() > tamanho) {
                substring = atual.substring(0, tamanho);
            } else {
                substring = atual;
            }
        }
        return substring;
    }

    public static void testaFocaNumeroDecimal(JTextField atual, JButton proximo) throws ValidacaoException {
        double aux = (soNumeros(atual.getText().replace(',', '.')));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
            default:
                NumberFormat duasDecimais = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.ENGLISH));
                atual.setText(String.valueOf(duasDecimais.format(aux)));
                proximo.requestFocus();
        }
    }

    public static void testaFocaNumeroDecimal(JTextField atual, JRadioButton proximo) throws ValidacaoException {
        double aux = (soNumeros(atual.getText().replace(',', '.')));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
            default:
                NumberFormat duasDecimais = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.ENGLISH));
                atual.setText(String.valueOf(duasDecimais.format(aux)));
                proximo.requestFocus();
        }
    }

    public static void testaFocaInt(JTextField atual, JButton proximo) throws ValidacaoException {
        double aux = (soNumeros(atual.getText()));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
            default:
                NumberFormat semDecimais = new DecimalFormat("########0", new DecimalFormatSymbols(Locale.ENGLISH));
                atual.setText(String.valueOf(semDecimais.format(aux)));
                proximo.requestFocus();
        }
    }

    public static void testaFocaInt(JTextField atual, JRadioButton proximo) throws ValidacaoException {
        double aux = (soNumeros(atual.getText()));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
            default:
                NumberFormat semDecimais = new DecimalFormat("########0", new DecimalFormatSymbols(Locale.ENGLISH));
                atual.setText(String.valueOf(semDecimais.format(aux)));
                proximo.requestFocus();
        }
    }

    public static void testaFocaInt(JTextField atual, JTextField proximo) throws ValidacaoException {
        double aux = (soNumeros(atual.getText()));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
            default:
                NumberFormat semDecimais = new DecimalFormat("########0", new DecimalFormatSymbols(Locale.ENGLISH));
                atual.setText(String.valueOf(semDecimais.format(aux)));
                proximo.requestFocus();
        }
    }

    public static void testaFocaSequenciaBotoes(JTextField atual, JTextField proximo) throws ValidacaoException {
        double aux = (soNumeros(atual.getText().replace(',', '.')));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
            default:
                if (aux < 1 || aux > 39) {
                    atual.requestFocus();
                    throw new ValidacaoException("Sequencia do botão \n < 0 ou > 39!");
                } else {
                    NumberFormat semDecimais = new DecimalFormat("########0", new DecimalFormatSymbols(Locale.ENGLISH));
                    atual.setText(String.valueOf(semDecimais.format(aux)));
                    proximo.requestFocus();
                }
        }
    }

    public static void testaFocaNumeroDecimal(JTextField atual, JComboBox proximo) throws ValidacaoException {

        double aux = (soNumeros(atual.getText().replace(',', '.')));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
            default:
                NumberFormat duasDecimais = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.ENGLISH));
                atual.setText(String.valueOf(duasDecimais.format(aux)));
                proximo.requestFocus();
        }
    }

    public static void testaFocaNumeroDecimal(JTextField atual, JTextField proximo) throws ValidacaoException {
        double aux = (soNumeros(atual.getText().replace(',', '.')));
        switch ((int) aux) {
            case -999999:
                aux = 0;
            default:
                atual.setText(String.valueOf(duasDecimais.format(aux)));
                proximo.requestFocus();
        }
    }

    private static void operacaoJtxtField(JTextField atual) throws ValidacaoException {

        // Verifica se e uma operacao
        if (atual.getText().contains("+")) {
            int sinal = atual.getText().indexOf("+");
            if (sinal == 0) {
                throw new ValidacaoException("Falta número");
            }

            int tamanhoFinal = atual.getText().length() - (sinal + 1);
            double result;
            if (tamanhoFinal <= 0) {
                result = soNumeros(atual.getText().substring(0, sinal));
            } else {
                result = soNumeros(atual.getText().substring(0, sinal)) + soNumeros(atual.getText().substring(sinal + 1, atual.getText().length()));
            }

            atual.setText(String.valueOf(result));
        } else {
            if (atual.getText().contains("-")) {
                double result;
                int sinal = atual.getText().indexOf("-");
                if (sinal == 0) {
                    result = Double.parseDouble(atual.getText());
                } else {

                    int tamanhoFinal = atual.getText().length() - (sinal + 1);
                    if (tamanhoFinal <= 0) {
                        result = Double.parseDouble(atual.getText().substring(0, sinal));
                    } else {
                        result = Double.parseDouble(atual.getText().substring(0, sinal)) - Double.parseDouble(atual.getText().substring(sinal + 1, atual.getText().length()));
                    }
                }
                atual.setText(String.valueOf(result));
            } else {
                if (atual.getText().contains("*")) {
                    int sinal = atual.getText().indexOf("*");
                    if (sinal == 0) {
                        throw new ValidacaoException("Falta multiplicador");
                    }

                    int tamanhoFinal = atual.getText().length() - (sinal + 1);
                    double result;
                    if (tamanhoFinal <= 0) {
                        result = Double.parseDouble(atual.getText().substring(0, sinal));
                    } else {
                        result = Double.parseDouble(atual.getText().substring(0, sinal)) * Double.parseDouble(atual.getText().substring(sinal + 1, atual.getText().length()));
                    }

                    atual.setText(String.valueOf(result));
                } else {
                    if (atual.getText().contains("/")) {
                        int sinal = atual.getText().indexOf("/");
                        if (sinal == 0) {
                            throw new ValidacaoException("Falta numerador");
                        }

                        int tamanhoFinal = atual.getText().length() - (sinal + 1);
                        double result;
                        if (tamanhoFinal <= 0) {
                            result = Double.parseDouble(atual.getText().substring(0, sinal));
                        } else {
                            result = Double.parseDouble(atual.getText().substring(0, sinal)) / Double.parseDouble(atual.getText().substring(sinal + 1, atual.getText().length()));
                        }

                        atual.setText(String.valueOf(result));
                    }
                }
            }
        }
    }

    public static void testaSeNumeroDecimal(JTextField atual) throws ValidacaoException {
        double aux = (soNumeros(atual.getText().replace(',', '.')));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
            case -2:
                aux = 0;
                NumberFormat duasDecimaisAux = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.ENGLISH));
                atual.setText(String.valueOf(duasDecimaisAux.format(aux)));
        }
    }

    public static int converteJtxtParaInt(JTextField atual) throws ValidacaoException {
        double aux = (soNumeros(atual.getText()));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
        }
        return Integer.parseInt(atual.getText());

    }

    public static double converteJtxtParaDouble(JTextField atual) throws ValidacaoException {
        double aux = (soNumeros(atual.getText().replace(",",".")));
        switch ((int) aux) {
            case -1:
                atual.requestFocus();
                break;
        }
        return Double.parseDouble(atual.getText().replace(",","."));

    }

    public static double soNumeros(String campo) throws ValidacaoException {
        if (campo.length() == 0) {
            return -999999;
        }
        campo = campo.replace(",", ".");
        try {
            return Double.parseDouble(campo);
        } catch (NumberFormatException ex) {
            Logger.getLogger(Geral.class.getName()).log(Level.SEVERE, null, ex);
            throw new ValidacaoException(ex.getMessage() + "\n Só números");
        }
    }

    public static Date convertStringToData(String dataString) throws ValidacaoException {
        try {
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            formatador.setLenient(false);
            return formatador.parse(dataString);
        } catch (ParseException ex) {
            throw new ValidacaoException(ex.getMessage() + "\n Data Invalida");
        }
    }

    public static String formatDate(String dateIn) {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dFormat.parse(dateIn);
            dFormat.applyPattern("dd/MM/yyyy");
            return dFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Geral.class.getName()).log(Level.SEVERE, null, ex);
            return dateIn;
        }
    }

    public static void capturaCertificados() {
        // Acrescenta Certificados -------------------------------------------
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Trust always
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Trust always
                    }
                }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            // Create empty HostnameVerifier
            HostnameVerifier hv = new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            };
            try {
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (KeyManagementException ex) {
                Logger.getLogger(Geral.class.getName()).log(Level.SEVERE, null, ex);
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Geral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String gerarChaveDeAcessoNfe(String chaveSemDigito) throws ValidacaoException {

        // UMA CHAVE DE ACESSO DE NF-E TEM 44 DIGITOS, ENTAO O CALCULO SE DÁ COM OS 43 ANTERIORES  
        if (chaveSemDigito.length() != 43) {
            throw new ValidacaoException("Chave Invalida possui " + chaveSemDigito.length());
        }
        int total = 0;
        int peso = 2;

        for (int i = 0; i < chaveSemDigito.length(); i++) {
            total += (chaveSemDigito.charAt((chaveSemDigito.length() - 1) - i) - '0') * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        int resto = total % 11;
        int dvi = (resto == 0 || resto == 1) ? 0 : (11 - resto);

        String chaveFinal = (chaveSemDigito + String.valueOf(dvi));
        return chaveFinal;

    }

    public static String completaComZero(String palavra, int tamanho) {
        String r = String.valueOf(palavra).trim();
        int tam = r.length();
        for (int j = tam; j <= tamanho - 1; j++) {
            r = "0" + r;
        }
        return r;
    }

    public static String retornaDataNFCe(Date dataASerFormatada) throws ValidacaoException {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dataASerFormatada);
        XMLGregorianCalendar xmlCalendar;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            xmlCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);

            return (xmlCalendar.toString());
        } catch (DatatypeConfigurationException ex) {
            throw new ValidacaoException("Erro ao gerar tag Data Hora emissão!");
        }
    }

    public static void removerArquivos(File f) {
        // Se o arquivo passado for um diretório
        if (f.isDirectory()) {
            /* Lista todos os arquivos do diretório em um array
             de objetos File */
            File[] files = f.listFiles();
            // Identa a lista (foreach) e deleta um por um
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String converteddMMaaaAAAAmmDD(String dataString) {
        return dataString.substring(6, 10) + "-" + dataString.substring(3, 5) + "-" + dataString.substring(0, 2);
    }

    public static String converteAAAAmmDDddMMaaa(String dataString) {
        return dataString.substring(8, 10) + "-" + dataString.substring(5, 7) + "-" + dataString.substring(0, 4);
    }

    public static Date convertStringToDataAnoMesDia(String dataString) throws ValidacaoException {
        if (dataString.length() != 10) {
            throw new ValidacaoException("Formato diferente de __/__/____\n Data Invalida");
        } else {
            try {
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy/MM/dd");
                formatador.setLenient(false);
                return formatador.parse(dataString);
            } catch (ParseException ex) {
                throw new ValidacaoException(ex.getMessage() + "\n Data Invalida");
            }
        }
    }

    public static Date convertStringToDataDiaMesAno(String dataString) throws ValidacaoException {
        if (dataString.length() != 10) {
            throw new ValidacaoException("Formato diferente de __/__/____\n Data Invalida");
        } else {

            try {
                SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                formatador.setLenient(false);
                return formatador.parse(dataString);
            } catch (ParseException ex) {
                throw new ValidacaoException(ex.getMessage() + "\n Data Invalida");
            }
        }
    }

    public static int diferencaEntreDatas(String data1, String data2) throws ParseException {
        GregorianCalendar ini = new GregorianCalendar();
        GregorianCalendar fim = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ini.setTime(sdf.parse(data1));
        fim.setTime(sdf.parse(data2));
        long dt1 = ini.getTimeInMillis();
        long dt2 = fim.getTimeInMillis();
        int dias = (int) ((dt1 - dt2) / 86400000);
        if (dias > 0) {
            return dias + 1;
        } else {
            return dias;
        }
    }

    public static boolean temConexaoInternet() {
        boolean conexao = false;
        try {
            //java.net.URL testaConexao = new java.net.URL("http://www.google.com");
            java.net.URL testaConexao = new java.net.URL("http://www.nfe.fazenda.gov.br/portal");
            java.net.URLConnection conn = testaConexao.openConnection();
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) conn;
            httpConn.connect();
            if (httpConn.getResponseCode() == 200) {
                conexao = true;
            }
        } catch (IOException ioexcp) {
            conexao = false;
            System.out.println("---------------------------------------------");
            System.out.println("--------- Erro " + ioexcp.getMessage());
            System.out.println("--------------------------------------------");
        }
         //boolean conexao = true;
        return conexao;
    }

        public static boolean temConexaoIntegrator() {
        boolean conexaoIntegrator = false;
        try {
            java.net.URL testaConexao = new java.net.URL("http://www.rosamarc.srv.br");
            java.net.URLConnection conn = testaConexao.openConnection();
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) conn;
            httpConn.connect();
            if (httpConn.getResponseCode() == 200) {
                conexaoIntegrator = true;
            }
        } catch (IOException ioexcp) {
            conexaoIntegrator = false;
            System.out.println("---------------------------------------------");
            System.out.println("--------- Erro " + ioexcp.getMessage());
            System.out.println("--------------------------------------------");
        }
         //boolean conexao = true;
        return conexaoIntegrator;
    }
        
    public static void considerarEnterComoTab(Component comp) {
        Set<AWTKeyStroke> keystrokes = comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set<AWTKeyStroke> newKeystrokes = new HashSet<>(keystrokes);
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeystrokes);
    }

    public static String valorPorExtenso(double vlr) {
        if (vlr == 0) {
            return ("zero");
        }

        long inteiro = (long) Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionÃ¡ria do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 10) {
            return ("Erro: Valor superior ao tamanho do campo BD!");
        }

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "trÃªs", "quatro", "cinco",
            "seis", "sete", "oito", "nove", "dez", "onze",
            "doze", "treze", "quatorze", "quinze", "dezesseis",
            "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
            "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhao", "bilhao", "trilhao"};
        String[] qualificaP = {"", "mil", "milhoes", "bilhoes", "trilhoes"};

        // definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
            // retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
            // 1a. parte = 789 (centena)
            // 2a. parte = 456 (mil)
            // 3a. parte = 123 (milhÃµes)
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else { // Ãºltima parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((dez != 0) || (unid != 0)) {
                        if ((n % 100) <= 19) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[n % 100];
                            } else {
                                saux = unidade[n % 100];
                            }
                        } else {
                            if (saux.length() != 0) {
                                saux = saux + " e " + dezena[dez];
                            } else {
                                saux = dezena[dez];
                            }
                            if (unid != 0) {
                                if (saux.length() != 0) {
                                    saux = saux + " e " + unidade[unid];
                                } else {
                                    saux = unidade[unid];
                                }
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + ", " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor
            }
            i = i + 1; // prÃ³ximo qualificador: 1- mil, 2- milhÃ£o, 3- bilhÃ£o, ...
        }

        if (s.length() != 0) {
            if (umReal) {
                s = s + " real";
            } else if (tem) {
                s = s + " reais";
            } else {
                s = s + " de reais";
            }
        }

        // definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se nÃ£o Ã© valor somente com centavos
            {
                s = s + " e ";
            }
            if (centavos.equals("1")) {
                s = s + "um centavo";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " e " + unidade[unid];
                    }
                }
                s = s + " centavos";
            }
        }
        return (s);
    }

    public static String repeteReal(double total, int tamanho) {
        String r = String.valueOf(duasDecimais.format(total)).trim();
        int tam = r.length();
        for (int j = tam; j <= tamanho - 1; j++) {
            r = " " + r;
        }
        return r + " ";
    }

    public static String repeteString(String palavra, int tamanho) {
        int tam = palavra.length();
        for (int j = tam; j <= tamanho - 1; j++) {
            palavra = palavra + " ";
        }
        return palavra + " ";
    }

    public static String repeteInt(int total, int tamanho) {
        String r = String.valueOf(total).trim();
        int tam = r.length();
        for (int j = tam; j <= tamanho - 1; j++) {
            r = " " + r;
        }
        return r + " ";
    }

    public static String substituiAcentos(String entrada) {
        Pattern numericos = Pattern.compile("[0-9a-z ;=()-]", Pattern.CASE_INSENSITIVE);
        Matcher encaixe = numericos.matcher(entrada);
        StringBuilder saida = new StringBuilder();
        while (encaixe.find()) {
            saida.append(encaixe.group());
        }
        return saida.toString();
    }

    public static void copiaArquivo(File origem, File destino) throws IOException {
        try {
            InputStream in = new FileInputStream(origem);
            OutputStream out = new FileOutputStream(destino);           // Transferindo bytes de entrada para saída
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro Copia arquivo" + e.toString());
        }
    }

    public static void moveArquivos(File origem, File destino) throws IOException {
        try {
            InputStream in = new FileInputStream(origem);
            OutputStream out = new FileOutputStream(destino);           // Transferindo bytes de entrada para saída
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            origem.delete();
        } catch (FileNotFoundException e) {
            System.out.println("Erro " + e.toString());

        }
    }

    public static void moveArquivosDirectorio(File origemDir, File destinoDir) {
        if (origemDir.isDirectory()) {
            if (!destinoDir.exists()) {
                destinoDir.mkdir();
            }
            String[] children = origemDir.list();
            for (String children1 : children) {
                try {
                    moveArquivos(new File(origemDir, children1), new File(destinoDir, children1));
                } catch (IOException ex) {
                    Logger.getLogger(Geral.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static String getNomeSchema() {
        FileInputStream stream;

        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\config.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            String result = br.readLine().trim();
            if (result.contains("<bancodedados>")) {
                return result.substring(result.indexOf("<bancodedados>"), result.indexOf("</bancodedados>")).replaceAll("<bancodedados>", "");
            } else {
                return "minicxi";
            }
        } catch (IOException | ValidacaoException ex) {
            return "minicxi";
        }
    }

    public static String getIPdoBanco() {
        FileInputStream stream;

        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\configip.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            String result = br.readLine().trim();
            if (result.contains("<ipbancodedados>")) {
                return result.substring(result.indexOf("<ipbancodedados>"), result.indexOf("</ipbancodedados>")).replaceAll("<ipbancodedados>", "");
            } else {
                return "localhost";
            }
        } catch (IOException | ValidacaoException ex) {
            return "localhost";
        }
    }

    public static boolean getAmbienteEspecial() {
        FileInputStream stream;

        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\AmbienteEspecial.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            String result = br.readLine().trim();
            if (result.contains("<ambiente>")) {
                return (result.substring(result.indexOf("<ambiente>"), result.indexOf("</ambiente>")).replaceAll("<ambiente>", "").equals("SIM"));
            } else {
                return false;
            }
        } catch (IOException | ValidacaoException ex) {
            return false;
        }
    }
        
    public static String getDiretorioAtual() throws ValidacaoException {
        File dir1 = new File(".");
        try {
            return dir1.getCanonicalPath();
        } catch (Exception e) {
            throw new ValidacaoException(e);
        }
    }

    public static String getDiretorioAtualDocFiscal() throws ValidacaoException {
        File dir1 = new File(".");
        String letra = getDriveUninfe().toUpperCase();
        try {
            if (letra.toUpperCase().equals("C:")) {
                return dir1.getCanonicalPath();
            } else {
                return letra;
            }
        } catch (Exception e) {
            throw new ValidacaoException(e);
        }
    }

    public static String getDriveUninfe() {
        FileInputStream stream;

        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\configdriveuninfe.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            String result = br.readLine().trim();
            if (result.contains("<driveuninfe>")) {
                return result.substring(result.indexOf("<driveuninfe>"), result.indexOf("</driveuninfe>")).replaceAll("<driveuninfe>", "");
            } else {
                return "c:";
            }
        } catch (IOException | ValidacaoException ex) {
            return "c:";
        }
    }

    public static boolean eVersao8BD() {
        FileInputStream stream;

        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\configversaobd.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            String result = br.readLine().trim();
            if (result.contains("<versaobd>")) {
                String v = result.substring(result.indexOf("<versaobd>"), result.indexOf("</versaobd>")).replaceAll("<versaobd>", "");
                return v.equals("8");
            } else {
                return false;
            }
        } catch (IOException | ValidacaoException ex) {
            return false;
        }
    }
        
    public static void criaArquivoLinhasNFeSimplesRemessa(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\linhasNFeSimplesRemessa.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static void criaArquivoLinhasNFeDevolucao(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\linhasNFeDevolucao.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static void criaArquivoLinhasNFeRemessaTroca(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\linhasNFeRemessaTroca.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }
        
     public static void criaArquivoLinhasNFeDevolucaoMercadoriaConsignada(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\NFeDevolucaoMercadoriaConsignada.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }
     
    public static void criaArquivoLinhasNFeDevolucaoNaoContribuinte(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\linhasNFeDevolucaoNaoContribuinte.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static void criaArquivoLinhasNFe(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\linhasNFe.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static void criaArquivoLinhasNFeSimplesRemessaImpostos(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\linhasNFeSimplesRemessaImpostos.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }
        
    public static void criaArquivoLinhasEmailCtb(String linha) throws ValidacaoException {
        // Usado para gravar o numero de linhas a aparecer no pesquisa
        try {
            File arquivo;
            arquivo = new File(getDiretorioAtual() + "\\Relatorios\\emialctb.ini");
            FileOutputStream fos;
            fos = new FileOutputStream(arquivo);
            fos.write(linha.getBytes());
            fos.close();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static String getArquivoLinhasNFe() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\linhasNFe.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static String getArquivoLinhasNFeSimplesRemessaImpostos() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\linhasNFeSimplesRemessaImpostos.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }
    public static String getArquivoLinhasNFeSimplesRemessa() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\linhasNFeSimplesRemessa.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static String getArquivoLinhasNFeDevolucao() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\linhasNFeDevolucao.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    
    public static String getArquivoLinhasNFeRemessaTroca() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\linhasNFeRemessaTroca.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }
    
    public static String getArquivoLinhasNFeDevolucaoSimbolicaMercadoriaVendida() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\NFeDevolucaoSimbolicaMercadoriaVendida.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static String getArquivoLinhasNFeDevolucaoMercadoriaConsignada() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\NFeDevolucaoMercadoriaConsignada.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static String getArquivoLinhasNFeDevolucaoNaoContribuinte() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\linhasNFeDevolucaoNaoContribuinte.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static String getArquivoLinhasEmialCtb() throws ValidacaoException {
        FileInputStream stream;
        try {
            stream = new FileInputStream(getDiretorioAtual() + "\\Relatorios\\emialctb.ini");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            return br.readLine().trim();
        } catch (IOException | NullPointerException ex) {
            throw new ValidacaoException(ex);
        }
    }

    public static double arredondar(double valor, int casas, int ceilOrFloor) {
        /*
         1 - Valor a arredondar. 
         2 - Quantidade de casas depois da vírgula. 
         3 - Arredondar para cima ou para baixo? Para cima = 0 (ceil) Para baixo = 1 ou qualquer outro inteiro (floor)
        */
        double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        if (ceilOrFloor == 0) {
            arredondado = Math.ceil(arredondado);
        } else {
            arredondado = Math.floor(arredondado);
        }
        arredondado /= (Math.pow(10, casas));
        return arredondado;
    }
    

}
