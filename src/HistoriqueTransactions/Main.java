package HistoriqueTransactions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

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

        int[] tailles = {1000, 5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000};
        int repetitions = 20;
        int nbOperations = 1000;

        //Lancement une première fois dans le vide pour le warmup
        Benchmark.lancerScenario(new int[]{1000}, 1, new HistoriqueTreeSet(), 1000, 20, 20, 20, 20, 20);

        //Scénario Gestion de compte bancaire (beaucoup d'ajouts, annulations et recherche par id)
        //System.out.println("\n###################### SCENARIO GESTION DE COMPTE BANCAIRE #####################");
        ArrayList<ResultatBenchmark> resultatsTreeSet_compteBancaire = (Benchmark.lancerScenario(tailles, repetitions, historiqueTreeSet, nbOperations, 35, 25, 30, 5, 5));

        //Scénario Consultation client (beaucoup de parcours chronologique)
        //System.out.println("\n###################### SCENARIO CONSULTATION CLIENT #####################");
        ArrayList<ResultatBenchmark> resultatsTreeSet_consultationClient = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeSet, nbOperations, 5, 5, 10, 70, 10);


        //Scénario trading (beaucoup d'ajouts et de comptage par type)
        //System.out.println("\n###################### SCENARIO TRADING #####################");
        ArrayList<ResultatBenchmark> resultatsTreeSet_trading = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeSet, nbOperations, 45, 5, 5, 10, 35);


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

        //Premier lancement à vide pour le warmup
        Benchmark.lancerScenario(new int[]{1000}, 1, new HistoriqueTreeMap(), 1000, 20, 20, 20, 20, 20);

        //Scenario Gestion de compte bancaire (beaucoup d'ajouts, d'annulations et de recherches par id)
        //System.out.println("\n###################### SCENARIO GESTION COMPTE BANCAIRE #####################");
        ArrayList<ResultatBenchmark> resultatsTreeMap_compteBancaire = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeMap, nbOperations, 35, 25, 30, 5, 5);


        //Scénario Consultation client (beaucoup de parcours chronologique)
        //System.out.println("\n###################### SCENARIO CONSULTATION CLIENT #####################");
        ArrayList<ResultatBenchmark> resultatsTreeMap_consultationClient = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeMap, nbOperations, 5, 5, 10, 70, 10);


        //Scénario trading (beaucoup d'ajouts et de comptage par type)
        //System.out.println("\n###################### SCENARIO TRADING #####################");
        ArrayList<ResultatBenchmark> resultatsTreeMap_trading = Benchmark.lancerScenario(tailles, repetitions, historiqueTreeMap, nbOperations, 45, 5, 5, 10, 35);


        //##############################################################################################################
        //#############################################  TABLEAUX  #####################################################
        //##############################################################################################################
        ArrayList<String> nomOperation = new ArrayList<String>(Arrays.asList(
                "Génération des données",
                "Ajout d'une transaction",
                "Comptage par type",
                "Recherche par identifiant",
                "Parcours chronologique",
                "Suppression d'une transaction"
        ));

        String formatLigne = "| %-30s | %-20.4f | %-20.4f |%n";
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


        //TABLEAUX - SCENARIOS ----------------------------------------------------------------------------------------
        System.out.println("\n                === SCENARIO 1 - GESTION COMPTE BANCAIRE ===");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-15s%n",
                "Taille (N)", "Temps TreeSet", "Mémoire TreeSet", "Temps TreeMap", "Mémoire TreeMap");
        System.out.println("------------------------------------------------------------------------------------");

        for (int i = 0; i < resultatsTreeSet_compteBancaire.size(); i++) {
            ResultatBenchmark resSet = resultatsTreeSet_compteBancaire.get(i);
            ResultatBenchmark resMap = resultatsTreeMap_compteBancaire.get(i);

            System.out.printf("%-10d | %-12.4f ms | %-12.2f Ko | %-12.4f ms | %-12.2f Ko%n",
                    resSet.getTaille(),
                    resSet.getCalculTemps(), resSet.getCalculMemoire(),
                    resMap.getCalculTemps(), resMap.getCalculMemoire());
        }

//        System.out.println("\n                    === SCENARIO 2 - CONSULTATION CLIENT ===");
//        System.out.println("------------------------------------------------------------------------------------");
//        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-15s%n",
//                "Taille (N)", "Temps TreeSet", "Mémoire TreeSet", "Temps TreeMap", "Mémoire TreeMap");
//        System.out.println("------------------------------------------------------------------------------------");
//
//        for (int i = 0; i < resultatsTreeSet_consultationClient.size(); i++) {
//            ResultatBenchmark resSet = resultatsTreeSet_consultationClient.get(i);
//            ResultatBenchmark resMap = resultatsTreeMap_consultationClient.get(i);
//
//            System.out.printf("%-10d | %-12.4f ms | %-12.2f Ko | %-12.4f ms | %-12.2f Ko%n",
//                    resSet.getTaille(),
//                    resSet.getCalculTemps(), resSet.getCalculMemoire(),
//                    resMap.getCalculTemps(), resMap.getCalculMemoire());
//        }
//
//        System.out.println("\n                       === SCENARIO 3 - TRADING ===");
//        System.out.println("------------------------------------------------------------------------------------");
//        System.out.printf("%-10s | %-15s | %-15s | %-15s | %-15s%n",
//                "Taille (N)", "Temps TreeSet", "Mémoire TreeSet", "Temps TreeMap", "Mémoire TreeMap");
//        System.out.println("------------------------------------------------------------------------------------");
//
//        for (int i = 0; i < resultatsTreeSet_trading.size(); i++) {
//            ResultatBenchmark resSet = resultatsTreeSet_trading.get(i);
//            ResultatBenchmark resMap = resultatsTreeMap_trading.get(i);
//
//            System.out.printf("%-10d | %-12.4f ms | %-12.2f Ko | %-12.4f ms | %-12.2f Ko%n",
//                    resSet.getTaille(),
//                    resSet.getCalculTemps(), resSet.getCalculMemoire(),
//                    resMap.getCalculTemps(), resMap.getCalculMemoire());
//        }
//


        //##############################################################################################################
        //########################################## GRAPHES ###########################################################
        //##############################################################################################################

        //GRAPHE 1 - SCENARIO GESTION COMPTE BANCAIRE ------------------------------------------------------------------
        ArrayList<Double> tempsSet_compteBancaire = new ArrayList<>();
        ArrayList<Double> tempsMap_compteBancaire = new ArrayList<>();
        ArrayList<Integer> taillesN_compteBancaire = new ArrayList<>();

        for (ResultatBenchmark r : resultatsTreeSet_compteBancaire) {
            tempsSet_compteBancaire.add(r.getCalculTemps());
            taillesN_compteBancaire.add(r.getTaille());
        }
        for (ResultatBenchmark r : resultatsTreeMap_compteBancaire) {
            tempsMap_compteBancaire.add(r.getCalculTemps());
        }

        SwingUtilities.invokeLater(() -> {
            new Graphe(tempsSet_compteBancaire, tempsMap_compteBancaire, taillesN_compteBancaire,
                    "Temps - Scénario 1 - Gestion compte bancaire", "Temps (ms)").setVisible(true);
        });

//        //GRAPHE MÉMOIRE
//        ArrayList<Double> memoireSet_compteBancaire = new ArrayList<>();
//        ArrayList<Double> memoireMap_compteBancaire = new ArrayList<>();
//        ArrayList<Integer> taillesN_memoireCompteBancaire = new ArrayList<>();
//
//        for (ResultatBenchmark r : resultatsTreeSet_compteBancaire) {
//            memoireSet_compteBancaire.add(r.getCalculMemoire()); // On récupère la mémoire ici
//            taillesN_memoireCompteBancaire.add(r.getTaille());
//        }
//
//        for (ResultatBenchmark r : resultatsTreeMap_compteBancaire) {
//            memoireMap_compteBancaire.add(r.getCalculMemoire()); // On récupère la mémoire ici
//        }
//
//        SwingUtilities.invokeLater(() -> {
//            new Graphe(memoireSet_compteBancaire, memoireMap_compteBancaire, taillesN_memoireCompteBancaire,
//                    "Mémoire : Scénario 1 - Gestion compte bancaire", "Mémoire (o)").setVisible(true);
//        });

        //GRAPHE 2 - SCENARIO CONSULTATION CLIENT ----------------------------------------------------------------------
        ArrayList<Double> tempsSet_consultationClient = new ArrayList<>();
        ArrayList<Double> tempsMap_consultationClient = new ArrayList<>();
        ArrayList<Integer> taillesN_consultationClient = new ArrayList<>();

        for (ResultatBenchmark r : resultatsTreeSet_consultationClient) {
            tempsSet_consultationClient.add(r.getCalculTemps());
            taillesN_consultationClient.add(r.getTaille());
        }
        for (ResultatBenchmark r : resultatsTreeMap_consultationClient) {
            tempsMap_consultationClient.add(r.getCalculTemps());
        }

        SwingUtilities.invokeLater(() -> {
            new Graphe(tempsSet_consultationClient, tempsMap_consultationClient, taillesN_consultationClient, "Temps : Scénario 2 - Consultation Client", "Temps (ms)").setVisible(true);
        });


        //GRAPHE 3 - TRADING -------------------------------------------------------------------------------------------
        ArrayList<Double> tempsSet_trading = new ArrayList<>();
        ArrayList<Double> tempsMap_trading = new ArrayList<>();
        ArrayList<Integer> taillesN_trading = new ArrayList<>();

        for (ResultatBenchmark r : resultatsTreeSet_trading) {
            tempsSet_trading.add(r.getCalculTemps());
            taillesN_trading.add(r.getTaille());
        }
        for (ResultatBenchmark r : resultatsTreeMap_trading) {
            tempsMap_trading.add(r.getCalculTemps());
        }

        SwingUtilities.invokeLater(() -> {
            new Graphe(tempsSet_trading, tempsMap_trading, taillesN_trading, "Scénario 3 - Trading", "Temps (ms)").setVisible(true);
        });






    }

}
