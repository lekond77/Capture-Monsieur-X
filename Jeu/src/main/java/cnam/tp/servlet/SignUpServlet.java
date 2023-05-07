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
import java.sql.Connection;



public class SignUpServlet extends HttpServlet {

     Connection connection;
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
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

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String ageSaisi = request.getParameter("age");

        
        try {

            int age = Integer.parseInt(ageSaisi); //Converit l'âge en int

            Joueur joueur = new Joueur(nom, prenom, age, login, password);
          
            if (isDonneeIncomplete(joueur)) {
               throw new Exception("Veuillez remplir tous les champs avec des saisies valides !");  
            }
            
            connection = DataBaseConnection.chargerBaseDonnes();
            joueur.setConnection(connection);
            
            boolean isAjout = joueur.ajoutJoueur(joueur);
            
            if(isAjout){
                 request.setAttribute("succes", "Félicitations "+  prenom + " ! Votre compte a été créé avec succès "); 
            }else{
                 request.setAttribute("error", "Une erreur est survenue lors de la création du compte "); 
            }

        } catch (NumberFormatException e) {
            
            //Si l'âge rentré n'est pas convertissable en entier
            request.setAttribute("error", "Veuillez remplir tous les champs avec des saisies valides !");
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/signup.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isDonneeIncomplete(Joueur joueur) {

        return joueur.getNom().isEmpty() || joueur.getPrenom().isEmpty() || joueur.getLogin().isEmpty()
                || joueur.getMotDePasse().isEmpty() || joueur.getAge() < 0;
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
