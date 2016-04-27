<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp"></jsp:include>
<html>
<body>
<div style="width:80%;">
	<form action="restaurantSearch">
	<div class="form-group"><label>Location</label><input class="form-control" id="city" name="city" type="text" /></div>
	<div class="form-group"><label>State</label><input class="form-control" id="state" name="state" type="text" /></div>
	<div class="form-group"><label>Name</label><input class="form-control" id="query" name="query" type="text" /></div>
	<div class="form-group"><button type="submit" value="submit">Submit</button></div>
	</form>
</div>
	
</body>
</html>