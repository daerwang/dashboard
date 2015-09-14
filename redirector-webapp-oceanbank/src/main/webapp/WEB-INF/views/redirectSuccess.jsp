<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>viewcheck</title>
</head>
<body>
	<TABLE WIDTH=100%>
		<TR>
			<TD ALIGN=CENTER><INPUT TYPE=BUTTON VALUE="Print"
				ONCLICK="javascript:top.window.print();"> <INPUT TYPE=BUTTON
				VALUE="Close" ONCLICK="javascript:top.window.close();"></TD>
		</TR>
		<TR>
			<TD><IMG src="${front}" width="780px"></TD>
		</TR>
		<TR>
			<TD><IMG src="${back}" width="780px"></TD>
		</TR>
	</TABLE>
</body>
</html>