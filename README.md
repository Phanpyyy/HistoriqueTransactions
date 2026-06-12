# Comparaison de Structures de Données en Java

Projet réalisé dans le cadre du Master Informatique ICo à l'Université de Montpellier.

---

## Présentation du Projet
Le projet porte sur l’étude expérimentale du choix d’une structure de données dans un programme Java.
Ici notre programme porte sur un **historique de transactions** qui nous permet d'étudier deux structures différentes : 
* `TreeSet` (organisation arborescente ordonnée par date).
* `TreeMap` (table associative indexée par identifiant unique).
L'objectif est de mesurer et comparer leur temps d'exécution ainsi que leur occupation mémoire face à différents scénarios.

---

##  Benchmark & Visualisation Data
Afin de comparer les deux structures, un outil de *Benchmarking* (`Benchmark.java`) a été développé pour mesurer :
1. **La performance temporelle** : vitesse d'exécution des scénarios d'opérations via `System.nanoTime()`.
2. **L'occupation mémoire** : consommation RAM (en Ko) lors de la création et la manipulation des objets.

---

### Visualisation Graphique (Swing)
Les résultats peuvent être visualisés via des graphiques comparatifs.

---

##  Structure des Fichiers Source
* `IHistorique.java` : Interface partagée par les implémentations `HistoriqueTreeSet` et `HistoriqueTreeMap`.
* `HistoriqueException` : Gestion des exceptions.
* `HistoriqueTreeSet.java` & `HistoriqueTreeMap.java` : Classes d'implémentations des structures testées.
* `Transaction.java` : Objet métier représentant une transaction (ID, date, type, montant).
* `GenerateurDonnees.java` : Génère de grands volumes de données.
* `Benchmark.java` & `ResultatBenchmark.java` : Mesure les performances temporelles et mémorielles.
* `Graphe.java` : Interface graphique générant les graphiques comparatifs.
