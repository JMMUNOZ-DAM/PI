package com.jmmunoz.netfix.vista.dialogos;

import com.formdev.flatlaf.FlatClientProperties;
import com.jmmunoz.netfix.vista.tema.TelecomTheme;
import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Diálogo modal moderno y estilizado para reemplazar JOptionPane.
 * Sigue la estética "Telecom" de la aplicación.
 * 
 * @author Juanma Muñoz
 */
public class ModernDialog extends JDialog {

    private boolean confirmed = false;
    private final JPanel contentPanel;
    private final JPanel buttonPanel;

    public ModernDialog(Window owner, String title, JComponent bodyComponent) {
        super(owner, title, ModalityType.APPLICATION_MODAL);

        // Configuración básica
        setUndecorated(true);

        // Configuración de fondo
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(TelecomTheme.WHITE);
        main.setBorder(new CompoundBorder(
                new LineBorder(TelecomTheme.BORDER_DARK, 1),
                new EmptyBorder(0, 0, 0, 0)));

        // 1. Cabecera (Título + Botón X opcional)
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(TelecomTheme.WHITE);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TelecomTheme.ACCENT_DARK);

        header.add(titleLabel, BorderLayout.WEST);

        // Botón cerrar (X)
        JButton closeBtn = new JButton("X");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeBtn.setForeground(TelecomTheme.GRAY);
        closeBtn.setBorder(null);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        closeBtn.setFocusPainted(false);

        header.add(closeBtn, BorderLayout.EAST);

        // 2. Contenido
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(TelecomTheme.WHITE);
        contentPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        if (bodyComponent != null) {
            contentPanel.add(bodyComponent, BorderLayout.CENTER);
        }

        // 3. Pie de página (Botones)
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        buttonPanel.setBackground(TelecomTheme.BG_SUBTLE); // Gris muy suave
        buttonPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        main.add(header, BorderLayout.NORTH);
        main.add(contentPanel, BorderLayout.CENTER);
        main.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(main);

        // Sombra (propiedad FlatLaf)
        getRootPane().putClientProperty(FlatClientProperties.STYLE, "dropShadowSize: 10; panel.background: #FFFFFF");
    }

    /**
     * Añade un botón de acción principal (Aceptar/Guardar).
     */
    public void addActionButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        stylePrimaryButton(btn);
        btn.addActionListener(e -> {
            if (action != null)
                action.run();
        });
        buttonPanel.add(btn);
    }

    /**
     * Añade botón Aceptar que marca confirmed = true y cierra.
     */
    public void addAcceptButton(String text) {
        JButton btn = new JButton(text);
        stylePrimaryButton(btn);
        btn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        buttonPanel.add(btn);
        getRootPane().setDefaultButton(btn);
    }

    /**
     * Añade botón Cancelar que cierra.
     */
    public void addCancelButton(String text) {
        JButton btn = new JButton(text);
        styleSecondaryButton(btn);
        btn.addActionListener(e -> dispose());
        buttonPanel.add(btn);
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(TelecomTheme.ACCENT);
        btn.setForeground(TelecomTheme.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(TelecomTheme.WHITE);
        btn.setForeground(TelecomTheme.GRAY);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(
                new LineBorder(TelecomTheme.BORDER_DARK, 1, true),
                new EmptyBorder(7, 19, 7, 19)));
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void showDialog() {
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
    }

    // --- MÉTODOS ESTÁTICOS DE AYUDA PARA REEMPLAZAR JOPTIONPANE ---

    public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        Window owner = (parentComponent instanceof Window) ? (Window) parentComponent
                : javax.swing.SwingUtilities.getWindowAncestor(parentComponent);

        JComponent body;
        if (message instanceof JComponent) {
            body = (JComponent) message;
        } else {
            JLabel lbl = new JLabel("<html><body style='width: 300px'>" + message.toString() + "</body></html>");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            body = lbl;
        }

        ModernDialog dialog = new ModernDialog(owner, title, body);
        dialog.addAcceptButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("btn.accept"));
        dialog.showDialog();
    }

    // Sobrecarga: showMessageDialog solo con mensaje (usa título/tipo por defecto)
    public static void showMessageDialog(Component parentComponent, Object message) {
        showMessageDialog(parentComponent, message, "Info", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        Window owner = (parentComponent instanceof Window) ? (Window) parentComponent
                : javax.swing.SwingUtilities.getWindowAncestor(parentComponent);

        JComponent body;
        if (message instanceof JComponent) {
            body = (JComponent) message;
        } else {
            JLabel lbl = new JLabel("<html><body style='width: 300px'>" + message.toString() + "</body></html>");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            body = lbl;
        }

        ModernDialog dialog = new ModernDialog(owner, title, body);

        // añadimos Aceptar primero, luego Cancelar.

        dialog.addAcceptButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("btn.accept"));
        dialog.addCancelButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("btn.cancel"));

        dialog.showDialog();

        return dialog.isConfirmed() ? javax.swing.JOptionPane.OK_OPTION : javax.swing.JOptionPane.CANCEL_OPTION;
    }

    // Sobrecarga: showConfirmDialog solo con mensaje
    public static int showConfirmDialog(Component parentComponent, Object message) {
        return showConfirmDialog(parentComponent, message, "Confirm", javax.swing.JOptionPane.OK_CANCEL_OPTION);
    }

    public static String showInputDialog(Component parentComponent, Object message, String title, int messageType) {
        Window owner = (parentComponent instanceof Window) ? (Window) parentComponent
                : javax.swing.SwingUtilities.getWindowAncestor(parentComponent);

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        if (message instanceof JComponent) {
            panel.add((JComponent) message, BorderLayout.NORTH);
        } else {
            JLabel lbl = new JLabel("<html><body style='width: 300px'>" + message.toString() + "</body></html>");
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            panel.add(lbl, BorderLayout.NORTH);
        }

        javax.swing.JTextField textField = new javax.swing.JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(textField, BorderLayout.CENTER);

        ModernDialog dialog = new ModernDialog(owner, title, panel);

        dialog.addAcceptButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("btn.accept"));
        dialog.addCancelButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("btn.cancel"));

        dialog.showDialog();

        if (dialog.isConfirmed()) {
            return textField.getText();
        }
        return null; // Cancelado
    }

    // Sobrecarga: showInputDialog solo con mensaje
    public static String showInputDialog(Component parentComponent, Object message) {
        return showInputDialog(parentComponent, message, "Input", javax.swing.JOptionPane.PLAIN_MESSAGE);
    }

    // Sobrecarga: showInputDialog para selección genérica (Desplegable)
    public static Object showInputDialog(Component parentComponent, Object message, String title, int messageType,
            Icon icon, Object[] selectionValues, Object initialSelectionValue) {

        Window owner = (parentComponent instanceof Window) ? (Window) parentComponent
                : javax.swing.SwingUtilities.getWindowAncestor(parentComponent);

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        // Mensaje del cuerpo
        JLabel lbl = new JLabel("<html><body style='width: 300px'>" + message.toString() + "</body></html>");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lbl, BorderLayout.NORTH);

        // Desplegable
        javax.swing.JComboBox<Object> comboBox = new javax.swing.JComboBox<>(selectionValues);
        if (initialSelectionValue != null) {
            comboBox.setSelectedItem(initialSelectionValue);
        }
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(comboBox, BorderLayout.CENTER);

        ModernDialog dialog = new ModernDialog(owner, title, panel);
        dialog.addAcceptButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("btn.accept"));
        dialog.addCancelButton(com.jmmunoz.netfix.config.AppConfig.getInstance().getMessage("btn.cancel"));

        dialog.showDialog();

        if (dialog.isConfirmed()) {
            return comboBox.getSelectedItem();
        }
        return null;
    }
}
