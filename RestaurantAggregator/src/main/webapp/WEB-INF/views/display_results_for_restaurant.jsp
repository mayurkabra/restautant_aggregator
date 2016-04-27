<%@page import="java.net.URLEncoder"%>
<%@page import="edu.rutgers.database.model.Business"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp"></jsp:include>
<html>
<body>

<div style="clear: both;">
<script type="text/javascript" src="//www.google.com/trends/embed.js?hl=en-US&q=<%=URLEncoder.encode((String)request.getAttribute("query"), "UTF-8") %>&geo=US-<%=(String)request.getAttribute("state") %>&date=today+1-m&cmpt=q&tz=Etc/GMT%2B4&tz=Etc/GMT%2B4&content=1&cid=TIMESERIES_GRAPH_0&export=5&w=500&h=330"></script>
<script type="text/javascript" src="//www.google.com/trends/embed.js?hl=en-US&q=<%=URLEncoder.encode((String)request.getAttribute("query"), "UTF-8") %>&geo=US-<%=(String)request.getAttribute("state") %>&date=today+12-m&cmpt=q&tz=Etc/GMT%2B4&tz=Etc/GMT%2B4&content=1&cid=TIMESERIES_GRAPH_0&export=5&w=500&h=330"></script>
</div>
<label>Near <%=request.getAttribute("name") %> serving <%=request.getAttribute("query") %></label>
<table class="table-striped">
	<tr>
		<th>Name</th>
		<th>Rating</th>
		<th>Rate Count</th>
		<th>Number of Sources</th>
		<th>Address</th>
		<%if(request.getAttribute("showDistance")!=null){%><th>Distance</th><%} %>
		<th>Contact Number</th>
	</tr>
	<% int i=1;
	if(request.getAttribute("result")!=null){
		for(Business business : (List<Business>)request.getAttribute("result")){
			%>
				<tr>
				<td><%=business.getName() %></td>
				<td><%=business.getRating() %></td>
				<td><%=business.getRatingCount() %></td>
				<td><%=business.getCountOfSources() %></td>
				<td><%=business.getAddress() %><%-- <form id="byDist_<%=i++%>" action="searchByDistance">
					<input type="hidden" id="city" name="city" value="<%=request.getAttribute("city")%>">
					<input type="hidden" id="state" name="state" value="<%=request.getAttribute("state")%>">
					<input type="hidden" id="query" name="query" value="<%=request.getAttribute("query")%>">
					<input type="hidden" id="lat" name="lat" value="<%=business.getLatitude()%>">
					<input type="hidden" id="lon" name="lon" value="<%=business.getLongitude()%>">
					<button type="submit">Near This</button>
				</form> --%></td>
				<%if(request.getAttribute("showDistance")!=null){%><td><%= business.getDistance()%></td><%} %>
				<td><%=business.getContactNumber() %></td>
				</tr>
			<%
		} 
	} %>
	
</table>

<div style="width:80%;">
	<form action="searchByDistanceForRestaurant">
	<input type="hidden" id="city" name="city" value="<%=request.getAttribute("city")%>">
	<input type="hidden" id="state" name="state" value="<%=request.getAttribute("state")%>">
	<input type="hidden" id="name" name="name" value="<%=request.getAttribute("name")%>">
	<div class="form-group"><label>Search for other menu items: </label><input class="form-control" id="query" name="query" type="text" /></div>
	<input type="hidden" id="lat" name="lat" value="<%=request.getAttribute("lat")%>">
	<input type="hidden" id="lon" name="lon" value="<%=request.getAttribute("lon")%>">
	<button type="submit">Go</button>
	</form>
</div>

	
</body>
</html>