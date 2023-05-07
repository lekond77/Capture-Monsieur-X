/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cnam.jeu.beans;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Personnage {

    private int[] positions;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    //Générer les positions aléatoires dans les positions occupeables
    public Personnage positionPersonnages(String[] positionsOccupable) {
        Personnage personne = new Personnage();

        Set<Integer> indexSet = new HashSet<>(); //Pour stocker un index une seule fois
        Random random = new Random();
        //Boucler afin de prendre 5 numéros aléatoires 
        while (indexSet.size() < 5) { 
            indexSet.add(random.nextInt(positionsOccupable.length));
        }
        positions = new int[5];
        //Ajouter les positions choisies à positions
        int i = 0;
        for (int index : indexSet) {
            positions[i] = Integer.parseInt(positionsOccupable[index]);
            i++;
        }
  //Ajouter les positions choisies à personne
        personne.setPositions(positions);

        return personne;
    }

    //Déplacer les personnages A, B ,C, et D
    public Personnage deplacerPersonne(int position, String mouvement, int colonne) {

        Personnage personne = new Personnage();

        if (mouvement.toUpperCase().equals("DROIT")) {
            if ((position ) % colonne != 0) {
                personne.setPosition(position + 1);
            }
        }
        if (mouvement.toUpperCase().equals("GAUCHE")) {
            if ((position - 1) % colonne != 0) {
                personne.setPosition(position - 1);
            }
        }
        if (mouvement.toUpperCase().equals("HAUT")) {
            personne.setPosition(position - colonne);
        }

        if (mouvement.toUpperCase().equals("BAS")) {
            personne.setPosition(position + colonne);
        }
        return personne;
    }

}
