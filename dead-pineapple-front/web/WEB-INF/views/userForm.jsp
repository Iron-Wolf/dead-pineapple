<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: saziri
  Date: 14/03/2016
  Time: 09:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <form method="POST" action="<spring:url value='/user/add'/>" id="subscribeForm" data-toggle="validator">
            <div class="form-group">
                <h2>Créer un compte</h2>
            </div>
            <div class="form-group">
                <label class="control-label" for="signupName">Votre nom</label>
                <input id="signupName" type="text" name="name" value="<c:out value="${user.lastName}"/>" placeholder="Nom" maxlength="50" class="form-control" required="true">
            </div>
            <div class="form-group">
                <label class="control-label" for="signupFirstName">Votre prénom</label>
                <input id="signupFirstName" type="text" name="firstname" value="<c:out value="${user.firstName}"/>"placeholder="Prenom" maxlength="50" class="form-control" required="true">
            </div>
            <div class="form-group">
                <label class="control-label" for="signupEmail">Email</label>
                <input id="signupEmail" type="email" maxlength="50" placeholder="Email" name="mail" class="form-control" required="true">
                <span class="erreur">${ erreurs['emailCheck']}</span>
            </div>
            <div class="form-group">
                <label class="control-label" for="signupEmailagain">Adresse</label>
                <input id="signupEmailagain" type="text" placeholder="Adresse" class="form-control" name="address" required="true">

            </div>
            <div class="form-group">
                <input type="text" placeholder="Code Postal" class="form-control" name="zipcode" required="true">
            </div>
            <div class="form-group">
                <input type="text" placeholder="Ville" class="form-control" name="city" required="true">
            </div>

            <div class="form-group">
                <label class="control-label" for="signupTelephone" >Telephone</label>
                <input id="signupTelephone" type="text" class="form-control" placeholder="Téléphone (Optional)"name="phone">
            </div>
            <div class="form-group">
                <label class="control-label" for="password">Password</label>
                <input id="password" type="password" name="password" maxlength="25" class="form-control" placeholder="Au moins 6 caractères" length="40" required="true">
            </div>
            <div class="form-group">
                <label class="control-label" for="signupPasswordagain">Password again</label>
                <input id="signupPasswordagain" type="password" data-match="#password" maxlength="25" name="passwordConfirm" class="form-control" required="true">
                <span class="erreur">${ erreurs['passwordCheck']}</span>
            </div>
            <div class="form-group">
                <button id="signupSubmit" type="submit" class="btn btn-info btn-block">Create your account</button>
            </div>
            <p class="form-group">By creating an account, you agree to our <a href="/bartering/termsOfService.jsp">Terms of Use</a> and our <a href="/bartering/privacypolicy.jsp">Privacy Policy</a>.</p>
            <hr> Déja un compte? Connectez vous <a href="#">Sign in</a></p>

        </form>
</body>
</html>
