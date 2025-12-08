package com.sandwichguy;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Grupo 8
 */
// Clase encargada de guardar y cargar el estado del juego en XML.
public class GameStateXML {

    public static void guardarPartida(File file, Caja caja, Mazo mazo, Mano mano, Pozo pozo) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("GameState");
        doc.appendChild(rootElement);

        rootElement.appendChild(crearElementoLista(doc, "Caja", caja.obtenerCartas()));
        rootElement.appendChild(crearElementoLista(doc, "Mazo", mazo.obtenerTodasLasCartas()));
        rootElement.appendChild(crearElementoLista(doc, "Mano", mano.obtenerTodasLasCartas()));
        rootElement.appendChild(crearElementoLista(doc, "Pozo", pozo.obtenerTodasLasCartas()));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);
    }

    private static Element crearElementoLista(Document doc, String nombre, List<Carta> cartas) {
        Element contenedor = doc.createElement(nombre);
        for (Carta c : cartas) {
            Element cartaNode = doc.createElement("Carta");
            cartaNode.setAttribute("palo", c.getPalo().name());
            cartaNode.setAttribute("valor", c.getValor().name());
            contenedor.appendChild(cartaNode);
        }
        return contenedor;
    }

    // Cargar estado de la partida
    public static void cargarPartida(File file, Caja caja, Mazo mazo, Mano mano, Pozo pozo,
            java.util.Map<String, ImageIcon> imagenes) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        // Limpiar estado actual
        caja.limpiar();
        mazo.limpiar();
        mano.limpiar();
        pozo.limpiar();

        cargarLista(doc, "Caja", caja, imagenes);
        cargarPila(doc, "Mazo", mazo, imagenes);
        cargarMano(doc, "Mano", mano, imagenes);
        cargarCola(doc, "Pozo", pozo, imagenes);
    }

    private static void cargarLista(Document doc, String tag, Caja caja, java.util.Map<String, ImageIcon> imagenes) {
        List<Carta> cartas = parsingCartas(doc, tag, imagenes);
        caja.agregarTodas(cartas);
    }

    private static void cargarPila(Document doc, String tag, Mazo mazo, java.util.Map<String, ImageIcon> imagenes) {

        List<Carta> cartas = parsingCartas(doc, tag, imagenes);
        for (int i = cartas.size() - 1; i >= 0; i--) {
            mazo.push(cartas.get(i));
        }
    }

    private static void cargarMano(Document doc, String tag, Mano mano, java.util.Map<String, ImageIcon> imagenes) {
        List<Carta> cartas = parsingCartas(doc, tag, imagenes);
        for (Carta c : cartas) {
            mano.agregar(c);
        }
    }

    private static void cargarCola(Document doc, String tag, Pozo pozo, java.util.Map<String, ImageIcon> imagenes) {
        List<Carta> cartas = parsingCartas(doc, tag, imagenes);
        for (Carta c : cartas) {
            pozo.agregar(c);
        }
    }

    private static List<Carta> parsingCartas(Document doc, String tag, java.util.Map<String, ImageIcon> imagenes) {
        List<Carta> lista = new LinkedList<>();
        NodeList nList = doc.getElementsByTagName(tag);
        if (nList.getLength() > 0) {
            Element container = (Element) nList.item(0);
            NodeList cartasNodes = container.getElementsByTagName("Carta");
            for (int i = 0; i < cartasNodes.getLength(); i++) {
                Element e = (Element) cartasNodes.item(i);
                String paloStr = e.getAttribute("palo");
                String valorStr = e.getAttribute("valor");

                Carta.Palo palo = Carta.Palo.valueOf(paloStr);
                Carta.Valor valor = Carta.Valor.valueOf(valorStr);

                // Recuperar imagen
                String key = palo.name() + "_" + valor.name();
                ImageIcon img = imagenes.getOrDefault(key, imagenes.get("reverso"));

                lista.add(new Carta(palo, valor, img));
            }
        }
        return lista;
    }
}
