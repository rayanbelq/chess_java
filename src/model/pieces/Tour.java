package model.pieces;
import model.*;

public class Tour extends Piece {
    public Tour(Joueur joueur) { 
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
        // Horizontal
        if (rangeeActuelle == rangeeDestination && colonneActuelle !=colonneDestination){
            return true;
        }
        // Vertical 
        if (colonneActuelle == colonneDestination && rangeeActuelle != rangeeDestination) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() { 
        return joueur.getCouleur() == Couleur.BLANC ? "t" : "T"; // Simplification de else if : condition ? si_vrai : si_faux
    }
}