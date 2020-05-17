<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%
	// Background color
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
	<p>
		<a href="http://www.localhost.com:8080/webapp2/colors.jsp">Background color chooser</a><br>
		<a href="http://www.localhost.com:8080/webapp2/trigonometric?a=0&b=90">Trigonometric values</a><br>
		<a href="http://www.localhost.com:8080/webapp2/powers?a=1&b=10&n=3">Powers</a><br>
		<a href="http://www.localhost.com:8080/webapp2/appinfo.jsp">Running time of application</a><br>
	</p>
	
	<form action="trigonometric" method="GET">
		Početni kut:<br> 
		<input type="number" name="a" min="0" max="360" step="1" value="0"><br> 
		Završni kut:<br> 
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj">
		<input type="reset"value="Reset">
	</form>
	

</body>


</html>