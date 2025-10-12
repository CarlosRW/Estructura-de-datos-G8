package com.sandwichguy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Grupo 8
 */

public class Interfaz extends JFrame {
    private JPanel panelMesa;
    private JPanel panelMazo;
    private JPanel panelMano;
    private List<ImageIcon> baraja;
    private List<ImageIcon> mazo;
    private List<ImageIcon> mano;
    private static final int ANCHO_CARTA = 80;  
    private static final int ALTO_CARTA = 120;   
    private boolean mostrarMazo = false;

    public Interfaz() {
        // Configuración de la ventana
        setTitle("Juego de Cartas");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cargarImagenes();
        inicializarComponentes();
    }
    
    private void cargarImagenes() {
        baraja = new ArrayList<>();
        mazo = new ArrayList<>();
        mano = new ArrayList<>();
        
        // Cargar baraja
        for (int i = 0; i < 52; i++) {
            try {
                String ruta = String.format("/images/baraja/%02d.jpg", i);
                java.net.URL imgUrl = getClass().getResource(ruta);
                if (imgUrl == null) {
                    System.err.println("No se pudo encontrar: " + ruta);
                    continue;
                }
                ImageIcon icono = new ImageIcon(imgUrl);
                Image img = icono.getImage().getScaledInstance(ANCHO_CARTA, ALTO_CARTA, Image.SCALE_SMOOTH);
                baraja.add(new ImageIcon(img));
            } catch (Exception e) {
                System.err.println("Error al cargar la carta " + i + ": " + e.getMessage());
            }
        }
        
        // Inicializar mazo
        mazo.addAll(baraja);
        
        // Tomar 8 cartas para la mano
        for (int i = 0; i < 8 && i < mazo.size(); i++) {
            mano.add(mazo.get(i));
        }
        
        // Eliminar las cartas de la mano del mazo
        mazo.subList(0, Math.min(8, mazo.size())).clear();
    }
    
    private void inicializarComponentes() {
        // Configuración de la ventana
        setLayout(new BorderLayout());
        setBackground(new Color(0, 100, 0)); // Fondo verde de mesa
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(0, 80, 0));
        JLabel titulo = new JLabel("Juego de Cartas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelTitulo.add(titulo);
        
        // Panel principal de la mesa
        panelMesa = new JPanel(new BorderLayout(0, 5));
        panelMesa.setBackground(new Color(0, 100, 0));
        panelMesa.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Panel del mazo
        panelMazo = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelMazo.setBackground(new Color(0, 100, 0));
        panelMazo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2), 
            "Mazo", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            Color.WHITE
        ));
        panelMazo.setPreferredSize(new Dimension(getWidth(), 200));  // Altura fija para el mazo
        
        // Panel de la mano
        panelMano = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelMano.setBackground(new Color(0, 100, 0));
        panelMano.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.YELLOW, 2), 
            "Tu Mano", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            Color.YELLOW
        ));
        panelMano.setPreferredSize(new Dimension(getWidth(), 180));  // Altura reducida
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(0, 80, 0));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        // Barajar
        JButton btnBarajar = new JButton("BARAJAR");
        btnBarajar.addActionListener(e -> barajarCartas());
        
        // Mostrar Mazo
        JButton btnMostrarMazo = new JButton("MOSTRAR MAZO");
        btnMostrarMazo.addActionListener(e -> {
            mostrarMazo = !mostrarMazo;
            actualizarVista();
        });
        
        // Estilo para los botones
        for (JButton btn : new JButton[]{btnBarajar, btnMostrarMazo}) {
            btn.setBackground(new Color(255, 215, 0));  // Fondo dorado
            btn.setForeground(Color.BLACK);            // Texto negro
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setPreferredSize(new Dimension(180, 45));
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));
            // Efecto hover
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(255, 230, 100));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(255, 215, 0));
                }
            });
            panelBotones.add(btn);
        }
        
        // Configurar panel principal
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(new Color(0, 100, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        gbc.weighty = 0.6;  
        gbc.gridy = 0;
        panelCentral.add(panelMazo, gbc);
        
        gbc.weighty = 0.4; 
        gbc.gridy = 1;
        panelCentral.add(panelMano, gbc);
        
        panelMesa.add(panelCentral, BorderLayout.CENTER);
        
        // Agregar componentes a la ventana
        add(panelTitulo, BorderLayout.NORTH);
        add(panelMesa, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Mostrar la vista inicial
        actualizarVista();
    }
    
    private void actualizarVista() {
        // Limpiar paneles
        panelMazo.removeAll();
        panelMano.removeAll();
        
        // Mostrar el mazo (solo la parte superior si no está expandido)
        if (mostrarMazo) {
            // Mostrar todas las cartas del mazo
            for (ImageIcon carta : mazo) {
                JLabel label = new JLabel(carta);
                label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                panelMazo.add(label);
            }
        } else if (!mazo.isEmpty()) {
            // Mostrar solo la parte superior del mazo
            JLabel mazoLabel = new JLabel(mazo.get(0));
            mazoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            panelMazo.add(mazoLabel);
            
            // Mostrar contador de cartas restantes
            if (mazo.size() > 1) {
                JLabel contador = new JLabel("+" + (mazo.size() - 1) + " cartas");
                contador.setForeground(Color.WHITE);
                contador.setFont(new Font("Arial", Font.BOLD, 14));
                panelMazo.add(contador);
            }
        } else {
            JLabel sinCartas = new JLabel("No hay cartas en el mazo");
            sinCartas.setForeground(Color.WHITE);
            sinCartas.setFont(new Font("Arial", Font.ITALIC, 14));
            panelMazo.add(sinCartas);
        }
        
        // Mostrar mano del jugador
        if (!mano.isEmpty()) {
            for (ImageIcon carta : mano) {
                JLabel label = new JLabel(carta);
                label.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                panelMano.add(label);
            }
        } else {
            JLabel sinCartas = new JLabel("No hay cartas en la mano");
            sinCartas.setForeground(Color.WHITE);
            sinCartas.setFont(new Font("Arial", Font.ITALIC, 14));
            panelMano.add(sinCartas);
        }
        
        // Actualizar interfaz
        panelMazo.revalidate();
        panelMazo.repaint();
        panelMano.revalidate();
        panelMano.repaint();
    }
    
    private void barajarCartas() {
        // Mezclar la baraja completa
        Collections.shuffle(baraja);
        
        // Reiniciar mazo y mano
        mazo.clear();
        mano.clear();
        
        // Agregar todas las cartas al mazo
        mazo.addAll(baraja);
        
        // Tomar las primeras 8 cartas para la mano
        int cartasATomar = Math.min(8, mazo.size());
        for (int i = 0; i < cartasATomar; i++) {
            mano.add(mazo.get(i));
        }
        
        // Eliminar las cartas de la mano del mazo
        if (!mazo.isEmpty()) {
            mazo.subList(0, cartasATomar).clear();
        }
        
        // Actualizar la vista
        actualizarVista();
        
        JOptionPane.showMessageDialog(this, 
            "¡Cartas barajadas!\nSe han repartido " + mano.size() + " cartas a tu mano.",
            "Barajar", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Mostrar interfaz
        SwingUtilities.invokeLater(() -> {
            Interfaz juego = new Interfaz();
            juego.setVisible(true);
        });
    }
}