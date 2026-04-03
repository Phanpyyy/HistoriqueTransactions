package HistoriqueTransactions;

import java.time.LocalDate;

public class Transaction implements Comparable<Transaction>{
    //Attributs --------------------------------------------------------------------------------------------------------
    private String id;
    private LocalDate date;
    private String type;
    private double montant;

    //Constructeurs ----------------------------------------------------------------------------------------------------
    public Transaction() {
    }

    public Transaction(String id, LocalDate date, String type, double montant) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.montant = montant;
    }

    //Accesseurs -------------------------------------------------------------------------------------------------------
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }


    //Méthodes ---------------------------------------------------------------------------------------------------------
    //Tri des données par dates et si même date, tri par id
    @Override
    public int compareTo(Transaction t) {
        int resultatDate = this.date.compareTo(t.getDate());
        if (resultatDate != 0) {
            return resultatDate;
        }
        else {
            return this.id.compareTo(t.getId());
        }
    }

    @Override
    public String toString() {
        return getId() + " - Type : " + getType() + " - Date : " + getDate()
                + "\nMontant : " + getMontant() + "\n";
    }
}
