package HistoriqueTransactions;
import java.time.LocalDate;
import java.util.Random;
public class Benchmark {

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
                    // On ignore l'erreur, on veut juste mesurer le temps
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
    }
