<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: saziri
  Date: 14/03/2016
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<spring:url value='/resources/css/styles.css'/>" type="text/css">
    <script src="<spring:url value='/resources/js/myscript.js'/>"></script>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="<spring:url value='/resources/css/bootstrap.min.css'/>" type="text/css">
    <script src="<spring:url value='/resources/js/bootstrap.min.js'/>"></script>

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
