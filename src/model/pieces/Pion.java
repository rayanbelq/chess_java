package model.pieces;
import model.*;

public class Pion extends Piece {
    public Pion(Joueur joueur) { 
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


        // Savoir si le pion monte (blanc) ou si le pion descend (noir)
        int direction = (joueur.getCouleur() == Couleur.BLANC) ? 1 : -1; // écriture qui simplifie les if et les else : (condition) ? vrai : faux

        int colonneVerif = colonneDestination - colonneActuelle;
        int rangeeVerif = rangeeDestination - rangeeActuelle;

        // Avancer tout droit d'une case
        if (colonneVerif == 0 && rangeeVerif == direction) {
             return !destination.estOccupee();
        }
        boolean premierCoup = (joueur.getCouleur() == Couleur.BLANC && rangeeActuelle == 2) || (joueur.getCouleur() == Couleur.NOIR && rangeeActuelle == 7); // Si la couleur du joueur est blanc alors sa rangée de départ est la 2 sinon il est joueur noir et sa rangée de départ est 7.

        // Si il s'agit du premier coup du joueur alors il avance de 2 vers le haut ou vers le bas selon sa couleur
        if (colonneVerif == 0 && rangeeVerif == 2 * direction && premierCoup){ 
            return true;
        }

        // Capturer une pièce en diagonale
        if (Math.abs(colonneVerif) == 1 && rangeeVerif == direction ){
            return destination.estOccupee();
        }
        return false;
    }
    @Override
    public String toString() { 
        return joueur.getCouleur() == Couleur.BLANC ? "p" : "P"; // Simplification de else if : condition ? si_vrai : si_faux
    } 
}