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
public class Conservateur extends Personne{
    private String idConservateur;
    private double commission;

    public Conservateur(String idConservateur, String nom, String prenom) {
        super(nom, prenom);
        this.idConservateur = idConservateur;
        this.commission = 0;
    }

    public String getIdConservateur() {
        return idConservateur;
    }

    public void setIdConservateur(String idConservateur) {
        this.idConservateur = idConservateur;
    }

    public void setCommission(double commission) {
        this.commission += commission;
    }

    @Override
    public String toString() {
        return "Conservateur ID : " + idConservateur + super.toString()
                +"\n   Total des commissions : "+commission+" $";
    }
    
}
