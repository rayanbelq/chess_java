package app;
import java.util.ArrayList;
import java.util.Scanner;
import model.*;

public class Main {
    public static void main(String[] args) {
        // Création et enregistrement des noms des joueurs
        try (Scanner nom = new Scanner(System.in)) {
            String nomBlanc, nomNoir;
            while (true) {
                System.out.println("Saisissez le nom du joueur BLANC : ");
                nomBlanc = nom.nextLine().trim();

                System.out.println("Saisissez le nom du joueur NOIR : ");
                nomNoir = nom.nextLine().trim();

                if (nomBlanc.isEmpty() || nomNoir.isEmpty()) {
                    System.out.println("Les noms ne doivent pas être vides.");
                } else if (nomBlanc.equalsIgnoreCase(nomNoir)) {
                    System.out.println("Les deux joueurs doivent avoir des noms différents.");
                } else {
                    break;
                }
            }
            Joueur blanc = new Joueur(nomBlanc,Couleur.BLANC);
            Joueur noir = new Joueur(nomNoir,Couleur.NOIR);
            
            // Lancement de la partie
            Partie partie = new Partie(blanc, noir);
            partie.demarrer();
            
            // Historique des coups
            System.out.println("Historique des coups : ");
            ArrayList<String> historique = partie.getHistorique();
            if (historique.isEmpty()) {
                System.out.println("Aucun coup joué.");
            } else {
                for (String coup : partie.getHistorique()) {
                    System.out.println("- " + coup);
                }
            }   }
    }
}