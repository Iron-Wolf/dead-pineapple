<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: saziri
  Date: 16/05/2016
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<spring:url value='/resources/js/bootstrap.min.js'/>"></script>
<script src="<spring:url value='/resources/js/video.js'/>"></script>
<link rel="stylesheet" href="<spring:url value='/resources/css/video-js.min.css'/>">
<div class="container">
    <br/>
    <h1>
        Mon espace
    </h1>
    <hr/>
    <div class="progress">
        <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="50"
             aria-valuemin="0" aria-valuemax="100" style="width:50%">
            50% d'espace utilisé (info)
        </div>
    </div>
    <div class="row">
        <div class="col-lg-5 col-lg-offset-1">
            <div id="invoices">
                <h3>Historique</h3>
                <c:if test="${not empty invoices}">
                    <c:forEach items="${invoices}" var="invoice">
                        Facture du <c:out value="${invoice.date}"/> au prix de <c:out value="${invoice.price}"/> &euro;
                        <div class="form-group">
                            <label for="sel1">Sélectionnez une vidéo</label>
                            <select class="form-control" id="sel1">
                                <c:forEach items="${invoice.convertedFiles}" var="file">
                                    <option><c:out value="${file.originalName}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="col-lg-6">
            <h3>Lecteur video</h3>
            <video id="my-video" class="video-js" controls preload="auto" width="550" height="200"
                   poster="<spring:url value='/resources/img/preview.png'/>" data-setup="{}">
                <source src="MY_VIDEO.mp4" type='video/mp4'>
                <source src="MY_VIDEO.webm" type='video/webm'>
                <p class="vjs-no-js">
                    To view this video please enable JavaScript, and consider upgrading to a web browser that
                    <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
                </p>
            </video>
            <!-- Occupe toi de mettre le lecteur video ici. Tu t'occupes à présent de cette partie <!-->
        </div>
    </div>
    <div class="row">
            <div class="col-lg-11 col-lg-offset-1">
            <h3>Mes informations personnelles</h3>
                <form:form method="POST" action="/user/edit" id="editForm" data-toggle="validator" modelAttribute="userAccount" disabled="true">
                    <div class="form-group">
                        <form:label path="lastName" class="control-label">Votre nom</form:label>
                        <form:input path="lastName" value="${USER_INFORMATIONS.lastName}" id="name" type="text" name="name" placeholder="Nom" maxlength="50" class="form-control" required="true"/>
                    </div>
                    <div class="form-group">
                        <form:label class="control-label" path="firstName">Votre prénom</form:label>
                        <form:input path="firstName" value="${USER_INFORMATIONS.firstName}" id="firstName" type="text" name="firstname" placeholder="Prenom" maxlength="50" class="form-control" required="true"/>
                    </div>
                    <div class="form-group">
                        <form:label class="control-label" path="email">Email</form:label>
                        <form:input path="email" value="${USER_INFORMATIONS.email}" id="signupEmail" type="email" maxlength="50" placeholder="Email" name="mail" class="form-control" disabled = "false" required="true" />
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
                        <button id="signupSubmit" type="submit" class="btn btn-info btn-block">Modifier</button>
                    </div>
                    </form:form>
            </div>
    </div>
</div>