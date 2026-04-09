package HistoriqueTransactions;

import java.time.LocalDate;

public class GenerateurDonnees {

    //Génération des données avec TreeSet ------------------------------------------------------------------------------
    public static IHistorique generer(int taille, IHistorique h){

        LocalDate now = LocalDate.now();

        for (int i = 1; i <= taille; i++) {

            LocalDate date = now.plusDays(i);
            String type = (i % 2 == 0) ? "Achat" : "Vente";
            double montant = (i % 500) + 10;

            Transaction t = new Transaction("ID_" + i, date, type, montant);

            try {
                h.addTransaction(t);
            } catch (HistoriqueException e){
                System.err.println("ERREUR : " + e.getMessage());
            }
        }
        return h;
    }






}
