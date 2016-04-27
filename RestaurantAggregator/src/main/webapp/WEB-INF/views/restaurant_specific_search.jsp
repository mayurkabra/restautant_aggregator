<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp"></jsp:include>
<html>
<body>
<div style="width:80%;">
<label>You have selected <%=request.getAttribute("name") %>. Type in a dish you serve to proceed</label>
	<form action="searchByDistanceForRestaurant">
	<input type="hidden" id="city" name="city" value="<%=request.getAttribute("city")%>">
	<input type="hidden" id="state" name="state" value="<%=request.getAttribute("state")%>">
	<input type="hidden" id="name" name="name" value="<%=request.getAttribute("name")%>">
	<div class="form-group"><label>Query</label><input class="form-control" id="query" name="query" type="text" /></div>
	<input type="hidden" id="lat" name="lat" value="<%=request.getAttribute("lat")%>">
	<input type="hidden" id="lon" name="lon" value="<%=request.getAttribute("lon")%>">
	<button type="submit">Go</button>
	</form>
</div>
	
</body>
</html>