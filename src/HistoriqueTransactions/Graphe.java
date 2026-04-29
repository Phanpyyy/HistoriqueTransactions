package HistoriqueTransactions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe permettant de générer un graphique comparatif entre TreeSet et TreeMap.
 * Adaptée pour afficher soit des données temporelles, soit des données mémoires.
 */
public class Graphe extends JFrame {
    private ArrayList<Double> donneesSet;
    private ArrayList<Double> donneesMap;
    private ArrayList<Integer> tailles;
    private String nomScenario;
    private String uniteAxeY; // Dynamique : permet d'afficher "Temps (ms)" ou "Mémoire (Ko)"

    public Graphe(ArrayList<Double> donneesSet, ArrayList<Double> donneesMap, ArrayList<Integer> tailles, String nomScenario, String uniteAxeY) {
        this.donneesSet = donneesSet;
        this.donneesMap = donneesMap;
        this.tailles = tailles;
        this.nomScenario = nomScenario;
        this.uniteAxeY = uniteAxeY;

        setTitle("Benchmark : " + nomScenario);
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE au lieu de EXIT pour ne pas fermer toute l'appli
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        // Lissage des lignes (Anti-aliasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = 80; // Un peu plus de place pour les textes d'axes
        int width = getWidth() - 2 * padding;
        int height = getHeight() - 2 * padding;

        // Calcul des valeurs max pour l'échelle
        double maxVal = Math.max(Collections.max(donneesSet), Collections.max(donneesMap));
        int maxN = Collections.max(tailles);

        // --- 1. DESSIN DES AXES ---
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        // Axe Y
        g2.drawLine(padding, getHeight() - padding, padding, padding);
        // Axe X
        g2.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

        // --- 2. TITRES ET LABELS ---
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        // Label Axe X
        g2.drawString("N (Nombre d'éléments)", getWidth() / 2 - 80, getHeight() - padding + 50);

        // Label Axe Y (avec rotation)
        g2.rotate(-Math.PI / 2);
        g2.drawString(uniteAxeY, -getHeight() / 2 - 50, padding - 50);
        g2.rotate(Math.PI / 2);

        // Titre principal
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        int titreWidth = g2.getFontMetrics().stringWidth(nomScenario);
        g2.drawString(nomScenario, getWidth() / 2 - titreWidth / 2, padding - 30);

        // --- 3. GRADUATIONS ET GRILLE ---
        g2.setFont(new Font("Arial", Font.PLAIN, 10));

        // Graduations Axe X
        for (int i = 0; i < tailles.size(); i++) {
            int x = padding + (tailles.get(i) * width / maxN);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(x, getHeight() - padding, x, padding); // Ligne de grille
            g2.setColor(Color.BLACK);
            g2.drawLine(x, getHeight() - padding, x, getHeight() - padding + 5);
            g2.drawString(String.valueOf(tailles.get(i)), x - 15, getHeight() - padding + 20);
        }

        // Graduations Axe Y
        int nbGradY = 10;
        for (int i = 0; i <= nbGradY; i++) {
            double val = (maxVal / nbGradY) * i;
            int y = (getHeight() - padding) - (int) (val * height / maxVal);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(padding, y, getWidth() - padding, y); // Ligne de grille
            g2.setColor(Color.BLACK);
            g2.drawLine(padding - 5, y, padding, y);
            g2.drawString(String.format("%.1f", val), padding - 55, y + 5);
        }

        // --- 4. DESSIN DES COURBES ---
        dessinerCourbe(g2, donneesSet, Color.RED, padding, width, height, maxVal, maxN);
        dessinerCourbe(g2, donneesMap, Color.BLUE, padding, width, height, maxVal, maxN);

        // --- 5. LÉGENDE ---
        int legX = padding + 20;
        int legY = padding + 10;
        g2.setColor(new Color(255, 255, 255, 200)); // Fond semi-transparent
        g2.fillRect(legX, legY, 110, 60);
        g2.setColor(Color.BLACK);
        g2.drawRect(legX, legY, 110, 60);

        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.RED);
        g2.drawLine(legX + 10, legY + 20, legX + 30, legY + 20);
        g2.drawString("TreeSet", legX + 35, legY + 25);

        g2.setColor(Color.BLUE);
        g2.drawLine(legX + 10, legY + 40, legX + 30, legY + 40);
        g2.drawString("TreeMap", legX + 35, legY + 45);
    }

    private void dessinerCourbe(Graphics2D g2, ArrayList<Double> data, Color couleur, int pad, int w, int h, double maxV, int maxN) {
        g2.setColor(couleur);
        g2.setStroke(new BasicStroke(2.5f));

        for (int i = 0; i < data.size() - 1; i++) {
            int x1 = pad + (tailles.get(i) * w / maxN);
            int y1 = (getHeight() - pad) - (int) (data.get(i) * h / maxV);
            int x2 = pad + (tailles.get(i + 1) * w / maxN);
            int y2 = (getHeight() - pad) - (int) (data.get(i + 1) * h / maxV);

            g2.drawLine(x1, y1, x2, y2);
            g2.fillOval(x1 - 4, y1 - 4, 8, 8); // Point de donnée
        }

        // Dessin du dernier point
        int lastIdx = data.size() - 1;
        int lastX = pad + (tailles.get(lastIdx) * w / maxN);
        int lastY = (getHeight() - pad) - (int) (data.get(lastIdx) * h / maxV);
        g2.fillOval(lastX - 4, lastY - 4, 8, 8);
    }
}