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
table, th, td {
	border: 1px solid black
}

body {
	background-color: #<%=bgColor%>
}
</style>
</head>

<body>
	<table>
		<thead>
			<tr>
				<td>x</td>
				<td>sin(x)</td>
				<td>cos(x)</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="values" items="${trigValues}">
				<tr>
					<td>${values.x}</td>
					<td>${values.sinX}</td>
					<td>${values.cosX}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>


