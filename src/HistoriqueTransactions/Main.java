package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("################################# UTILISATION DE TREESET #################################\n");

        System.out.println("--------------- Génération des données ---------------"); //################################
        long debut = System.nanoTime();
        HistoriqueTreeSet historique = GenerateurDonnees.generer();
        long fin = System.nanoTime();
        System.out.println("Temps de génération des données : " + (fin-debut)*1e-6 + " ms.");
        System.out.println("Temps d'ajout d'une transaction : " + ((fin-debut)*1e-6)/10000 + " ms.");

        System.out.println("Les données ont été générées.\n");


        System.out.println("--------------- Test des méthodes ---------------"); //#####################################


        System.out.println("----- Test méthode comptage -----"); //------------------------------------------------

        //Calcul temps
        debut = System.nanoTime();
        historique.comptageType("Achat");
        fin = System.nanoTime();
        System.out.println("Temps de comptage de 10000 transactions : " + (fin-debut)*1e-6 + " ms.");

        //Affichage
        System.out.println("Nombre de transaction type Achat : " + historique.comptageType("Achat"));



        System.out.println("\n----- Test méthode recherche par id -----"); //----------------------------------------------------

        //Calcul temps
        debut = System.nanoTime();
        for (int i = 1; i <= 10000; i++) {
            try {
                historique.rechercheId("ID_" + i);
            } catch (HistoriqueException e) {
                System.err.println("ERREUR : " + e.getMessage());
            }
        }
        fin = System.nanoTime();
        System.out.println("Temps de 10 000 rechercheId : " + (fin-debut)*1e-6 + " ms.");
        System.out.println("Temps d'une rechercheId : " + ((fin-debut)*1e-6)/10000 + " ms.");

        //Affichage
        try {
            System.out.println(historique.rechercheId("ID_8"));
        } catch (HistoriqueException e){
            System.err.println("ERREUR : " + e.getMessage());
        }




        System.out.println("------ Test méthode parcours chronologique ------"); //-------------------------------------

        //Calcul temps
        debut = System.nanoTime();
        LocalDate dateD = LocalDate.now();
        LocalDate dateF = LocalDate.now().plusDays(10000);
        historique.parcoursChronologique(dateD, dateF);
        fin = System.nanoTime();
        System.out.println("Temps pour parcourir 10000 transactions : " + (fin-debut)*1e-6 + " ms.");

        //Affichage
        dateD = LocalDate.of(2026, 7, 3);
        dateF = LocalDate.of(2026, 7, 6);
        System.out.println(historique.parcoursChronologique(dateD, dateF));




        System.out.println("\n----- Test méthode suppression transaction -----"); //------------------------------------

        //Calcul temps
        debut = System.nanoTime();
        for (int i = 1; i <= 10000; i++) {
            try {
                historique.annulerTransaction("ID_" + i);
            } catch (HistoriqueException e) {
                System.err.println("ERREUR : " + e.getMessage());
            }
        }
        fin = System.nanoTime();
        System.out.println("Temps de suppression de 10000 transaction : " + (fin-debut)*1e-6 + " ms.");
        System.out.println("Temps de suppression d'une transaction : " + ((fin-debut)*1e-6)/10000 + " ms.");

//        //Affichage
//        try {
//            System.out.println(historique.rechercheId("ID_8"));
//            historique.annulerTransaction("ID_8");
//            System.out.println(historique.rechercheId("ID_8"));
//        } catch(HistoriqueException e){
//            System.err.println("ERREUR : " + e.getMessage());
//        }







    }
}
