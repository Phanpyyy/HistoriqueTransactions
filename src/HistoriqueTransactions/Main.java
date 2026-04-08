package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("################################# UTILISATION DE TREESET #################################\n");
        System.out.println("--------------- Génération des données ---------------");
        HistoriqueTreeSet historique = GenerateurDonnees.generer();
        System.out.println("Les données ont été générées.\n");


        System.out.println("--------------- Test des méthodes ---------------");
        System.out.println("Test méthode comptage : ");
        System.out.println("Nombre de transaction type Achat : " + historique.comptageType("Achat"));

        System.out.println("\nTest méthode recherche par id : ");
        try {
            System.out.println(historique.rechercheId("ID_8"));
        } catch (HistoriqueException e){
            System.err.println("ERREUR : " + e.getMessage());
        }

        System.out.println("Test méthode parcours chronologique : ");
        LocalDate dateD = LocalDate.of(2026, 7, 3);
        LocalDate dateF = LocalDate.of(2026, 7, 6);
        System.out.println(historique.parcoursChronologique(dateD, dateF));


        System.out.println("\nTest méthode suppression transaction : ");
        try {
            System.out.println(historique.rechercheId("ID_7"));
            historique.annulerTransaction("ID_7");
            System.out.println(historique.rechercheId("ID_7"));
        } catch(HistoriqueException e){
            System.err.println("ERREUR : " + e.getMessage());
        }

        System.out.println("################################# TEST : HISTORIQUE TREEMAP #################################\n");

        // 1. GÉNÉRATION
        System.out.println("--- 1. Remplissage du TreeMap (10 000 transactions) ---");
        // On récupère les données générées par ta classe GenerateurDonnees
        HistoriqueTreeMap monHistorique = GenerateurDonnees.genererMap();
        System.out.println("Génération terminée.\n");

        // 2. RECHERCHE (Le point fort du TreeMap)
        System.out.println("--- 2. Test de la Recherche Directe (O(log n)) ---");
        String idTest = "ID_5555";
        try {
            long debut = System.nanoTime();
            Transaction t = monHistorique.rechercheId(idTest);
            long fin = System.nanoTime();

            System.out.println("Transaction trouvée : " + t);
            System.out.println("Temps de recherche : " + (fin - debut) + " ns.");
        } catch (HistoriqueException e) {
            System.err.println("Erreur : " + e.getMessage());
        }

        // 3. PARCOURS CHRONOLOGIQUE (Filtre + Tri)
        System.out.println("\n--- 3. Test du Parcours Chronologique ---");
        LocalDate debutFiltre = LocalDate.now().plusDays(100);
        LocalDate finFiltre = LocalDate.now().plusDays(105);

        System.out.println("Recherche entre " + debutFiltre + " et " + finFiltre + "...");
        ArrayList<Transaction> resultat = monHistorique.parcoursChronologique(debutFiltre, finFiltre);

        for (Transaction t : resultat) {
            System.out.println(" -> [" + t.getDate() + "] " + t.getId() + " : " + t.getMontant() + "€");
        }

        // 4. SUPPRESSION
        System.out.println("\n--- 4. Test de Suppression ---");
        String idADel = "ID_10";
        try {
            System.out.println("Suppression de " + idADel + "...");
            monHistorique.annulerTransaction(idADel);

            System.out.println("Vérification : on tente de la rechercher à nouveau...");
            monHistorique.rechercheId(idADel);
        } catch (HistoriqueException e) {
            System.out.println("Confirmation de sécurité : " + e.getMessage());
        }

        System.out.println("\n################################# FIN DES TESTS #################################");
    }
}