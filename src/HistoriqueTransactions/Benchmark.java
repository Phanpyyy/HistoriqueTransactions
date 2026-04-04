package HistoriqueTransactions;

import java.time.LocalDate;

public class Benchmark {
    //##################################################################################################################
    //############################################## TREESET ###########################################################
    //##################################################################################################################

    //############################################# TEMPS CALCUL #######################################################
    //Permet de générer n transactions
    //et de calculer l'occupation mémoire et le temps de génération de ce jeu de données
    public static HistoriqueTreeSet donneesTreeSet(int n) {
        System.gc();
        Runtime rt = Runtime.getRuntime();
        //Mémoire occupée avant la génération des données
        long memoireAvant = rt.totalMemory() - rt.freeMemory();
        long debut = System.nanoTime();

        HistoriqueTreeSet historique = GenerateurDonnees.generer(n);

        long fin = System.nanoTime();
        System.out.println("Temps de génération des données : " + (fin-debut)*1e-6 + " ms.");
        System.out.println("Temps d'ajout d'une transaction : " + ((fin-debut)*1e-6)/n + " ms.");

        //Mémoire occupée après la génération des données
        long memoireApres = rt.totalMemory() - rt.freeMemory();
        //Différence entre les deux qui donne l'occupation de mémoire du jeu de données créé
        long resultatOctets = memoireApres - memoireAvant;

        System.out.println("\n--- Occupation Mémoire (TreeSet) ---");
        System.out.println("Poids total : " + resultatOctets / 1024 + " Ko");
        System.out.println("Poids moyen par transaction : " + resultatOctets / n + " octets");

        return historique;
    }

    //Calcul du temps utilisé pour compter toutes les transactions d'un type
    public static void tempsComptage(HistoriqueTreeSet h, String type, int n) {
        long debut = System.nanoTime();
        h.comptageType(type);
        long fin = System.nanoTime();
        System.out.println("Temps de comptage de " + n + " les transactions : " + (fin - debut) * 1e-6 + " ms.");
    }

    //Calcul du temps utilisé pour effectuer n recherches par id
    public static void tempsRechercheId(HistoriqueTreeSet h, int n) {
        long debut = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            try {
                h.rechercheId("ID_" + i);
            } catch (HistoriqueException e) {
            }
        }
        long fin = System.nanoTime();
        System.out.println("Temps de " + n + " rechercheId : " + (fin - debut) * 1e-6 + " ms.");
        System.out.println("Temps d'une rechercheId : " + ((fin - debut) * 1e-6) / n + " ms.");

    }

    //Calcul du temps utilisé pour parcourir tout le jeu de données
    public static void tempsParcoursChronologique(HistoriqueTreeSet h, int n){
        long debut = System.nanoTime();
        LocalDate dateD = LocalDate.now();
        LocalDate dateF = LocalDate.now().plusDays(n);
        h.parcoursChronologique(dateD, dateF);
        long fin = System.nanoTime();
        System.out.println("Temps pour parcourir " + n + " transactions : " + (fin-debut)*1e-6 + " ms.");
    }

    //Calcul du temps utilisé pour annuler n transactions
    public static void tempsAnnulerTransaction(HistoriqueTreeSet h, int n) {
        long debut = System.nanoTime();
        for (int i = 1; i <= n; i++) {
            try {
                h.annulerTransaction("ID_" + i);
            } catch (HistoriqueException e) {
            }
        }
        long fin = System.nanoTime();
        System.out.println("Temps de suppression de " + n + " transaction : " + (fin - debut) * 1e-6 + " ms.");
        System.out.println("Temps de suppression d'une transaction : " + ((fin - debut) * 1e-6) / n + " ms.");

    }



    //############################################## SCENARIOS #########################################################

    public static void scenario(HistoriqueTreeSet h, int nbOperations, int pAdd, int pAnnuler, int pRechercheId, int pParcoursChrono, int pComptage) {
        System.out.println("\n--- SCÉNARIO (" + nbOperations + " opérations) ---");
        System.out.println("Répartition : " + pAdd + "% ajout, " + pAnnuler + "% annulation, " + pRechercheId + "% recherche, "
                            + pParcoursChrono + "% parcours et " + pComptage + "% comptage.");


        for (int i = 0; i < nbOperations; i++) {
            double random = Math.random() * 100;

            if (random < pAdd) {
                try { h.addTransaction(new Transaction("NEW_" + i, LocalDate.now(), "Achat", 100.0)); } catch (HistoriqueException e){}
            }
            else if (random < pAdd + pAnnuler) {
                try { h.annulerTransaction("ID_" + Math.random() * 1000); } catch(Exception e){}
            }
            else if (random < pAdd + pAnnuler + pRechercheId) {
                try { h.rechercheId("ID_" + Math.random() * 1000); } catch(Exception e){}
            }
            else if (random < pAdd + pAnnuler + pRechercheId + pParcoursChrono) {
                h.parcoursChronologique(LocalDate.now(), LocalDate.now().plusDays(10));
            }
            else {
                h.comptageType("Vente");
            }
        }


    }





    //##################################################################################################################
    //############################################## TREEMAP ###########################################################
    //##################################################################################################################



}
