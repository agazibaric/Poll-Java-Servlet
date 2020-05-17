<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "FFFFFF" : color;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style>
body {
	background-color: #<%=color%>
}
</style>
</head>

<body>
	<a href="http://www.localhost.com:8080/webapp2/setcolor?bgcolor=FFFFFF">WHITE</a>
	<a href="http://www.localhost.com:8080/webapp2/setcolor?bgcolor=FF0000">RED</a>
	<a href="http://www.localhost.com:8080/webapp2/setcolor?bgcolor=00FF00">GREEN</a>
	<a href="http://www.localhost.com:8080/webapp2/setcolor?bgcolor=00FFFF">CYAN</a>
</body>

</html>