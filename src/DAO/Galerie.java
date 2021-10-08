/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Toufik DAHMANI
 */
public class Galerie {
    /*
    *   listes des differents composants de notre system
    */
    public ArrayList<Artiste> artistes = new ArrayList<>();
    public ArrayList<Conservateur> conservateurs = new ArrayList<>();
    public ArrayList<Oeuvre> oeuvres = new ArrayList<>();
    
    // couleurs pour les differents messages a afficher
    static final String ROUGE = "\033[0;31m";
    static final String VERT = "\033[0;32m";
                
    // variable de saisie (input)
    static Scanner input = new Scanner(System.in);
        
    //   declaration des variables en global pour gagner en eficassité
    public String id = "";
    public boolean saisieCorrect;
    public boolean oeuvreExiste;
    public int choix;
    public String affiche = "";
    public String idConservateur = ""; 
    public String nomConservateur = "";
    public String prenomConservateur = "";
    public double totalEstimation = 0;
    public double totalPrixVente = 0;
    public int nbrOeuvresVendu = 0;
    
    /*
    *   la methode d'ajout d'un  artiste
    */
    public void ajouterArtiste(String idAriste, String nom, String prenom){
        Artiste art = new Artiste(idAriste, nom, prenom);
        artistes.add(art);
        //   si liste conservateur vide 
        if (conservateurs.isEmpty()) {
            //  ajouter conservateur a la liste avec tous les tests
            System.out.println(ROUGE+" !! La liste des conservateur est presentement VIDE !!");
            System.out.println(VERT+" **//** Ajouter un conservateur et l'assigner **\\\\**");
            
                    
            do{
                saisieCorrect = true;
                // methode de saisie de l'ID le test et affiche les messages d'erreurs 
                do{
                    saisieCorrect = true;
                    System.out.print(" Identifiant (5 caractères) :");
                    id = input.nextLine().toUpperCase();
                    if (id.length()!=5) {
                        System.out.println(ROUGE +" *****   ID saisie INCORRECT !!!!   *****");
                        System.out.println(ROUGE +"L'ID doit contenir exactement 5 caractéres.\n");
                        saisieCorrect = false;
                    }
                } while (!saisieCorrect);
                // verification si ID saisie existe déjà, alors demande de saisire a nouveux
                if (isConservateurExiste(id)) {
                    System.out.println(ROUGE+"ERREUR : L'Identifiant du conservateurque vous avez saisie existe déjà !!");
                    System.out.println("Veuillez saisir un nouvel ID :");
                    saisieCorrect = false;
                }
            }while(!saisieCorrect);
            System.out.print(" Nom du Concervateur :");
            nomConservateur = input.nextLine();
            System.out.print(" Prénom du Concervateur :");
            prenomConservateur = input.nextLine();

            ajouterConservateur(id, nomConservateur, prenomConservateur);
            
            //  assigner ce conservateur a cet artiste
            art.setIdConservateur(id);
            
        }else{
            //   selectionner conservateur déjà existant a assigner
            art.setIdConservateur(assignerUnConcervateur());            
        }
    }
    
    /*
    *   la methode d'ajout d'un conservateur
    */
    public void ajouterConservateur(String idConservateur, String nom, String prenom){
        conservateurs.add(new Conservateur(idConservateur, nom, prenom));
    }
    
    /*
    *   la methode d'ajout d'une oeuvre
    */
    public void ajouterOeuvre(String idOeuvre, String titre, String annee, double estimation, String idAriste){
        oeuvres.add(new Oeuvre(idOeuvre, titre, annee, estimation, idAriste));
        System.out.println(VERT+"Conservateur ajouté avec succès");
    }
    
    /*
    *   afficher la liste des artistes, ou afficher VIDE si aucun artiste
    */
    public String afficherArtistes(){
        affiche = "";
        //  Verifie si la liste n'est pas vide pour afficher son contenu, sinon afficher un message explissite
        if (!artistes.isEmpty()) {
            for (Artiste artiste : artistes) {
                //  afficher les infos de l'artiste et les infos du conservateur assigné
                affiche += artiste+"\nSon Conservateur Assigné : "+afficherConservateurAssigner(artiste.getIdConservateur())
                        +"\n-----------------------------------------------\n";
            }
        }else{
            affiche = ROUGE+" !! La liste des Artistes est presentement VIDE !!";
        }
        return affiche;
    }
    
    /*
    *   afficher la liste des conservateur, ou afficher VIDE si aucun artiste
    */
    public String afficherConservateurs(){
        affiche = "";
        //  Verifie si la liste n'est pas vide pour afficher son contenu, sinon afficher un message explissite
        if (!conservateurs.isEmpty()) {
            for (Conservateur conservateur : conservateurs) {
                affiche += conservateur+"\n"
                        +"\n-----------------------------------------------\n";
            }
        }else{
            affiche = ROUGE+" !! La liste des Conservateurs est presentement VIDE !!";
        }
        return affiche;
    }
    
    /*
    *   methode de vente d'une oeuvre
    */
    public boolean vendreOeuvre(String idOeuvre,double prix){
        idConservateur = ""; 
        for (int i = 0;i < oeuvres.size(); i++) {
            //  selectionner l'oeuvre qu'en souhaite vendre     
            if (oeuvres.get(i).getIdOeuvre().equalsIgnoreCase(idOeuvre)) {
                //   verifier si oeuvre est Exposé
                if (oeuvres.get(i).getEtat()=='E'){                
                    //  verifier si prix de vente > estimation, pour faire la vente
                    if (prix > oeuvres.get(i).getEstimation()) {
                        oeuvres.get(i).changerEtat('V');
                        oeuvres.get(i).prixPaye(prix);
                      
                        //  ajouter la commission au conservateur responsable
                        addCommissionConservateur(oeuvres.get(i).getIdAriste(), prix);
                        
                        return true;
                    }else{
                        System.out.println(ROUGE+" /*/  Erreur, La vente n'a pas pu se faire");
                        System.out.println(ROUGE+"Le prix de vente est inferieur à l'estimation de loeuvre");
                    }
                }else{
                    System.out.println(ROUGE+" /*/  Erreur, La vente n'a pas pu se faire");
                    System.out.println(ROUGE+"L'ouvre selectionner à déjà été vendue");
                    //  donner possibilité de voir liste des oeuvres exposé ou revenir au menu
                    listeOeuvresOuMenu();
                }
            }
        }
        return false;
    }
    
    /*
    *   Affiche la liste des oeuvres, le total des vente et le total des valeurs estimées
    */
    public String rapportOeuvres(){
        affiche = "";
        totalEstimation = 0;
        totalPrixVente = 0;
        nbrOeuvresVendu = 0;
        //  Verifie si la liste n'est pas vide pour afficher son contenu, sinon afficher un message explissite
        if (!oeuvres.isEmpty()) {
            System.out.println(VERT+"  **  Liste des oeuvres :");
            for (Oeuvre oeuvre : oeuvres) {
                //  afficher les infos de l'oeuvre et son etat
                affiche += oeuvre+"\n-----------------------------------------------\n";
                if (oeuvre.getEtat()=='V') {
                    totalPrixVente += oeuvre.getPrix();
                    nbrOeuvresVendu ++;
                }
                totalEstimation += oeuvre.getEstimation();
            }
            affiche += VERT+"  **  Recapitulatif des ventes :";
            affiche += "\n  Nombre total des oeuvres vendues :"+nbrOeuvresVendu;
            affiche += "\n  Total des ventes :"+totalPrixVente+" $";
            affiche += "\n  Total des estimations:"+totalEstimation+" $";
        }else{
            affiche = ROUGE+" !! La liste des oeuvres est presentement VIDE !!";
        }
        return affiche;
    }
    /*
    *   affiche les conservateurs et demande de choisir lequel assigner a l'artiste
    */
    public String assignerUnConcervateur(){
        System.out.println(VERT+"  * Assigner conservateur N°: ");
        System.out.println("  ----------------------------");
        if (!conservateurs.isEmpty()) {
            for (int i=0;i< conservateurs.size(); i++) {
                System.out.println(" N° "+(i+1)+": "+conservateurs.get(i).getNom()+"  "
                        +conservateurs.get(i).getPrenom()+"\tID : "+conservateurs.get(i).getIdConservateur());
            }
        }else{
            System.out.println(ROUGE+" !! La liste des Conservateurs est presentement VIDE !!");
        }
        do {
            saisieCorrect = true;
            System.out.print("Choix N°: ");
            choix = input.nextInt();
            if((choix < 1) || (choix > conservateurs.size())){
                System.out.println(ROUGE +"Choix erronné !!");
                saisieCorrect=false;
            }
        } while (!saisieCorrect);
        return conservateurs.get(choix-1).getIdConservateur();
    }   
    
    /*
    *   charcher un conservateur grace a son id et retourne son toString()
    */
    public String afficherConservateurAssigner(String idConservateur){
        for (Conservateur conservateur : conservateurs) {
            if (conservateur.getIdConservateur().equals(idConservateur)) {
                return conservateur.getNom()+" "+conservateur.getPrenom()
                        +" - ID : "+conservateur.getIdConservateur();
            }
        }
        return ROUGE+"Aucun conservateur assigné !";
    } 
       
       
    /*
    *   ajouter la commission au conservateur grace a l'id de l'artiste à qui il est assigné
    */
    public void addCommissionConservateur(String idAriste, double prix){
        // trouver idConservateur grace a l'ID de l'artiste auquel il est assigné
        for (int i = 0; i < artistes.size(); i++) {
            if (artistes.get(i).getIdAriste().equalsIgnoreCase(idAriste)) {
                // trouver le conservateur dans liste des conservateur et modifier sa commission
                for (Conservateur conservateur : conservateurs) {
                    if (conservateur.getIdConservateur().equals(artistes.get(i).getIdConservateur())) {
                        conservateur.setCommission(oeuvres.get(i).calculerCommission(prix));
                    }
                }
            }
        }
    }    
      
    /*
    *   retourne VRAI si le conservateur existe dans la liste false sinon
    */
    public boolean isConservateurExiste(String idConservateur){
        for (int i = 0; i < conservateurs.size(); i++) {
            if (conservateurs.get(i).getIdConservateur().equalsIgnoreCase(idConservateur )) {
                return true;
            }        
        }      
        return false;      
    }
        
    /*
    *   retourne VRAI si l'artiste existe dans la liste false sinon
    */
    public boolean isArtistExiste(String idArtiste){
        for (int i = 0; i < artistes.size(); i++) {
            if (artistes.get(i).getIdAriste().equalsIgnoreCase(idArtiste)) {
                return true;
            }        
        }      
        return false;      
    }
    
    /*
    *   retourne VRAI si l'oeuvre existe dans la liste false sinon
    */
    
    public boolean isOeuvreExiste(String idOeuvre){
        for (int i = 0; i < oeuvres.size(); i++) {
            if (oeuvres.get(i).getIdOeuvre().equalsIgnoreCase(idOeuvre)) {
                return true;
            }
        }
        return false;
    }
    
    /*
    *   demander a l'utilisateur de choisir entre voir liste des oeuvre Exposé ou aller au menu principal
    */
    public void listeOeuvresOuMenu(){
        choix = 0;
        int nbrOeuvreExpose = 0;
        System.out.println(VERT+"Faites un choix :");
        System.out.println(" 1 - Voir liste oeuvres exposé (avant de ressayer de vendre)");
        System.out.println(" 2 - Retour au menu principal");
        do {
            saisieCorrect = true;
            System.out.print("Choix N°: ");
            choix = input.nextInt();
            if((choix < 1) || (choix > 2)){
                System.out.println(ROUGE +"Choix erronné !!");
                saisieCorrect=false;
            }
        } while (!saisieCorrect);
        if (choix == 1) {
            // liste des oeuvres Exposés
            for (Oeuvre oeuvre : oeuvres) {
                //  afficher les infos de l'oeuvre et son etat
                if (oeuvre.getEtat()=='E') {
                    System.out.println("ID: "+oeuvre.getIdOeuvre()+" Titre: "+oeuvre.getTitre());
                    nbrOeuvreExpose ++;
                }
            }
            if (nbrOeuvreExpose == 0) {
                System.out.println(ROUGE +"Désolé, Toute les oeuvre de notre liste sont dèja vendue !!");
            }
            input.nextLine();
        }
    }
    
}
