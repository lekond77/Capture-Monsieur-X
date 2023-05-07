/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cnam.tp.servlet;

import cnam.jeu.beans.Joueur;
import cnam.jeu.beans.db.DataBaseConnection;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;

public class Authentification extends HttpServlet {
    Connection connection;
    Joueur joueur = new Joueur();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);

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

        String error; //contient le message d'erreur

        Joueur canPlay; //

        //Les données envoyées par l'utilisateur
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if (login.isEmpty() || password.isEmpty()) {

            error = "Login  et/ou mot de passe incorrect(es)";
            request.setAttribute("error", error);
            //Afficher le message d'erreur sur la page d'authentification
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

        } else {

            //On ajoute les données de connexion à joueur pour vérifier s'il peut jouer
            Joueur joueurDmdConnection = new Joueur(login, password);

            try {    
                connection =  DataBaseConnection.chargerBaseDonnes();
                joueur.setConnection(connection);
                canPlay = joueur.seConnecter(joueurDmdConnection);

                String age = String.valueOf(canPlay.getAge());
                
                if (canPlay.getNom() == null || canPlay.getPrenom() == null || age == null
                        || canPlay.getNom().isEmpty() || canPlay.getPrenom().isEmpty() || age.isEmpty()) {

                    error = "Login  et/ou mot de passe incorrect(es)";
                    request.setAttribute("error", error);
                    //Afficher le message d'erreur sur la page d'authentification
                    this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                } else {
                    if (canPlay.getAge() < 18) {
                        session.invalidate(); //Invalider la session
                        error = "Désolé " + canPlay.getPrenom() + ", vous n'êtes pas autorisé à jouer. Attendez d'avoir 18 ans. ";
                        request.setAttribute("error", error);

                    } else {
                        //Variable de session, permettra de retrouver le nom du joueur quand retour à l'acceuil cliqué
                        session.setAttribute("prenom", canPlay.getPrenom());
                    }
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(request, response);

                }

            } catch (Exception ex) {
              
                request.setAttribute("error", ex.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);

            }

        }
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
