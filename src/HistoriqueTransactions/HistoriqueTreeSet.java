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

    //Suppression d'une transaction
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

    //Recherche d'une transaction par son id
    @Override
    public Transaction rechercheId(String id) throws HistoriqueException{
        for (Transaction t : transactions){
            if (t.getId().equals(id)){
                return t;
            }
        }
        throw new HistoriqueException("La transaction " + id + " n'existe pas.");
    }

    //Renvoi des transactions situées entre les deux dates passées en paramètre
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
