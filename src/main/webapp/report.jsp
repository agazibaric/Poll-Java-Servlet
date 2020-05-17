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
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<img src="reportImage">
</body>
</html>