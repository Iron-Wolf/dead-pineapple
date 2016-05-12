<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: saziri
  Date: 14/03/2016
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/leftbar.css">

    <!--Register Jquery-->
    <script src="http://code.jquery.com/jquery-2.2.2.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script>
    <!--Register Google Api-->
    <script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>

    <script src="<spring:url value='/resources/js/loginAPI/facebookLogin.js'/>"></script>
    <script src="<spring:url value='/resources/js/loginAPI/googleLogin.js'/>"></script>
    <script src="<spring:url value='/resources/js/leftbar.js'/>"></script>
</head>

Header here... Templates / header.jsp <br/>
<div id="leftbar">
    <ul class="menu-vertical">




<c:choose>
<c:when test="${sessionScope.LOGGEDIN_USER == null}">

<form:form action="/user/login" method="post"  modelAttribute="loginAttribute">


            <li id="creer" class="mv-item"><a href="#">Créer un compte, se connecter</a></li>
            <li id="login" class="connect"><form:input type="text"  class="login-inp" path="username" value="login"/></li>
            <li id="mdp" class="connect"><form:input type="password" onfocus="this.value=''" class="login-inp" path="password"/></li>

            <input type="checkbox" class="checkbox-size connect" id="login-check" /><label for="login-check">Remember me</label>
            <div class="connect" type="button" ><input type="submit" class="submit-login" value="connection" /></div>
    <li class="connect">
        <input type="button" value="Facebook login" id="fbLogin" onclick=""/>
        <input type="button" value="Google login" id="googlelogin"/>
    </li>
            <li class="connect mv-item"><a href="<spring:url value='/user/add'/>"> Ajouter un utilisateur </a></li>


</form:form>
</c:when>
    <c:otherwise>
        <div >
            Bienvenue <c:out value="${LOGGEDIN_USER.username}"/> !
        </div>
        <div >
            <a href="<spring:url value='/user/logOff'/>" class="btn btn-warning">Se déconnecter</a>
        </div>
        <li class="mv-item"><a href="<spring:url value='/upload'/>"> Uploader une video </a></li>

    </c:otherwise>
</c:choose>


        <li id="convertir" class="mv-item"><a href="#">Convertir une video</a></li>
        <li id="kesako" class="mv-item"><a href="#">Que faisons nous</a></li>
        <li id="qui" class="mv-item"><a href="#">Qui sommes nous</a></li>
        <li id="call" class="mv-item"><a href="#">Nous contacter</a></li>
    </ul>
</div>
</body>
</html>

