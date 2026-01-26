/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.jmmunoz.netfix.vista;

import com.jmmunoz.netfix.modelo.Usuario;
import com.jmmunoz.netfix.vista.tema.ThemeManager;
import java.awt.CardLayout;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Ventana principal (Dashboard) de la aplicación.
 * <p>
 * Implementa un diseño de barra lateral de navegación y un panel de contenido
 * que cambia dinámicamente usando {@link CardLayout}. Gestiona los permisos
 * basados
 * en roles y la sesión del usuario actual.
 * </p>
 * 
 * @author Juanma Muñoz
 */
public class MainFrame extends javax.swing.JFrame {

    private final CardLayout cardLayout;
    private final EstadisPanel estadisPanel;
    private final IncidPanel incidenciasPanel;
    private final AparatosPanel aparatosPanel;
    private final SupervisorPanel supervisorPanel;
    private final AdminPanel adminPanel;
    private final UsuariosPanel usuariosPanel;
    private Usuario usuario;
    private JButton activeButton = null;
    final int ICON_SIZE = 24;

    /**
     * Crea un nuevo mainFrame e inicializa todos los paneles.
     * 
     * @param usuario El objeto {@link Usuario} autenticado que inicia la sesión.
     */
    public MainFrame(Usuario usuario) {
        initComponents();
        setupWindowSize();
        contentPanel.setPreferredSize(null);
        contentPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        contentPanel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        /*
         * =========================
         * ICONO DE VENTANA
         * =========================
         */
        URL logoIcon = getClass().getResource("/img/netfix_N.png");
        if (logoIcon != null) {
            Image base = new ImageIcon(logoIcon).getImage();

            List<Image> icons = List.of(
                    base.getScaledInstance(16, 16, Image.SCALE_SMOOTH),
                    base.getScaledInstance(24, 24, Image.SCALE_SMOOTH),
                    base.getScaledInstance(32, 32, Image.SCALE_SMOOTH),
                    base.getScaledInstance(48, 48, Image.SCALE_SMOOTH),
                    base.getScaledInstance(64, 64, Image.SCALE_SMOOTH),
                    base.getScaledInstance(128, 128, Image.SCALE_SMOOTH));

            setIconImages(icons);
        }
        this.usuario = usuario;
        cargarDatosUsuario();
        aplicarPermisosPorRol();
        setLocationRelativeTo(null);
        setActiveButton(estaButton);
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        estadisPanel = new EstadisPanel();
        incidenciasPanel = new IncidPanel(usuario);
        aparatosPanel = new AparatosPanel();
        adminPanel = new AdminPanel();
        supervisorPanel = new SupervisorPanel(usuario);
        usuariosPanel = new UsuariosPanel(usuario);
        contentPanel.add(estadisPanel, "estadisticas");
        contentPanel.add(incidenciasPanel, "incidencias");
        contentPanel.add(aparatosPanel, "aparatos");
        contentPanel.add(supervisorPanel, "supervisores");
        contentPanel.add(usuariosPanel, "usuarios");
        contentPanel.add(adminPanel, "admin");
        cardLayout.show(contentPanel, "estadisticas");
        contentPanel.revalidate();
        contentPanel.repaint();
        ThemeManager.getInstance().applyMainFrameTheme(this);
        new com.jmmunoz.netfix.controlador.Utilities().logAction("OK", "MainFrame",
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("main.log.start") + usuario.getNombre());
    }

    /**
     * Configura el tamaño inicial de la ventana para adaptarse a la pantalla.
     * Establece el estado maximizado por defecto.
     */
    private void setupWindowSize() {
        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        setSize(
                gd.getDisplayMode().getWidth(),
                gd.getDisplayMode().getHeight());
        setLocation(0, 0);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void cargarDatosUsuario() {
        logName.setText(usuario.getNombre());
        logRol.setText(usuario.getRol());
    }

    /**
     * Habilita o deshabilita botones del menú lateral según el rol del usuario.
     * <p>
     * - Sistemas: Acceso total.
     * - Técnico: Acceso limitado (sin administración).
     * </p>
     */
    private void aplicarPermisosPorRol() {

        String rol = usuario.getRol().toLowerCase();

        switch (rol) {

            case "sistemas":
                // Acceso total
                adminButton.setEnabled(true);
                break;

            case "supervisor":
                // Puede ver admin pero no tocar todo (ejemplo)
                adminButton.setEnabled(false);

                break;

            case "tecnico":
                // Sin acceso a administración
                adminButton.setEnabled(false);
                usuButton.setText(
                        " " + com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("main.nav.users.singular")
                                .trim());

                break;

            default:
                // Rol desconocido = acceso mínimo
                adminButton.setEnabled(false);
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * 
     * @param estaButton
     * @param inciButton
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondoPanel = new javax.swing.JPanel();
        panelLateral = new javax.swing.JPanel();
        estaButton = estaButton = new javax.swing.JButton();
        estaButton.setLayout(new java.awt.BorderLayout());

        // Texto / comportamiento
        estaButton.setText("Estadísticas");
        estaButton.setFocusPainted(false);
        estaButton.setBorderPainted(false);
        estaButton.setContentAreaFilled(false);
        estaButton.setOpaque(false);
        estaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        estaButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        estaButton.setMargin(new java.awt.Insets(10, 16, 10, 16));
        estaButton.putClientProperty("JButton.buttonType", "borderless");

        // (Opcional recomendado) ancho fijo para justificar iconos en columna
        estaButton.setPreferredSize(new java.awt.Dimension(260, 48));
        estaButton.setMinimumSize(new java.awt.Dimension(260, 48));
        estaButton.setMaximumSize(new java.awt.Dimension(260, 48));

        // Icono a la derecha
        java.net.URL estaUrl = getClass().getResource("/img/nav/stats.png");
        if (estaUrl != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(estaUrl);
            java.awt.Image scaled = icon.getImage().getScaledInstance(
                    ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH);

            javax.swing.JLabel iconLabel = new javax.swing.JLabel(new javax.swing.ImageIcon(scaled));
            iconLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 16));
            iconLabel.setOpaque(false);
            iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            estaButton.add(iconLabel, java.awt.BorderLayout.EAST);
        }
        ;
        inciButton = inciButton = new javax.swing.JButton();
        inciButton.setLayout(new java.awt.BorderLayout());

        // Texto / comportamiento
        inciButton.setText("Incidencias");
        inciButton.setFocusPainted(false);
        inciButton.setBorderPainted(false);
        inciButton.setContentAreaFilled(false);
        inciButton.setOpaque(false);
        inciButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        inciButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inciButton.setMargin(new java.awt.Insets(10, 16, 10, 16));
        inciButton.putClientProperty("JButton.buttonType", "borderless");

        // ancho fijo
        inciButton.setPreferredSize(new java.awt.Dimension(260, 48));
        inciButton.setMinimumSize(new java.awt.Dimension(260, 48));
        inciButton.setMaximumSize(new java.awt.Dimension(260, 48));

        // Icono a la derecha
        java.net.URL inciUrl = getClass().getResource("/img/nav/incidencias.png");
        if (inciUrl != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(inciUrl);
            java.awt.Image scaled = icon.getImage().getScaledInstance(
                    ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH);

            javax.swing.JLabel iconLabel = new javax.swing.JLabel(new javax.swing.ImageIcon(scaled));
            iconLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 16));
            iconLabel.setOpaque(false);
            iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            inciButton.add(iconLabel, java.awt.BorderLayout.EAST);
        }
        ;
        aparaButton = aparaButton = new javax.swing.JButton();
        aparaButton.setLayout(new java.awt.BorderLayout());

        // Texto / comportamiento
        aparaButton.setText("Aparatos");
        aparaButton.setFocusPainted(false);
        aparaButton.setBorderPainted(false);
        aparaButton.setContentAreaFilled(false);
        aparaButton.setOpaque(false);
        aparaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        aparaButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        aparaButton.setMargin(new java.awt.Insets(10, 16, 10, 16));
        aparaButton.putClientProperty("JButton.buttonType", "borderless");

        // ancho fijo
        aparaButton.setPreferredSize(new java.awt.Dimension(260, 48));
        aparaButton.setMinimumSize(new java.awt.Dimension(260, 48));
        aparaButton.setMaximumSize(new java.awt.Dimension(260, 48));

        // Icono a la derecha
        java.net.URL apaUrl = getClass().getResource("/img/nav/aparatos.png");
        if (apaUrl != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(apaUrl);
            java.awt.Image scaled = icon.getImage().getScaledInstance(
                    ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH);

            javax.swing.JLabel iconLabel = new javax.swing.JLabel(new javax.swing.ImageIcon(scaled));
            iconLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 16));
            iconLabel.setOpaque(false);
            iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            aparaButton.add(iconLabel, java.awt.BorderLayout.EAST);
        }
        ;
        usuButton = usuButton = new javax.swing.JButton();
        usuButton.setLayout(new java.awt.BorderLayout());

        // Texto / comportamiento
        usuButton.setText("Usuarios");
        usuButton.setFocusPainted(false);
        usuButton.setBorderPainted(false);
        usuButton.setContentAreaFilled(false);
        usuButton.setOpaque(false);
        usuButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        usuButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        usuButton.setMargin(new java.awt.Insets(10, 16, 10, 16));
        usuButton.putClientProperty("JButton.buttonType", "borderless");

        // ancho fijo
        usuButton.setPreferredSize(new java.awt.Dimension(260, 48));
        usuButton.setMinimumSize(new java.awt.Dimension(260, 48));
        usuButton.setMaximumSize(new java.awt.Dimension(260, 48));

        // Icono a la derecha
        java.net.URL userUrl = getClass().getResource("/img/nav/usuarios.png");
        if (userUrl != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(userUrl);
            java.awt.Image scaled = icon.getImage().getScaledInstance(
                    ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH);

            javax.swing.JLabel iconLabel = new javax.swing.JLabel(new javax.swing.ImageIcon(scaled));
            iconLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 16));
            iconLabel.setOpaque(false);
            iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            usuButton.add(iconLabel, java.awt.BorderLayout.EAST);
        }
        ;
        adminButton = adminButton = new javax.swing.JButton();
        adminButton.setLayout(new java.awt.BorderLayout());

        // Texto / comportamiento
        adminButton.setText("Administrar");
        adminButton.setFocusPainted(false);
        adminButton.setBorderPainted(false);
        adminButton.setContentAreaFilled(false);
        adminButton.setOpaque(false);
        adminButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        adminButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        adminButton.setMargin(new java.awt.Insets(10, 16, 10, 16));
        adminButton.putClientProperty("JButton.buttonType", "borderless");

        // ancho fijo
        adminButton.setPreferredSize(new java.awt.Dimension(260, 48));
        adminButton.setMinimumSize(new java.awt.Dimension(260, 48));
        adminButton.setMaximumSize(new java.awt.Dimension(260, 48));

        // Icono a la derecha
        java.net.URL adminUrl = getClass().getResource("/img/nav/admin.png");
        if (adminUrl != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(adminUrl);
            java.awt.Image scaled = icon.getImage().getScaledInstance(
                    ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH);

            javax.swing.JLabel iconLabel = new javax.swing.JLabel(new javax.swing.ImageIcon(scaled));
            iconLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 16));
            iconLabel.setOpaque(false);
            iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            adminButton.add(iconLabel, java.awt.BorderLayout.EAST);
        }
        ;
        logName = new javax.swing.JLabel();
        logRol = new javax.swing.JLabel();
        salir = salir = new javax.swing.JButton();

        salir.setText("");
        salir.setFocusPainted(false);
        salir.setBorderPainted(false);
        salir.setContentAreaFilled(false);
        salir.setOpaque(false);
        salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salir.setToolTipText("Cerrar sesión");

        java.net.URL normalUrl = getClass().getResource("/img/logout.png");
        java.net.URL hoverUrl = getClass().getResource("/img/logout_hover.png");

        java.util.function.Consumer<java.net.URL> setScaledIcon = (url) -> {
            if (url == null)
                return;
            int size = Math.min(salir.getWidth(), salir.getHeight());
            if (size <= 0)
                return;

            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(url);
            java.awt.Image scaled = icon.getImage()
                    .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
            salir.setIcon(new javax.swing.ImageIcon(scaled));
        };

        // cuando el botón ya tiene tamaño real
        salir.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                setScaledIcon.accept(normalUrl);
                salir.removeComponentListener(this); // solo una vez
            }
        });

        // hover
        salir.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setScaledIcon.accept(hoverUrl);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setScaledIcon.accept(normalUrl);
            }
        });
        contentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1920, 1080));

        fondoPanel.setBackground(new java.awt.Color(255, 255, 255));
        fondoPanel.setLayout(new java.awt.BorderLayout());

        panelLateral.setForeground(new java.awt.Color(153, 153, 153));

        estaButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        estaButton.setText(" Estadísticas");
        estaButton.setBorder(null);
        estaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estaButtonActionPerformed(evt);
            }
        });

        inciButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        inciButton.setText(" Incidencias");
        inciButton.setBorder(null);
        inciButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inciButtonActionPerformed(evt);
            }
        });

        aparaButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        aparaButton.setText(" Aparatos");
        aparaButton.setBorder(null);
        aparaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aparaButtonActionPerformed(evt);
            }
        });

        usuButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        usuButton.setText(" Usuarios");
        usuButton.setBorder(null);
        usuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuButtonActionPerformed(evt);
            }
        });

        adminButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        adminButton.setText(" Administrar");
        adminButton.setBorder(null);
        adminButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminButtonActionPerformed(evt);
            }
        });

        logName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        logRol.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        salir.setToolTipText("Pulse para cerrar sesión.");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLateralLayout = new javax.swing.GroupLayout(panelLateral);
        panelLateral.setLayout(panelLateralLayout);
        panelLateralLayout.setHorizontalGroup(
                panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(estaButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inciButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(aparaButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(usuButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(adminButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelLateralLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelLateralLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(logName, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(logRol, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 57,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
        panelLateralLayout.setVerticalGroup(
                panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLateralLayout.createSequentialGroup()
                                .addComponent(estaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inciButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(aparaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adminButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 711,
                                        Short.MAX_VALUE)
                                .addGroup(panelLateralLayout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelLateralLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(logName, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(logRol, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)));

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));
        contentPanel.setLayout(new java.awt.BorderLayout());

        // USAR BORDERLAYOUT EN LUGAR DE GROUPLAYOUT
        fondoPanel.add(panelLateral, java.awt.BorderLayout.WEST);
        fondoPanel.add(contentPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fondoPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fondoPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void estaButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_estaButtonActionPerformed
        setActiveButton(estaButton);
        cardLayout.show(contentPanel, "estadisticas");
        contentPanel.revalidate();
        contentPanel.repaint();

        SwingUtilities.invokeLater(() -> estadisPanel.cargarDatos());
    }// GEN-LAST:event_estaButtonActionPerformed

    private void inciButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_inciButtonActionPerformed
        setActiveButton(inciButton);
        cardLayout.show(contentPanel, "incidencias");
        contentPanel.revalidate();
        contentPanel.repaint();

        SwingUtilities.invokeLater(() -> incidenciasPanel.cargarDatos());
    }// GEN-LAST:event_inciButtonActionPerformed

    private void aparaButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aparaButtonActionPerformed
        setActiveButton(aparaButton);
        cardLayout.show(contentPanel, "aparatos");
        contentPanel.revalidate();
        contentPanel.repaint();

    }// GEN-LAST:event_aparaButtonActionPerformed

    /**
     * Navega directamente al panel de aparatos y realiza una búsqueda automática.
     * Útil para accesos rápidos desde incidencias.
     * 
     * @param dato El dato a buscar (MAC, teléfono, contrato).
     */
    public void navegarAparatos(String dato) {
        if (dato == null || dato.trim().isEmpty()) {
            return;
        }
        setActiveButton(aparaButton);
        cardLayout.show(contentPanel, "aparatos");
        contentPanel.revalidate();
        contentPanel.repaint();

        SwingUtilities.invokeLater(() -> aparatosPanel.buscarAparato(dato));
    }

    private void usuButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_usuButtonActionPerformed
        setActiveButton(usuButton);
        String rol = usuario.getRol().toLowerCase();

        switch (rol) {

            case "sistemas":
                // Acceso total
                cardLayout.show(contentPanel, "supervisores");
                break;

            case "supervisor":
                // Puede ver admin pero no tocar todo (ejemplo)
                cardLayout.show(contentPanel, "supervisores");
                // Si quieres limitar algo más, aquí
                break;

            case "tecnico":
                // Sin acceso a administración
                cardLayout.show(contentPanel, "usuarios");
                break;

            default:
                // Rol desconocido = acceso mínimo
                cardLayout.show(contentPanel, "usuarios");
                break;
        }

    }// GEN-LAST:event_usuButtonActionPerformed

    private void adminButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_adminButtonActionPerformed
        setActiveButton(adminButton);
        cardLayout.show(contentPanel, "admin");
    }// GEN-LAST:event_adminButtonActionPerformed

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_salirActionPerformed

        int opcion = com.jmmunoz.netfix.vista.dialogos.ModernDialog.showConfirmDialog(
                this,
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("main.logout.confirm.msg"),
                com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("main.logout.confirm.title"),
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            new com.jmmunoz.netfix.controlador.Utilities().logAction("OK", "MainFrame",
                    "Usuario " + usuario.getNombre() + " "
                            + com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("main.log.logout"));
            new login().setVisible(true);
            this.dispose();
        }
    }// GEN-LAST:event_salirActionPerformed

    /**
     * Gestiona el estado visual (activo/inactivo) de los botones del menú lateral.
     * Resalta el botón seleccionado y restaura el estilo de los demás.
     * 
     * @param button El botón que ha sido pulsado.
     */
    private void setActiveButton(JButton button) {
        if (activeButton != null) {
            ThemeManager.getInstance().setInactiveNavButton(activeButton);
        }

        activeButton = button;
        ThemeManager.getInstance().setActiveNavButton(activeButton);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminButton;
    private javax.swing.JButton aparaButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton estaButton;
    private javax.swing.JPanel fondoPanel;
    private javax.swing.JButton inciButton;
    private javax.swing.JLabel logName;
    private javax.swing.JLabel logRol;
    private javax.swing.JPanel panelLateral;
    private javax.swing.JButton salir;
    private javax.swing.JButton usuButton;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JPanel getFondoPanel() {
        return fondoPanel;
    }

    public javax.swing.JPanel getPanelLateral() {
        return panelLateral;
    }

    public javax.swing.JPanel getContentPanel() {
        return contentPanel;
    }

    public javax.swing.JLabel getLogName() {
        return logName;
    }

    public javax.swing.JLabel getLogRol() {
        return logRol;
    }

    public javax.swing.JButton getEstaButton() {
        return estaButton;
    }

    public javax.swing.JButton getInciButton() {
        return inciButton;
    }

    public javax.swing.JButton getAparaButton() {
        return aparaButton;
    }

    public javax.swing.JButton getUsuButton() {
        return usuButton;
    }

    public javax.swing.JButton getAdminButton() {
        return adminButton;
    }

    public javax.swing.JButton getActiveButton() {
        return activeButton;
    }
}
