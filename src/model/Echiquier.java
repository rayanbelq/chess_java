package model;
import model.pieces.*;

/**
 * Représente un échiquier 8x8. Il contient une grille de cases et gère l'affichage ainsi que l'initialisation du plateau.
 */
public class Echiquier {
    private final Case[][] cases = new Case[8][8];

    /**
     * Constructeur de l'échiquier. Il initialise les 64 cases avec leurs coordonnées respectives (colonnes de 'a' jusqu'à 'h' et lignes de 1 jusqu'à 8))
     */
    public Echiquier() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cases[i][j] = new Case((char)('a' + j), i + 1); 
            }
        }
    }

    /**
     * Affiche l'état actuel de l'échiquier dans la console. Les pièces sont représenter grâce à leurs toString() et les cases vide par des '.'.
     */
    public void afficherEchiquier() {
        System.out.println("  +-----------------+"); 
        for (int i = 7; i >= 0; i--) {
            System.out.print((i+1)+" | "); // Ecrire les colonnes de 8 à 1
            for (int j = 0; j < 8; j++) {
                Piece p = cases[i][j].getPiece();
                System.out.print(p == null ? ". " : p.toString() + " "); //Place les symboles des pièces
            }
            System.out.println("|");
        }
        System.out.println("  +-----------------+"); 
        System.out.println("    a b c d e f g h"); // Ecrire la ligne de a à h
    }
    
    /**
     * Récupère une case de l'échiquier grâce à sa ligne et sa colonne
     * @param col La colonne (entre 'a' et 'h')
     * @param ligne La ligne (entre '1' et '8')
     * @return La case correspondante
     */
    public Case getCase(char col, int ligne) {
        col = Character.toLowerCase(col); //Evite les erreurs de saisies en majuscules
        return cases[ligne - 1][col - 'a']; 
    }

    /**
     * Initialise toutes les pièces des blancs et des noirs au lancement de la partie
     * @param blanc Le joueur qui à les blancs
     * @param noir Le joueur qui à les noirs
     */
    public void initialiserPieces(Joueur blanc, Joueur noir) {
        // Placer toutes les pièces à la bonne position
        for (int i = 0; i < 8; i++) {
            cases[1][i].placerPiece(new Pion(blanc));
            cases[6][i].placerPiece(new Pion(noir));
        }

        cases[0][0].placerPiece(new Tour(blanc));
        cases[0][7].placerPiece(new Tour(blanc));
        cases[7][0].placerPiece(new Tour(noir));
        cases[7][7].placerPiece(new Tour(noir));

        cases[0][1].placerPiece(new Cavalier(blanc));
        cases[0][6].placerPiece(new Cavalier(blanc));
        cases[7][1].placerPiece(new Cavalier(noir));
        cases[7][6].placerPiece(new Cavalier(noir));

        cases[0][2].placerPiece(new Fou(blanc));
        cases[0][5].placerPiece(new Fou(blanc));
        cases[7][2].placerPiece(new Fou(noir));
        cases[7][5].placerPiece(new Fou(noir));

        cases[0][3].placerPiece(new Dame(blanc));
        cases[0][4].placerPiece(new Roi(blanc));
        cases[7][3].placerPiece(new Dame(noir));
        cases[7][4].placerPiece(new Roi(noir));
    }
}