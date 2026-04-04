package HistoriqueTransactions;

import java.time.LocalDate;

public class Benchmark {

    //############################################## TREESET ###########################################################

    //Permet de générer un jeu de données de la taille du nombre passé en paramètre
    //et de calculer l'occupation mémoire et le temps de génération de ce jeu de données
    public static HistoriqueTreeSet donneesTreeSet(int taille) {
        System.gc();
        Runtime rt = Runtime.getRuntime();
        //Mémoire occupée avant la génération des données
        long memoireAvant = rt.totalMemory() - rt.freeMemory();
        long debut = System.nanoTime();

        HistoriqueTreeSet historique = GenerateurDonnees.generer(taille);

        long fin = System.nanoTime();
        System.out.println("Temps de génération des données : " + (fin-debut)*1e-6 + " ms.");
        System.out.println("Temps d'ajout d'une transaction : " + ((fin-debut)*1e-6)/taille + " ms.");

        //Mémoire occupée après la génération des données
        long memoireApres = rt.totalMemory() - rt.freeMemory();
        //Différence entre les deux qui donne l'occupation de mémoire du jeu de données créé
        long resultatOctets = memoireApres - memoireAvant;

        System.out.println("\n--- Occupation Mémoire (TreeSet) ---");
        System.out.println("Poids total : " + resultatOctets / 1024 + " Ko");
        System.out.println("Poids moyen par transaction : " + resultatOctets / taille + " octets");

        return historique;
    }

    //Calcul du temps de la méthode comptageType
    public static void tempsComptage(HistoriqueTreeSet h, String type, int taille) {
        long debut = System.nanoTime();
        h.comptageType(type);
        long fin = System.nanoTime();
        System.out.println("Temps de comptage de " + taille + " les transactions : " + (fin - debut) * 1e-6 + " ms.");
    }

    //Calcul du temps du nombre de rechercheId passé en paramètre
    public static void tempsRechercheId(HistoriqueTreeSet h, int taille) {
        long debut = System.nanoTime();
        for (int i = 1; i <= taille; i++) {
            try {
                h.rechercheId("ID_" + i);
            } catch (HistoriqueException e) {
            }
        }
        long fin = System.nanoTime();
        System.out.println("Temps de " + taille + " rechercheId : " + (fin - debut) * 1e-6 + " ms.");
        System.out.println("Temps d'une rechercheId : " + ((fin - debut) * 1e-6) / taille + " ms.");

    }

    //Calcul du temps utilisé pour parcourir tout le jeu de données
    public static void tempsParcoursChronologique(HistoriqueTreeSet h, int taille){
        long debut = System.nanoTime();
        LocalDate dateD = LocalDate.now();
        LocalDate dateF = LocalDate.now().plusDays(taille);
        h.parcoursChronologique(dateD, dateF);
        long fin = System.nanoTime();
        System.out.println("Temps pour parcourir " + taille + " transactions : " + (fin-debut)*1e-6 + " ms.");
    }

    //Calcul du temps utilisé pour supprimer le nombre de transactions passés en paramètre
    public static void tempsAnnulerTransaction(HistoriqueTreeSet h, int taille) {
        long debut = System.nanoTime();
        for (int i = 1; i <= taille; i++) {
            try {
                h.annulerTransaction("ID_" + i);
            } catch (HistoriqueException e) {
            }
        }
        long fin = System.nanoTime();
        System.out.println("Temps de suppression de " + taille + " transaction : " + (fin - debut) * 1e-6 + " ms.");
        System.out.println("Temps de suppression d'une transaction : " + ((fin - debut) * 1e-6) / taille + " ms.");

    }







}
