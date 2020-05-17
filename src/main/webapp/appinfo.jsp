<%@ page import="java.util.concurrent.TimeUnit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	// Background color
	String color = (String) session.getAttribute("pickedBgCol");
	color = color == null ? "FFFFFF" : color;
%>

<%
	Long currentTime = System.currentTimeMillis();
	Long startTime = (Long) getServletContext().getAttribute("startTime");
	Long runningTime = currentTime - startTime;
	
	Long days = TimeUnit.MILLISECONDS.toDays(runningTime);
    runningTime -= TimeUnit.DAYS.toMillis(days);
    Long hours = TimeUnit.MILLISECONDS.toHours(runningTime);
    runningTime -= TimeUnit.HOURS.toMillis(hours);
    Long minutes = TimeUnit.MILLISECONDS.toMinutes(runningTime);
    runningTime -= TimeUnit.MINUTES.toMillis(minutes);
    Long seconds = TimeUnit.MILLISECONDS.toSeconds(runningTime);
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
	<h1>Application info</h1>
	<p>
		<b>Application is running for:</b>
	</p>
	<ul>
		<li><%=days%> days <%=hours%> hours <%=minutes%> minutes <%=seconds%>seconds</li>
	</ul>
</body>
</html>
