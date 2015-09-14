<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>viewcheck - error</title>
</head>
<body>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<table border="1" align="center">
		<tr>
			<td colspan=3 align="center"><br> <font size="6"
				face="Verdana, Arial, Helvetica, sans-serif" color="#FF0000"><strong>UNABLE
						TO LOCATE<br> IMAGE FOR THIS<br> TRANSACTION
				</strong></font><br>
			<br></td>
		</tr>
		<tr>
			<td><font face="Verdana, Arial, Helvetica, sans-serif"><strong><font
						size="-1">Account Number: ${acc}</font></strong></font> <font size="-1">&nbsp;&nbsp;</font></td>
			<td><font face="Verdana, Arial, Helvetica, sans-serif"><strong><font
						size="-1">Check Number: ${ser}</font></strong></font> <font size="-1">&nbsp;&nbsp;</font></td>
			<td><font face="Verdana, Arial, Helvetica, sans-serif"><strong><font
						size="-1">Amount: ${amo}</font></strong></font> <font
				size="-1">&nbsp;&nbsp;</font></td>
		</tr>
		<tr>
			<td align=right colspan=3><input type=button value="Close"
				onclick="javascript:window.close();">&nbsp;</td>
		</tr>

	</table>
</body>
</html>