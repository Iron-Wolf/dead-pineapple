<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <div class="row">
        <div class="col-lg-5 col-lg-offset-1">
            <div id="invoices">
                <h3>Historique</h3>
                <c:if test="${not empty invoices}">
                    <c:forEach items="${invoices}" var="invoice">
                        Facture du <c:out value="${invoice.date}"/> au prix de <c:out value="${invoice.price}"/>
                        <select class="selectpicker">
                            <c:forEach items="${invoice.convertedFiles}" var="file">
                                <option><c:out value="${file.originalName}"/></option>
                            </c:forEach>
                        </select>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div class="col-lg-6">
            <h3>Lecteur video</h3>
            <video id="my-video" class="video-js" controls preload="auto" width="640" height="264"
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
            </div>
    </div>
</div>