package model;
import model.pieces.*;

public class ReglesEchecs {
    /**
     * Permet de vérifier si un coup met le roi en Echec
     * @param echiquier
     * @param couleur
     * @param partie
     * @param afficher
     * @return boolean
     */
    public boolean estEchec(Echiquier echiquier, Couleur couleur,Partie partie,boolean afficher) {
        // Cherchons la case du roi
        Case caseRoi = null;
        for (char colonne = 'a'; colonne <= 'h'; colonne++){ // parcourt toutes les colonnes
            for (int rangee = 1; rangee  <= 8; rangee++){ // parcourt toutes les lignes
                Case c = echiquier.getCase(colonne,rangee); // devient toutes les cases du plateau une à une
                if(c.estOccupee()) { // si la case n'est pas vide
                    Piece piece = c.getPiece(); // On definit une pièce qui prend la valeur non nulle de c

                    if (piece instanceof Roi && piece.getJoueur().getCouleur() == couleur) { // si c'est le roi de la couleur recherché
                        caseRoi = c ; // la case du roi est alors trouvé
                        break ;
                    }
                }
            }
            if (caseRoi != null) break;
        }
        if (caseRoi == null){
            return false;
        }

        // Verifions si il est en echec

        Couleur couleurAdverse = (couleur == Couleur.BLANC) ? Couleur.NOIR : Couleur.BLANC ;
        for (char colonne ='a'; colonne <= 'h';colonne++){ // parcourt des colonnes
            for (int rangee = 1; rangee <= 8; rangee++){ // parcourt des lignes
                Case c = echiquier.getCase(colonne,rangee); // c devient toutes les cases une à une
                if (c.estOccupee()) { // si c n'est pas vide
                    Piece pieceAdverse = c.getPiece(); //pieceAdverse devient la piece
                    if (pieceAdverse.getJoueur().getCouleur()==couleurAdverse){ // si pieceAdverse est de la couleur adverse
                        boolean peutAttaquer = false; 
                        if (pieceAdverse instanceof Cavalier || pieceAdverse instanceof Roi) {
                            peutAttaquer = pieceAdverse.deplacementValide(caseRoi);
                        }
                        else if (pieceAdverse.deplacementValide(caseRoi) && partie.CheminLibre(c,caseRoi)){
                            peutAttaquer = true;
                        }

                        if (peutAttaquer){  // et que cette piece peut attaquer le roi
                           if (afficher) {
                                System.out.println(pieceAdverse.getClass().getSimpleName() + " tente une attaque !"); // alors echec
                            }    
                            return true;
                        }
                    }
                }
            }
        }
        // Aucun echec
        return false;
    }
    /**
     * Permet de vérifier si un coup crée un Echec et Mat
     * @param echiquier
     * @param couleur
     * @param partie
     * @return boolean
     */
    public boolean estEchecEtMat(Echiquier echiquier, Couleur couleur,Partie partie){
        if (!estEchec(echiquier,couleur,partie,false)) {
            return false; // Si pad d'échec pas d'échec et mat
        }

        for (char colonne ='a'; colonne <= 'h';colonne++){ // parcourt des colonnes
            for (int rangee = 1; rangee <= 8; rangee++){ // parcourt des lignes
                Case origine = echiquier.getCase(colonne,rangee); // origine devient toutes les cases une à une
                if (origine.estOccupee()) { // si origine n'est pas vide
                    Piece piece = origine.getPiece(); // piece devient la piece qui était à la position origine
                    if (piece.getJoueur().getCouleur() == couleur) { // si cette pièce est de la même couleur que le joueur
                        for (char destinationC = 'a'; destinationC <= 'h'; destinationC++){ 
                            for (int destinationR = 1; destinationR <= 8; destinationR++){ 
                                Case destination = echiquier.getCase(destinationC,destinationR); // on regarde tout les coups que cette piece peut faire
                                if (piece.deplacementValide(destination)){ // Si elle peut bouger
                                    boolean chemin = true; 
                                    if (piece instanceof Tour || piece instanceof Fou || piece instanceof Dame){
                                        chemin = partie.CheminLibre(origine, destination);
                                    }
                                    if (chemin){
                                    Piece pieceCapturee = destination.getPiece(); // On prend la piece qui est sur la case destination (si elle existe) pour pouvoir la remettre apres simulation
                                    Case anciennePosition = piece.getPosition(); // On sauvegarde l'ancienne position pour pouvoir simuler tout les coups juste après
                                    Piece pieceDestination = destination.getPiece();
                                    if (pieceDestination != null && pieceDestination.getJoueur().getCouleur() == couleur){ // si la piece qui est a la destination est de la même couleur que le joueur alors on ne simulera pas le coup
                                        continue;
                                    }
                                    // Simulation du coup
                                    origine.retirerPiece(); 
                                    destination.placerPiece(piece);
                                    piece.setPosition(destination);
                                    // On verifie si en simulant le coup le roi est toujours en echec
                                    boolean roiToujoursEnEchec = estEchec(echiquier, couleur,partie,false);
                                    // on revient à l'état avant de simuler le coup
                                    destination.retirerPiece();
                                    if (pieceCapturee != null) {
                                        destination.placerPiece(pieceCapturee);
                                    }
                                    origine.placerPiece(piece);
                                    piece.setPosition(anciennePosition);
                                    // Si un coup permet de sortir de l'échec pas de mat
                                    if (!roiToujoursEnEchec){
                                        return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }    
        }    
        // sinon echec et mat
        return true;
    }
    /**
     * Permet de vérifier si un coup crée un Pat
     * @param echiquier
     * @param couleur
     * @param partie
     * @return boolean
     */
    public boolean estPat(Echiquier echiquier, Couleur couleur, Partie partie){
        if (estEchec(echiquier, couleur,partie,false)){
            return false; //pas pat si en echec
        }
        for (char colonne = 'a'; colonne <= 'h'; colonne++){ // parcourt toutes les colonnes
            for (int rangee = 1; rangee  <= 8; rangee++){ // parcourt toutes les lignes
                Case origine = echiquier.getCase(colonne,rangee); // origine devient toutes les cases du plateau une à une
                if (origine.estOccupee()){ // si origine n'est pas vide
                    Piece piece = origine.getPiece(); // piece devient la piece sur origine
                    if (piece.getJoueur().getCouleur() == couleur) { // si piece est de la couleur du joueur
                        for (char destinationC = 'a'; destinationC <= 'h'; destinationC++){ // parcourt toutes les colonnes
                            for (int destinationR = 1; destinationR  <= 8; destinationR++){ // parcourt toutes les lignes
                                Case destination = echiquier.getCase(destinationC, destinationR); // destination devient toutes les cases une à une
                                if (piece.deplacementValide(destination)){ // si piece peut se deplacer à destination
                                    Piece pieceCapturee = destination.getPiece(); // on sauvegarde la potentiel pièce capturer pour simuler un coup
                                    Case anciennePosition = piece.getPosition(); // on sauvegarde la position avant déplacement pour simuler un coup
                                    Piece pieceDestination = destination.getPiece();
                                    if (pieceDestination != null && pieceDestination.getJoueur().getCouleur() == couleur){
                                        continue;
                                    }
                                    // on simule le coup
                                    origine.retirerPiece(); 
                                    destination.placerPiece(piece);
                                    piece.setPosition(destination);
                                    // on verifie si on trouve un echec
                                    boolean roiEnEchecApres = estEchec(echiquier, couleur, partie,false);
                                    // on revient à l'état d'avant le coup
                                    destination.retirerPiece();
                                    if (pieceCapturee != null) {
                                        destination.placerPiece(pieceCapturee);
                                    }
                                    origine.placerPiece(piece);
                                    piece.setPosition(anciennePosition);
                                    // si un coup existe alors pas de pat
                                    if (!roiEnEchecApres){
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // sinon pat
        return true;
    }
}