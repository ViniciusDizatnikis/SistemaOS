package br.com.SistemaOS.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

public class RelatoriosDAO {
    private final Connection con;
    private final boolean status;

    public RelatoriosDAO() {
        this.con = ModuloDeConexao.conector();
        this.status = con != null;
    }

    public void getRelatorioClientes() {
        try {
            InputStream relatorioStream = getClass().getResourceAsStream("/reports/Clientes.jasper");
            
            if (relatorioStream == null) {
                System.err.println("Erro: Arquivo Clientes.jasper não encontrado no diretório reports.");
                return;
            }

            HashMap<String, Object> parametros = new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);

            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getRelatorioServicos() {
        try {
            InputStream relatorioStream = getClass().getResourceAsStream("/reports/Servicos.jasper");
            
            if (relatorioStream == null) {
                System.err.println("Erro: Arquivo Clientes.jasper não encontrado no diretório reports.");
                return;
            }

            HashMap<String, Object> parametros = new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);

            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getRelatorioUsuarios() {
        try {
            InputStream relatorioStream = getClass().getResourceAsStream("/reports/Usuarios.jasper");
            
            if (relatorioStream == null) {
                System.err.println("Erro: Arquivo Clientes.jasper não encontrado no diretório reports.");
                return;
            }

            HashMap<String, Object> parametros = new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, con);

            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            System.err.println("Erro ao gerar o relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
