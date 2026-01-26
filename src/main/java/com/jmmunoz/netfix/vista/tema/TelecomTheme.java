/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix.vista.tema;

import java.awt.Color;

/**
 * Paleta de colores corporativa para la aplicación Netfix.
 * Define constantes estáticas para colores de fondo, texto, acentos y estados.
 * 
 * @author Juanma Muñoz
 */
public final class TelecomTheme {

    private TelecomTheme() {
    } // no instanciable

    // Marca / Accent (cyan telecom)
    public static final Color ACCENT = new Color(0, 195, 255);
    public static final Color ACCENT_DARK = new Color(10, 80, 160);

    // Neutros claros
    public static final Color APP_BG = Color.WHITE; // fondo app
    public static final Color SURFACE = new Color(255, 255, 255); // cards/paneles
    public static final Color BORDER = new Color(220, 226, 235);

    // Texto
    public static final Color TEXT = new Color(16, 24, 40);
    public static final Color TEXT_MUTED = new Color(102, 112, 133);

    // Sidebar
    public static final Color NAV_BG = new Color(10, 18, 35);
    public static final Color NAV_BG_ACTIVE = new Color(14, 26, 48);
    public static final Color NAV_BG_HOVER = new Color(18, 34, 62);
    public static final Color TEXT_ON_DARK = new Color(230, 235, 245);

    // Estados
    public static final Color OK = new Color(0, 180, 120);
    public static final Color WARN = new Color(255, 170, 0);
    public static final Color ERROR = new Color(220, 70, 70);
}
