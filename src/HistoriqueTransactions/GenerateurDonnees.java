package HistoriqueTransactions;

import java.time.LocalDate;

public class GenerateurDonnees {

    //Génération des données avec TreeSet ------------------------------------------------------------------------------
    public static HistoriqueTreeSet generer(int taille){

        HistoriqueTreeSet historique = new HistoriqueTreeSet();
        LocalDate now = LocalDate.now();

        for (int i = 1; i <= taille; i++) {

            LocalDate date = now.plusDays(i);
            String type = (i % 2 == 0) ? "Achat" : "Vente";
            double montant = (i % 500) + 10;

            Transaction t = new Transaction("ID_" + i, date, type, montant);

            try {
                historique.addTransaction(t);
            } catch (HistoriqueException e){
                System.err.println("ERREUR : " + e.getMessage());
            }
        }
        return historique;
    }

    //Génération des données avec TreeMap
    public static HistoriqueTreeMap genererMap(){

        HistoriqueTreeMap historique = new HistoriqueTreeMap();
        LocalDate now = LocalDate.now();

        // Calcul temps de l'action d'ajout
        long debut = System.nanoTime();

        for (int i = 1; i <= 10000; i++) {
            // On crée les mêmes données pour que le test soit juste
            LocalDate date = now.plusDays(i);
            String type = (i % 2 == 0) ? "Achat" : "Vente";
            double montant = (i % 500) + 10;
            String id = "ID_" + i;

            Transaction t = new Transaction(id, date, type, montant);

            try {
                historique.addTransaction(t);
            } catch (HistoriqueException e){
                System.err.println("ERREUR : " + e.getMessage());
            }
        }

        long fin = System.nanoTime();
        double tempsTotal = (fin - debut) * 1e-6;

        System.out.println("--- RESULTATS TREEMAP ---");
        System.out.println("Temps de génération total : " + tempsTotal + " ms.");
        System.out.println("Temps moyen par ajout : " + (tempsTotal / 10000) + " ms.");

        return historique;
    }




}
