package HistoriqueTransactions;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IHistorique {

        void addTransaction(Transaction t) throws HistoriqueException;
        void annulerTransaction(String id) throws HistoriqueException;
        Transaction rechercheId(String id) throws HistoriqueException;
        ArrayList<Transaction> parcoursChronologique(LocalDate dateD, LocalDate dateF);
        int comptageType(String type);

}
