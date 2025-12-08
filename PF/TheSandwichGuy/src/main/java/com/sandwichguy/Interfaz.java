package com.sandwichguy;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Interfaz extends JFrame {
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;
    private static final Color TABLE_COLOR = new Color(0, 100, 0);

    private final Caja caja;
    private final Mazo mazo;
    private final Mano mano;
    private final Pozo pozo;

    private final JPanel panelMano;
    private final JPanel panelMazo;
    private final JPanel panelPozo;
    private final JLabel statusLabel;
    private final JLabel deckCountLabel;
    private final JLabel discardCountLabel;
    private boolean[] selectedCards;

    private final Map<String, ImageIcon> cardImages = new HashMap<>();

    public Interfaz() {
        caja = new Caja();
        mazo = new Mazo();
        mano = new Mano();
        pozo = new Pozo();

        setTitle("The Sandwich Guy - Avance 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(TABLE_COLOR);
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        // Componentes UI
        panelMano = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelMazo = new JPanel(new BorderLayout());
        panelPozo = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Bienvenido a The Sandwich Guy");
        deckCountLabel = new JLabel("Mazo: 0");
        discardCountLabel = new JLabel("Pozo: 0");

        initUI();
        cargarImagenes();
        iniciarNuevoJuego();
    }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        main.setBackground(TABLE_COLOR);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setOpaque(false);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.setOpaque(false);
        buttons.add(crearBoton("BARAJAR", e -> barajarCartas()));
        buttons.add(crearBoton("REPARTIR MANO", e -> repartirMano()));
        buttons.add(crearBoton("VALIDAR", e -> validarJugada()));
        buttons.add(crearBoton("DESCARTAR", e -> descartarSeleccionadas()));

        JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        info.setOpaque(false);
        deckCountLabel.setForeground(Color.WHITE);
        discardCountLabel.setForeground(Color.WHITE);
        info.add(deckCountLabel);
        info.add(discardCountLabel);

        top.add(statusLabel, BorderLayout.NORTH);
        top.add(buttons, BorderLayout.CENTER);
        top.add(info, BorderLayout.SOUTH);

        panelMazo.setOpaque(false);
        panelMazo.setPreferredSize(new Dimension(150, 220));
        panelMazo.setBorder(crearBorde("MAZO"));

        panelPozo.setOpaque(false);
        panelPozo.setPreferredSize(new Dimension(150, 220));
        panelPozo.setBorder(crearBorde("POZO"));

        panelMano.setOpaque(false);
        panelMano.setBorder(crearBorde("TU MANO"));

        JPanel sides = new JPanel(new BorderLayout(20, 10));
        sides.setOpaque(false);
        sides.add(panelMazo, BorderLayout.WEST);
        sides.add(panelPozo, BorderLayout.EAST);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setOpaque(false);
        center.add(sides, BorderLayout.NORTH);
        center.add(panelMano, BorderLayout.CENTER);

        main.add(top, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        add(main);
    }

    private JButton crearBoton(String texto, ActionListener accion) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.addActionListener(accion);
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(new Color(100,160,210)); }
            @Override public void mouseExited(MouseEvent e)  { b.setBackground(new Color(70,130,180)); }
        });
        return b;
    }

    private TitledBorder crearBorde(String titulo) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.YELLOW, 2),
            titulo,
            TitledBorder.CENTER,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 14),
            Color.YELLOW
        );
    }

    private void cargarImagenes() {
        // Reverso (00.png) y mazo
        ImageIcon back = loadAndScale("/images/mano/00.png");
        if (back == null) back = new ImageIcon(createCardBackImage());
        cardImages.put("reverso", back);
        cardImages.put("mazo", back);

        List<ImageIcon> pool = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            ImageIcon ic = loadAndScale(String.format("/images/mano/%02d.png", i));
            if (ic != null) pool.add(ic);
        }

        for (Carta.Palo p : Carta.Palo.values()) {
            for (Carta.Valor v : Carta.Valor.values()) {
                String key = p.name() + "_" + v.name();
                ImageIcon icon = null;
                // 1) Preferir archivo numerado NN.jpg/png
                int deckIndex = p.ordinal() * 13 + v.ordinal(); // 0..51
                icon = loadFromBarajaIndex(deckIndex);
                // 2) Si no, intentar nombre específico palo_valor.png
                if (icon == null) {
                    String specific = String.format("/images/baraja/%s_%s.png", p.name().toLowerCase(), v.name().toLowerCase());
                    icon = loadAndScale(specific);
                }
                // 3) Si no, usar pool de mano (01-07)
                if (icon == null && !pool.isEmpty()) {
                    int poolIdx = (deckIndex) % pool.size();
                    icon = pool.get(poolIdx);
                }
                // 4) Último recurso: generar imagen
                if (icon == null) icon = new ImageIcon(createGenericCardImage(p, v));
                cardImages.put(key, icon);
            }
        }
    }

    private ImageIcon loadFromBarajaIndex(int index) {
        String jpg = String.format("/images/baraja/%02d.jpg", index);
        ImageIcon icon = loadAndScale(jpg);
        if (icon != null) return icon;
        String png = String.format("/images/baraja/%02d.png", index);
        return loadAndScale(png);
    }

    private ImageIcon loadAndScale(String resourcePath) {
        try {
            java.net.URL url = getClass().getResource(resourcePath);
            if (url == null) return null;
            ImageIcon ii = new ImageIcon(url);
            Image scaled = ii.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception ex) {
            return null;
        }
    }

    private BufferedImage createCardBackImage() {
        BufferedImage img = new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(0, 0, 139));
        g.fillRect(0, 0, CARD_WIDTH, CARD_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("REVERSO", 10, CARD_HEIGHT / 2);
        g.dispose();
        return img;
    }

    private BufferedImage createGenericCardImage(Carta.Palo palo, Carta.Valor valor) {
        BufferedImage img = new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CARD_WIDTH, CARD_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, CARD_WIDTH - 1, CARD_HEIGHT - 1);
        boolean isRed = (palo == Carta.Palo.CORAZONES || palo == Carta.Palo.DIAMANTES);
        g.setColor(isRed ? Color.RED : Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(valor.getSimbolo(), 5, 18);
        char suit = palo.getSimbolo();
        g.drawString(String.valueOf(suit), CARD_WIDTH - 18, CARD_HEIGHT - 6);
        g.setFont(new Font("Arial", Font.PLAIN, 40));
        FontMetrics fm = g.getFontMetrics();
        String s = String.valueOf(suit);
        int x = (CARD_WIDTH - fm.stringWidth(s)) / 2;
        int y = (CARD_HEIGHT - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(s, x, y);
        g.dispose();
        return img;
    }

    private void iniciarNuevoJuego() {
        caja.limpiar();
        mazo.limpiar();
        mano.limpiar();
        pozo.limpiar();

        for (Carta.Palo p : Carta.Palo.values()) {
            for (Carta.Valor v : Carta.Valor.values()) {
                String key = p.name() + "_" + v.name();
                ImageIcon img = cardImages.getOrDefault(key, cardImages.get("reverso"));
                caja.agregar(new Carta(p, v, img));
            }
        }

        actualizarInterfaz();
        statusLabel.setText("Juego listo. Haz clic en 'BARAJAR'.");
    }

    private void barajarCartas() {
        List<Carta> todas = new ArrayList<>();
        todas.addAll(mazo.obtenerTodasLasCartas());
        todas.addAll(mano.obtenerTodasLasCartas());
        todas.addAll(pozo.obtenerTodasLasCartas());
        caja.agregarTodas(todas);
        mazo.limpiar();
        mano.limpiar();
        pozo.limpiar();

        List<Carta> barajadas = caja.moverTodoElContenido();
        Collections.shuffle(barajadas);
        for (Carta c : barajadas) mazo.push(c);

        selectedCards = new boolean[0];
        actualizarInterfaz();
        statusLabel.setText("Cartas barajadas. 'REPARTIR MANO'.");
    }

    private void repartirMano() {
        if (mazo.estaVacio()) {
            JOptionPane.showMessageDialog(this, "No hay cartas en el mazo. BARAJAR primero.", "Mazo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mano.limpiar();
        int n = Math.min(8, mazo.size());
        for (int i = 0; i < n; i++) {
            Carta c = mazo.pop();
            if (c != null) mano.agregar(c);
        }
        selectedCards = new boolean[mano.tamano()];
        actualizarInterfaz();
        statusLabel.setText("Selecciona 3 cartas para validar.");
    }

    private void validarJugada() {
        if (mano.estaVacia()) {
            JOptionPane.showMessageDialog(this, "No hay cartas en la mano.", "Mano vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int sel = 0;
        for (boolean b : selectedCards) if (b) sel++;
        if (sel != 3) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar exactamente 3 cartas.", "Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        statusLabel.setText("Posible sándwich. Si deseas, presiona DESCARTAR.");
    }

    private void descartarSeleccionadas() {
        if (selectedCards == null || selectedCards.length == 0) return;
        int sel = 0;
        for (boolean b : selectedCards) if (b) sel++;
        if (sel != 3) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar exactamente 3 cartas.", "Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Carta> enMano = mano.obtenerTodasLasCartas();
        List<Carta> aDescartar = new ArrayList<>();
        for (int i = 0; i < Math.min(selectedCards.length, enMano.size()); i++)
            if (selectedCards[i]) aDescartar.add(enMano.get(i));

        for (Carta c : aDescartar) { mano.eliminar(c); pozo.agregar(c); }

        while (mano.tamano() < 8 && !mazo.estaVacio()) {
            Carta c = mazo.pop();
            if (c != null) mano.agregar(c);
        }

        selectedCards = new boolean[mano.tamano()];

        if (mazo.estaVacio() && mano.estaVacia()) {
            JOptionPane.showMessageDialog(this, "¡Ganaste! Mazo vacío.", "Fin", JOptionPane.INFORMATION_MESSAGE);
        }
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        deckCountLabel.setText("Mazo: " + mazo.size());
        discardCountLabel.setText("Pozo: " + pozo.size());
        actualizarPanelMazo();
        actualizarPanelMano();
        actualizarPanelPozo();
        revalidate();
        repaint();
    }

    private void actualizarPanelMazo() {
        panelMazo.removeAll();
        if (mazo.estaVacio()) {
            JLabel l = new JLabel("Vacío"); l.setForeground(Color.WHITE); l.setHorizontalAlignment(SwingConstants.CENTER);
            panelMazo.add(l, BorderLayout.CENTER);
        } else {
            JLabel l = new JLabel(cardImages.getOrDefault("reverso", new ImageIcon(createCardBackImage())));
            l.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            panelMazo.add(l, BorderLayout.CENTER);
            if (mazo.size() > 1) {
                JLabel c = new JLabel("+" + (mazo.size() - 1) + " cartas");
                c.setForeground(Color.WHITE); c.setHorizontalAlignment(SwingConstants.CENTER);
                panelMazo.add(c, BorderLayout.SOUTH);
            }
        }
        panelMazo.revalidate(); panelMazo.repaint();
    }

    private void actualizarPanelMano() {
        panelMano.removeAll();
        if (mano.estaVacia()) {
            JLabel l = new JLabel("Sin cartas"); l.setForeground(Color.WHITE); panelMano.add(l);
        } else {
            List<Carta> cartas = mano.obtenerTodasLasCartas();
            for (int i = 0; i < cartas.size(); i++) {
                Carta c = cartas.get(i);
                JLabel lbl = new JLabel(c.getImagen());
                lbl.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                final int idx = i;
                lbl.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) {
                        if (selectedCards != null && idx < selectedCards.length) {
                            selectedCards[idx] = !selectedCards[idx];
                            // Limitar a 3 seleccionadas
                            int count = 0; int last = -1;
                            for (int k = 0; k < selectedCards.length; k++) { if (selectedCards[k]) { count++; last = k; } }
                            if (count > 3 && last >= 0) { selectedCards[last] = false; }
                            actualizarInterfaz();
                        }
                    }
                });
                if (selectedCards != null && i < selectedCards.length && selectedCards[i])
                    lbl.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                panelMano.add(lbl);
            }
        }
        panelMano.revalidate(); panelMano.repaint();
    }

    private void actualizarPanelPozo() {
        panelPozo.removeAll();
        if (pozo.isEmpty()) {
            JLabel l = new JLabel("Vacío"); l.setForeground(Color.WHITE); l.setHorizontalAlignment(SwingConstants.CENTER);
            panelPozo.add(l, BorderLayout.CENTER);
        } else {
            JLabel l = new JLabel(cardImages.getOrDefault("reverso", new ImageIcon(createCardBackImage())));
            l.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            panelPozo.add(l, BorderLayout.CENTER);
            if (pozo.size() > 1) {
                JLabel c = new JLabel("+" + (pozo.size() - 1) + " cartas");
                c.setForeground(Color.WHITE); c.setHorizontalAlignment(SwingConstants.CENTER);
                panelPozo.add(c, BorderLayout.SOUTH);
            }
        }
        panelPozo.revalidate(); panelPozo.repaint();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            Interfaz ui = new Interfaz();
            ui.setVisible(true);
        });
    }
}
