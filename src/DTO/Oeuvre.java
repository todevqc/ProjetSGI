/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Toufik DAHMANI
 */
public class Oeuvre {
    private String idOeuvre;
    private String titre;
    private String annee;
    private double prix;
    private double estimation;
    private char etat;
    private String idAriste;

    public Oeuvre(String idOeuvre, String titre, String annee, double estimation, String idAriste) {
        this.idOeuvre = idOeuvre;
        this.titre = titre;
        this.annee = annee;
        this.estimation = estimation;
        this.idAriste = idAriste;
        
        this.prix = 0;
        this.etat = 'E';
    }
    
    public void changerEtat(char etat){
        this.etat = etat;
    }
    
    public void prixPaye(double prix){
        this.prix = prix;
    }
    
    public double calculerCommission(double prix){
        return (prix-estimation)*0.25;
    }

    public String getIdOeuvre() {
        return idOeuvre;
    }

    public String getTitre() {
        return titre;
    }

    public String getAnnee() {
        return annee;
    }

    public double getPrix() {
        return prix;
    }

    public double getEstimation() {
        return estimation;
    }

    public char getEtat() {
        return etat;
    }

    public String getIdAriste() {
        return idAriste;
    }

    public void setIdAriste(String idAriste) {
        this.idAriste = idAriste;
    }

    @Override
    public String toString() {
        String afficher = "ID : " + idOeuvre + "  Titre : " + titre + "\nAnnee : " + annee;
        if (etat == 'E') {
            afficher += "\nÉtat : Exposé\n - Estimation : "+estimation+" $";
        }else{
            afficher += "\nÉtat : Vendu\n - Estimation : "+estimation+" $\n - Prix de Vente : "+prix+" $";
        }
        return afficher;
    }
        
}