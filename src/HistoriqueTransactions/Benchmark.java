package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.Random;

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



        public static void lancerTestComplet(IHistorique historique, String nomStructure, int volumeDonnees) {
            System.out.println("\n=== BENCHMARK : " + nomStructure + " (" + volumeDonnees + " éléments) ===");

            // 1. SCÉNARIO : Mesure du Coût d'Ajout (déjà fait dans le générateur, mais on peut globaliser)
            // On considère que l'historique est déjà rempli ici.

            // 2. SCÉNARIO : Recherche Intensive (Cout Opérationnel)
            testCoutRecherche(historique, volumeDonnees);

            // 3. SCÉNARIO : Suppression en rafale
            testCoutSuppression(historique, volumeDonnees);

            // 4. MÉMOIRE (Estimation approximative en Java)
            estimerMemoire();
        }

        private static void testCoutRecherche(IHistorique historique, int volume) {
            Random random = new Random();
            long debut = System.nanoTime();

            // On simule 1000 recherches aléatoires
            for (int i = 0; i < 1000; i++) {
                String idAleatoire = "ID_" + random.nextInt(volume);
                try {
                    historique.rechercheId(idAleatoire);
                } catch (HistoriqueException e) {
                }
            }

            long fin = System.nanoTime();
            System.out.println("> Coût Moyen Recherche : " + (fin - debut) / 1000 + " ns / opération");
        }

        private static void testCoutSuppression(IHistorique historique, int volume) {
            long debut = System.nanoTime();

            // On supprime 100 éléments pour voir la réaction
            for (int i = 1; i <= 100; i++) {
                try {
                    historique.annulerTransaction("ID_" + i);
                } catch (HistoriqueException e) {}
            }

            long fin = System.nanoTime();
            System.out.println("> Coût Moyen Suppression : " + (fin - debut) / 100 + " ns / opération");
        }

        private static void estimerMemoire() {
            Runtime runtime = Runtime.getRuntime();
            // On force un peu le ramasse-miettes pour avoir un chiffre plus propre
            runtime.gc();
            long memoireUtilisee = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("> Occupation Mémoire estimée : " + (memoireUtilisee / (1024 * 1024)) + " Mo");
        }


    //############################################## SCENARIOS #########################################################

    public static void scenarioMap(HistoriqueTreeMap h, int nbOperations, int pAdd, int pAnnuler, int pRechercheId, int pParcoursChrono, int pComptage) {
        System.out.println("\n--- SCÉNARIO TREEMAP (" + nbOperations + " opérations) ---");
        System.out.println("Répartition : " + pAdd + "% ajout, " + pAnnuler + "% annulation, " + pRechercheId + "% recherche, "
                + pParcoursChrono + "% parcours et " + pComptage + "% comptage.");

        for (int i = 0; i < nbOperations; i++) {
            double random = Math.random() * 100;

            if (random < pAdd) {
                try { h.addTransaction(new Transaction("NEW_" + i, LocalDate.now(), "Achat", 100.0)); } catch (HistoriqueException e){}
            }
            else if (random < pAdd + pAnnuler) {
                // Utilisation de (int) pour avoir un ID entier cohérent
                try { h.annulerTransaction("ID_" + (int)(Math.random() * 1000)); } catch(Exception e){}
            }
            else if (random < pAdd + pAnnuler + pRechercheId) {
                try { h.rechercheId("ID_" + (int)(Math.random() * 1000)); } catch(Exception e){}
            }
            else if (random < pAdd + pAnnuler + pRechercheId + pParcoursChrono) {
                h.parcoursChronologique(LocalDate.now(), LocalDate.now().plusDays(10));
            }
            else {
                h.comptageType("Vente");
            }
        }
    }


    }
