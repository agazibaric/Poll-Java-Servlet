<%@page import="hr.fer.zemris.java.hw13.servlets.ServletUtil.BandInfo"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	// Background color
	String bgColor = (String) session.getAttribute("pickedBgCol");
	bgColor = bgColor == null ? "FFFFFF" : bgColor;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
table {
	border-collapse: collapse;
	border-spacing: 0;
}

table.rez td {
	text-align: center;
}

body {
	background-color: #<%=bgColor%>
}
</style>
</head>

<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="band" items="${votingInfo}">
				<tr>
					<td>${band.name}</td>
					<td>${band.numberOfVotes}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="500" height="300" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="/webapp2/glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="band" items="${winnerBands}">
			<li><a href="${band.link}" target="_blank">${band.name}</a></li>
		</c:forEach>
	</ul>

</body>
</html>
