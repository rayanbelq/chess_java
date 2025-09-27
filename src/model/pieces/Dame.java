package model.pieces;
import model.*;

public class Dame extends Piece {
    public Dame(Joueur joueur) { 
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

        int colonneVerif = Math.abs(colonneDestination - colonneActuelle);
        int rangeeVerif = Math.abs(rangeeDestination - rangeeActuelle);

        //Déplacement diagonale horizontal ou vertical
        boolean deplacementDiagonale = colonneVerif == rangeeVerif && colonneVerif !=0; // Reprend la règle de fou
        boolean deplacementHorizontal = rangeeActuelle == rangeeDestination && colonneActuelle !=colonneDestination; // Reprend la règle de tour
        boolean deplaceementVertical = colonneActuelle == colonneDestination && rangeeActuelle != rangeeDestination; // Reprend la règle de tour
        return deplacementDiagonale || deplacementHorizontal || deplaceementVertical;
    }
    @Override
    public String toString() { 
        return joueur.getCouleur() == Couleur.BLANC ? "d" : "D"; // Simplification de else if : condition ? si_vrai : si_faux
    } 
}