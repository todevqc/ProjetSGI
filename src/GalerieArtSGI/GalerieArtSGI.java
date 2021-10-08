/*
 * Toufik DAHMANI
 * Projet intégrateur
 */
package GalerieArtSGI;

import DAO.Galerie;
import java.util.Scanner;

/**
 *
 * @author Toufik DAHMANI
 */
public class GalerieArtSGI {
    static Scanner input = new Scanner(System.in);
    
    static boolean saisieCorrect;
    static int choix;
    /*
    *   declaration des variables de recuperation des saisie
    *   (je les utilises dans toutes les methodes)
    */
    static String id = "";
    static String nom = "";
    static String prenom = "";
    static String titreOeuvre = "";
    static String anneOeuvre = "";
    static double estimation = 0;
    static String idArtiste = "";
    static String idOeuvre = ""; 
    // couleurs pour les differents messages a afficher
    static final String ROUGE = "\033[0;31m";
    static final String VERT = "\033[0;32m";
    
    
    static Galerie galerie = new Galerie(); 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //boucle de mon application
        
        //  variables de choix d'option dans le menu        
        int selection;
        
        do{
        //affichage du menu et recuperation du choix utilisateur            
        selection = menu();

            switch(selection){
                case (1) -> {
                    afficherTitreMenu("AJOUTER UN CONSERVATEUR");
                    addConservateur();
                    System.out.println(VERT+"Conservateur ajouté avec succès");
                    break;
                }
                case (2) -> {
                    afficherTitreMenu("  AJOUTER UN ARTISTE   ");
                    addArtiste();
                    System.out.println(VERT+"Artiste ajouté avec succès");
                    break;
                }
                case (3) -> {
                    afficherTitreMenu("  AJOUTER UNE OEUVRE   ");
                    addOeuvre();
                    break;
                }
                case (4) -> {
                    afficherTitreMenu("LISTE DES CONSERVATEURS");
                    System.out.println(galerie.afficherConservateurs());
                    break;
                }
                case (5) -> {
                    afficherTitreMenu("  LISTE DES ARTISTES   ");
                    System.out.println(galerie.afficherArtistes());
                    break;
                }
                case (6) -> {
                    afficherTitreMenu("  VENDRE UNE OEUVRE    ");
                    sellOeuvre();
                    break;
                }
                case (7) -> {
                    afficherTitreMenu("  RAPORT DES OEUVRES   ");
                    System.out.println(galerie.rapportOeuvres());
                    break;
                }
                case (8) -> {
                    afficherTitreMenu("        QUITTER        ");
                    break;
                }
            }
        }while(selection !=8);
    }
    
    /****************************************************************
                methodes estethique pour les menus
    ****************************************************************/
    /*
    *   la methode du menu et de selection des operations à faire
    */
    public static int menu(){
        System.out.println("\n-----------------------------------------------");
        System.out.println(VERT+"||        ***   Galerie Art SGI   ***        ||");
        System.out.println("===============================================");
        System.out.println("   1 - Ajouter conservateur");
        System.out.println("   2 - Ajouter un artiste");
        System.out.println("   3 - Ajouter une oeuvre");
        System.out.println("   4 - Afficher les conservateurs");
        System.out.println("   5 - Afficher les artistes");
        System.out.println("   6 - Vendre une oeuvre");
        System.out.println("   7 - Rapport des oeuvres");
        System.out.println("   8 - Quitter");
        do {
            saisieCorrect = true;
            System.out.print("Faites votre choix : ");
            choix = input.nextInt();
            if((choix < 1) || (choix > 8)){
                System.out.println(ROUGE+"Choix erronné !!");
                saisieCorrect=false;
            }
        } while (!saisieCorrect);
        return choix;
    }
    
    /*
    *  methode pour le style des titres de menu
    */
    public static void afficherTitreMenu(String titre){
        System.out.println("\n-----------------------------------------------");
        System.out.println(VERT+"||          "+titre+"          ||");
        System.out.println("===============================================");
    }
    
    /****************************************************************
                        les methodes du menu
    ****************************************************************/
    
    /*
    *   ajouter conservateur avec test de saisie et messages d'erreurs
    */
    public static void addConservateur() {
        do{
            saisieCorrect = true;
            // methode de saisie de l'ID le test et affiche les messages d'erreurs 
            id = saisieID();
            // verification si ID saisie existe déjà, alors demande de saisire a nouveux
            if (galerie.isConservateurExiste(id)) {
                System.out.println(ROUGE+"ERREUR : L'Identifiant du conservateur que vous avez saisie existe déjà !!");
                System.out.println(VERT+"Veuillez saisir un nouvel ID :");
                saisieCorrect = false;
            }
        }while(!saisieCorrect);
        System.out.print(" Nom du Conservateur :");
        nom = input.next();
        input.nextLine(); 
        System.out.print(" Prénom du Conservateur :");
        prenom = input.nextLine();

        galerie.ajouterConservateur(id, nom, prenom);
    }
        
    /*
    *   ajouter artiste avec test de saisie et messages d'erreurs
    */
    public static void addArtiste() {
        do{
            saisieCorrect = true;
            // methode de saisie de l'ID le test et affiche les messages d'erreurs 
            id = saisieID();
            // verification si ID saisie existe déjà, alors demande de saisire a nouveux
            if (galerie.isArtistExiste(id)) {
                System.out.println(ROUGE+"ERREUR : L'Identifiant de l'artiste que vous avez saisie existe déjà !!");
                System.out.println(VERT+"Veuillez saisir un nouvel ID :");
                saisieCorrect = false;
            }
        }while(!saisieCorrect);
        
        System.out.print(" Nom de l'artiste :");
        nom = input.next();
        input.nextLine(); 
        System.out.print(" Prénom de l'artiste :");
        prenom = input.nextLine();

        galerie.ajouterArtiste(id, nom, prenom);
    }
    
    /*
    *   ajouter une oeuvre avec test de saisie et messages d'erreurs
    */
    public static void addOeuvre() {

        //   si liste des artiste vide 
        if (galerie.artistes.isEmpty()) {             
            
            System.out.println(ROUGE+" !! La liste des artiste est presentement VIDE !!");
            System.out.println(ROUGE+" **//** Veuillez ajouter un artiste avant de saisir l'oeuvre **\\\\**");
            
            
        }else{
            
            do{
                // methode de saisie de l'ID le test et affiche les messages d'erreurs 
                id = saisieID();
                // verification si ID saisie existe déjà, alors demande de saisire a nouveux
                if (galerie.isOeuvreExiste(id)) {
                    System.out.println(ROUGE+"ERREUR : L'Identifiant de l'oeuvre que vous avez saisie existe déjà !!");
                    System.out.println(VERT+"Veuillez saisir un nouvel ID :");
                    saisieCorrect = false;
                }
            }while(!saisieCorrect);

            System.out.print(" Titre de l'oeuvre :");
            titreOeuvre = input.next();
            input.nextLine(); 
            System.out.print(" Année de l'oeuvre :");
            anneOeuvre = input.nextLine();
            System.out.print(" Estimation de l'oeuvre :");
            estimation = input.nextDouble();        
            
            do {
                saisieCorrect = true;
                System.out.print(" ID Artiste qui a créer l'oeuvre :");
                idArtiste = input.next(); 
                // tester si l'artiste existe ou non
                if (!galerie.isArtistExiste(idArtiste)) {
                    System.out.println(ROUGE+"*****   ID de l'artiste saisie n'existe pas !!!!   *****");
                    System.out.println(VERT+"Veuillez entrer un ID d'artiste existant.\n");
                    saisieCorrect = false;
                }
            } while (!saisieCorrect); 
        galerie.ajouterOeuvre(id, titreOeuvre, anneOeuvre, estimation, idArtiste);
        }
    }
    
    /*
    *   vendre une oeuvre
    */
    public static void sellOeuvre() {  
        //   verifier si liste des oeuvres est non vide
        if (!galerie.oeuvres.isEmpty()) {
            id = saisieID();
            // test existance de l'oeuvre
            if (galerie.isOeuvreExiste(id)) {                
                //  saisie du prix de vente
                System.out.print(" Prix de vente de l'oeuvre :");
                double prixVente = input.nextDouble();
                //  tentative de vente de l'oeuvre
                if (galerie.vendreOeuvre(id, prixVente)) {
                    System.out.println(VERT+" /*/  Succés de la vente  \\*\\");
                }
            }else{
                System.out.println(ROUGE+" /*/  Erreur  \\*\\");
                //  l'oeuvre recherché n'existe pas
                System.out.println(ROUGE+"L'Identifiant saisie ne corresponds à aucune oeuvre de la liste");
                //  donner possibilité de voir liste des oeuvres exposé ou revenir au menu
                galerie.listeOeuvresOuMenu();
            }
            
        }else{
            System.out.print(ROUGE+"!! La liste des Oeuvres est presentement VIDE !!");
        }
    }
    
    
    /*
    *   permet de saisire ID, test sa longeur et
    *   retourne ID une fois bien valide
    */
    public static String saisieID(){
        id = "";
        do{
            saisieCorrect = true;
            System.out.print(" Identifiant (5 caractères) :");
            id = input.next().toUpperCase();
            if (id.length()!=5) {
                System.out.println(ROUGE +" *****   ID saisie INCORRECT !!!!   *****");
                System.out.println(ROUGE +"L'ID doit contenir exactement 5 caractéres.\n");
                saisieCorrect = false;
            }
        } while (!saisieCorrect);
        return id;
    }
}
