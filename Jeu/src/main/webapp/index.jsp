<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css"/>
        <title>Authentification </title>
    </head>
    <body>
       
        <div id="banner">
             <div>
        <h1>Connexion</h1>
        <p>Entrer votre login et votre mot de passe</p>
        <c:if test="${!empty error }"><p id="error"><c:out value="${ error }" /></p></c:if>
        <form action="login"  method="POST">
            <label>
                Login :
            </label> <br>
            <input type="text" name="login" id="login"><br>
            <label>
                Mot de passe :
            </label><br>
            <input type="password" name="password" id="password">
            <div id="compte">
             <input type="submit" name="submit" id="submit" value="Se connecter">
             <a href="signup">Créer un compte</a>
             </div>
            </form>
    
        </div>
            </div>
     
    </body>
</html>

