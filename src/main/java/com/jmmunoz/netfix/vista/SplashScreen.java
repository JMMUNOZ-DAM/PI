/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.vista;

import java.awt.AlphaComposite;
import com.jmmunoz.netfix.vista.tema.TelecomTheme;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Pantalla de carga estilo Netflix con animación de fade-in/fade-out.
 * Se muestra al iniciar la aplicación antes del login.
 * 
 * @author Juanma Muñoz
 */
public class SplashScreen extends JWindow {

    private float opacity = 0.0f;
    private Timer timer;
    private final int FADE_IN_DURATION = 1500; // ms
    private final int HOLD_DURATION = 2000; // ms
    private final int FADE_OUT_DURATION = 1000;// ms
    private long startTime;
    private Image logo;
    private int logoW, logoH;

    /**
     * Crea e inicializa la pantalla de splash.
     * Configura el tamaño, carga el logo e inicia la animación.
     */
    public SplashScreen() {
        // Configuración básica
        setSize(800, 500); // Un poco más grande para que luzca
        setLocationRelativeTo(null);

        // Cargar logo con ruta absoluta y relativa por si acaso
        URL url = getClass().getResource("/img/netfix_logo.png");
        if (url == null) {
            System.getLogger(SplashScreen.class.getName()).log(System.Logger.Level.INFO,
                    "Intentando ruta relativa para logo: img/netfix_logo.png");
            url = getClass().getResource("img/netfix_logo.png"); // Intento relativo
        }

        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            logo = icon.getImage();
            logoW = icon.getIconWidth();
            logoH = icon.getIconHeight();
        } else {
            System.getLogger(SplashScreen.class.getName()).log(System.Logger.Level.ERROR,
                    "Error: No se encontró el logo en /img/netfix_logo.png ni en img/netfix_logo.png");
        }

        setContentPane(new SplashPanel());
        startAnimation();
    }

    /**
     * Inicia la animación de fade-in, espera y fade-out.
     * Utiliza un Timer para actualizar la opacidad.
     */
    private void startAnimation() {
        startTime = System.currentTimeMillis();

        timer = new Timer(16, e -> { // ~60 FPS
            long elapsed = System.currentTimeMillis() - startTime;

            if (elapsed < FADE_IN_DURATION) {
                opacity = (float) elapsed / FADE_IN_DURATION;
            } else if (elapsed < FADE_IN_DURATION + HOLD_DURATION) {
                opacity = 1.0f;
            } else if (elapsed < FADE_IN_DURATION + HOLD_DURATION + FADE_OUT_DURATION) {
                float fadeOutElapsed = elapsed - (FADE_IN_DURATION + HOLD_DURATION);
                opacity = 1.0f - (fadeOutElapsed / FADE_OUT_DURATION);
            } else {
                timer.stop();
                opacity = 0.0f;
                dispose();
                launchLogin();
                return;
            }

            opacity = Math.max(0.0f, Math.min(1.0f, opacity));
            repaint();
        });

        timer.start();
        setVisible(true);
    }

    /**
     * Lanza la ventana de login y cierra el splash screen.
     * Se ejecuta al finalizar la animación.
     */
    private void launchLogin() {
        SwingUtilities.invokeLater(() -> {
            new login().setVisible(true);
        });
    }

    private class SplashPanel extends JPanel {
        public SplashPanel() {
            setOpaque(false);
            setBackground(TelecomTheme.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Fondo negro sólido
            g2d.setColor(TelecomTheme.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            if (logo != null && opacity > 0) {
                // Aplicar opacidad
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

                // Calcular escala para que quepa bien
                double scale = 1.0;
                int maxW = getWidth() - 100;
                int maxH = getHeight() - 100;

                if (logoW > maxW || logoH > maxH) {
                    scale = Math.min((double) maxW / logoW, (double) maxH / logoH);
                }

                int drawW = (int) (logoW * scale);
                int drawH = (int) (logoH * scale);

                // Centrar
                int x = (getWidth() - drawW) / 2;
                int y = (getHeight() - drawH) / 2;

                g2d.drawImage(logo, x, y, drawW, drawH, this);
            }
        }
    }
}
