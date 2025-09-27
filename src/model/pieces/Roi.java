package model.pieces;
import model.*;

public class Roi extends Piece {
    public Roi(Joueur joueur) { 
        super(joueur); 
    }

    @Override
    public boolean deplacementValide(Case destination) {
        if (this.position == null || destination == null) {
            return false;
        }

        char colonneActuelle = this.position.getColonne();
        int rangeeActuelle = this.position.getLigne();

        char colonneDestination = destination.getColonne();
        int rangeeDestination = destination.getLigne();

        // Déplacement de 1 case uniquement
        int colonneVerif = Math.abs(colonneDestination - colonneActuelle);
        int rangeeVerif = Math.abs(rangeeDestination - rangeeActuelle);
        // On vérifie que la pièce ne reste pas sur place
        return (colonneVerif <= 1 && rangeeVerif <= 1) && !(colonneVerif == 0 && rangeeVerif == 0);
    }
    
    @Override
    public String toString() { 
        return joueur.getCouleur() == Couleur.BLANC ? "r" : "R"; // Simplification de else if : condition ? si_vrai : si_faux
    }
}