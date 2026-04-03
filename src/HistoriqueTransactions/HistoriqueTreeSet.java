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
    public void addTransaction(Transaction t) throws HistoriqueException{
        if (transactions.contains(t)){
            throw new HistoriqueException("La transaction existe déjà.");
        }
        else {
            transactions.add(t);
        }
    }

    //Suppression d'une transaction
    public void annulerTransaction(String id) throws HistoriqueException{
        Iterator<Transaction> iterateur = transactions.iterator();

        long debut = System.nanoTime();
        while (iterateur.hasNext()){
            Transaction t = iterateur.next();
            if (t.getId().equals(id)){
                iterateur.remove();
                System.out.println("La transaction a été supprimée.");
                return;
            }

        }
        long fin = System.nanoTime();
        System.out.println("Temps de suppression : " + (fin-debut)*1e-6 + " ms.");
        throw new HistoriqueException("La transaction n'existe pas.");
    }

    //Recherche d'une transaction par son id
    public Transaction rechercheId(String id) throws HistoriqueException{
        for (Transaction t : transactions){
            if (t.getId().equals(id)){
                return t;
            }
        }
        throw new HistoriqueException("La transaction " + id + " n'existe pas.");
    }

    //Renvoi des transactions situées entre les deux dates passées en paramètre
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
