<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	// Background color
	String bgColor = (String) session.getAttribute("pickedBgCol");
	bgColor = bgColor == null ? "FFFFFF" : bgColor;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style>
body {
	background-color: #<%=bgColor%>
}
</style>
</head>
<body>
	<h1>Error</h1>
	<p>Invalid arguments for '/powers'</p>
</body>
</html>