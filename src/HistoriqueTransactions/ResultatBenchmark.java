package HistoriqueTransactions;

public class ResultatBenchmark {
    //Attributs --------------------------------------------------------------------------------------------------------
    private IHistorique historique;
    private double tempsGenerationDonnees;
    private double calculMemoireGenerationDonnees;
    private double calculTemps;
    private double calculMemoire;

    public ResultatBenchmark(double calculTemps, double calculMemoire) {
        this.calculTemps = calculTemps;
        this.calculMemoire = calculMemoire;
    }

    //Constructeurs ----------------------------------------------------------------------------------------------------
    public ResultatBenchmark(IHistorique historique, double tempsGenerationDonnees, double calculMemoireGenerationDonnees, double calculTemps, double calculMemoire) {
        this.historique = historique;
        this.tempsGenerationDonnees = tempsGenerationDonnees;
        this.calculMemoireGenerationDonnees = calculMemoireGenerationDonnees;
        this.calculTemps = calculTemps;
        this.calculMemoire = calculMemoire;
    }

    public IHistorique getHistorique() {
        return historique;
    }

    public double getCalculTemps() {
        return calculTemps;
    }

    public double getCalculMemoire() {
        return calculMemoire;
    }

    public double getTempsGenerationDonnees() {
        return tempsGenerationDonnees;
    }

    public double getCalculMemoireGenerationDonnees() {
        return calculMemoireGenerationDonnees;
    }
}
