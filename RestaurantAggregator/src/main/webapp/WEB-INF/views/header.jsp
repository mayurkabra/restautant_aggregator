<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://code.jquery.com/jquery-1.12.3.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">

<style type="text/css">
button.gps {
	background-image: url(../resources/images/gps.png);
	background-repeat: no-repeat;
	background-position: 50% 50%;
	/* put the height and width of your image here */
	height: 20px;
	width: 20px;
	border: none;
}

button.gps span {
	display: none;
}

table{
	width: 100%;
}
</style>

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<title>Restaurant Aggregator</title>
</head>
<body>
	<table>
		<tr>
			<th align="center" colspan="10">
				<center>
					<a href="/database/"> <img alt="DBProject"
						src="/database/resources/images/banner1.png" />
					</a>
				</center>
			</th>
		</tr>
		<tr valign="top" height="50px">
			<td align="center">
				<a href="/database/data/form">Search for consumers</a>
			</td>
			<td align="center">
				<a href="/database/data/restaurant">Search for owners</a>
			</td>
		</tr>
	</table>

</body>
</html>