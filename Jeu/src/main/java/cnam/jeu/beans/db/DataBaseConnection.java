package cnam.jeu.beans.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataBaseConnection {

    public static Connection chargerBaseDonnes() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbUrl = "jdbc:mysql://localhost:3306/jeu?serverTimezone=Europe/Paris&useSSL=false";
        String user = "jeu";
        String password = "java123";
        return  DriverManager.getConnection(dbUrl, user, password);

    }

    //Fermer la connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignore) {
             
            }
        }
    }

    //Fermer le resultat
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ignore) {
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException ignore) {
            }
        }
    }

}
