package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.*;

public class HistoriqueTreeMap implements IHistorique {

    //Attributs
    private TreeMap<String, Transaction> transactionTreeMap;


    //Constructeurs
    public HistoriqueTreeMap(){
        this.transactionTreeMap = new TreeMap<>();
    }

    //Methodes
    //Ajout d'une transaction

    @Override
    public void addTransaction(Transaction t) throws HistoriqueException{
        if (transactionTreeMap.containsKey(t.getId())){
            throw new HistoriqueException("La transaction existe déjà.");
        }
        else {
            transactionTreeMap.put(t.getId(), t);
        }
    }

    //Suppression d'une transaction
    @Override
    public void annulerTransaction(String id) throws HistoriqueException {
        long debut = System.nanoTime();

        // Pas besoin d'itérateur ! .remove(id) le fait instantanément
        if (transactionTreeMap.remove(id) != null) {
            long fin = System.nanoTime();
            System.out.println("Temps de suppression : " + (fin - debut) * 1e-6 + " ms.");
        } else {
            throw new HistoriqueException("La transaction n'existe pas.");
        }
    }

    //Recherche d'une transaction par son id
    @Override
    public Transaction rechercheId(String id) throws HistoriqueException {
        Transaction t = transactionTreeMap.get(id);
        if (t == null) throw new HistoriqueException("ID inconnu.");
        return t;
    }

    //Renvoi des transactions situées entre les deux dates passées en paramètre
    @Override
    public ArrayList<Transaction> parcoursChronologique(LocalDate dateD, LocalDate dateF) {
        ArrayList<Transaction> resultat = new ArrayList<>();
        for (Transaction t : transactionTreeMap.values()) {
            if (!t.getDate().isBefore(dateD) && !t.getDate().isAfter(dateF)) {
                resultat.add(t);
            }
        }
        resultat.sort(Comparator.comparing(Transaction::getDate));
        return resultat;
    }

    //Compte le nombre de transactions du type passé en paramètre
    @Override
    public int comptageType(String type) {
        int compteur = 0;
        for (Transaction t : transactionTreeMap.values()){
            if (t.getType().equalsIgnoreCase(type)){
                compteur++;
            }
        }
        return compteur;
    }
    }
