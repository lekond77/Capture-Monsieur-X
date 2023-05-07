/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cnam.tp.servlet;

import cnam.jeu.beans.Carte;
import cnam.jeu.beans.Personnage;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class FileServlet extends HttpServlet {

    Carte carte = new Carte();
    Personnage personne = new Personnage();
    String chemin = "C:/Users/leo/Documents/NetBeansProjects/Jeu/src/main/webapp/tp2_cartes-2.csv";
    Carte carteAAfficher;
    int[] positionAleatoire; //Les positions aléatoires
    String[] positionOccupables;
    int compteur = 0;
    final int MAX_FILE_SIZE = 10485760;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request
     * @param response
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        carteAAfficher = carte.carteExistante(chemin);

        if (carteAAfficher != null) {

           compteur = 0;
            positionOccupables = carte.positionOccupables(carteAAfficher);

            positionAleatoire = personne.positionPersonnages(positionOccupables).getPositions();

            String[] donnees = carteAAfficher.getDonnees();

            request.setAttribute("donnees", donnees);
            request.setAttribute("colonnes", carteAAfficher.getColonnes());
            request.setAttribute("lignes", carteAAfficher.getLignes());

            request.setAttribute("positions", positionAleatoire);
            request.setAttribute("positionsLibres", positionOccupables);
            request.setAttribute("compteur", compteur);
        }

        // Afficher la page
        this.getServletContext().getRequestDispatcher("/jeu.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String submitBtn = request.getParameter("submit");
        String jouerBtn = request.getParameter("jouer");

        if (submitBtn != null && submitBtn.equals("Envoyer")) {

            compteur = 0;
            // Récupère <input type="file" name="carte">
            Part filePart = request.getPart("carte");

            if (filePart == null || filePart.getSize() == 0 || filePart.getSize() > MAX_FILE_SIZE) {
                //En cas d'erreur
                String erreur = "Veuillez charger un fichier valide et de taille inférieure à 10Mo !";
                request.setAttribute("erreur", erreur);

            } else {

                carteAAfficher = carte.carteUtilisateur(filePart);

                if (carteAAfficher != null) {
                    request.setAttribute("donnees", carteAAfficher.getDonnees());
                    request.setAttribute("colonnes", carteAAfficher.getColonnes());
                    request.setAttribute("lignes", carteAAfficher.getLignes());

                    positionOccupables = carte.positionOccupables(carteAAfficher);
                    positionAleatoire = personne.positionPersonnages(positionOccupables).getPositions();
                    request.setAttribute("positionsLibres", positionOccupables);
                    request.setAttribute("positions", positionAleatoire);
                    request.setAttribute("compteur", compteur);
                }
            }

        }

        if (jouerBtn != null && jouerBtn.equals("jouer")) {

            // Récupération de la session
            HttpSession session = request.getSession();

            //Recuperer le mouvement désiré
            String mvtA = request.getParameter("mvtA");
            String mvtB = request.getParameter("mvtB");
            String mvtC = request.getParameter("mvtC");
            String mvtD = request.getParameter("mvtD");
           

            //Recuperer les positions
            int positionA = Integer.parseInt(request.getParameter("position0"));
            int positionB = Integer.parseInt(request.getParameter("position1"));
            int positionC = Integer.parseInt(request.getParameter("position2"));
            int positionD = Integer.parseInt(request.getParameter("position3"));
            int positionX = Integer.parseInt(request.getParameter("position4"));

            // Récupération de la valeur de la variable colonne
            int colonne = (int) session.getAttribute("colonnes");
          
            positionOccupables = (String[]) session.getAttribute("positionsLibres");

            int[] positions = {positionA, positionB, positionC, positionD, positionX};

            //Conversion des positions en list String
            List<String> listAlea = Arrays.stream(positions)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.toList());

            List<String> list = Arrays.asList(positionOccupables); // Les positions occupeables

            Personnage deplacerA = null, deplacerB = null, deplacerC = null, deplacerD = null;

            try {

                //Changer les positions des joueurs et de monsieur X
                positionA = nouveauPosition(mvtA, positionA, listAlea, deplacerA, colonne, list);
                positionB = nouveauPosition(mvtB, positionB, listAlea, deplacerB, colonne, list);
                positionC = nouveauPosition(mvtC, positionC, listAlea, deplacerC, colonne, list);
                positionD = nouveauPosition(mvtD, positionD, listAlea, deplacerD, colonne, list);

                //Les nouveaux positions pour remplacer les anciennes positions dans le jsp
                int[] nouveauPosition = {positionA, positionB, positionC, positionD};
                positionX = deplacerX(nouveauPosition, positionX, colonne, list);
                compteur++;

                if (positionX == -2) {
                    positionX = Integer.parseInt(request.getParameter("position4")); //Maintenir la position de X
                    request.setAttribute("gagne", "gagné");
                }

            } catch (Exception ex) {

                request.setAttribute("erreur", ex.getMessage());
            }

            //Les nouveaux positions pour remplacer les anciennes positions dans le jsp
            int[] nouveauPosition = {positionA, positionB, positionC, positionD, positionX};

            request.setAttribute("positions", nouveauPosition);

        }

        request.setAttribute("compteur", compteur);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jeu.jsp");
        dispatcher.forward(request, response);
    }

    private int nouveauPosition(String mvt, int position, List<String> listAlea, Personnage deplacer, int colonne, List<String> list) throws Exception {

        //Personnage personne = new Personnage();
        if (mvt != null) {

            deplacer = personne.deplacerPersonne(position, mvt, colonne);

            position = deplacer.getPosition();

            if (listAlea.contains(String.valueOf(position)) || !list.contains(String.valueOf(position))) {
                throw new Exception("Déplacement impossible");
            }
        }
        return position;
    }

    //Méthode pour déplacer M X
    private int deplacerX(int[] positionsJoueurs, int positionOrdinateur, int colonne, List<String> list) {
        // Listes pour stcker les cases adjacentes de l'ordinateur
        List<Integer> casesAdjacentesLibres = new ArrayList<>();
        // Déterminer les cases adjacentes à l'ordinateur
        int[] casesAdjacentes = {positionOrdinateur - colonne, positionOrdinateur + colonne, (((positionOrdinateur - 1) % colonne != 0) ? positionOrdinateur - 1 : -2 ), (((positionOrdinateur ) % colonne != 0) ? positionOrdinateur + 1 : -2)};
     
        // Vérifier si une case adjacente est libre
        for (int caseAdjacente : casesAdjacentes) {
            if (caseAdjacente >= 0 ) { //Vérifie si la case adjacente est occupeable
                boolean caseOccupee = false;
                for (int positionJoueur : positionsJoueurs) {
                    if (caseAdjacente == positionJoueur || !list.contains(String.valueOf(caseAdjacente))) { //Vérifie s'il y a un joueur ou si obstacle
                        caseOccupee = true;
                        break;
                    }
                }
                if (!caseOccupee) {
                    casesAdjacentesLibres.add(caseAdjacente);
                }
            }
        }

        // Choisir une case aléatoire parmi les cases adjacentes libres
        if (!casesAdjacentesLibres.isEmpty()) {
            Random random = new Random();
            positionOrdinateur = casesAdjacentesLibres.get(random.nextInt(casesAdjacentesLibres.size()));
        } else {
            return -2; //Mettre fin au jeu en cas ou la fonction renvoie -2
        }

        return positionOrdinateur;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
