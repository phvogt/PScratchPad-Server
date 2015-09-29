<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="com.github.phvogt.pscratchpad.server.config.IConstants"%>
<%@page import="com.github.phvogt.pscratchpad.server.config.IConstantsRequest"%>
<%
    final ResourceBundle r = ResourceBundle.getBundle(IConstants.MESSAGES_RESSOURCE);

    final String error = (String) request.getAttribute(IConstantsRequest.REQUEST_ATTR_ERROR_MESSAGE);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">

<meta content="width=device-width, height=device-height, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

<link rel="stylesheet" type="text/css" href="css/main.css">
<script language="javascript" type="text/javascript" src="js/forge.min.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" language="javascript" src="js/pscratchpad.js"></script>

<title><%=r.getString("label.errorpage.titel")%></title>
</head>
<body>
  <div class="error">
    <%=error%>
  </div>

</body>
</html>
