<%@ page import="java.util.Random" import="java.awt.Color" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	// Font color
	Random rand = new Random();
	Color randColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	String fontColor = Integer.toHexString(randColor.getRGB()).substring(2);
	
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
 	color: #<%=fontColor%>;
 	background-color: #<%=bgColor%>
 }
</style>
</head>
<body>
	<p>
	    Znaš onog nogometaša Lacazettea?? E on... <br>
		Oženio on srpkinju. <br>
		I umre. <br>
		Na sprovod u Francusku mu dolazi njegov punac. <br>
		Prilazi lijesu, uzima grumen zemlje, baca ga i govori: <br>
		"Neka ti je zemlja laka zete."
	</p>
</body>
</html>