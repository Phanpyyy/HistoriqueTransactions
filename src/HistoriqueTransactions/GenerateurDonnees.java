package HistoriqueTransactions;

import java.time.LocalDate;

public class GenerateurDonnees {

    //Génération des données avec TreeSet ------------------------------------------------------------------------------
    public static HistoriqueTreeSet generer(){

        HistoriqueTreeSet historique = new HistoriqueTreeSet();
        LocalDate now = LocalDate.now();

        //Calcul temps de l'action d'ajout
        long debut = System.nanoTime();

        for (int i = 1; i <= 10000; i++) {

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
        long fin = System.nanoTime();
        System.out.println("Temps de génération des données : " + (fin-debut)*1e-6 + " ms.");
        System.out.println("Temps d'ajout d'une transaction : " + ((fin-debut)*1e-6)/10000 + " ms.");

        return historique;
    }





}
