package cnam.jeu.beans;

import jakarta.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;


public class Carte {

    private int lignes;
    private int colonnes;
    private String[] donnees;

    public int getLignes() {
        return lignes;
    }

    public void setLignes(int lignes) {
        this.lignes = lignes;
    }

    public int getColonnes() {
        return colonnes;
    }

    public void setColonnes(int colonnes) {
        this.colonnes = colonnes;
    }

    public String[] getDonnees() {
        return donnees;
    }

    public void setDonnees(String[] donnees) {
        this.donnees = donnees;
    }

    //Charger la carte de l'utilisateur
    public Carte carteUtilisateur(Part part) throws IOException {

        Carte carte = new Carte();

        InputStream fileContent = part.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8"));

        String[] strTableau;
        strTableau = reader.readLine().split(":");

        carte.setLignes(Integer.parseInt(strTableau[1].trim())); // Lignes de la carte
        carte.setColonnes(Integer.parseInt(strTableau[2].trim())); //Colonnes de la carte

        donnees = new String[carte.getColonnes() * carte.getLignes()];
        int i = 0;
        while (reader.ready()) {
            String ligne = reader.readLine();
            strTableau = ligne.substring(6).split(":"); //Divise la chaîne en fonction de :

            for (int j = 0; j < strTableau.length; j++) {
                donnees[i] = String.valueOf(strTableau[j].trim());
                i++;
            }
            carte.setDonnees(donnees);

        }

        return carte;
    }

    //Carger une carte existante
    public Carte carteExistante(String path) throws IOException {

        Carte carte = new Carte();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));

        String[] strTableau;
        strTableau = reader.readLine().split(":"); //Lire la première ligne et sépare par :

        carte.setLignes(Integer.parseInt(strTableau[1].trim())); // Lignes de la carte
        carte.setColonnes(Integer.parseInt(strTableau[2].trim()));//Colonnes de la carte

        donnees = new String[carte.getColonnes() * carte.getLignes()];
        int i = 0;
        //  String ligne;
        //  while ((ligne = reader.readLine()) != null) {
        while (reader.ready()) {

            String ligne = reader.readLine();
            strTableau = ligne.substring(6).split(":"); // Divise la chaîne en fonction de :

            for (int j = 0; j < strTableau.length; j++) {
                donnees[i] = String.valueOf(strTableau[j].trim());
                i++;
            }
        }
        carte.setDonnees(donnees);
        return carte;
    }

    public String[] positionOccupables(Carte carte) {

        String[] valeurTableau = carte.getDonnees();

        //Un tableau qui contiendra les positions des zéros
        String[] placesOccupable = new String[valeurTableau.length];

        int nbZeros = 0;
        
        //Récupere les positions des zéros 
        for (int k = 0; k < valeurTableau.length; k++) {
            if (valeurTableau[k].equals("0")) {
                placesOccupable[nbZeros] = String.valueOf(k + 1);
                nbZeros++;
            }
        }
        //Rédimentionner le tableau en fonction du zéros obtenus
        return Arrays.copyOf(placesOccupable, nbZeros);
    }

}
