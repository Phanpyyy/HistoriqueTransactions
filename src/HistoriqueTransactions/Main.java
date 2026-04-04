package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {

        //##################################################################################################################
        //############################################## TREESET ###########################################################
        //##################################################################################################################

        int taille = 10000;

        System.out.println("################################# UTILISATION DE TREESET #################################\n");

        System.out.println("--------------- Génération des données ---------------"); //################################
        HistoriqueTreeSet historique = Benchmark.donneesTreeSet(taille);
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
//        try {
//            System.out.println(historique.rechercheId("ID_" + taille));
//            historique.annulerTransaction("ID_" + taille);
//            System.out.println(historique.rechercheId("ID_" + taille));
//        } catch(HistoriqueException e){
//            System.err.println("ERREUR : " + e.getMessage());
//        }



        //############################################### SCENARIOS ####################################################
        int[] tailles = {1000, 5000, 10000};
        int repetitions = 5;

        //Scénario trading (beaucoup d'ajouts)
        System.out.println("\n###################### SCENARIO TRADING #####################");
        for (int n : tailles) {
            System.out.println("\n########### TAILLE N = " + n + " ############");
            long duree = 0;
            for (int r = 1; r <= repetitions; r++) {
                System.out.println("\n--- Répétition n°" + r + " ---");

                HistoriqueTreeSet h = GenerateurDonnees.generer(n);

                long debut = System.nanoTime();
                Benchmark.scenario(h, 1000, 70, 2, 15, 10, 3);
                long fin = System.nanoTime();
                System.out.println("Temps total du scénario : " + (fin-debut) * 1e-6 + " ms");
                duree = duree + (fin-debut);
            }

            System.out.println("\nTemps moyen du scénario (taille : " + n + ") : " + (duree * 1e-6)/repetitions + " ms");

        }

        //Scénario Audit (beaucoup de vérifications)
        System.out.println("\n###################### SCENARIO AUDIT #####################");
        for (int n : tailles) {
            System.out.println("\n########### TAILLE N = " + n + " ############");
            long duree = 0;
            for (int r = 1; r <= repetitions; r++) {
                System.out.println("\n--- Répétition n°" + r + " ---");

                HistoriqueTreeSet h = GenerateurDonnees.generer(n);

                long debut = System.nanoTime();
                Benchmark.scenario(h, 1000, 5, 1, 20, 34, 40);
                long fin = System.nanoTime();
                System.out.println("Temps total du scénario : " + (fin-debut) * 1e-6 + " ms");
                duree = duree + (fin-debut);
            }

            System.out.println("\nTemps moyen du scénario (taille : " + n + ") : " + (duree * 1e-6)/repetitions + " ms");

        }

        //Scénario e-commerce (consulte les commandes)
        System.out.println("\n###################### SCENARIO E-COMMERCE #####################");
        for (int n : tailles) {
            System.out.println("\n########### TAILLE N = " + n + " ############");
            long duree = 0;
            for (int r = 1; r <= repetitions; r++) {
                System.out.println("\n--- Répétition n°" + r + " ---");

                HistoriqueTreeSet h = GenerateurDonnees.generer(n);

                long debut = System.nanoTime();
                Benchmark.scenario(h, 1000, 25, 10, 30, 25, 10);
                long fin = System.nanoTime();
                System.out.println("Temps total du scénario : " + (fin-debut) * 1e-6 + " ms");
                duree = duree + (fin-debut);
            }

            System.out.println("\nTemps moyen du scénario (taille : " + n + ") : " + (duree * 1e-6)/repetitions + " ms");

        }


        //##################################################################################################################
        //############################################## TREEMAP ###########################################################
        //##################################################################################################################

    }
}
