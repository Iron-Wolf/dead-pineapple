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
<div id="glissiere" >
    <div id="poussoirG"></div>
    <div id="leftbar" class="col-lg-2">
        <ul class="menu-vertical col-lg-10">

    <c:choose>
    <c:when test="${sessionScope.LOGGEDIN_USER == null}">

    <form:form action="/user/login" method="post"  modelAttribute="loginAttribute">


                <li id="creer" class="mv-item"><a >Se connecter</a></li>
                <li id="login" class="connect"><form:input type="text"  class="login-inp" path="username" placeholder="login"/></li>
                <li id="mdp" class="connect"><form:input type="password" onfocus="this.value=''" class="login-inp" path="password" placeholder="mot de passe"/></li>


                <li class="connect">
                    <img type="subtmit" value="Facebook login" id="fbLogin" onclick="" class="login" src="/resources/img/F_icon.svg.png">
                    <img type="submit" value="Google login" id="googlelogin" class="login" src="/resources/img/google-icon.png">
                </li>
                <input type="checkbox" class="checkbox-size connect" id="login-check" /><label class="connect" for="login-check">Remember me</label>
                <div class="connect" type="button" ><input type="submit" class="submit-login" value="connection" /></div>
                <li class="mv-item"><a href="<spring:url value='/user/add'/>"> Ajouter un utilisateur </a></li>


    </form:form>
    </c:when>
        <c:otherwise>
            <div >
                Bienvenue <c:out value="${USER_INFORMATIONS.firstName}"/>
            </div>
            <div >
                <a href="<spring:url value='/user/logOff'/>" class="btn btn-warning">Se déconnecter</a>
            </div>
            <li class="mv-item"><a href="<spring:url value='/dashboard'/>"> Mon espace </a></li>

        </c:otherwise>
    </c:choose>

            <li class="mv-item"><a href="<spring:url value='/upload'/>"> Convertir une video </a></li>
            <li id="kesako" class="mv-item"><a href="#whats">Que faisons nous</a></li>
            <li id="qui" class="mv-item"><a href="#whos">Qui sommes nous</a></li>
            <li id="call" class="mv-item"><a href="#contact">Nous contacter</a></li>
        </ul>

        <div id="repli" class="col-lg-2">
            <img src="/resources/img/login.ico" height="30" class="icon col-lg-offset-1">
            <img src="/resources/img/adduser.ico" height="30" class="icon col-lg-offset-1">
            <img src="/resources/img/convert.ico" height="30" class="icon col-lg-offset-1">

            <img src="/resources/img/weare.ico" height="30" class="icon col-lg-offset-1">
            <img src="/resources/img/contact.ico" height="30" class="icon col-lg-offset-1">



        </div>

    </div>
    <div id="poussoirD"></div>
</div>
