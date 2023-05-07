<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css">
         <link rel="stylesheet" href="css/signup.css">
        <title>Créer compte</title>
    </head>
    <body>
         <div id="banner">
             <div>
        <h1>Création de compte</h1>
          <c:if test="${!empty error }"><p id="error"><c:out value="${ error }" /></p></c:if>
          <c:if test="${!empty succes }"><p id="succes"><c:out value="${ succes }" /></p></c:if>
         <form action="signup"  method="POST">
            <label>
                Nom :
                <input type="text" name="nom" id="nom">
            </label>
            <br>
            <label>
                Prénom :
                 <input type="text" name="prenom" id="prenom">
            </label>
            <br>
              <label>
                Login :
                 <input type="text" name="login" id="login">
            </label>
            <br>
             <label>
                Mot de passe :
                <input type="password" name="password" id="password">
            </label>
            <br>
            <label>
               Age :
               <input type="number" name="age" id="age" min="8">
            </label>
           <br>
            <div id="compte">
             <input type="submit" name="submit" id="submit" value="Créer">
             <a href="login">Retour à l'accueil</a>
             </div>
            </form>
        </div>
            </div>
    </body>
</html>
