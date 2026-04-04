package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        int taille = 10000;

        System.out.println("################################# UTILISATION DE TREESET #################################\n");

        System.out.println("--------------- Génération des données ---------------"); //################################
        HistoriqueTreeSet historique = Benchmark.donneesTreeSet(10000);
        System.out.println("Les données ont été générées.\n");


        System.out.println("--------------- Test des méthodes ---------------"); //#####################################


        System.out.println("----- Test méthode comptage -----"); //------------------------------------------------
        //Calcul temps
        Benchmark.tempsComptage(historique, "Achat", taille);
        //Affichage
        System.out.println("Nombre de transaction type Achat : " + historique.comptageType("Achat"));



        System.out.println("\n----- Test méthode recherche par id -----"); //----------------------------------------------------

        //Calcul temps
        Benchmark.tempsRechercheId(historique, taille);
        //Affichage
        try {
            System.out.println(historique.rechercheId("ID_8"));
        } catch (HistoriqueException e){
            System.err.println("ERREUR : " + e.getMessage());
        }



        System.out.println("------ Test méthode parcours chronologique ------"); //-------------------------------------

        //Calcul temps
        Benchmark.tempsParcoursChronologique(historique, taille);
        //Affichage
        LocalDate dateD = LocalDate.of(2026, 7, 3);
        LocalDate dateF = LocalDate.of(2026, 7, 6);
        System.out.println(historique.parcoursChronologique(dateD, dateF));



        System.out.println("\n----- Test méthode suppression transaction -----"); //------------------------------------

        //Calcul temps
        Benchmark.tempsAnnulerTransaction(historique, taille-1);
        //Affichage
        try {
            System.out.println(historique.rechercheId("ID_" + taille));
            historique.annulerTransaction("ID_" + taille);
            System.out.println(historique.rechercheId("ID_" + taille));
        } catch(HistoriqueException e){
            System.err.println("ERREUR : " + e.getMessage());
        }







    }
}
