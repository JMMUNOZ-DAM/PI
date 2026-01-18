/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

/**
 *
 * @author Juanma Muñoz
 */
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class SimuladorDiagnostico {

    private final Random random = new Random();

    // =============================================================
    // MODELO DE APARATO
    // =============================================================
    public static class Aparato {

        private final String tipoAparato;
        private final String marca;
        private final String modelo;
        private final String numeroSerie;
        private final String mac;
        private final int idContrato;
        private final int idAparato;

        public Aparato(String tipoAparato, String marca, String modelo, String numeroSerie, String mac, int idContrato, int idAparato) {
            this.tipoAparato = tipoAparato;
            this.marca = marca;
            this.modelo = modelo;
            this.numeroSerie = numeroSerie;
            this.mac = mac;
            this.idContrato = idContrato;
            this.idAparato = idAparato;
        }

        public String getTipoAparato() {
            return tipoAparato;
        }

        public int getIdAparato() {
            return idAparato;
        }

        public String getMarca() {
            return marca;
        }

        public String getModelo() {
            return modelo;
        }

        public String getNumeroSerie() {
            return numeroSerie;
        }

        public String getMac() {
            return mac;
        }

        public int getIdContrato() {
            return idContrato;
        }

    }

    // =============================================================
    // MODELO DE DIAGNOSTICO
    // =============================================================
    public static class Diagnostico {

        public String estadoGeneral;
        public double velocidadInternet;
        public String nivelesOpticos;
        public String cobertura;
        public double ping;
        public String observaciones;

        public String getEstadoGeneral() {
            return estadoGeneral;
        }

        public double getVelocidadInternet() {
            return velocidadInternet;
        }

        public String getNivelesOpticos() {
            return nivelesOpticos;
        }

        public String getCobertura() {
            return cobertura;
        }

        public double getPing() {
            return ping;
        }

        public String getObservaciones() {
            return observaciones;
        }

        @Override
        public String toString() {
            return "Estado: " + estadoGeneral
                    + "\nVelocidad: " + velocidadInternet + " Mbps"
                    + "\nNiveles ópticos: " + nivelesOpticos
                    + "\nCobertura: " + cobertura
                    + "\nPing: " + ping + " ms"
                    + "\nObs: " + observaciones;
        }

    }

    // =============================================================
    // GENERADOR DE DIAGNÓSTICO SEGÚN EL APARATO
    // =============================================================
    public Diagnostico generarDiagnostico(Aparato aparato) {
        Diagnostico diag = new Diagnostico();

        switch (aparato.getTipoAparato()) {
            case "FTTH" ->
                simularFTTH(diag);
            case "5G" ->
                simular5G(diag);
            default ->
                simularGenerico(diag);
        }
        guardarDiagnostico(aparato, diag);
        return diag;
    }

    public void generarDiagnosticoConCarga(JFrame parent, Aparato aparato, JList<String> listaDiagnostico) {
        // Crear diálogo de carga
        JDialog loadingDialog = new JDialog(parent, "Generando diagnóstico", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Cargando niveles y realizando diagnóstico...");
        loadingDialog.add(progressBar);
        loadingDialog.setSize(400, 100);
        loadingDialog.setLocationRelativeTo(parent);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        SwingWorker<Diagnostico, String> worker = new SwingWorker<>() {
            @Override
            protected Diagnostico doInBackground() throws Exception {
                // Simular pasos
                String[] pasos = {"Velocidad internet", "Niveles ópticos", "Cobertura", "Ping"};
                for (String paso : pasos) {
                    Thread.sleep(1000); // simula tiempo de carga
                    publish("Comprobando: " + paso);
                }

                // Ejecutar diagnóstico real usando tu método existente
                return generarDiagnostico(aparato);
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                progressBar.setString(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                try {
                    Diagnostico d = get();

                    DefaultListModel<String> model = (DefaultListModel<String>) listaDiagnostico.getModel();
                    if (model == null) {
                        model = new DefaultListModel<>();
                    } else {
                        model.clear();
                    }

                    model.addElement("---Diagnóstico actualizado---");
                    model.addElement("Estado: " + d.getEstadoGeneral());
                    model.addElement("Velocidad: " + String.format("%.2f", d.getVelocidadInternet()) + " Mbps");
                    model.addElement("Niveles ópticos: " + d.getNivelesOpticos());
                    model.addElement("Cobertura: " + d.getCobertura());
                    model.addElement("Ping: " + String.format("%.2f", d.getPing()) + " ms");
                    model.addElement("Observaciones: " + d.getObservaciones());
                    listaDiagnostico.setModel(model);

                    JOptionPane.showMessageDialog(
                            parent,
                            "Diagnóstico completado para: " + aparato.getNumeroSerie(),
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            parent,
                            "Error al generar diagnóstico",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }

    // =============================================================
    // SIMULACIÓN PARA APARATOS FTTH
    // =============================================================
    private void simularFTTH(Diagnostico d) {

        double velocidad = 600 + random.nextDouble() * 500; // 500–1000 Mbps
        double ping = 5 + random.nextDouble() * 10;         // 5–15 ms
        double nivelOptico = -28 + random.nextDouble() * 10; // -28 a -18 dBm

        d.velocidadInternet = velocidad;
        d.ping = ping;
        d.nivelesOpticos = String.format("%.1f dBm", nivelOptico);
        d.cobertura = "N/A (FTTH)";

        if (nivelOptico < -25) {
            d.estadoGeneral = "advertencia";
            d.observaciones = "Nivel óptico bajo. Posible problema en fibra.";
            d.velocidadInternet = 10 + random.nextDouble() * 20;
            d.ping = 99 + random.nextDouble() * 10;
        } else if (velocidad < 400) {
            d.estadoGeneral = "advertencia";
            d.observaciones = "Rendimiento inferior al esperado.";

        } else {
            d.estadoGeneral = "correcto";
            d.observaciones = "Funcionamiento óptimo.";
        }
    }

    // =============================================================
    // SIMULACIÓN PARA APARATOS 5G
    // =============================================================
    private void simular5G(Diagnostico d) {

        double velocidad = random.nextDouble() * 600 + 200;   // 0–600 Mbps
        double ping = 20 + random.nextDouble() * 20 + 50;   // 20–220 ms
        String[] coberturas = {"Excelente", "Buena", "Irregular", "Sin señal"};
        String cobertura = coberturas[random.nextInt(coberturas.length)];

        d.velocidadInternet = velocidad;
        d.ping = ping;
        d.cobertura = cobertura;
        d.nivelesOpticos = "N/A (5G)"; // No aplica en 5G

        if (null == cobertura) {
            d.estadoGeneral = "correcto";
            d.observaciones = "Sin incidencias detectadas.";
        } else {
            switch (cobertura) {
                case "Sin señal" -> {
                    d.estadoGeneral = "fallo";
                    d.observaciones = "No hay conexión 5G. El dispositivo no responde.";
                    d.velocidadInternet = 0;
                    d.ping = 9999999;
                }
                case "Irregular" -> {
                    d.estadoGeneral = "advertencia";
                    d.observaciones = "Cobertura 5G inestable.";
                    d.velocidadInternet = random.nextDouble() * 20;
                    d.ping = 99 + random.nextDouble() * 200;
                }
                default -> {
                    d.estadoGeneral = "correcto";
                    d.observaciones = "Sin incidencias detectadas.";
                }
            }
        }
    }

    // =============================================================
    // SIMULACIÓN GENÉRICA (por si aparecen nuevos tipos)
    // =============================================================
    private void simularGenerico(Diagnostico d) {
        d.estadoGeneral = "desconocido";
        d.velocidadInternet = 0;
        d.nivelesOpticos = "N/A";
        d.cobertura = "N/A";
        d.ping = 999;
        d.observaciones = "Tipo de aparato no soportado.";
    }

    private void guardarDiagnostico(Aparato aparato, Diagnostico diag) {
        DatabaseManager db = DatabaseManager.getInstance();

        try {
            db = DatabaseManager.getInstance();

            db.executePreparedUpdate(
                    querys.diagnostico,
                    aparato.getIdAparato(), // id_aparato
                    new Timestamp(System.currentTimeMillis()), // fecha_actualizacion
                    diag.estadoGeneral,
                    diag.velocidadInternet,
                    diag.nivelesOpticos,
                    diag.cobertura,
                    diag.ping,
                    diag.observaciones
            );

            System.out.println("Diagnóstico guardado en la BD.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // =============================================================
    // MÉTODO DE PRUEBA
    // =============================================================
    public static void main(String[] args) {
        SimuladorDiagnostico s = new SimuladorDiagnostico();
        System.out.println("=============================FTTH==============================");
        Aparato a = new Aparato("FTTH", "Huawei", "HG8145V5", "SNFTTH001", "ABC123", 1, 5);
        Diagnostico d = s.generarDiagnostico(a);
        System.out.println(d);
//        System.out.println("==============================5G===============================");
//        Aparato b = new Aparato("5G", "ESIM", "ESIMBLANCA", "8434000001234564", "OEOE", 8);
//        d = s.generarDiagnostico(b);
        // System.out.println(d);
    }
}
