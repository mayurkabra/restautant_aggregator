<%@page import="edu.rutgers.database.model.Business"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp"></jsp:include>
<html>
<body>
<table class="table-striped">
	<tr>
		<th>Name</th>
		<th>Rating</th>
		<th>Rate Count</th>
		<th>Number of Sources</th>
		<th>Address</th>
		<th>Contact Number</th>
	</tr>
	<%if(request.getAttribute("result")!=null){
		for(Business business : (List<Business>)request.getAttribute("result")){
			%>
				<tr>
				<td><%=business.getName() %></td>
				<td><%=business.getRating() %></td>
				<td><%=business.getRatingCount() %></td>
				<td><%=business.getCountOfSources() %></td>
				<td><%=business.getAddress() %></td>
				<td><%=business.getContactNumber() %></td>
				</tr>
			<%
		} 
	} %>
	
</table>
	
</body>
</html>