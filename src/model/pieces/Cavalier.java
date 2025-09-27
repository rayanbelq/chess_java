package model.pieces;
import model.*;

public class Cavalier extends Piece {
    public Cavalier(Joueur joueur) { 
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

        //Déplacement en "L"
        int colonneVerif = Math.abs(colonneDestination - colonneActuelle);
        int rangeeVerif = Math.abs(rangeeDestination - rangeeActuelle);
        return (colonneVerif == 2 && rangeeVerif == 1) || (colonneVerif == 1 && rangeeVerif == 2);  
    }
    
    @Override
    public String toString() { 
        return joueur.getCouleur() == Couleur.BLANC ? "c" : "C"; // Simplification de else if : condition ? si_vrai : si_faux
    }
}