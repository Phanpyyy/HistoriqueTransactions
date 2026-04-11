package HistoriqueTransactions;

import javax.swing.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> nomOperation = new ArrayList<String>(Arrays.asList(
                "Génération des données",
                "Ajout d'une transaction",
                "Comptage par type",
                "Recherche par identifiant",
                "Parcours chronologique",
                "Suppression d'une transaction"
        ));

        //##################################################################################################################
        //############################################## TREESET ###########################################################
        //##################################################################################################################
        int taille = 10000;
        ArrayList<Double> calculTempsTreeSet = new ArrayList<Double>();
        ArrayList<Double> calculMemoireTreeSet = new ArrayList<Double>();

        System.out.println("################################# UTILISATION DE TREESET #################################\n");

        System.out.println("--------------- Génération des données ---------------"); //################################
        ResultatBenchmark res = Benchmark.donnees(taille, new HistoriqueTreeSet());
        IHistorique historiqueTreeSet = res.getHistorique();
        System.out.println("Les données ont été générées.\n");

        //Ajout du temps de calcul et mémoire dans les listes
        calculTempsTreeSet.add(res.getTempsGenerationDonnees());
        calculTempsTreeSet.add(res.getCalculTemps());
        calculMemoireTreeSet.add(res.getCalculMemoireGenerationDonnees());
        calculMemoireTreeSet.add(res.getCalculMemoire());

        System.out.println("--------------- Test des méthodes ---------------"); //#####################################


        System.out.println("----- Test méthode comptage -----"); //------------------------------------------------
        //Calcul temps
        res = Benchmark.tempsComptage(historiqueTreeSet, "Achat", taille);
        calculTempsTreeSet.add(res.getCalculTemps());
        calculMemoireTreeSet.add(res.getCalculMemoire());
//        //Affichage
//        System.out.println("Nombre de transaction type Achat : " + historiqueTreeSet.comptageType("Achat"));


        System.out.println("\n----- Test méthode recherche par id -----"); //----------------------------------------------------

        //Calcul temps
        res = Benchmark.tempsRechercheId(historiqueTreeSet, taille);

        calculTempsTreeSet.add(res.getCalculTemps());
        calculMemoireTreeSet.add(res.getCalculMemoire());
//        //Affichage
//        try {
//            System.out.println(historiqueTreeSet.rechercheId("ID_8"));
//        } catch (HistoriqueException e) {
//            System.err.println("ERREUR : " + e.getMessage());
//        }


        System.out.println("\n------ Test méthode parcours chronologique ------"); //-------------------------------------

        //Calcul temps
        res = Benchmark.tempsParcoursChronologique(historiqueTreeSet, taille);

        calculTempsTreeSet.add(res.getCalculTemps());
        calculMemoireTreeSet.add(res.getCalculMemoire());
//        //Affichage
//        LocalDate dateD = LocalDate.of(2026, 7, 3);
//        LocalDate dateF = LocalDate.of(2026, 7, 6);
//        System.out.println(historiqueTreeSet.parcoursChronologique(dateD, dateF));


        System.out.println("\n----- Test méthode suppression transaction -----"); //------------------------------------

        //Calcul temps
        res = Benchmark.tempsAnnulerTransaction(historiqueTreeSet, taille - 1);

        calculTempsTreeSet.add(res.getCalculTemps());
        calculMemoireTreeSet.add(res.getCalculMemoire());
        //Affichage
//        try {
//            System.out.println(historique.rechercheId("ID_" + taille));
//            historique.annulerTransaction("ID_" + taille);
//            System.out.println(historique.rechercheId("ID_" + taille));
//        } catch(HistoriqueException e){
//            System.err.println("ERREUR : " + e.getMessage());
//        }

        //##############################################################################################################
        //############################################### SCENARIOS ####################################################
        //##############################################################################################################

        int[] tailles = {1000, 5000, 10000};
        int repetitions = 5;

        //Scénario trading (beaucoup d'ajouts)
        //System.out.println("\n###################### SCENARIO TRADING #####################");
        ArrayList<ResultatBenchmark> resultatsTreeSet1 = (Benchmark.lancerScenario(tailles, repetitions, historiqueTreeSet, 1000, 70, 2, 15, 10, 3));

        //Scénario Audit (beaucoup de vérifications)
        //System.out.println("\n###################### SCENARIO AUDIT #####################");
        ArrayList<ResultatBenchmark> resultatsTreeSet2 = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeSet, 1000, 5, 1, 20, 34, 40);


        //Scénario e-commerce (consulte les commandes)
        //System.out.println("\n###################### SCENARIO E-COMMERCE #####################");
        ArrayList<ResultatBenchmark> resultatsTreeSet3 = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeSet, 1000, 25, 10, 30, 25, 10);


        //##############################################################################################################
        //############################################## TREEMAP #######################################################
        //##############################################################################################################
        ArrayList<Double> calculTempsTreeMap = new ArrayList<>();
        ArrayList<Double> calculMemoireTreeMap = new ArrayList<>();

        System.out.println("\n\n################################# TEST : HISTORIQUE TREEMAP #################################\n");

        System.out.println("--------------- Génération des données ---------------"); //################################
        res = Benchmark.donnees(taille, new HistoriqueTreeMap());
        IHistorique historiqueTreeMap = res.getHistorique();
        System.out.println("Les données ont été générées.\n");

        calculTempsTreeMap.add(res.getTempsGenerationDonnees());
        calculTempsTreeMap.add(res.getCalculTemps());
        calculMemoireTreeMap.add(res.getCalculMemoireGenerationDonnees());
        calculMemoireTreeMap.add(res.getCalculMemoire());

        System.out.println("--------------- Test des méthodes ---------------"); //#####################################


        System.out.println("----- Test méthode comptage -----"); //------------------------------------------------
        //Calcul temps
        res = Benchmark.tempsComptage(historiqueTreeMap, "Achat", taille);
        calculTempsTreeMap.add(res.getCalculTemps());
        calculMemoireTreeMap.add(res.getCalculMemoire());
        //Affichage
//        System.out.println("Nombre de transaction type Achat : " + historiqueTreeMap.comptageType("Achat"));


        System.out.println("\n----- Test méthode recherche par id -----"); //----------------------------------------------------

        //Calcul temps
        res = Benchmark.tempsRechercheId(historiqueTreeMap, taille);
        calculTempsTreeMap.add(res.getCalculTemps());
        calculMemoireTreeMap.add(res.getCalculMemoire());
        //Affichage
//        try {
//            System.out.println(historiqueTreeMap.rechercheId("ID_8"));
//        } catch (HistoriqueException e) {
//            System.err.println("ERREUR : " + e.getMessage());
//        }


        System.out.println("\n------ Test méthode parcours chronologique ------"); //-------------------------------------

        //Calcul temps
        res = Benchmark.tempsParcoursChronologique(historiqueTreeMap, taille);
        calculTempsTreeMap.add(res.getCalculTemps());
        calculMemoireTreeMap.add(res.getCalculMemoire());
//        //Affichage
//        dateD = LocalDate.of(2026, 7, 3);
//        dateF = LocalDate.of(2026, 7, 6);
//        System.out.println(historiqueTreeMap.parcoursChronologique(dateD, dateF));


        System.out.println("\n----- Test méthode suppression transaction -----"); //------------------------------------

        //Calcul temps
        res = Benchmark.tempsAnnulerTransaction(historiqueTreeMap, taille - 1);
        calculTempsTreeMap.add(res.getCalculTemps());
        calculMemoireTreeMap.add(res.getCalculMemoire());
        //Affichage
//        try {
//            System.out.println(historique.rechercheId("ID_" + taille));
//            historique.annulerTransaction("ID_" + taille);
//            System.out.println(historique.rechercheId("ID_" + taille));
//        } catch(HistoriqueException e){
//            System.err.println("ERREUR : " + e.getMessage());
//        }

        //##############################################################################################################
        //############################################### SCENARIOS ####################################################
        //##############################################################################################################

        //Scenario Trading (beaucoup d'ajouts)
        //System.out.println("\n###################### SCENARIO TRADING #####################");
        ArrayList<ResultatBenchmark> resultatsTreeMap1 = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeMap, 1000, 70, 2, 15, 10, 3);


        //Scénario Audit (beaucoup de vérifications)
        //System.out.println("\n###################### SCENARIO AUDIT #####################");
        ArrayList<ResultatBenchmark> resultatsTreeMap2 = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeMap, 1000, 5, 1, 20, 34, 40);


        //Scénario e-commerce (consulte les commandes)
        //System.out.println("\n###################### SCENARIO E-COMMERCE #####################");
        ArrayList<ResultatBenchmark> resultatsTreeMap3 = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeMap, 1000, 25, 10, 30, 25, 10);


        //##############################################################################################################
        //#############################################  TABLEAUX  #####################################################
        //##############################################################################################################

        String formatLigne = "| %-30s | %-20.3f | %-20.3f |%n";
        String separateur = "+--------------------------------+----------------------+----------------------+";

        //TABLEAU 1 - TEMPS CALCUL -------------------------------------------------------------------------------------
        System.out.println("\n              === TABLEAU TEMPS CALCUL DES OPERATIONS (ms) ===");
        System.out.println(separateur);
        System.out.printf("| %-30s | %-20s | %-20s |%n", "Nom du Test", "TreeSet (ms)", "TreeMap (ms)");
        System.out.println(separateur);

        for (int i = 0; i < nomOperation.size(); i++) {
            System.out.format(formatLigne,
                    nomOperation.get(i),
                    calculTempsTreeSet.get(i),
                    calculTempsTreeMap.get(i)
            );
        }
        System.out.println(separateur);

        //TABLEAU 2 - CALCUL MÉMOIRE -----------------------------------------------------------------------------------
        System.out.println("\n              === TABLEAU CALCUL MÉMOIRE DES OPERATIONS (octets) ===");
        System.out.println(separateur);
        System.out.printf("| %-30s | %-20s | %-20s |%n", "Nom de l'opération", "TreeSet (oct)", "TreeMap (oct)");
        System.out.println(separateur);

        for (int i = 0; i < nomOperation.size(); i++) {
            System.out.format(formatLigne,
                    nomOperation.get(i),
                    calculMemoireTreeSet.get(i),
                    calculMemoireTreeMap.get(i)
            );
        }
        System.out.println(separateur);


        //TABLEAU 3 - SCENARIOS ----------------------------------------------------------------------------------------
        System.out.println("\n                         === TABLEAU SCENARIO 1 ===");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-15s%n",
                "Taille (N)", "Temps TreeSet", "Mémoire TreeSet", "Temps TreeMap", "Mémoire TreeMap");
        System.out.println("------------------------------------------------------------------------------------");

        for (int i = 0; i < resultatsTreeSet1.size(); i++) {
            ResultatBenchmark resSet = resultatsTreeSet1.get(i);
            ResultatBenchmark resMap = resultatsTreeMap1.get(i);

            System.out.printf("%-10d | %-12.4f ms | %-12.2f Ko | %-12.4f ms | %-12.2f Ko%n",
                    resSet.getTaille(),
                    resSet.getCalculTemps(), resSet.getCalculMemoire(),
                    resMap.getCalculTemps(), resMap.getCalculMemoire());
        }

        System.out.println("\n                         === TABLEAU SCENARIO 2 ===");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-15s%n",
                "Taille (N)", "Temps TreeSet", "Mémoire TreeSet", "Temps TreeMap", "Mémoire TreeMap");
        System.out.println("------------------------------------------------------------------------------------");

        for (int i = 0; i < resultatsTreeSet2.size(); i++) {
            ResultatBenchmark resSet = resultatsTreeSet2.get(i);
            ResultatBenchmark resMap = resultatsTreeMap2.get(i);

            System.out.printf("%-10d | %-12.4f ms | %-12.2f Ko | %-12.4f ms | %-12.2f Ko%n",
                    resSet.getTaille(),
                    resSet.getCalculTemps(), resSet.getCalculMemoire(),
                    resMap.getCalculTemps(), resMap.getCalculMemoire());
        }

        System.out.println("\n                         === TABLEAU SCENARIO 3 ===");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-15s%n",
                "Taille (N)", "Temps TreeSet", "Mémoire TreeSet", "Temps TreeMap", "Mémoire TreeMap");
        System.out.println("------------------------------------------------------------------------------------");

        for (int i = 0; i < resultatsTreeSet3.size(); i++) {
            ResultatBenchmark resSet = resultatsTreeSet3.get(i);
            ResultatBenchmark resMap = resultatsTreeMap3.get(i);

            System.out.printf("%-10d | %-12.4f ms | %-12.2f Ko | %-12.4f ms | %-12.2f Ko%n",
                    resSet.getTaille(),
                    resSet.getCalculTemps(), resSet.getCalculMemoire(),
                    resMap.getCalculTemps(), resMap.getCalculMemoire());
        }


    }

}


        //##############################################################################################################
        //########################################## GRAPHES ###########################################################
        //##############################################################################################################
//
//        int[] tailles2 = {1000, 10000, 50000};
//
//        // 1. Tes carnets de notes (vides au début)
//        ArrayList<Integer> resultatsTailles = new ArrayList<>();
//        ArrayList<Long> resultatsTempsSet = new ArrayList<>();
//        ArrayList<Long> resultatsTempsMap = new ArrayList<>();
//
//
//        // --- TEST TREESET ---
//        System.out.println("### BENCHMARK TREESET ###");
//        for (int n : tailles2) {
//            long dureeCumulee = 0;
//            for (int r = 1; r <= repetitions; r++) {
//                HistoriqueTreeSet h = GenerateurDonnees.generer(n);
//                long debut = System.nanoTime();
//
//                // On choisit le scénario AUDIT (le plus représentatif pour le TreeMap)
//                Benchmark.scenario(h, 1000, 5, 1, 20, 34, 40);
//
//                long fin = System.nanoTime();
//                dureeCumulee += (fin - debut);
//            }
//            // ON REMPLIT LES LISTES ICI
//            long moyenne = dureeCumulee / repetitions;
//            resultatsTailles.add(n);
//            resultatsTempsSet.add(moyenne);
//
//            System.out.println("Moyenne TreeSet (N=" + n + ") : " + (moyenne * 1e-6) + " ms");
//        }
//
//        // --- TEST TREEMAP ---
//        System.out.println("\n### BENCHMARK TREEMAP ###");
//        for (int n : tailles2) {
//            long dureeCumulee = 0;
//            for (int r = 1; r <= repetitions; r++) {
//                HistoriqueTreeMap hMap = GenerateurDonnees.genererMap();
//                long debut = System.nanoTime();
//
//                // Même scénario exact !
//                Benchmark.scenarioMap(hMap, 1000, 5, 1, 20, 34, 40);
//
//                long fin = System.nanoTime();
//                dureeCumulee += (fin - debut);
//            }
//            // ON REMPLIT LES LISTES ICI
//            long moyenneMap = dureeCumulee / repetitions;
//            resultatsTempsMap.add(moyenneMap);
//
//            System.out.println("Moyenne TreeMap (N=" + n + ") : " + (moyenneMap * 1e-6) + " ms");
//        }
//
//        // 3. AFFICHAGE DU GRAPHIQUE
//        SwingUtilities.invokeLater(() -> {
//            Graphe graphe = new Graphe(resultatsTempsSet, resultatsTempsMap, resultatsTailles);
//            graphe.setVisible(true);
//        });
//
//
//        System.out.println("\n################################# FIN DES TESTS #################################");
//    }
//    }
//}