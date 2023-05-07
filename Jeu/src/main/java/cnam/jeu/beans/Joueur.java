package cnam.jeu.beans;

import cnam.jeu.beans.db.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Joueur {

    private String nom;
    private String prenom;
    private int age;
    private String login;
    private String motDePasse;
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public Joueur(String nom, String prenom, int age, String login, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public Joueur(String login, String motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
    }

    public Joueur() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    //Ajout d'un joueur suite àla création de compte
    public boolean ajoutJoueur(Joueur joueur) throws Exception {

        PreparedStatement preparedStatement = null;
        int ligneAffectee = 0;

        try {

            String query = "INSERT INTO comptes (Nom,Prenom, Age, Login, Motdepasse) VALUES(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, joueur.getNom());
            preparedStatement.setString(2, joueur.getPrenom());
            preparedStatement.setInt(3, joueur.getAge());
            preparedStatement.setString(4, joueur.getLogin());
            preparedStatement.setString(5, joueur.getMotDePasse());

            ligneAffectee = preparedStatement.executeUpdate();

        } catch (SQLException ignore) {
            //  throw new Exception(ex.getMessage());
        } finally {
            DataBaseConnection.closePreparedStatement(preparedStatement);
            DataBaseConnection.closeConnection( connection);
        }

        return ligneAffectee == 1;//Si l'insertion à été faite
    }

    //Connecter un joueur
    public Joueur seConnecter(Joueur joueur) throws Exception {

        PreparedStatement preparedStatement = null;
        ResultSet donnnesJoueur = null;
        Joueur joueurConnecte = new Joueur();

        try {

            String query = "SELECT * FROM comptes where Login = ? AND Motdepasse = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, joueur.getLogin());
            preparedStatement.setString(2, joueur.getMotDePasse());

            donnnesJoueur = preparedStatement.executeQuery();
            donnnesJoueur.next();

            //Ajout des informations du joueur connecté depuis la base des données
            joueurConnecte.setNom(donnnesJoueur.getString("Nom"));
            joueurConnecte.setPrenom(donnnesJoueur.getString("Prenom"));
            joueurConnecte.setAge(donnnesJoueur.getInt("Age"));

        } catch (SQLException ex) {

            throw new Exception(ex.getMessage());

        } finally {
            DataBaseConnection.closePreparedStatement(preparedStatement);
            DataBaseConnection.closeConnection(connection);
            DataBaseConnection.closeResultSet(donnnesJoueur);
        }

        return joueurConnecte;
    }

}
