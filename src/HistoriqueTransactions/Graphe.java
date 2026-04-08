package HistoriqueTransactions;

import javax.swing.*;
        import java.awt.*;
        import java.util.ArrayList;

// Cette classe crée une fenêtre Java (JFrame) pour dessiner
public class Graphe extends JFrame {

    // On stocke les données pour dessiner
    private final ArrayList<Long> tempsSet;
    private final ArrayList<Long> tempsMap;
    private final ArrayList<Integer> tailles;

    // Constructeur : on lui donne les résultats du Benchmark
    public Graphe(ArrayList<Long> tempsSet, ArrayList<Long> tempsMap, ArrayList<Integer> tailles) {
        this.tempsSet = tempsSet;
        this.tempsMap = tempsMap;
        this.tailles = tailles;

        setTitle("Benchmark : TreeSet vs TreeMap");
        setSize(800, 600); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
    }

    // C'EST ICI QUE LA MAGIE OPÈRE : Cette méthode dessine dans la fenêtre
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Efface l'écran avant de dessiner
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int largeur = getWidth();
        int hauteur = getHeight();
        int marge = 50;

        // 1. DESSINER LES AXES (en noir)
        g2d.setColor(Color.BLACK);
        g2d.drawLine(marge, hauteur - marge, marge, marge); // Axe Y (Temps)
        g2d.drawLine(marge, hauteur - marge, largeur - marge, hauteur - marge); // Axe X (Taille N)

        // Légendes simples
        g2d.drawString("Temps (ms)", marge - 40, marge - 10);
        g2d.drawString("Taille (N)", largeur - marge, hauteur - marge + 20);

        // 2. TROUVER LES VALEURS MAXIMALES POUR L'ÉCHELLE
        long maxTemps = 0;
        for (long t : tempsSet) if (t > maxTemps) maxTemps = t;
        for (long t : tempsMap) if (t > maxTemps) maxTemps = t;
        if (maxTemps == 0) maxTemps = 1; // Évite la division par zéro

        int maxN = tailles.getLast();

        // 3. DESSINER LA COURBE DU TREESET (en Rouge)
        g2d.setColor(Color.RED);
        dessinerCourbe(g2d, tempsSet, tailles, maxTemps, maxN, marge, largeur, hauteur);
        g2d.drawString("TreeSet", largeur - marge - 80, marge + 20);

        // 4. DESSINER LA COURBE DU TREEMAP (en Bleu)
        g2d.setColor(Color.BLUE);
        dessinerCourbe(g2d, tempsMap, tailles, maxTemps, maxN, marge, largeur, hauteur);
        g2d.drawString("TreeMap", largeur - marge - 80, marge + 40);
    }

    // Méthode utilitaire pour calculer les points et tirer les lignes
    private void dessinerCourbe(Graphics2D g2d, ArrayList<Long> temps, ArrayList<Integer> tailles, long maxTemps, int maxN, int marge, int larg, int haut) {
        int xPrec = -1, yPrec = -1;

        for (int i = 0; i < tailles.size(); i++) {
            // Calcul des coordonnées X et Y en PIXELS sur l'écran
            int x = marge + (tailles.get(i) * (larg - 2 * marge) / maxN);
            int y = (haut - marge) - (int) (temps.get(i) * (haut - 2 * marge) / maxTemps);

            // Dessine un petit point
            g2d.fillOval(x - 3, y - 3, 6, 6);

            // Relie au point précédent
            if (xPrec != -1) {
                g2d.drawLine(xPrec, yPrec, x, y);
            }
            xPrec = x;
            yPrec = y;
        }
    }
}