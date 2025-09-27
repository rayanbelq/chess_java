package model.pieces;
import model.*;

public class Fou extends Piece {
    public Fou(Joueur joueur) { 
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

        //Déplacement en diagonale
        int colonneVerif = Math.abs(colonneDestination - colonneActuelle);
        int rangeeVerif = Math.abs(rangeeDestination - rangeeActuelle);
        return colonneVerif == rangeeVerif && colonneVerif != 0;
    }
    @Override
    public String toString() { 
        return joueur.getCouleur() == Couleur.BLANC ? "f" : "F"; // Simplification de else if : condition ? si_vrai : si_faux
    }
}
