package model;
import model.pieces.*;



public class Case {
    private final char colonne;
    private final int ligne;
    private Piece piece;
    /**
    * Construit une case qui aura pour coordonnées une colonne(a-h) et une ligne (1-8)
    * @param colonne,ligne Les coordonées de la case
    */
    public Case(char colonne, int ligne) {
        this.colonne = colonne;
        this.ligne = ligne;
    }
    /**
     * Permet d'obtenir la colonne 
     * @return char (a-h)
     */
    public char getColonne() { 
        return colonne; 
    }
    /**
     * Permet d'obtenir la ligne
     * @return ligne (1-8)
     */
    public int getLigne() { 
        return ligne; 
    }
    /**
     * Permet de savoir quelle pièce occupe la case
     * @return piece
     */
    public boolean estOccupee() { 
        return piece != null; 
    }
    /**
     * Permet de placer une pièce sur la case
     * @param p
     */
    public void placerPiece(Piece p) { 
        this.piece = p; 
        p.setPosition(this); // Chaque pièce doit savoir ou elle se trouve
    }
    /**
     * Permet de retirer une pièce de la case
     */
    public void retirerPiece() { 
        this.piece = null; 
    }
    /**
     * Permet d'obtenir la pièce qui est sur la case
     * @return piece
     */
    public Piece getPiece() { 
        return piece; 
    }
}