package model;

public class Joueur {
    private String nom;
    private Couleur couleur;
    /**
     * Permet de crÃ©er un joueur avec son nom et sa couleur (noir ou blanc)
     * @param nom
     * @param couleur
     */
    public Joueur(String nom, Couleur couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }
    /**
     * Permet d'obtenir le nom du joueur
     * @return nom
     */
    public String getNom() { 
        return nom; 
    }
    /**
     * Permet d'obtenir la couleur du joueur
     * @return couleur
     */
    public Couleur getCouleur() { 
        return couleur; 
    }
    /**
     * Permet de changer le nom d'un joueur
     * @param nom
     */
    public void setNom(String nom) { 
        this.nom = nom; 
    }
    /**
     * Permet de changer la couleur d'un joueur
     * @param couleur
     */
    public void setCouleur(Couleur couleur) { 
        this.couleur = couleur; 
    }
}