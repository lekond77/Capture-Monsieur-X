<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Acceuil</title>
        <link rel="stylesheet" href="css/style1.css"/>
    </head>
    <body>


        <div id="banner">

            <h1>Menu</h1>

            <c:if test="${!empty prenom}"><p><c:out value =" Bonjour ${ prenom }" /></p> 
            <p>Vous êtes autorisé à jouer.</p>
            <p><a href="carte">Commencez une nouvelle partie</a></p>
            </c:if>

            <c:if test="${!empty error }"><p id ="error"><c:out value="${ error }" /></p></c:if>
   
        </div>
</body>
</html>
