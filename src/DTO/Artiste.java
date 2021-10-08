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
public class Artiste extends Personne{
    private String idAriste;
    private String idConservateur;

    public Artiste(String idAriste, String nom, String prenom) {
        super(nom, prenom);
        this.idAriste = idAriste;
    }

    public String getIdAriste() {
        return idAriste;
    }

    public void setIdAriste(String idAriste) {
        this.idAriste = idAriste;
    }

    public String getIdConservateur() {
        return idConservateur;
    }

    public void setIdConservateur(String idConservateur) {
        this.idConservateur = idConservateur;
    }

    @Override
    public String toString() {
        return "Artiste ID : " + idAriste + super.toString();
    }
    
}
