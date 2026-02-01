/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.modelo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

import com.jmmunoz.netfix.vista.tema.TelecomTheme;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 * Servicio de simulación de diagnóstico para equipos de red.
 * <p>
 * Genera valores aleatorios realistas (velocidad, ping, cobertura, niveles
 * ópticos)
 * para diferentes tipos de dispositivos (FTTH, 5G), permitiendo probar la
 * interfaz
 * sin necesidad de hardware real conectado.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class SimuladorDiagnostico {

    private final Random random = new Random();

    // =============================================================
    // MODELO DE APARATO
    // =============================================================
    /**
     * Modelo inmutable que representa un dispositivo de red.
     * Contiene los datos necesarios para identificar el aparato y realizar el
     * diagnóstico.
     */
    public static class Aparato {

        private final String tipoAparato;
        private final String marca;
        private final String modelo;
        private final String numeroSerie;
        private final String mac;
        private final int idContrato;
        private final int idAparato;

        public Aparato(String tipoAparato, String marca, String modelo, String numeroSerie, String mac, int idContrato,
                int idAparato) {
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
    /**
     * Modelo de datos que almacena los resultados de un diagnóstico.
     * Incluye métricas técnicas (velocidad, ping, cobertura) y una valoración
     * general del estado del dispositivo.
     */
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
    /**
     * Genera un diagnóstico completo para el aparato proporcionado.
     * <p>
     * Determina el algoritmo de simulación según el tipo de aparato (FTTH o 5G)
     * y persiste el resultado en la base de datos.
     * </p>
     * 
     * @param aparato El dispositivo a diagnosticar.
     * @return Objeto {@link Diagnostico} con los resultados.
     */
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

    /**
     * Ejecuta el diagnóstico en segundo plano mostrando una barra de progreso.
     * <p>
     * Útil para la UI, ya que evita congelar la interfaz durante el proceso.
     * Actualiza un JList con los resultados al finalizar.
     * </p>
     * 
     * @param parent           Ventana padre para el diálogo modal de carga.
     * @param aparato          Dispositivo a diagnosticar.
     * @param listaDiagnostico JList donde se mostrarán los resultados.
     */
    public void generarDiagnosticoConCarga(JFrame parent, Aparato aparato, JList<String> listaDiagnostico,
            Runnable onSuccess) {
        // Crear diálogo de carga SIN DECORACIÓN
        JDialog loadingDialog = new JDialog(parent, true);
        loadingDialog.setUndecorated(true);
        loadingDialog.setBackground(TelecomTheme.TRANSPARENT); // transparente real

        // Panel principal con bordes redondeados y fondo semitransparente
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fondo blanco con opacidad (245/255)
                g2.setColor(TelecomTheme.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                // Borde sutil
                g2.setColor(TelecomTheme.BORDER_DARK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new java.awt.BorderLayout(0, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        // Etiqueta de Título
        JLabel titleLabel = new JLabel(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.loading.title"),
                SwingConstants.CENTER);
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        titleLabel.setForeground(TelecomTheme.TEXT_DARK);
        panel.add(titleLabel, java.awt.BorderLayout.NORTH);

        // Barra de progreso estilizada
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new java.awt.Dimension(300, 6)); // Más fina
        progressBar.setForeground(TelecomTheme.CHART_PRIMARY); // Azul corporativo
        progressBar.setBackground(TelecomTheme.GRID_LINE);
        progressBar.setBorder(null);
        panel.add(progressBar, java.awt.BorderLayout.CENTER);

        // Etiqueta de estado cambiante
        JLabel statusLabel = new JLabel(
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.loading.status"),
                SwingConstants.CENTER);
        statusLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        statusLabel.setForeground(TelecomTheme.TEXT_MUTED);
        panel.add(statusLabel, java.awt.BorderLayout.SOUTH);

        loadingDialog.add(panel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(parent);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        SwingWorker<Diagnostico, String> worker = new SwingWorker<>() {
            @Override
            protected Diagnostico doInBackground() throws Exception {
                // Simular pasos visuales
                String[] pasos = {
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.step.1"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.step.2"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.step.3"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.step.4"),
                        com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.step.5")
                };

                for (String paso : pasos) {
                    publish(paso);
                    Thread.sleep(800 + random.nextInt(400)); // Tiempo variable para realismo
                }

                // Ejecutar diagnóstico real
                return generarDiagnostico(aparato);
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                // Actualizamos el texto inferior
                statusLabel.setText(chunks.get(chunks.size() - 1));
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

                    model.addElement(
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.result.header"));
                    model.addElement(" ");
                    model.addElement(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.result.general")
                            + d.getEstadoGeneral().toUpperCase());
                    model.addElement(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.result.speed")
                            + String.format("%.2f", d.getVelocidadInternet()) + " Mbps");
                    model.addElement(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.result.optical")
                            + d.getNivelesOpticos());
                    model.addElement(
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.result.coverage")
                                    + d.getCobertura());
                    model.addElement(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.result.ping")
                            + String.format("%.2f", d.getPing()) + " ms");
                    model.addElement(" ");
                    model.addElement(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.result.obs")
                            + d.getObservaciones());
                    listaDiagnostico.setModel(model);

                    // Mensaje final
                    com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                            parent,
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.msg.success.title"),
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.msg.success.content"),
                            com.jmmunoz.netfix.vista.tema.CustomNotification.Type.SUCCESS);

                    // Ejecutar callback si existe
                    if (onSuccess != null) {
                        onSuccess.run();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    com.jmmunoz.netfix.vista.tema.CustomNotification.show(
                            parent,
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.msg.error.title"),
                            com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("diag.msg.error.content")
                                    + ex.getMessage(),
                            com.jmmunoz.netfix.vista.tema.CustomNotification.Type.ERROR);
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }

    // =============================================================
    // SIMULACIÓN PARA APARATOS FTTH
    // =============================================================
    /**
     * Simula el diagnóstico de una conexión de Fibra Óptica (FTTH).
     * Genera valores como niveles ópticos (dBm), velocidad y ping.
     * 
     * @param d Objeto Diagnostico a rellenar.
     */
    private void simularFTTH(Diagnostico d) {

        double velocidad = 600 + random.nextDouble() * 500; // Mbps
        double ping = 5 + random.nextDouble() * 10; // ms
        double nivelOptico = -28 + random.nextDouble() * 10; // dBm

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
    /**
     * Simula el diagnóstico de una conexión móvil 5G.
     * Evalúa cobertura y velocidad, omitiendo niveles ópticos.
     * 
     * @param d Objeto Diagnostico a rellenar.
     */
    private void simular5G(Diagnostico d) {

        double velocidad = random.nextDouble() * 600 + 200; // Mbps
        double ping = 20 + random.nextDouble() * 20 + 50; // ms
        String[] coberturas = { "Excelente", "Buena", "Irregular", "Sin señal" };
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

    /**
     * Guarda el resultado del diagnóstico en la base de datos.
     * Utiliza la fecha y hora actual como timestamp.
     * 
     * @param aparato El dispositivo diagnosticado.
     * @param diag    El resultado del diagnóstico.
     */
    private void guardarDiagnostico(Aparato aparato, Diagnostico diag) {
        DatabaseManager db = DatabaseManager.getInstance();

        try {
            db = DatabaseManager.getInstance();

            db.executePreparedUpdate(
                    Querys.diagnostico,
                    aparato.getIdAparato(), // id_aparato
                    new Timestamp(System.currentTimeMillis()), // fecha_actualizacion
                    diag.estadoGeneral,
                    diag.velocidadInternet,
                    diag.nivelesOpticos,
                    diag.cobertura,
                    diag.ping,
                    diag.observaciones);

            System.out.println("Diagnóstico guardado en la BD.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
