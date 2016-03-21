<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<a href="<spring:url value='/user/add'/>"> Ajouter un utilisateur </a>
<a href="<spring:url value='/upload'/>"> Uploader une video </a>

<input type="button" value="Facebook login" id="fbLogin" onclick=""/>
<input type="button" value="Google login" id="googlelogin"/>

</body>
</html>

