<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: saziri
  Date: 14/03/2016
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <!-- Force latest IE rendering engine or ChromeFrame if installed -->
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
    <meta charset="utf-8">
    <meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support, progress bar and preview images for jQuery. Supports cross-domain, chunked and resumable file uploads. Works with any server-side platform (Google App Engine, PHP, Python, Ruby on Rails, Java, etc.) that supports standard HTML form file uploads.">
    <meta name="viewport" content="width=device-width">


    <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
    <link rel="stylesheet" href="<spring:url value='/resources/css/jquery.fileupload-ui.css'/>" type="text/css">
    <link rel="stylesheet" type="text/css" media="screen" href="<spring:url value='/resources/css/jquery.fileupload.css'/>">

    <link rel="stylesheet" href="<spring:url value='/resources/css/jqueryFileTree.css'/>"/>
    <!-- Shim to make HTML5 elements usable in older Internet Explorer versions -->
    <!-- Generic page styles -->
    <link rel="stylesheet" href="<spring:url value='/resources/css/style.css'/>" type="text/css">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="<spring:url value='/resources/css/bootstrap.min.css'/>" type="text/css">
    <!-- Bootstrap styles for responsive website layout, supporting different screen sizes -->
    <link rel="stylesheet" href="<spring:url value='/resources/css/bootstrap-responsive.min.css'/>">
    <!-- Bootstrap CSS fixes for IE6 -->
    <!--[if lt IE 7]><link rel="stylesheet" href="http://blueimp.github.com/cdn/css/bootstrap-ie6.min.css"><![endif]-->
    <!-- Bootstrap Image Gallery styles -->
    <link rel="stylesheet" href="<spring:url value='/resources/css/bootstrap-image-gallery.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<spring:url value='/resources/css/leftbar.css'/>">
    <link rel="stylesheet" href="<spring:url value='/resources/css/deadpinnaple.css?v=1.0'/>">
    <link rel="stylesheet" href="<spring:url value='/resources/css/dashboard.css?v=1.0'/>">
    <link rel="stylesheet" href="<spring:url value='/resources/css/bootstrap-select.css'/>">



    <!--Register Jquery Mika
    <script src="http://code.jquery.com/jquery-2.2.2.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.1.0.js"></script>-->

    <script src="<spring:url value='/resources/js/jquery-2.1.1.min.js'/>"></script>
    <script src="<spring:url value='/resources/js/bootstrap.min.js'/>"></script>

    <!--Register Google Api-->
    <script src="https://apis.google.com/js/client.js?onload=handleClientLoad"></script>

    <script src="<spring:url value='/resources/js/loginAPI/facebookLogin.js'/>"></script>
    <script src="<spring:url value='/resources/js/loginAPI/googleLogin.js'/>"></script>
    <!--[if lt IE 9]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
    <script src="<spring:url value='/resources/js/leftbar.js'/>"></script>
    <script src="<spring:url value='/resources/js/globalfront.js'/>"></script>
    <script src="<spring:url value='/resources/js/dashboard.js'/>"></script>
    <script src="<spring:url value='/resources/js/loading.js'/>"></script>
    <title>deadpineapple - Index</title>
</head>
<body>
<header>
    <jsp:include page="templates/header.jsp"/>
</header>
<jsp:include page="${partial}"/>
<footer>
    <jsp:include page="templates/footer.jsp"/>
</footer>
</body>
</html>
