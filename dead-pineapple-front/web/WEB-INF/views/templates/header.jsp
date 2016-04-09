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
    <title>Title</title>

    <!--Register Jquery-->
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script>
    <!--Register Google Api-->
    <script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>

    <script src="<spring:url value='/resources/js/loginAPI/facebookLogin.js'/>"></script>
    <script src="<spring:url value='/resources/js/loginAPI/googleLogin.js'/>"></script>
</head>

Header here... Templates / header.jsp <br/>
<c:choose>
<c:when test="${sessionScope.LOGGEDIN_USER == null}">

<form:form action="/user/login" method="post"  modelAttribute="loginAttribute">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th>Username</th>
            <td><form:input type="text"  class="login-inp" path="username" /></td>
        </tr>
        <tr>
            <th>Password</th>
            <td><form:input type="password" onfocus="this.value=''" class="login-inp" path="password"/></td>
        </tr>
        <tr>
            <th></th>
            <td valign="top"><input type="checkbox" class="checkbox-size" id="login-check" /><label for="login-check">Remember me</label></td>
        </tr>
        <tr>
            <th></th>
            <td><input type="submit" class="submit-login"  /></td>
        </tr>
    </table>
</form:form>
</c:when>
    <c:otherwise>
        <div class="col-lg-6 col-sm-offset-6">
            Bienvenue <c:out value="${LOGGEDIN_USER.username}"/> !
        </div>
        <div class="col-lg-6 col-sm-offset-6">
            <a href="${logoff}" class="btn btn-warning">Se déconnecter</a>
        </div>
    </c:otherwise>
</c:choose>
<a href="<spring:url value='/user/add'/>"> Ajouter un utilisateur </a>
<a href="<spring:url value='/upload'/>"> Uploader une video </a>

<input type="button" value="Facebook login" id="fbLogin" onclick=""/>
<input type="button" value="Google login" id="googlelogin"/>

</body>
</html>

