package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("################################# UTILISATION DE TREESET #################################\n");
        System.out.println("--------------- Génération des données ---------------");
        long debut = System.nanoTime();
        HistoriqueTreeSet historique = GenerateurDonnees.generer();
        long fin = System.nanoTime();
        System.out.println("Temps de génération des données : " + (fin-debut)*1e-6 + " ms.");
        System.out.println("Les données ont été générées.\n");


        System.out.println("--------------- Test des méthodes ---------------");
        System.out.println("Test méthode comptage : ");
        debut = System.nanoTime();
        System.out.println("Nombre de transaction type Achat : " + historique.comptageType("Achat"));
        fin = System.nanoTime();
        System.out.println("Temps de comptage par type : " + (fin-debut)*1e-6 + " ms.\n");

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
