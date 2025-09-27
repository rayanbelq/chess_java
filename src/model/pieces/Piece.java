package model.pieces;
import model.*;

/**
 * Classe abstraite qui représente une pièce sur l'échiquier. Chaque piece est associé à un joueur et occupe une case de l'échiquier.
 */
public abstract class Piece {
    protected Joueur joueur;
    protected Case position;

    /** 
    * Construit la pièce à partir d'un joueur
    * @param joueur Le propriétaire de la pièce
    */
    public Piece(Joueur joueur) {
        this.joueur = joueur;
    }

    /** 
    * Retourne le proprio de la pièce
    * @return Le joueur propriétaire de la pièce
    */
    public Joueur getJoueur(){
        return joueur;
    }

    /** 
    * Retourne la case occupé par la pièce (ex : a2)
    * @return La case occupé par la pièce
    */
    public Case getPosition(){
        return position;
    }

    /** 
    * Modifie la position de la piece (ex : a4)
    * @param position La nouvelle case occupée par la pièce
    */
    public void setPosition(Case position){
        this.position = position;
    }

    /**
     * Vérifie si le déplacement vers une case est valide pour une pièce donnée. La logique exacte dépend de chaque type de pièce et est donc défini dans les classes filles.
     * @param destination La case où la pièce va se déplacer
     * @return true si le déplacement est possible, sinon false
     */
    public abstract boolean deplacementValide(Case destination);
    
    /**
     * Déplace la pièce vers la case voulu si le déplacement est possible. 
     * @param destination La case vers laquel la pièce veut se déplacer
     * @return true si le déplacement à été effectué, sinon false
     */
    public boolean deplacer(Case destination) {
    if (this.position == null) {
        System.out.println("Erreur");
        return false;
    }
    if (!deplacementValide(destination)) {
        System.out.println("Déplacement non valide pour cette pièce.");
        return false;
    }
    if (destination.estOccupee() && destination.getPiece().getJoueur() == this.joueur) {
        System.out.println("Impossible de capturer une pièce alliée.");
        return false;
    }
    this.position.retirerPiece();
    destination.placerPiece(this);
    this.position = destination;
    System.out.println("Boum ! La pièce prend ses bagages et avance.");
    return true;
    }
    
    /**
     * Retourne la pièce en texte (ex: P pour pion noir) 
     * @return La représentation textuelle de la pièce
     */
    @Override
    public abstract String toString();
}