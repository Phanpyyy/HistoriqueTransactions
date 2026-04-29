package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class HistoriqueTreeSet implements IHistorique{
    //Attributs --------------------------------------------------------------------------------------------------------
    private TreeSet<Transaction> transactions;

    //Constructeurs ----------------------------------------------------------------------------------------------------
    public HistoriqueTreeSet() {
        this.transactions = new TreeSet<>();
    }

    //Méthodes ---------------------------------------------------------------------------------------------------------
    //Ajout d'une transaction
    @Override
    public void addTransaction(Transaction t) throws HistoriqueException{
        if (transactions.contains(t)){
            throw new HistoriqueException("La transaction existe déjà.");
        }
        else {
            transactions.add(t);
        }
    }

    //Suppression d'une transaction avec TreeSet trié par date
    @Override
    public void annulerTransaction(String id) throws HistoriqueException{
        Iterator<Transaction> iterateur = transactions.iterator();

        while (iterateur.hasNext()){
            Transaction t = iterateur.next();
            if (t.getId().equals(id)){
                iterateur.remove();
                return;
            }

        }
        throw new HistoriqueException("La transaction n'existe pas.");
    }

    //Méthode annulerTransaction avec TreeSet trié par id
//    @Override
//    public void annulerTransaction(String id) throws HistoriqueException {
//        // On crée un objet "factice" avec le même ID pour que le TreeSet puisse le trouver
//        Transaction cible = new Transaction(id);
//
//        // remove() utilise le tri par ID -> O(log n) au lieu de la boucle while
//        if (!transactions.remove(cible)) {
//            throw new HistoriqueException("La transaction n'existe pas.");
//        }
//    }

    //Recherche d'une transaction par son id avec TreeSet trié par date
    @Override
    public Transaction rechercheId(String id) throws HistoriqueException{
        for (Transaction t : transactions){
            if (t.getId().equals(id)){
                return t;
            }
        }
        throw new HistoriqueException("La transaction " + id + " n'existe pas.");
    }

    //Méthode recherche par id avec TreeSet trié par id
//    @Override
//    public Transaction rechercheId(String id) throws HistoriqueException {
//        // On utilise ceiling() pour trouver l'élément exact ou le plus proche via l'arbre
//        Transaction cible = new Transaction(id);
//        Transaction trouvee = transactions.ceiling(cible);
//
//        // On vérifie si l'élément trouvé correspond bien à notre ID
//        if (trouvee != null && trouvee.getId().equals(id)) {
//            return trouvee;
//        }
//        throw new HistoriqueException("La transaction " + id + " n'existe pas.");
//    }


    //Renvoi des transactions situées entre les deux dates passées en paramètre avec TreeSet trié par date
    @Override
    public ArrayList<Transaction> parcoursChronologique(LocalDate dateD, LocalDate dateF){
        ArrayList<Transaction> transactionChrono = new ArrayList<>();
        for (Transaction t : transactions){
            if (t.getDate().isAfter(dateF)) {
                break;
            }
            if (!t.getDate().isBefore(dateD)) {
                transactionChrono.add(t);
            }
        }
        return transactionChrono;
    }

    //Méthode parcours chronologique avec le TreeSet trié par id
//    @Override
//    public ArrayList<Transaction> parcoursChronologique(LocalDate dateD, LocalDate dateF) {
//        ArrayList<Transaction> transactionChrono = new ArrayList<>();
//        // ATTENTION : Les données ne sont plus triées par date dans l'arbre.
//        // On doit obligatoirement TOUT parcourir (O(n)).
//        // Le "break" n'est plus possible car une date ultérieure peut arriver après.
//        for (Transaction t : transactions) {
//            if (!t.getDate().isBefore(dateD) && !t.getDate().isAfter(dateF)) {
//                transactionChrono.add(t);
//            }
//        }
//        return transactionChrono;
//    }

    //Compte le nombre de transactions du type passé en paramètre
    @Override
    public int comptageType(String type){
        int compteur = 0;
        for (Transaction t : transactions){
            if (t.getType().equalsIgnoreCase(type)){
                compteur++;
            }
        }
        return compteur;
    }




}
