<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jeu</title>
        <link rel="stylesheet" href="css/file.css"/>

    </head>

    <body>
        <div id="banner">
            <div id="div">
                <h1>Capturez Monsieur X </h1>
                <a href="login.jsp" >Retour à l'accueil</a>
            </div>

            <form action="carte" enctype="multipart/form-data" method="POST">
                <label><b>Charger une nouvelle carte de la ville : </b></label>  <br/><br/>
                <input type="file" name="carte" />
                <!-- 10 Mo maximum -->
                <input type="hidden" name="MAX_FILE_SIZE" value="10485760" /><br/><br/>
                <input type="submit" name="submit" value="Envoyer" />
            </form>
            <br>
            <c:if test="${!empty erreur}">
                <p class="erreur">${erreur}</p>
            </c:if>
            <p>Nombre de tours : ${compteur} /20</p> 

            <c:set var="donnees" value="${donnees}" scope="session" />
            <c:set var="colonnes" value="${colonnes}" scope="session" />
            <c:set var="lignes" value="${lignes}" scope="session" /> 

            <c:if test="${!empty donnees && !empty colonnes && !empty lignes}">

                <table>
                    <thead>
                        <tr>
                            <th></th> <!-- coin supérieur gauche vide -->
                                <c:forEach var="i" begin="1" end="${colonnes}">
                                <th>${i}</th> <!-- Ajoute les nombres de colonne en haut -->
                                </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="i" begin="0" end="${lignes - 1}" >
                            <tr>
                                <th>${i + 1}</th> <!-- Ajoute les nombres de lignes à gauche -->

                                <c:forEach var="j" begin="${i*colonnes}" end="${i*colonnes +  colonnes-1}" items="${donnees}" varStatus="status">
                                    <td style="${donnees[j-1] eq "1" ? 'background-color: black' : 'background-color:white'}"></td>
                                </c:forEach>

                            </tr> 
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <br/>
            <c:if test="${compteur < 20 && !empty  gagne}">
                <p> Bravo, vous avez gagné !!!! </p>  
            </c:if>

            <c:if test="${compteur == 20}">
                <p> Perdu! Le nombre maximal de tours est atteint. </p>  
            </c:if>

            <c:if test="${compteur < 20 && empty  gagne}">

                <form action="carte" method="POST" id="jouer">
                    <label><b>Charger une nouvelle carte de la ville : </b></label>  <br/>
                    <div>
                        <b>A :</b>

                        <label ><input type="radio" name="mvtA" value="Haut" >Haut</label>
                        <label ><input type="radio" name="mvtA" value="Bas" >Bas</label>
                        <label > <input type="radio" name="mvtA" value="Droit">Droit</label>
                        <label ><input type="radio" name="mvtA" value="Gauche">Gauche</label>

                        <button type="submit" name="jouer" value="jouer">Jouer</button>
                    </div>
                    <div>
                        <b>B :</b>

                        <label ><input type="radio" name="mvtB" value="Haut" >Haut</label>
                        <label ><input type="radio" name="mvtB" value="Bas" >Bas</label>
                        <label > <input type="radio" name="mvtB" value="Droit">Droit</label>
                        <label ><input type="radio" name="mvtB" value="Gauche">Gauche</label>

                        <button type="submit" name="jouer" value="jouer">Jouer</button>
                    </div>
                    <div>
                        <b>C :</b>

                        <label ><input type="radio" name="mvtC" value="Haut" >Haut</label>

                        <label ><input type="radio" name="mvtC" value="Bas" >Bas</label>

                        <label > <input type="radio" name="mvtC" value="Droit">Droit</label>

                        <label ><input type="radio" name="mvtC" value="Gauche">Gauche</label>


                        <button type="submit" name="jouer" value="jouer">Jouer</button>
                    </div>
                    <div>
                        <b>D :</b>

                        <label ><input type="radio" name="mvtD" value="Haut" >Haut</label>
                        <label ><input type="radio" name="mvtD" value="Bas" >Bas</label>
                        <label > <input type="radio" name="mvtD" value="Droit">Droit</label>
                        <label ><input type="radio" name="mvtD" value="Gauche">Gauche</label>

                        <button type="submit" name="jouer" value="jouer">Jouer</button>
                    </div>
                </c:if>
                <c:set var="positions" value="${positions}"  scope="session" />
                <c:set var="positionsLibres" value="${positionsLibres}" scope="session" />

                <c:if test="${!empty positions}">

                    <c:forEach var="value" items="${positions}" varStatus="statut">
                        <input type="hidden" value="${value}" name="${"position"}${statut.index}">
                    </c:forEach>

                </c:if>
            </form>

        </div>

        <script>

            let positionOccupeables = document.querySelectorAll('input[type="hidden"]'); //Recupérer les positions aléatoires

            let td = document.getElementsByTagName("td");

            //Ajout des joueurs dans le tableau
            td[parseInt(positionOccupeables[1].value - 1)].innerHTML = "A";
            td[parseInt(positionOccupeables[2].value - 1)].innerHTML = "B";
            td[parseInt(positionOccupeables[3].value - 1)].innerHTML = "C";
            td[parseInt(positionOccupeables[4].value - 1)].innerHTML = "D";
            td[parseInt(positionOccupeables[5].value - 1)].innerHTML = "X";
            td[parseInt(positionOccupeables[5].value - 1)].style.color = "red";


            let radios = document.querySelectorAll('input[type="radio"]');

            //Autoriser une seule selection de boutons radios dans les 4 groupes
            for (let i = 0; i < radios.length; i++) {
                radios[i].addEventListener('change', function () {
                    if (this.checked) {
                        for (let j = 0; j < radios.length; j++) {
                            if (radios[j] !== this) {
                                radios[j].checked = false;
                            }
                        }
                    }
                });
            }

        </script>
    </body>
</html>
