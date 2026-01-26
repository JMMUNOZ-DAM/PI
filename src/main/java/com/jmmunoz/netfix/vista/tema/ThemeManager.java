package com.jmmunoz.netfix.vista.tema;

import com.jmmunoz.netfix.vista.login;
import com.jmmunoz.netfix.vista.MainFrame;
import com.jmmunoz.netfix.vista.IncidPanel;
import com.jmmunoz.netfix.vista.AdminPanel;
import com.jmmunoz.netfix.vista.AparatosPanel;
import com.jmmunoz.netfix.vista.EstadisPanel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.labels.ItemLabelAnchor;

/**
 * Gestor centralizado de temas y estilos visuales para la aplicación Netfix.
 * Utiliza una instancia única para proporcionar un punto de acceso global
 * a toda la lógica visual.
 * 
 * @author Juanma Muñoz
 */
public class ThemeManager {

    private static ThemeManager instance;
    private final Border activeBorder = BorderFactory.createMatteBorder(0, 4, 0, 0, TelecomTheme.ACCENT);
    private final Border normalBorder = BorderFactory.createEmptyBorder(0, 4, 0, 0);

    private ThemeManager() {
    }

    public static synchronized ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    /**
     * Configuración global del Look and Feel (FlatLaf).
     */
    public void setupGlobalTheme() {
        // 1) Look & Feel claro
        FlatLightLaf.setup();

        // 2) Geometría moderna
        UIManager.put("Component.arc", 14);
        UIManager.put("Button.arc", 14);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackArc", 999);

        // 3) Foco y bordes
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 0);

        UIManager.put("Component.focusColor", TelecomTheme.ACCENT);
        UIManager.put("Component.focusedBorderColor", TelecomTheme.ACCENT);
        UIManager.put("Button.focusedBorderColor", TelecomTheme.ACCENT);
        UIManager.put("Component.borderColor", TelecomTheme.BORDER);

        // 4) Tipografía base (limpia)
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));

        // 5) Fondo de la app (general)
        UIManager.put("Panel.background", TelecomTheme.APP_BG);

        // 6) Tablas estilo “dashboard”
        UIManager.put("Table.rowHeight", 28);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.showVerticalLines", false);

        // 7) Campos un poco más “pro”
        UIManager.put("TextComponent.selectAllOnFocusPolicy", "once");
        UIManager.put("ScrollBar.width", 12);

        // 8) Monoespaciada SOLO para tablas
        UIManager.put("Table.font", new Font("Consolas", Font.PLAIN, 13));
        UIManager.put("TableHeader.font", new Font("Consolas", Font.BOLD, 13));

        // 9) Mensajes de Dialogo en Español
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.okButtonText", "Aceptar");
        UIManager.put("OptionPane.title.text", "Mensaje");

        FlatLaf.updateUI();
    }

    /**
     * Aplica el tema y construye la interfaz "Pro" para la ventana de login.
     * 
     * @param view Instancia de la ventana de login.
     */
    public void applyLoginTheme(login view) {
        view.setTitle("NETFIX | Acceso");
        view.setMinimumSize(new Dimension(560, 420));
        view.setLocationRelativeTo(null);

        /* ========================= RAÍZ (FONDO) ========================= */
        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.putClientProperty(FlatClientProperties.STYLE, "background: #F5F6F8;");

        /* ========================= TARJETA CENTRAL ========================= */
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 224, 230), 1, true),
                new EmptyBorder(20, 20, 20, 20)));
        card.putClientProperty(FlatClientProperties.STYLE, "background: #FFFFFF;");
        card.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        /* ========================= LOGO ========================= */
        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        URL logoUrl = getClass().getResource("/img/netfix_logo.png");
        URL logoIcon = getClass().getResource("/img/netfix_N.png");

        if (logoUrl != null) {
            ImageIcon icon = new ImageIcon(logoUrl);
            Image scaled = icon.getImage().getScaledInstance(300, -1, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        } else {
            logoLabel.setText("NETFIX");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        }

        /* ========================= INSIGNIA ========================= */
        JLabel badge = new JLabel("NOC · TICKETS · SLA");
        badge.setOpaque(true);
        badge.setAlignmentX(Component.CENTER_ALIGNMENT);
        badge.putClientProperty(FlatClientProperties.STYLE,
                "background: #EEF4FF; foreground: #2E90FA; arc: 999; "
                        + "border: 6,12,6,12; font: bold 12 'Segoe UI';");

        /* ========================= SUBTÍTULO ========================= */
        JLabel subtitle = new JLabel("Diagnóstico y gestión de tickets · Telecomunicaciones");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.putClientProperty(FlatClientProperties.STYLE, "font: plain 14 'Segoe UI'; foreground: #667085;");

        /* ========================= CABECERA ========================= */
        card.add(logoLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(badge);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(15));

        /* ========================= CAMPOS ========================= */
        // Accedemos a los componentes a través de getters que debemos crear en
        // login.java
        // NOTA: Asumo que se añadirán getters públicos en login.java

        // Estilos
        view.getMailUser().setFont(new Font("Segoe UI", Font.PLAIN, 16));
        view.getPassword().setFont(new Font("Segoe UI", Font.PLAIN, 16));

        view.getMailUser().setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        view.getPassword().setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        view.getMailUser().putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,
                "usuario@netfix.com / usuario@netfix.es");
        view.getPassword().putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Introduce tu contraseña");

        view.getMailUser().addActionListener(e -> view.getLoginButton().doClick());
        view.getPassword().addActionListener(e -> view.getLoginButton().doClick());

        view.getLoginButton().setFont(new Font("Segoe UI", Font.BOLD, 16));
        view.getLoginButton().setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        view.getLoginButton().putClientProperty(FlatClientProperties.STYLE, "arc: 12;");

        view.getRootPane().setDefaultButton(view.getLoginButton());

        card.add(createFullWidthPanel(view.getMailUser()));
        card.add(Box.createVerticalStrut(14));
        card.add(createFullWidthPanel(view.getPassword()));
        card.add(Box.createVerticalStrut(18));
        card.add(createFullWidthPanel(view.getLoginButton()));

        /* ========================= PIE DE PÁGINA ========================= */
        JLabel footer = new JLabel("Soporte: NOC / Sistemas · NETFIX");
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);
        footer.putClientProperty(FlatClientProperties.STYLE, "font: 12 'Segoe UI'; foreground: #98A2B3;");

        card.add(Box.createVerticalStrut(14));
        card.add(footer);

        /* ========================= COLOCAR TARJETA ========================= */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        root.add(card, gbc);
        view.setContentPane(root);

        /* ========================= ICONO DE VENTANA ========================= */
        if (logoIcon != null) {
            Image base = new ImageIcon(logoIcon).getImage();
            List<Image> icons = List.of(
                    base.getScaledInstance(16, 16, Image.SCALE_SMOOTH),
                    base.getScaledInstance(24, 24, Image.SCALE_SMOOTH),
                    base.getScaledInstance(32, 32, Image.SCALE_SMOOTH),
                    base.getScaledInstance(48, 48, Image.SCALE_SMOOTH),
                    base.getScaledInstance(64, 64, Image.SCALE_SMOOTH),
                    base.getScaledInstance(128, 128, Image.SCALE_SMOOTH));
            view.setIconImages(icons);
        }

        view.pack();
        SwingUtilities.invokeLater(() -> view.getMailUser().requestFocusInWindow());
    }

    private JPanel createFullWidthPanel(Component c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    /**
     * Aplica el tema a la ventana principal (Dashboard).
     * 
     * @param view Instancia de MainFrame.
     */
    public void applyMainFrameTheme(MainFrame view) {
        // fondo general claro
        view.getFondoPanel().setBackground(TelecomTheme.APP_BG);
        view.getContentPanel().setBackground(TelecomTheme.APP_BG);

        // barra lateral telecom oscuro
        view.getPanelLateral().setBackground(TelecomTheme.NAV_BG);
        view.getPanelLateral().setOpaque(true);

        view.getLogName().setForeground(TelecomTheme.TEXT_ON_DARK);
        view.getLogRol().setForeground(TelecomTheme.TEXT_MUTED);

        styleNavButton(view.getEstaButton(), view);
        styleNavButton(view.getInciButton(), view);
        styleNavButton(view.getAparaButton(), view);
        styleNavButton(view.getUsuButton(), view);
        styleNavButton(view.getAdminButton(), view);

        // Efectos Hover - requiere lógica para saber qué botón es "activo" en MainFrame
        applyHoverEffect(view.getEstaButton(), view);
        applyHoverEffect(view.getInciButton(), view);
        applyHoverEffect(view.getAparaButton(), view);
        applyHoverEffect(view.getUsuButton(), view);
        applyHoverEffect(view.getAdminButton(), view);
    }

    private void styleNavButton(JButton b, MainFrame view) {
        b.setOpaque(true);
        b.setBackground(TelecomTheme.NAV_BG);
        b.setForeground(TelecomTheme.TEXT_ON_DARK);

        b.setBorder(normalBorder);
        b.setHorizontalAlignment(SwingConstants.LEFT);

        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(true);

        // Ajusta tamaño
        b.setFont(b.getFont().deriveFont(Font.PLAIN, 18f));

        // Ancho fijo
        Dimension dim = new Dimension(260, 48);
        b.setPreferredSize(dim);
        b.setMinimumSize(dim);
        b.setMaximumSize(dim);
    }

    private void applyHoverEffect(JButton button, MainFrame view) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Comprobar si el botón está activo usando el método de la vista o getter
                // público para
                // activeButton
                // Dado que activeButton es privado en MainFrame, podríamos necesitar un getter
                // o método
                // en MainFrame para comprobar.
                // O podemos encapsular esta lógica completamente aquí si rastreamos el botón
                // activo aquí,
                // pero el estado está en MainFrame.
                // Enfoque más simple: MainFrame gestiona el estado, solo añadimos listener que
                // comprueba
                // el estado.
                // Necesitamos view.getActiveButton()
                if (button != view.getActiveButton()) {
                    button.setBackground(TelecomTheme.NAV_BG_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != view.getActiveButton()) {
                    button.setBackground(TelecomTheme.NAV_BG);
                }
            }
        });

        // Añadir listener a hijos (componentes dentro del botón)
        for (Component c : button.getComponents()) {
            c.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (button != view.getActiveButton()) {
                        button.setBackground(TelecomTheme.NAV_BG_HOVER);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (button != view.getActiveButton()) {
                        button.setBackground(TelecomTheme.NAV_BG);
                    }
                }
            });
        }
    }

    // Auxiliar para obtener borde activo para MainFrame si es necesario, o
    // MainFrame puede
    // usar el de ThemeManager
    public Border getActiveBorder() {
        return activeBorder;
    }

    public Border getNormalBorder() {
        return normalBorder;
    }

    // También podemos tener un método en ThemeManager para establecer estilo de
    // botón activo directamente
    public void setActiveNavButton(JButton button) {
        button.setBackground(TelecomTheme.NAV_BG_ACTIVE);
        button.setBorder(activeBorder);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
    }

    public void setInactiveNavButton(JButton button) {
        if (button != null) {
            button.setBackground(TelecomTheme.NAV_BG);
            button.setBorder(normalBorder);
            button.setFont(button.getFont().deriveFont(Font.PLAIN));
        }
    }

    /**
     * Aplica el estilo visual corporativo a una tabla y su ScrollPane.
     * Ajusta altura de filas, colores de cabecera y bordes.
     * 
     * @param table  Tabla a estilizar.
     * @param scroll ScrollPane contenedor (opcional, puede ser null).
     */
    public void styleTable(javax.swing.JTable table, javax.swing.JScrollPane scroll) {
        if (table != null) {
            table.setRowHeight(28);
            table.setShowVerticalLines(false);
            table.setShowHorizontalLines(true);

            if (table.getTableHeader() != null) {
                table.getTableHeader().setReorderingAllowed(false);
                table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            }
        }

        if (scroll != null) {
            scroll.setBorder(BorderFactory.createEmptyBorder());
            if (scroll.getViewport() != null) {
                // Color de SUPERFICIE usualmente blanco o gris claro
                scroll.getViewport().setBackground(TelecomTheme.SURFACE);
            }
        }
    }

    /**
     * Aplica el tema al panel de Administración.
     * 
     * @param view Instancia del AdminPanel.
     */
    public void applyAdminPanelTheme(AdminPanel view) {
        // Fondos
        view.setBackground(TelecomTheme.APP_BG);
        view.setOpaque(true);
        if (view.getAdminPanel() != null) {
            view.getAdminPanel().setBackground(TelecomTheme.APP_BG);
        }

        if (view.getManagementPanel() != null) {
            view.getManagementPanel().setBackground(TelecomTheme.APP_BG);
        }

        // Títulos y Etiquetas
        if (view.getSisTitle() != null) {
            view.getSisTitle().setFont(new Font("Segoe UI", Font.BOLD, 28));
            view.getSisTitle().setForeground(TelecomTheme.ACCENT_DARK);
        }

        if (view.getSelectedContractLabel() != null) {
            view.getSelectedContractLabel().setFont(new Font("Segoe UI", Font.BOLD, 16));
        }

        if (view.getDeviceLabel() != null) {
            view.getDeviceLabel().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        // Buscador
        if (view.getSeaField() != null) {
            view.getSeaField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
            // La lógica de tamaño preferido podría permanecer en la vista o aquí, pero aquí
            // implica estilo.
            // view.getSeaField().setPreferredSize(new Dimension(400, 32));
        }
        if (view.getSearchButtSis() != null) {
            view.getSearchButtSis().setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        // Tabla
        styleTable(view.getContratosTable(), view.getScrollPane());

        // Botones de Gestión
        if (view.getAssignButton() != null)
            view.getAssignButton().setFont(new Font("Segoe UI", Font.BOLD, 14));
        if (view.getReleaseButton() != null)
            view.getReleaseButton().setFont(new Font("Segoe UI", Font.BOLD, 14));
        if (view.getAddNumberButton() != null)
            view.getAddNumberButton().setFont(new Font("Segoe UI", Font.BOLD, 12));
        if (view.getDelNumberButton() != null)
            view.getDelNumberButton().setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    /**
     * Aplica el estilo de tarjeta (fondo, borde, redondeo) a un componente.
     * 
     * @param c Componente a modificar.
     */
    /**
     * Crea una tarjeta KPI (Indicador Clave de Desempeño) estilizada.
     * 
     * @param title       Título de la tarjeta (ej. "Pendientes").
     * @param valueLabel  JLabel que contendrá el valor dinámico.
     * @param accentColor Color de acento (borde izquierdo e icono).
     * @param iconChar    Carácter Unicode para el icono (o ruta de imagen si se
     *                    implementa).
     * @return JPanel configurado como tarjeta.
     */
    public JPanel createKPICard(String title, JLabel valueLabel, Color accentColor, String iconChar) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(230, 230, 230), 1, true),
                new EmptyBorder(15, 20, 15, 20)));

        // Borde de acento izquierdo
        JPanel accentStrip = new JPanel();
        accentStrip.setBackground(accentColor);
        accentStrip.setPreferredSize(new Dimension(4, 0));

        // Contenido
        JPanel content = new JPanel(new GridLayout(2, 1, 0, 5));
        content.setOpaque(false);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(TelecomTheme.TEXT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(TelecomTheme.TEXT_MUTED);

        content.add(valueLabel);
        content.add(titleLabel);

        // Icono (Simulado con etiqueta coloreada)
        JLabel iconLabel = new JLabel(iconChar, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconLabel.setForeground(accentColor);
        iconLabel.setPreferredSize(new Dimension(40, 40));

        // Envoltorio para icono con fondo suave
        JPanel iconWrapper = new JPanel(new BorderLayout());
        iconWrapper.setOpaque(false); // O pintar un fondo suave si se desea
        iconWrapper.add(iconLabel, BorderLayout.CENTER);

        // Ensamblaje
        JPanel main = new JPanel(new BorderLayout(15, 0));
        main.setOpaque(false);
        main.add(accentStrip, BorderLayout.WEST);
        main.add(content, BorderLayout.CENTER);
        main.add(iconWrapper, BorderLayout.EAST);

        // Envolver en tarjeta real para el borde exterior
        card.add(main, BorderLayout.CENTER);

        return card;
    }

    /**
     * Aplica el tema visual corporativo (TelecomTheme) a un gráfico JFreeChart.
     * Personaliza colores de fondo, fuentes, ejes y barras.
     */
    public void applyTelecomChartTheme(org.jfree.chart.JFreeChart chart) {

        // Fondo del gráfico
        chart.setBackgroundPaint(Color.WHITE);

        // Título
        if (chart.getTitle() != null) {
            chart.getTitle().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
            chart.getTitle().setPaint(TelecomTheme.TEXT);
            chart.getTitle().setPadding(10, 0, 20, 0); // Espacio extra
        }

        // Trama (categorías)
        if (chart.getPlot() instanceof org.jfree.chart.plot.CategoryPlot plot) {

            plot.setBackgroundPaint(Color.WHITE);
            plot.setOutlineVisible(false);

            // rejilla sutil vertical (para ver valores X)
            plot.setDomainGridlinesVisible(false);
            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlinePaint(new Color(240, 240, 240));

            // Ejes
            // Domain (Y en barra horizontal) -> Categorías (Días)
            if (plot.getDomainAxis() != null) {
                plot.getDomainAxis().setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
                plot.getDomainAxis().setLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                plot.getDomainAxis().setTickLabelPaint(TelecomTheme.TEXT);
                plot.getDomainAxis().setAxisLineVisible(false);
                plot.getDomainAxis().setTickMarksVisible(false);
            }

            // Range (X en barra horizontal) -> Valores
            if (plot.getRangeAxis() != null) {
                plot.getRangeAxis().setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
                plot.getRangeAxis().setLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
                plot.getRangeAxis().setTickLabelPaint(TelecomTheme.TEXT_MUTED);
                plot.getRangeAxis().setAxisLineVisible(false);
                plot.getRangeAxis().setTickMarksVisible(false);
            }

            if (plot.getRangeAxis() instanceof NumberAxis na) {
                na.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                na.setAutoRangeIncludesZero(true);
            }

            // Renderizado de Barras
            if (plot.getRenderer() instanceof org.jfree.chart.renderer.category.BarRenderer r) {
                r.setDrawBarOutline(false);
                r.setShadowVisible(false);

                // color telecom (Azul principal)
                r.setSeriesPaint(0, new Color(20, 100, 192));

                // estilo moderno
                r.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());

                // Control de grosor de las barras
                r.setMaximumBarWidth(0.05);
                r.setItemMargin(0.05);

                // Etiquetas de valor al lado de la barra
                r.setDefaultItemLabelsVisible(true);
                r.setDefaultItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
                r.setDefaultItemLabelFont(new Font("Segoe UI", Font.BOLD, 11));
                r.setDefaultItemLabelPaint(TelecomTheme.TEXT);
                r.setDefaultPositiveItemLabelPosition(new org.jfree.chart.labels.ItemLabelPosition(
                        ItemLabelAnchor.OUTSIDE3,
                        TextAnchor.CENTER_LEFT));
            }
        }
    }

    public void cardify(javax.swing.JComponent c) {
        c.setOpaque(true);
        c.setBackground(TelecomTheme.SURFACE);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TelecomTheme.BORDER, 1, true),
                new EmptyBorder(14, 14, 14, 14)));
        c.putClientProperty(FlatClientProperties.STYLE, "arc: 16;");
    }

    /**
     * Aplica el tema al panel de Aparatos.
     * 
     * @param view Instancia del AparatosPanel.
     */
    public void applyAparatosPanelTheme(AparatosPanel view) {
        // ===== PÁGINA =====
        view.setBackground(TelecomTheme.APP_BG);
        view.setOpaque(true);

        // Afloja tamaños fijos del diseñador
        view.setPreferredSize(null);
        view.setMinimumSize(new Dimension(0, 0));
        view.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // ===== TITULO =====
        if (view.getApaTitle() != null) {
            view.getApaTitle().setFont(new Font("Segoe UI", Font.BOLD, 28));
            view.getApaTitle().setForeground(TelecomTheme.ACCENT_DARK);
        }

        // ===== BUSCADOR =====
        if (view.getSearchField() != null) {
            view.getSearchField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
            view.getSearchField().setPreferredSize(new Dimension(400, 32));
        }

        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        if (view.getSearchButton() != null)
            view.getSearchButton().setFont(btnFont);
        if (view.getDiagnoButton() != null)
            view.getDiagnoButton().setFont(btnFont);

        // ===== PANEL PRINCIPAL =====
        if (view.getMainPanel() != null) {
            cardify(view.getMainPanel());
        }

        // ===== ETIQUETAS (tenues) =====
        Font lbl = new Font("Segoe UI", Font.PLAIN, 13);
        Color muted = TelecomTheme.TEXT_MUTED;

        JLabel[] mutedLabels = {
                view.getTipoLabel(), view.getNsLabeel(), view.getMacLabel(),
                view.getContratoLabel(), view.getEstadoLabel(), view.getLasDlabel(),
                view.getObserPanel(), view.getActualDlbale()
        };

        for (JLabel l : mutedLabels) {
            if (l != null) {
                l.setFont(lbl);
                l.setForeground(muted);
            }
        }

        // ===== VALORES (más "panel de control") =====
        Font val = new Font("Segoe UI", Font.BOLD, 14);
        Color text = TelecomTheme.TEXT;

        JLabel[] valueLabels = {
                view.getTipLbl(), view.getNumSLabel(), view.getMacL(),
                view.getNumConLabel(), view.getStatusLabel(), view.getFeLastLabel()
        };

        for (JLabel l : valueLabels) {
            if (l != null) {
                l.setFont(val);
                l.setForeground(text);
            }
        }

        // El nombre que resalta
        if (view.getNombreLabel() != null) {
            view.getNombreLabel().setFont(new Font("Segoe UI", Font.BOLD, 18));
            view.getNombreLabel().setForeground(text);
        }

        // ===== ÁREA DE TEXTO (observaciones) =====
        if (view.getObservaciones() != null) {
            view.getObservaciones().setFont(new Font("Segoe UI", Font.PLAIN, 13));
            view.getObservaciones().setBackground(TelecomTheme.SURFACE);
            view.getObservaciones().setForeground(text);
            view.getObservaciones().setCaretColor(text);
        }

        if (view.getObervaText() != null) {
            view.getObervaText().setBorder(BorderFactory.createEmptyBorder());
            if (view.getObervaText().getViewport() != null) {
                view.getObervaText().getViewport().setBackground(TelecomTheme.SURFACE);
            }
        }

        // ===== LISTA DIAGNÓSTICO =====
        if (view.getActualDiag() != null) {
            view.getActualDiag().setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
            view.getActualDiag().setBackground(Color.WHITE);
            view.getActualDiag().setForeground(TelecomTheme.ACCENT_DARK);
            view.getActualDiag().setFixedCellHeight(28); // Más aire entre líneas
        }

        if (view.getListaDiag() != null) {
            view.getListaDiag().setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            if (view.getListaDiag().getViewport() != null) {
                view.getListaDiag().getViewport().setBackground(Color.WHITE);
            }
        }

        // ===== (opcional) "cápsula" en estado general =====
        if (view.getStatusLabel() != null) {
            view.getStatusLabel().setOpaque(true);
            view.getStatusLabel().setBackground(new Color(230, 248, 255));
            view.getStatusLabel().setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        }

        // ---------------------------------------------------------
        // REFACTORIZACIÓN DE DISEÑO
        // ---------------------------------------------------------
        view.removeAll();
        view.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(20, 20, 10, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;

        // 1) Título
        view.add(view.getApaTitle(), gbc);

        // 2) Buscador (envuelto en panel flow para centrarlo junto)
        // Necesitamos comprobar si podemos añadir searchField y searchButton
        // directamente, asumiendo
        // que la lógica maneja nulos
        if (view.getSearchField() != null && view.getSearchButton() != null) {
            JPanel searchWrapper = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
            searchWrapper.setOpaque(false);
            searchWrapper.add(view.getSearchField());
            searchWrapper.add(view.getSearchButton());

            gbc.gridy++;
            view.add(searchWrapper, gbc);
        }

        // 3) Panel Principal
        gbc.gridy++;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if (view.getMainPanel() != null) {
            view.add(view.getMainPanel(), gbc);
        }

        // 4) Botón Diagnóstico
        gbc.gridy++;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        if (view.getDiagnoButton() != null) {
            view.add(view.getDiagnoButton(), gbc);
        }

        // Espaciador final
        gbc.gridy++;
        gbc.weighty = 1.0;
        view.add(Box.createVerticalGlue(), gbc);

        view.revalidate();
        view.repaint();
    }

    /**
     * Ajusta el ancho de las columnas de la tabla de incidencias.
     * Prioriza la descripción.
     */
    public void configureTableColumns(javax.swing.JTable table) {
        // 0: ID, 1: Contrato, 2: Descripcion, 3: Fecha Reporte, 4: Agente, 5: Estado
        if (table.getColumnModel().getColumnCount() > 0) {
            try {
                // ID Incidencia - Estrecha
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(0).setMaxWidth(80);

                // ID Contrato - Estrecha
                table.getColumnModel().getColumn(1).setPreferredWidth(100);
                table.getColumnModel().getColumn(1).setMaxWidth(120);

                // Descripción - Ancha (main content)
                table.getColumnModel().getColumn(2).setPreferredWidth(400);

                // Fecha - Media
                table.getColumnModel().getColumn(3).setPreferredWidth(100);

                // Agente - Media
                table.getColumnModel().getColumn(4).setPreferredWidth(100);

                // Estado - Media
                table.getColumnModel().getColumn(5).setPreferredWidth(100);

            } catch (Exception e) {
                // Failsafe
            }
        }
    }

    /**
     * Aplica el tema al panel de Estadísticas.
     * 
     * @param view Instancia del EstadisPanel.
     */
    public void applyEstadisPanelTheme(EstadisPanel view) {
        // Fondo general del panel (página)
        view.setBackground(TelecomTheme.APP_BG);

        // Tabla
        styleTable(view.getInciTabla(), null);
        configureTableColumns(view.getInciTabla());

        // El resto se gestiona en buildDashboardLayout() de EstadisPanel usando
        // createKPICard()
    }

    public void applyIncidenciaDetalleTheme(javax.swing.JDialog dialog) {
        dialog.getContentPane().setBackground(TelecomTheme.APP_BG);
    }

    /**
     * Configura el ancho de columnas para tabla de incidencias.
     */
    public void configureIncidenciasTableColumns(javax.swing.JTable table) {
        if (table.getColumnModel().getColumnCount() > 0) {
            try {
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(0).setMaxWidth(80);
                table.getColumnModel().getColumn(1).setPreferredWidth(100);
                table.getColumnModel().getColumn(1).setMaxWidth(120);
                table.getColumnModel().getColumn(2).setPreferredWidth(400);
                table.getColumnModel().getColumn(3).setPreferredWidth(100);
                table.getColumnModel().getColumn(4).setPreferredWidth(100);
                table.getColumnModel().getColumn(5).setPreferredWidth(100);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Configura el ancho de columnas para tabla de comentarios.
     */
    public void configureComentariosTableColumns(javax.swing.JTable table) {
        if (table.getColumnModel().getColumnCount() > 0) {
            try {
                table.getColumnModel().getColumn(0).setPreferredWidth(100);
                table.getColumnModel().getColumn(0).setMaxWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(300);
                table.getColumnModel().getColumn(2).setPreferredWidth(120);
                table.getColumnModel().getColumn(2).setMaxWidth(150);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Aplica el tema al panel de Incidencias.
     * 
     * @param view Instancia del IncidPanel.
     */
    public void applyIncidPanelTheme(IncidPanel view) {
        // ===== PÁGINA =====
        view.setBackground(TelecomTheme.APP_BG);
        view.setOpaque(true);

        view.setPreferredSize(null);
        view.setMinimumSize(new Dimension(0, 0));
        view.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // ===== TITULOS =====
        if (view.getInciGTitle() != null) {
            view.getInciGTitle().setFont(new Font("Segoe UI", Font.BOLD, 28));
            view.getInciGTitle().setForeground(TelecomTheme.ACCENT_DARK);
        }

        if (view.getListadoText() != null) {
            view.getListadoText().setFont(new Font("Segoe UI", Font.BOLD, 16));
            view.getListadoText().setForeground(TelecomTheme.TEXT);
        }

        if (view.getGestiPanel() != null) {
            view.getGestiPanel().setFont(new Font("Segoe UI", Font.BOLD, 14));
            view.getGestiPanel().setForeground(TelecomTheme.TEXT);
        }

        if (view.getComenLabel() != null) {
            view.getComenLabel().setFont(new Font("Segoe UI", Font.BOLD, 14));
            view.getComenLabel().setForeground(TelecomTheme.TEXT);
        }

        // ===== ETIQUETAS (tenues) =====
        Font lbl = new Font("Segoe UI", Font.PLAIN, 13);
        Color muted = TelecomTheme.TEXT_MUTED;

        javax.swing.JLabel[] mutedLabels = {
                view.getNumLabel(), view.getContatoLabel(), view.getProblemaLabel(),
                view.getTituLabel(), view.getFtthLabel(), view.getMovilLabel()
        };
        for (javax.swing.JLabel l : mutedLabels) {
            if (l != null) {
                l.setFont(lbl);
                l.setForeground(muted);
            }
        }

        // ===== VALORES =====
        Font val = new Font("Segoe UI", Font.BOLD, 14);
        javax.swing.JLabel[] valLabels = {
                view.getTxtID(), view.getTxtContrato(), view.getTxtNombre(),
                view.getTxtDescripcion(), view.getTxtAparato(), view.getTxtMoFT(), view.getTxtMAC()
        };
        for (javax.swing.JLabel l : valLabels) {
            if (l != null) {
                l.setFont(val);
                l.setForeground(TelecomTheme.TEXT);
            }
        }

        // ===== BUSCADOR =====
        Font inp = new Font("Segoe UI", Font.PLAIN, 14);
        if (view.getSearchField() != null) {
            view.getSearchField().setFont(inp);
            view.getSearchField().setPreferredSize(new Dimension(400, 32));
        }
        if (view.getSearchButton() != null) {
            view.getSearchButton().setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        // ===== BOTONES =====
        Font btn = new Font("Segoe UI", Font.BOLD, 14);
        if (view.getSoluButton() != null)
            view.getSoluButton().setFont(btn);
        if (view.getActuButton() != null)
            view.getActuButton().setFont(btn);
        if (view.getEnviButton() != null)
            view.getEnviButton().setFont(btn);

        // ===== SEPARADORES =====
        if (view.getSepaDatos() != null)
            view.getSepaDatos().setForeground(TelecomTheme.BORDER);
        if (view.getSepaAparatos() != null)
            view.getSepaAparatos().setForeground(TelecomTheme.BORDER);

        // ===== TARJETAS =====
        if (view.getInciPanel() != null)
            cardify(view.getInciPanel());
        if (view.getDatosPanel() != null)
            cardify(view.getDatosPanel());

        // ===== TABLAS Y SCROLLS =====
        styleTable(view.getInciTabla(), view.getIncidenciasTable());
        styleTable(view.getComenTable(), view.getJScrollPane1());
        configureIncidenciasTableColumns(view.getInciTabla());
        configureComentariosTableColumns(view.getComenTable());

        // Lista 5G
        if (view.getJScrollPane2() != null) {
            view.getJScrollPane2().setBorder(BorderFactory.createEmptyBorder());
            if (view.getJScrollPane2().getViewport() != null) {
                view.getJScrollPane2().getViewport().setBackground(TelecomTheme.SURFACE);
            }
        }
        if (view.getLista5G() != null) {
            view.getLista5G().setBackground(TelecomTheme.SURFACE);
            view.getLista5G().setForeground(TelecomTheme.TEXT);
            view.getLista5G().setSelectionBackground(TelecomTheme.ACCENT);
            view.getLista5G().setSelectionForeground(Color.WHITE);
            view.getLista5G().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }

        // Rellena Viewport
        if (view.getInciTabla() != null)
            view.getInciTabla().setFillsViewportHeight(true);
        if (view.getComenTable() != null)
            view.getComenTable().setFillsViewportHeight(true);

        // ---------------------------------------------------------
        // REFACTORIZACIÓN DE DISEÑO
        // ---------------------------------------------------------
        // Estilo de botones de diagnóstico rápido
        Font btnSmall = new Font("Segoe UI", Font.BOLD, 12);
        if (view.getBtnDiagFTTH() != null)
            view.getBtnDiagFTTH().setFont(btnSmall);
        if (view.getBtnDiagMovil() != null)
            view.getBtnDiagMovil().setFont(btnSmall);

        // 1. REESTRUCTURAR datosPanel
        javax.swing.JPanel datosPanel = view.getDatosPanel();
        if (datosPanel != null) {
            datosPanel.removeAll();
            datosPanel.setLayout(new GridBagLayout());
            datosPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TelecomTheme.BORDER, 1, true),
                    new EmptyBorder(20, 20, 20, 20)));

            GridBagConstraints gbcD = new GridBagConstraints();
            gbcD.insets = new java.awt.Insets(5, 5, 5, 5);
            gbcD.fill = GridBagConstraints.HORIZONTAL;
            gbcD.anchor = GridBagConstraints.WEST;

            // --- FILA 0: Cabecera ---
            gbcD.gridx = 0;
            gbcD.gridy = 0;
            gbcD.gridwidth = 4;
            JPanel headerPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
            headerPanel.setOpaque(false);
            if (view.getGestiPanel() != null)
                headerPanel.add(view.getGestiPanel());
            if (view.getNumLabel() != null)
                headerPanel.add(view.getNumLabel());
            if (view.getTxtID() != null)
                headerPanel.add(view.getTxtID());
            datosPanel.add(headerPanel, gbcD);

            // --- FILA 1: Separador ---
            gbcD.gridy++;
            if (view.getSepaDatos() != null)
                datosPanel.add(view.getSepaDatos(), gbcD);

            // --- FILA 2: Contrato y Titular ---
            gbcD.gridy++;
            gbcD.gridwidth = 1;
            gbcD.weightx = 0.0;
            if (view.getContatoLabel() != null)
                datosPanel.add(view.getContatoLabel(), gbcD);

            gbcD.gridx = 1;
            gbcD.weightx = 0.2;
            if (view.getTxtContrato() != null)
                datosPanel.add(view.getTxtContrato(), gbcD);

            gbcD.gridx = 2;
            gbcD.weightx = 0.0;
            if (view.getTituLabel() != null)
                datosPanel.add(view.getTituLabel(), gbcD);

            gbcD.gridx = 3;
            gbcD.weightx = 0.8;
            if (view.getTxtNombre() != null)
                datosPanel.add(view.getTxtNombre(), gbcD);

            // --- FILA 3: Descripción del problema ---
            gbcD.gridx = 0;
            gbcD.gridy++;
            gbcD.weightx = 0.0;
            if (view.getProblemaLabel() != null)
                datosPanel.add(view.getProblemaLabel(), gbcD);

            gbcD.gridx = 1;
            gbcD.gridwidth = 3;
            gbcD.weightx = 1.0;
            if (view.getTxtDescripcion() != null)
                datosPanel.add(view.getTxtDescripcion(), gbcD);

            // --- FILA 4: Separador ---
            gbcD.gridx = 0;
            gbcD.gridy++;
            gbcD.gridwidth = 4;
            if (view.getSepaAparatos() != null)
                datosPanel.add(view.getSepaAparatos(), gbcD);

            // --- FILA 5: Información de FTTH ---
            gbcD.gridy++;
            gbcD.gridwidth = 1;
            gbcD.weightx = 0.0;
            if (view.getFtthLabel() != null)
                datosPanel.add(view.getFtthLabel(), gbcD);

            gbcD.gridx = 1;
            gbcD.gridwidth = 3;
            gbcD.weightx = 1.0;
            JPanel ftthWrap = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
            ftthWrap.setOpaque(false);
            if (view.getTxtMoFT() != null)
                ftthWrap.add(view.getTxtMoFT());
            ftthWrap.add(Box.createHorizontalStrut(20));
            if (view.getTxtAparato() != null)
                ftthWrap.add(view.getTxtAparato());
            datosPanel.add(ftthWrap, gbcD);

            // --- FILA 6: Dirección MAC de FTTH ---
            gbcD.gridx = 1;
            gbcD.gridy++;
            gbcD.gridwidth = 3;
            JPanel macWrap = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
            macWrap.setOpaque(false);
            if (view.getTxtMAC() != null)
                macWrap.add(view.getTxtMAC());
            macWrap.add(Box.createHorizontalStrut(15));
            if (view.getBtnDiagFTTH() != null)
                macWrap.add(view.getBtnDiagFTTH());
            datosPanel.add(macWrap, gbcD);

            // --- FILA 7: Información móvil 5G ---
            gbcD.gridx = 0;
            gbcD.gridy++;
            gbcD.gridwidth = 1;
            gbcD.weightx = 0.0;
            gbcD.anchor = GridBagConstraints.NORTHWEST;
            if (view.getMovilLabel() != null)
                datosPanel.add(view.getMovilLabel(), gbcD);

            gbcD.gridx = 1;
            gbcD.gridwidth = 3;
            gbcD.weightx = 1.0;
            gbcD.fill = GridBagConstraints.BOTH;
            gbcD.weighty = 0.1;
            JPanel listWrap = new JPanel(new BorderLayout(10, 0));
            listWrap.setOpaque(false);
            if (view.getJScrollPane2() != null)
                listWrap.add(view.getJScrollPane2(), BorderLayout.CENTER);
            JPanel listBtnPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
            listBtnPanel.setOpaque(false);
            if (view.getBtnDiagMovil() != null)
                listBtnPanel.add(view.getBtnDiagMovil());
            listWrap.add(listBtnPanel, BorderLayout.EAST);
            datosPanel.add(listWrap, gbcD);

            gbcD.anchor = GridBagConstraints.WEST;
            gbcD.fill = GridBagConstraints.HORIZONTAL;

            // --- FILA 8: Título de comentarios ---
            gbcD.gridx = 0;
            gbcD.gridy++;
            gbcD.gridwidth = 4;
            gbcD.weighty = 0.0;
            if (view.getComenLabel() != null)
                datosPanel.add(view.getComenLabel(), gbcD);

            // --- FILA 9: Tabla de comentarios ---
            gbcD.gridy++;
            gbcD.weighty = 0.3;
            gbcD.fill = GridBagConstraints.BOTH;
            if (view.getJScrollPane1() != null)
                datosPanel.add(view.getJScrollPane1(), gbcD);

            // --- FILA 10: Botonera de acciones ---
            gbcD.gridy++;
            gbcD.weighty = 0.0;
            gbcD.fill = GridBagConstraints.HORIZONTAL;
            JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
            buttonPanel.setOpaque(false);
            if (view.getSoluButton() != null)
                buttonPanel.add(view.getSoluButton());
            if (view.getActuButton() != null)
                buttonPanel.add(view.getActuButton());
            if (view.getEnviButton() != null)
                buttonPanel.add(view.getEnviButton());
            datosPanel.add(buttonPanel, gbcD);
        }

        // 2. REESTRUCTURAR inciPanel
        javax.swing.JPanel inciPanel = view.getInciPanel();
        if (inciPanel != null) {
            inciPanel.removeAll();
            inciPanel.setLayout(new BorderLayout(0, 10));
            inciPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(TelecomTheme.BORDER, 1, true),
                    new EmptyBorder(10, 10, 10, 10)));

            JPanel listHeader = new JPanel(new BorderLayout(0, 10));
            listHeader.setOpaque(false);

            JPanel searchWrap = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
            searchWrap.setOpaque(false);
            if (view.getSearchField() != null)
                searchWrap.add(view.getSearchField());
            searchWrap.add(Box.createHorizontalStrut(10));
            if (view.getSearchButton() != null)
                searchWrap.add(view.getSearchButton());

            listHeader.add(searchWrap, BorderLayout.NORTH);
            if (view.getListadoText() != null)
                listHeader.add(view.getListadoText(), BorderLayout.CENTER);

            inciPanel.add(listHeader, BorderLayout.NORTH);
            if (view.getIncidenciasTable() != null)
                inciPanel.add(view.getIncidenciasTable(), BorderLayout.CENTER);
        }

        // 3. DISEÑO RAÍZ
        view.removeAll();
        view.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(20, 20, 10, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;

        if (view.getInciGTitle() != null)
            view.add(view.getInciGTitle(), gbc);

        javax.swing.JPanel contentSplit = new javax.swing.JPanel(new GridLayout(1, 2, 20, 0));
        contentSplit.setOpaque(false);
        if (datosPanel != null)
            contentSplit.add(datosPanel);
        if (inciPanel != null)
            contentSplit.add(inciPanel);

        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        view.add(contentSplit, gbc);

        view.revalidate();
        view.repaint();
    }

    /**
     * Aplica el tema visual al diálogo de visor de logs.
     * 
     * @param dialog El JDialog a estilizar.
     */
    public void applyLogViewerTheme(javax.swing.JDialog dialog) {
        dialog.getContentPane().setBackground(TelecomTheme.APP_BG);
        if (dialog.getContentPane() instanceof javax.swing.JComponent) {
            ((javax.swing.JComponent) dialog.getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
        }
        dialog.setTitle("Visor de Logs del Sistema");
    }

    /**
     * Configura las columnas y el renderizado de la tabla de logs.
     * Colorea la columna "Estado" según el valor (OK=Verde, ERROR=Rojo,
     * WARNING=Naranja).
     * 
     * @param table La tabla a configurar.
     */
    public void configureLogTableColumns(javax.swing.JTable table) {
        // Estilo base
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Columnas esperadas: Estado, Panel, Descripción, Fecha
        // Indices: 0=Estado, 1=Panel, 2=Descripción, 3=Fecha

        // Renderizador para ESTADO
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = (String) value;

                if (estado != null) {
                    setBorder(new EmptyBorder(0, 10, 0, 10)); // Relleno
                    if ("OK".equalsIgnoreCase(estado)) {
                        setForeground(new Color(34, 197, 94)); // Verde
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if ("ERROR".equalsIgnoreCase(estado)) {
                        setForeground(new Color(239, 68, 68)); // Rojo
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if ("WARNING".equalsIgnoreCase(estado)) {
                        setForeground(new Color(249, 115, 22)); // Naranja
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                    }
                }
                return c;
            }
        });

        // Tamaños de columna
        table.getColumnModel().getColumn(0).setPreferredWidth(80); // Estado
        table.getColumnModel().getColumn(0).setMaxWidth(100);

        table.getColumnModel().getColumn(1).setPreferredWidth(120); // Panel
        table.getColumnModel().getColumn(1).setMaxWidth(200);

        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Fecha
        table.getColumnModel().getColumn(3).setMaxWidth(180);

        // Descripción flexible
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
    }
}
