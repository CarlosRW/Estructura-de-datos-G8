package com.sandwichguy;

import static com.sandwichguy.Carta.Palo.CORAZONES;
import static com.sandwichguy.Carta.Palo.DIAMANTES;
import static com.sandwichguy.Carta.Palo.PICAS;
import static com.sandwichguy.Carta.Palo.TREBOLES;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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
        buttons.add(crearBoton("ORDENAR", e -> ordenarMano()));
        buttons.add(crearBoton("VALIDAR", e -> validarJugada()));
        buttons.add(crearBoton("GUARDAR", e -> guardarJuego()));
        buttons.add(crearBoton("CARGAR", e -> cargarJuego()));

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
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(100, 160, 210));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(70, 130, 180));
            }
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
                Color.YELLOW);
    }

    private void cargarImagenes() {
        // Mazo (mazo.jpg)
        ImageIcon mazoImg = loadAndScale("/images/mazo.jpg");
        if (mazoImg == null) {
            mazoImg = new ImageIcon(createCardBackImage());
        }
        cardImages.put("mazo", mazoImg);

        // Reverso (00.png)
        ImageIcon back = loadAndScale("/images/mano/00.png");
        if (back == null) {
            back = new ImageIcon(createCardBackImage());
        }
        cardImages.put("reverso", back);

        for (Carta.Palo p : Carta.Palo.values()) {
            for (Carta.Valor v : Carta.Valor.values()) {
                String key = p.name() + "_" + v.name();
                ImageIcon icon = null;

                // Mapeo especificado por usuario:
                // Orden: A, 2, 3 ... K.
                // Dentro de cada valor: Corazones, Picas, Diamantes, Treboles.
                // Indices 00-03: Ases. 04-07: Doses...
                int suitIndex = 0;
                switch (p) {
                    case CORAZONES:
                        suitIndex = 0;
                        break;
                    case PICAS:
                        suitIndex = 1;
                        break;
                    case DIAMANTES:
                        suitIndex = 2;
                        break;
                    case TREBOLES:
                        suitIndex = 3;
                        break;
                }

                int rankIndex = v.ordinal(); // 0(As)..12(K) en Carta.Valor
                int deckIndex = (rankIndex * 4) + suitIndex;

                icon = loadFromBarajaIndex(deckIndex);

                if (icon == null) {
                    icon = new ImageIcon(createGenericCardImage(p, v));
                }
                cardImages.put(key, icon);
            }
        }
    }

    private ImageIcon loadFromBarajaIndex(int index) {
        String jpg = String.format("/images/baraja/%02d.jpg", index);
        ImageIcon icon = loadAndScale(jpg);
        if (icon != null) {
            return icon;
        }
        String png = String.format("/images/baraja/%02d.png", index);
        return loadAndScale(png);
    }

    private ImageIcon loadAndScale(String resourcePath) {
        try {
            java.net.URL url = getClass().getResource(resourcePath);
            if (url == null) {
                return null;
            }
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
        for (Carta c : barajadas) {
            mazo.push(c);
        }

        selectedCards = new boolean[0];
        actualizarInterfaz();
        statusLabel.setText("Cartas barajadas. 'REPARTIR MANO'.");
    }

    private void repartirMano() {
        if (mazo.estaVacio()) {
            JOptionPane.showMessageDialog(this, "No hay cartas en el mazo. BARAJAR primero.", "Mazo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        mano.limpiar();
        int n = Math.min(8, mazo.size());
        for (int i = 0; i < n; i++) {
            Carta c = mazo.pop();
            if (c != null) {
                mano.agregar(c);
            }
        }
        selectedCards = new boolean[mano.tamano()];
        actualizarInterfaz();
        statusLabel.setText("Selecciona 3 cartas para validar.");
    }

    private void ordenarMano() {
        if (mano.isEmpty()) {
            return;
        }
        mano.ordenar();
        // Reset selection because indices change
        selectedCards = new boolean[mano.size()];
        actualizarInterfaz();
        statusLabel.setText("Mano ordenada.");
    }

    private void guardarJuego() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Partida");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos XML", "xml"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".xml")) {
                file = new File(file.getParentFile(), file.getName() + ".xml");
            }
            try {
                GameStateXML.guardarPartida(file, caja, mazo, mano, pozo);
                JOptionPane.showMessageDialog(this, "Partida guardada exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void cargarJuego() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cargar Partida");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos XML", "xml"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                GameStateXML.cargarPartida(fileChooser.getSelectedFile(), caja, mazo, mano, pozo, cardImages);
                selectedCards = new boolean[mano.size()];
                actualizarInterfaz();
                chequearFinDeJuego();
                statusLabel.setText("Partida cargada.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void validarJugada() {
        if (mano.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay cartas en la mano.", "Mano vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener cartas seleccionadas
        int selCount = 0;
        List<Carta> seleccion = new ArrayList<>();

        List<Carta> manoCartas = mano.obtenerTodasLasCartas();
        if (selectedCards != null) {
            for (int i = 0; i < selectedCards.length; i++) {
                if (selectedCards[i] && i < manoCartas.size()) {
                    seleccion.add(manoCartas.get(i));
                    selCount++;
                }
            }
        }

        if (selCount != 3) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar exactamente 3 cartas.", "Selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear árbol de permutaciones
        PermutationTree tree = new PermutationTree(seleccion);
        mostrarOpcionesPermutacion(tree, seleccion);
    }

    private void mostrarOpcionesPermutacion(PermutationTree tree, List<Carta> originalSelection) {
        List<PermutationTree.Node> opciones = tree.getOpciones();

        JDialog dialog = new JDialog(this, "Selecciona una Opción", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(30, 30, 30));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(30, 30, 30));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JLabel header = new JLabel("Elige la combinación correcta:");
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(header);
        container.add(Box.createVerticalStrut(15));

        final PermutationTree.Node[] selectedNode = {null};

        for (PermutationTree.Node node : opciones) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            row.setBackground(new Color(50, 50, 50));
            row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(70, 70, 70)));
            row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Cartas visuales
            for (Carta c : node.getPermutacion()) {
                // Escalar pequeño para lista
                ImageIcon icon = c.getImagen();
                if (icon != null) {
                    Image img = icon.getImage().getScaledInstance(40, 60, Image.SCALE_SMOOTH);
                    JLabel imgLbl = new JLabel(new ImageIcon(img));
                    imgLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    row.add(imgLbl);
                }
            }

            // Texto info
            String infoText = node.isEsSandwichValido()
                    ? "<html><font color='#90EE90'><b>VÁLIDO</b></font> (Robar " + node.getCartasATomar() + ") - "
                    + node.getDescripcion() + "</html>"
                    : "<html><font color='#FF6347'>Inválido</font></html>";

            JLabel infoLbl = new JLabel(infoText);
            infoLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            infoLbl.setForeground(Color.WHITE);
            row.add(infoLbl);

            // Interaction
            row.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    row.setBackground(new Color(70, 70, 70));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    row.setBackground(new Color(50, 50, 50));
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedNode[0] = node;
                    dialog.dispose();
                }
            });

            container.add(row);
            container.add(Box.createVerticalStrut(5));
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(500, 400));
        dialog.add(scroll, BorderLayout.CENTER);

        // Botón cancelar
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.setBackground(new Color(200, 50, 50));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> dialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 30));
        btnPanel.add(cancelBtn);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (selectedNode[0] != null) {
            if (selectedNode[0].isEsSandwichValido()) {
                ejecutarJugada(originalSelection, selectedNode[0].getCartasATomar());
            } else {
                JOptionPane.showMessageDialog(this, "Opción inválida seleccionada.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ejecutarJugada(List<Carta> cartasJugadas, int cartasARobar) {
        // Descartar las 3 cartas
        for (Carta c : cartasJugadas) {
            mano.eliminar(c);
            pozo.agregar(c);
        }

        // Robar cartas
        int cartasRobadas = 0;
        int cartasNecesarias = cartasARobar;

        while (cartasRobadas < cartasNecesarias && !mazo.estaVacio() && mano.size() < 8) {
            Carta c = mazo.pop();
            if (c != null) {
                mano.agregar(c);
                cartasRobadas++;
            }
        }

        selectedCards = new boolean[mano.size()];
        actualizarInterfaz();

        String msg = "Jugada exitosa. Se robaron " + cartasRobadas + " cartas.";
        statusLabel.setText(msg);
        JOptionPane.showMessageDialog(this, msg);

        chequearFinDeJuego();
    }

    private void chequearFinDeJuego() {
        if (mazo.estaVacio() && mano.existeSandwichValido()) {
            // Si mazo vacío, ganamos.
            JOptionPane.showMessageDialog(this, "¡FELICIDADES! ¡El mazo está vacío! Has ganado.", "VICTORIA",
                    JOptionPane.INFORMATION_MESSAGE);
            bloquearJuego();
            return;
        }

        if (!mano.existeSandwichValido()) {
            // Si la mano no tiene ningún sándwich posible, es derrota.
            JOptionPane.showMessageDialog(this, "No hay sándwiches válidos en tu mano. Has perdido.", "DERROTA",
                    JOptionPane.ERROR_MESSAGE);
            bloquearJuego();
        }
    }

    private void bloquearJuego() {
        statusLabel.setText("JUEGO TERMINADO. Inicia una nueva partida.");
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
            JLabel l = new JLabel("Vacío");
            l.setForeground(Color.WHITE);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            panelMazo.add(l, BorderLayout.CENTER);
        } else {
            JLabel l = new JLabel(cardImages.getOrDefault("mazo", cardImages.get("reverso")));
            l.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panelMazo.add(l, BorderLayout.CENTER);
            if (mazo.size() > 1) {
                JLabel c = new JLabel("+" + (mazo.size() - 1) + " cartas");
                c.setForeground(Color.WHITE);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                panelMazo.add(c, BorderLayout.SOUTH);
            }
        }
        panelMazo.revalidate();
        panelMazo.repaint();
    }

    private void actualizarPanelMano() {
        panelMano.removeAll();
        if (mano.estaVacia()) {
            JLabel l = new JLabel("Sin cartas");
            l.setForeground(Color.WHITE);
            panelMano.add(l);
        } else {
            List<Carta> cartas = mano.obtenerTodasLasCartas();
            for (int i = 0; i < cartas.size(); i++) {
                Carta c = cartas.get(i);
                JLabel lbl = new JLabel(c.getImagen());
                lbl.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                final int idx = i;
                lbl.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (selectedCards != null && idx < selectedCards.length) {
                            selectedCards[idx] = !selectedCards[idx];
                            // Limitar a 3 seleccionadas
                            int count = 0;
                            int last = -1;
                            for (int k = 0; k < selectedCards.length; k++) {
                                if (selectedCards[k]) {
                                    count++;
                                    last = k;
                                }
                            }
                            if (count > 3 && last >= 0) {
                                selectedCards[last] = false;
                            }
                            actualizarInterfaz();
                        }
                    }
                });
                if (selectedCards != null && i < selectedCards.length && selectedCards[i]) {
                    lbl.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }
                panelMano.add(lbl);
            }
        }
        panelMano.revalidate();
        panelMano.repaint();
    }

    private void actualizarPanelPozo() {
        panelPozo.removeAll();
        if (pozo.isEmpty()) {
            JLabel l = new JLabel("Vacío");
            l.setForeground(Color.WHITE);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            panelPozo.add(l, BorderLayout.CENTER);
        } else {
            // Usar la imagen reverso
            ImageIcon img = cardImages.getOrDefault("mazo", cardImages.get("reverso"));

            JLabel l = new JLabel(img);
            l.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panelPozo.add(l, BorderLayout.CENTER);
            if (pozo.size() > 1) {
                JLabel c = new JLabel("+" + (pozo.size() - 1) + " cartas");
                c.setForeground(Color.WHITE);
                c.setHorizontalAlignment(SwingConstants.CENTER);
                panelPozo.add(c, BorderLayout.SOUTH);
            }
        }
        panelPozo.revalidate();
        panelPozo.repaint();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 14));
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            Interfaz ui = new Interfaz();
            ui.setVisible(true);
        });
    }
}
