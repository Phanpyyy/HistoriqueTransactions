package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Benchmark {

    //############################################# TEMPS CALCUL #######################################################
    //Permet de générer n transactions
    //et de retourner l'occupation mémoire, le temps de génération et le jeu de données
    public static ResultatBenchmark donnees(int n, IHistorique h) {
        System.gc();
        Runtime rt = Runtime.getRuntime();
        //Mémoire occupée avant la génération des données
        long memoireAvant = rt.totalMemory() - rt.freeMemory();
        long debut = System.nanoTime();

        IHistorique historique = GenerateurDonnees.generer(n, h);

        long fin = System.nanoTime();
        double duree = (fin - debut) * 1e-6;
        System.out.println("Temps de génération des données : " + duree + " ms.");
        System.out.println("Temps d'ajout d'une transaction : " + duree / n + " ms.");

        //Mémoire occupée après la génération des données
        long memoireApres = rt.totalMemory() - rt.freeMemory();
        //Différence entre les deux qui donne l'occupation de mémoire du jeu de données créé
        double resultatOctets = memoireApres - memoireAvant;

        System.out.println("\n--- Occupation Mémoire ---");
        System.out.println("Poids total : " + resultatOctets / 1024 + " Ko");
        System.out.println("Poids moyen par transaction : " + resultatOctets / n + " octets");

        return new ResultatBenchmark(historique, duree, resultatOctets,  duree / n, resultatOctets / n );
    }


    //Calcul du temps utilisé pour compter toutes les transactions d'un type
    public static ResultatBenchmark tempsComptage(IHistorique h, String type, int n) {
        //Mémoire avant
        System.gc();
        Runtime rt = Runtime.getRuntime();
        long memoireAvant = rt.totalMemory() - rt.freeMemory();
        //Temps début
        long debut = System.nanoTime();
        h.comptageType(type);
        //Temps fin
        long fin = System.nanoTime();
        //Mémoire après
        long memoireApres = rt.totalMemory() - rt.freeMemory();
        double resultatOctets = memoireApres - memoireAvant;

        System.out.println("Temps de comptage de " + n + " les transactions : " + (fin - debut) * 1e-6 + " ms.");
        System.out.println("\n--- Occupation Mémoire ---");
        System.out.println("Poids total : " + resultatOctets / 1024 + " Ko");
        System.out.println("Poids moyen par transaction : " + resultatOctets / n + " octets");
        return new ResultatBenchmark((fin - debut) * 1e-6, resultatOctets / n);
    }

    //Calcul du temps utilisé pour effectuer n recherches par id
    public static ResultatBenchmark tempsRechercheId(IHistorique h, int n) {
        //Mémoire avant
        System.gc();
        Runtime rt = Runtime.getRuntime();
        long memoireAvant = rt.totalMemory() - rt.freeMemory();

        long debut = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            try {
                h.rechercheId("ID_" + i);
            } catch (HistoriqueException e) {
            }
        }
        long fin = System.nanoTime();

        //Mémoire après
        long memoireApres = rt.totalMemory() - rt.freeMemory();
        double resultatOctets = memoireApres - memoireAvant;

        System.out.println("Temps de " + n + " rechercheId : " + (fin - debut) * 1e-6 + " ms.");
        System.out.println("Temps d'une rechercheId : " + ((fin - debut) * 1e-6) / n + " ms.");
        System.out.println("\n--- Occupation Mémoire ---");
        System.out.println("Poids total : " + resultatOctets / 1024 + " Ko");
        System.out.println("Poids moyen par transaction : " + resultatOctets / n + " octets");
        return new ResultatBenchmark(((fin - debut) * 1e-6)/n, resultatOctets / n);
    }

    //Calcul du temps utilisé pour parcourir tout le jeu de données
    public static ResultatBenchmark tempsParcoursChronologique(IHistorique h, int n) {
        //Mémoire avant
        System.gc();
        Runtime rt = Runtime.getRuntime();
        long memoireAvant = rt.totalMemory() - rt.freeMemory();
        long debut = System.nanoTime();
        LocalDate dateD = LocalDate.now();
        LocalDate dateF = LocalDate.now().plusDays(n);
        h.parcoursChronologique(dateD, dateF);
        long fin = System.nanoTime();
        //Mémoire après
        long memoireApres = rt.totalMemory() - rt.freeMemory();
        double resultatOctets = memoireApres - memoireAvant;
        System.out.println("Temps pour parcourir " + n + " transactions : " + (fin - debut) * 1e-6 + " ms.");
        return new ResultatBenchmark((fin - debut) * 1e-6, resultatOctets / n);
    }

    //Calcul du temps utilisé pour annuler n transactions
    public static ResultatBenchmark tempsAnnulerTransaction(IHistorique h, int n) {
        //Mémoire avant
        System.gc();
        Runtime rt = Runtime.getRuntime();
        long memoireAvant = rt.totalMemory() - rt.freeMemory();
        long debut = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            try {
                h.annulerTransaction("ID_" + i);
            } catch (HistoriqueException e) {
            }
        }
        long fin = System.nanoTime();
        //Mémoire après
        long memoireApres = rt.totalMemory() - rt.freeMemory();
        double resultatOctets = memoireApres - memoireAvant;
        System.out.println("Temps de suppression de " + n + " transaction : " + (fin - debut) * 1e-6 + " ms.");
        System.out.println("Temps de suppression d'une transaction : " + ((fin - debut) * 1e-6) / n + " ms.");
        return new ResultatBenchmark(((fin - debut) * 1e-6)/n, resultatOctets / n);
    }




    //############################################## SCENARIOS #########################################################

    public static void scenario(IHistorique h, int nbOperations, int pAdd, int pAnnuler, int pRechercheId, int pParcoursChrono, int pComptage) {
        System.out.println("\n--- SCÉNARIO (" + nbOperations + " opérations) ---");
        System.out.println("Répartition : " + pAdd + "% ajout, " + pAnnuler + "% annulation, " + pRechercheId + "% recherche, "
                + pParcoursChrono + "% parcours et " + pComptage + "% comptage.");

        for (int i = 0; i < nbOperations; i++) {
            double random = Math.random() * 100;

            if (random < pAdd) {
                try {
                    h.addTransaction(new Transaction("NEW_" + i, LocalDate.now(), "Achat", 100.0));
                } catch (HistoriqueException e) {
                }
            } else if (random < pAdd + pAnnuler) {
                try {
                    h.annulerTransaction("ID_" + Math.random() * 1000);
                } catch (Exception e) {
                }
            } else if (random < pAdd + pAnnuler + pRechercheId) {
                try {
                    h.rechercheId("ID_" + Math.random() * 1000);
                } catch (Exception e) {
                }
            } else if (random < pAdd + pAnnuler + pRechercheId + pParcoursChrono) {
                h.parcoursChronologique(LocalDate.now(), LocalDate.now().plusDays(10));
            } else {
                h.comptageType("Vente");
            }
        }


    }

    public static void lancerScenario (int[] taille, int repetitions, IHistorique h, int nbOperations, int pAdd, int pAnnuler, int pRechercheId, int pParcoursChrono, int pComptage){
        for (int n : taille) {
            System.out.println("\n########### TAILLE N = " + n + " ############");
            long duree = 0;
            for (int r = 1; r <= repetitions; r++) {
                System.out.println("\n--- Répétition n°" + r + " ---");

                long debut = System.nanoTime();
                Benchmark.scenario(h, nbOperations, pAdd, pAnnuler, pRechercheId, pParcoursChrono, pComptage);
                long fin = System.nanoTime();
                System.out.println("Temps total du scénario : " + (fin - debut) * 1e-6 + " ms");
                duree = duree + (fin - debut);
            }

            System.out.println("\nTemps moyen du scénario (taille : " + n + ") : " + (duree * 1e-6) / repetitions + " ms");

        }
    }

}


//
//
//        private static void estimerMemoire() {
//            Runtime runtime = Runtime.getRuntime();
//            // On force un peu le ramasse-miettes pour avoir un chiffre plus propre
//            runtime.gc();
//            long memoireUtilisee = runtime.totalMemory() - runtime.freeMemory();
//            System.out.println("> Occupation Mémoire estimée : " + (memoireUtilisee / (1024 * 1024)) + " Mo");
//        }
//
//
//    //############################################## SCENARIOS #########################################################
//
//    public static void scenarioMap(HistoriqueTreeMap h, int nbOperations, int pAdd, int pAnnuler, int pRechercheId, int pParcoursChrono, int pComptage) {
//        System.out.println("\n--- SCÉNARIO TREEMAP (" + nbOperations + " opérations) ---");
//        System.out.println("Répartition : " + pAdd + "% ajout, " + pAnnuler + "% annulation, " + pRechercheId + "% recherche, "
//                + pParcoursChrono + "% parcours et " + pComptage + "% comptage.");
//
//        for (int i = 0; i < nbOperations; i++) {
//            double random = Math.random() * 100;
//
//            if (random < pAdd) {
//                try { h.addTransaction(new Transaction("NEW_" + i, LocalDate.now(), "Achat", 100.0)); } catch (HistoriqueException e){}
//            }
//            else if (random < pAdd + pAnnuler) {
//                // Utilisation de (int) pour avoir un ID entier cohérent
//                try { h.annulerTransaction("ID_" + (int)(Math.random() * 1000)); } catch(Exception e){}
//            }
//            else if (random < pAdd + pAnnuler + pRechercheId) {
//                try { h.rechercheId("ID_" + (int)(Math.random() * 1000)); } catch(Exception e){}
//            }
//            else if (random < pAdd + pAnnuler + pRechercheId + pParcoursChrono) {
//                h.parcoursChronologique(LocalDate.now(), LocalDate.now().plusDays(10));
//            }
//            else {
//                h.comptageType("Vente");
//            }
//        }
//    }



