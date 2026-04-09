package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {

        //##################################################################################################################
        //############################################## TREESET ###########################################################
        //##################################################################################################################

        int taille = 10000;

        System.out.println("################################# UTILISATION DE TREESET #################################\n");

        System.out.println("--------------- Génération des données ---------------"); //################################
        IHistorique historiqueTreeSet = Benchmark.donnees(taille, new HistoriqueTreeSet());
        System.out.println("Les données ont été générées.\n");


        System.out.println("--------------- Test des méthodes ---------------"); //#####################################


        System.out.println("----- Test méthode comptage -----"); //------------------------------------------------
        //Calcul temps
        Benchmark.tempsComptage(historiqueTreeSet, "Achat", taille);
        //Affichage
        System.out.println("Nombre de transaction type Achat : " + historiqueTreeSet.comptageType("Achat"));



        System.out.println("\n----- Test méthode recherche par id -----"); //----------------------------------------------------

        //Calcul temps
        Benchmark.tempsRechercheId(historiqueTreeSet, taille);
        //Affichage
        try {
            System.out.println(historiqueTreeSet.rechercheId("ID_8"));
        } catch (HistoriqueException e){
            System.err.println("ERREUR : " + e.getMessage());
        }



        System.out.println("------ Test méthode parcours chronologique ------"); //-------------------------------------

        //Calcul temps
        Benchmark.tempsParcoursChronologique(historiqueTreeSet, taille);
        //Affichage
        LocalDate dateD = LocalDate.of(2026, 7, 3);
        LocalDate dateF = LocalDate.of(2026, 7, 6);
        System.out.println(historiqueTreeSet.parcoursChronologique(dateD, dateF));



        System.out.println("\n----- Test méthode suppression transaction -----"); //------------------------------------

        //Calcul temps
        Benchmark.tempsAnnulerTransaction(historiqueTreeSet, taille-1);
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

                IHistorique h = GenerateurDonnees.generer(n, new HistoriqueTreeSet());

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

                IHistorique h = GenerateurDonnees.generer(n, new HistoriqueTreeSet());

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

                IHistorique h = GenerateurDonnees.generer(n, new HistoriqueTreeSet());

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


        System.out.println("\n\n################################# TEST : HISTORIQUE TREEMAP #################################\n");

        System.out.println("--------------- Génération des données ---------------"); //################################
        IHistorique historiqueTreeMap = Benchmark.donnees(taille, new HistoriqueTreeMap());
        System.out.println("Les données ont été générées.\n");


        System.out.println("--------------- Test des méthodes ---------------"); //#####################################


        System.out.println("----- Test méthode comptage -----"); //------------------------------------------------
        //Calcul temps
        Benchmark.tempsComptage(historiqueTreeMap, "Achat", taille);
        //Affichage
        System.out.println("Nombre de transaction type Achat : " + historiqueTreeMap.comptageType("Achat"));



        System.out.println("\n----- Test méthode recherche par id -----"); //----------------------------------------------------

        //Calcul temps
        Benchmark.tempsRechercheId(historiqueTreeMap, taille);
        //Affichage
        try {
            System.out.println(historiqueTreeMap.rechercheId("ID_8"));
        } catch (HistoriqueException e){
            System.err.println("ERREUR : " + e.getMessage());
        }



        System.out.println("------ Test méthode parcours chronologique ------"); //-------------------------------------

        //Calcul temps
        Benchmark.tempsParcoursChronologique(historiqueTreeMap, taille);
        //Affichage
        dateD = LocalDate.of(2026, 7, 3);
        dateF = LocalDate.of(2026, 7, 6);
        System.out.println(historiqueTreeMap.parcoursChronologique(dateD, dateF));



        System.out.println("\n----- Test méthode suppression transaction -----"); //------------------------------------

        //Calcul temps
        Benchmark.tempsAnnulerTransaction(historiqueTreeMap, taille-1);
        //Affichage
//        try {
//            System.out.println(historique.rechercheId("ID_" + taille));
//            historique.annulerTransaction("ID_" + taille);
//            System.out.println(historique.rechercheId("ID_" + taille));
//        } catch(HistoriqueException e){
//            System.err.println("ERREUR : " + e.getMessage());
//        }



        //############################################### SCENARIOS ####################################################

        //Scénario trading (beaucoup d'ajouts)
        System.out.println("\n###################### SCENARIO TRADING #####################");
        for (int n : tailles) {
            System.out.println("\n########### TAILLE N = " + n + " ############");
            long duree = 0;
            for (int r = 1; r <= repetitions; r++) {
                System.out.println("\n--- Répétition n°" + r + " ---");

                IHistorique h = GenerateurDonnees.generer(n, new HistoriqueTreeMap());

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

                IHistorique h = GenerateurDonnees.generer(n, new HistoriqueTreeMap());

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

                IHistorique h = GenerateurDonnees.generer(n, new HistoriqueTreeMap());

                long debut = System.nanoTime();
                Benchmark.scenario(h, 1000, 25, 10, 30, 25, 10);
                long fin = System.nanoTime();
                System.out.println("Temps total du scénario : " + (fin-debut) * 1e-6 + " ms");
                duree = duree + (fin-debut);
            }

            System.out.println("\nTemps moyen du scénario (taille : " + n + ") : " + (duree * 1e-6)/repetitions + " ms");

        }





























//        // 1. GÉNÉRATION
//        System.out.println("--- 1. Remplissage du TreeMap (10 000 transactions) ---");
//        // On récupère les données générées par ta classe GenerateurDonnees
//        IHistorique HistoriqueTreeMap = Benchmark.donneesMap(10000);
//        System.out.println("Génération terminée.\n");
//
//        // 2. RECHERCHE (Le point fort du TreeMap)
//        System.out.println("--- 2. Test de la Recherche Directe (O(log n)) ---");
//        String idTest = "ID_5555";
//        try {
//            long debut = System.nanoTime();
//            Transaction t = HistoriqueTreeMap.rechercheId(idTest);
//            long fin = System.nanoTime();
//
//            System.out.println("Transaction trouvée : " + t);
//            System.out.println("Temps de recherche : " + (fin - debut) + " ns.");
//        } catch (HistoriqueException e) {
//            System.err.println("Erreur : " + e.getMessage());
//        }
//
//        // 3. PARCOURS CHRONOLOGIQUE (Filtre + Tri)
//        System.out.println("\n--- 3. Test du Parcours Chronologique ---");
//        LocalDate debutFiltre = LocalDate.now().plusDays(100);
//        LocalDate finFiltre = LocalDate.now().plusDays(105);
//
//        System.out.println("Recherche entre " + debutFiltre + " et " + finFiltre + "...");
//        ArrayList<Transaction> resultat = HistoriqueTreeMap.parcoursChronologique(debutFiltre, finFiltre);
//
//
//        for (Transaction t : resultat) {
//            System.out.println(" -> [" + t.getDate() + "] " + t.getId() + " : " + t.getMontant() + "€");
//        }
//
//        // 4. SUPPRESSION
//        System.out.println("\n--- 4. Test de Suppression ---");
//        String idADel = "ID_10";
//        try {
//            System.out.println("Suppression de " + idADel + "...");
//            HistoriqueTreeMap.annulerTransaction(idADel);
//
//            System.out.println("Vérification : on tente de la rechercher à nouveau...");
//            HistoriqueTreeMap.rechercheId(idADel);
//        } catch (HistoriqueException e) {
//            System.out.println("Confirmation de sécurité : " + e.getMessage());
//        }
//

        System.out.println("\n################################# FIN DES TESTS #################################");
    }
}