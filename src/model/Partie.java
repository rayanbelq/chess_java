package model;
import java.util.ArrayList;
import java.util.Scanner;
import model.pieces.*;

/**
 * Gère la partie d'échec, le déroulement, les tours des joueurs, les déplacements des pièces, les règles, l'historique de coups.
 */
public class Partie {
    private final Echiquier echiquier;
    private final Joueur joueurBlanc;
    private final Joueur joueurNoir;
    private Joueur joueurActuel;
    private final ReglesEchecs regles;
    private final ArrayList<String> historiqueDesCoups;


    /**
     * Crée une nouvelle partie avec deux joueurs et initialise l'échiquier.
     * @param joueurBlanc Le joueur qui à les blancs
     * @param joueurNoir Le joueur qui à les noirs
     */
    public Partie(Joueur joueurBlanc, Joueur joueurNoir) {
        this.echiquier = new Echiquier();
        this.joueurBlanc = joueurBlanc;
        this.joueurNoir = joueurNoir;
        this.joueurActuel = joueurBlanc;
        this.regles = new ReglesEchecs();
        this.historiqueDesCoups = new ArrayList<>();
        this.echiquier.initialiserPieces(joueurBlanc, joueurNoir);
    }


    /**
     * Vérifie si une chaine est bien compris entre a1 et h8.
     * @param cordonnee La chaine à vérifier
     * @return true si la coordonée est valide, sinon false
     */
    private boolean estCoordonneeValide(String cordonnee) {
    if (cordonnee.length() != 2) return false;
    char colonne = Character.toLowerCase(cordonnee.charAt(0));
    char ligne = cordonnee.charAt(1);
    return (colonne >= 'a' && colonne <= 'h') && (ligne >= '1' && ligne <= '8');
    }


    /**
     * Démarre la partie, gère l'historique et gère les tours des joueurs jusqu'à ce que la partie ce termine.
     */
    public void demarrer() {
        try (Scanner scanner = new Scanner(System.in)) { 
            while (true) {

                System.out.println("Au tour de : " + joueurActuel.getNom() + " entrez votre coup ou 'FUIR' si votre adversaire est trop fort et que vous voulez fuir.");
                echiquier.afficherEchiquier();
                System.out.println(joueurActuel.getNom() + " joue : ");

                String coup = scanner.nextLine().toUpperCase();
                if (coup.equals("FUIR")) break;

                String[] parties = coup.split(" ");
                if (parties.length != 2 || !estCoordonneeValide(parties[0]) || !estCoordonneeValide(parties[1])) {
                    System.out.println("Hmm... Essaye un format comme 'e2 e4'. Le GPS de l'échiquier ne peut opérer que entre a1 et h8");
                    continue;
                }

                Case origine = echiquier.getCase(parties[0].charAt(0), Character.getNumericValue(parties[0].charAt(1))); // Pour récuperer séparement la partie char et la partine int (ex : e2 e4, on prend e avec parties[0].charAt(0) et 2 avec getNumericValue(parties[0].CharAt(1)))
                Case destination = echiquier.getCase(parties[1].charAt(0), Character.getNumericValue(parties[1].charAt(1))); // Pour récuperer séparement la partie char et la partine int (ex : e2 e4, on prend e avec parties[1].charAt(0) et 4 avec getNumericValue(parties[1].CharAt(1)))

                if (!origine.estOccupee()) {
                    System.out.println("Tu veux déplacer... du vide ? Personne sur la case d'origine !");
                    continue;
                }

                Piece piece = origine.getPiece(); //On récupère la piece que le joueur veut jouer

                if (piece.getJoueur() != joueurActuel) {
                    System.out.println("Pas touche ! Ce n'est pas ta pièce, voleur de " + piece.getClass().getSimpleName() + " !");
                    continue;
                }


                if (!piece.deplacementValide(destination)){
                    System.out.println("Oula, mission impossible ! Cette pièce ne sait pas faire ça...");
                    continue;
                }

                if ((piece instanceof Tour || piece instanceof Fou || piece instanceof Dame) && !CheminLibre(origine, destination)){
                    System.out.println("Il y a des embouteillages sur la route. Essaie une autre voie !");
                    continue;
                }

                // Vérifions si le coup ne laisse pas le roi en échec
                Piece pieceCapturee = destination.getPiece(); // On sauvegarde la piece qui va potentiellement être capturée
                Case anciennePosition = piece.getPosition(); // On sauvegarde la position de la pièce qui va bouger
                // On simule le coup demandé
                origine.retirerPiece();
                destination.placerPiece(piece);
                piece.setPosition(destination);
                boolean roiEnEchecApres = regles.estEchec(echiquier, joueurActuel.getCouleur(),this,false); // On enregistre si après ce coup le roi serait toujours en échec 
                // On réinitialise la situation à avant la simulation
                destination.retirerPiece();
                if (pieceCapturee != null) {
                    destination.placerPiece(pieceCapturee);
                }
                origine.placerPiece(piece);
                piece.setPosition(anciennePosition);
                // On verifie si le coup est valide ou non
                if (roiEnEchecApres){
                    System.out.println("Ton roi est deçu... il refuse que tu l'abandonne ! Coup interdit !");
                    continue;
                }

                boolean deplacementReussi = piece.deplacer(destination);

                if (deplacementReussi) { // Si le coup à été joué
                    String coupJouer;
                    boolean capture = pieceCapturee != null;

                    // Si un pion veut manger ou pas, sinon si une piece veut manger ou pas
                    if (piece instanceof Pion) {
                        coupJouer = capture ? ("" + parties[0].charAt(0) + 'x' + parties[1]).toLowerCase() : parties[1].toLowerCase();
                    } else {
                        coupJouer = piece.toString() + (capture ? 'x' : "") + parties[1].toLowerCase();
                    }

                    // Si il y a échec et mat
                    if (regles.estEchecEtMat(echiquier,joueurAdverse().getCouleur(),this)) {
                        coupJouer += '#';
                        historiqueDesCoups.add(coupJouer);
                        echiquier.afficherEchiquier();
                        System.out.println("Echec et mat. Le grand gagnant est " + joueurActuel.getNom() + ". HOURRAAAAA !");
                        break;
                    }
                    
                    // Si il y a échec 
                    if (regles.estEchec(echiquier,joueurAdverse().getCouleur(),this,true)) {
                        coupJouer += '+';
                        System.out.println("Attention ! Le roi adversaire est en panique : Échec !");
                    } 
                    
                    // Si il y a pat
                    else if (regles.estPat(echiquier,joueurAdverse().getCouleur(),this)) {
                        echiquier.afficherEchiquier();
                        System.out.println("Pat... personne ne gagne. C'est l'heure de la sieste... Zzz... Zzzz...");
                        historiqueDesCoups.add(coupJouer);
                        break;
                    }

                    historiqueDesCoups.add(coupJouer);
                    joueurActuel = joueurAdverse();
                } 
                else {
                    System.out.println("Nope. Cette pièce refuse de bouger comme ça. Peut-être qu’elle fait grève ?");
                }
            }
        }
        }


        /**
         * Retourne le joueur adverse du joueur actuel.
         * @returnLe joueur adverse
         */
        private Joueur joueurAdverse() {
            return (joueurActuel == joueurBlanc) ? joueurNoir : joueurBlanc; // On change de joueur après chaque coup
        }


        /**
         * Vérifie que si le chemin entre une case d'origine et une case de destination est libre. Notament pour dame, fou, tour.
         * @param origine La case de départ
         * @param destination La case d'arrivée
         * @return true si le chemin est libre, sinon false
         */
        public boolean CheminLibre(Case origine, Case destination){
        char origineC = origine.getColonne();
        int origineR = origine.getLigne();
        char destinationC = destination.getColonne();
        int destinationR = destination.getLigne();

        int colonneVerif = Integer.compare(destinationC, origineC); // 0 si destinationC==origineC , -1 si destinationC < origineC, 1 si destinationC > origineC
        int rangeeVerif = Integer.compare(destinationR,origineR); // 0 si destinationR==origineR , -1 si destinationR < origineR, 1 si destinationR > origineR

        char colonne = (char)(origineC + colonneVerif); // Si 1 alors on se déplace vers la droite (+1 sur une colonne) si -1 alors on se déplace vers la gauche (-1 sur une colonne)
        int rangee = origineR + rangeeVerif; // Si 1 alors on se déplace vers le haut (+1 sur une rangee) si -1 alors on se déplace vers le bas (-1 sur une ligne)
        // Pour le fou on combine les deux. Par exemple si colonneVerif = -1 et rangeeVerif = 1 alors on se déplace vers la gauche et on monte

        while (colonne != destinationC || rangee != destinationR){ // Tant qu'on est pas arrivé à destination
            // On vérifie si il n'y a rien sur le chemin
            Case chemin = echiquier.getCase(colonne, rangee);
            if(chemin.estOccupee()){
                return false;
            }
            colonne = (char)(colonne + colonneVerif);
            rangee = rangee + rangeeVerif;
        }
        return true;
    }

    /**
     * Retourne l'historique des coups joués pendant toute la partie.
     * @return Une liste des coups sous forme de chaine de caractère
     */
    public ArrayList<String> getHistorique() {
        return historiqueDesCoups;
    }
}