package HistoriqueTransactions;

import java.time.LocalDate;
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







    }
}
