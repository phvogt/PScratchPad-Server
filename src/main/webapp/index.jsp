<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="com.github.phvogt.pscratchpad.server.config.IConstants"%>
<%@page import="com.github.phvogt.pscratchpad.server.web.IConstantsRequest"%>
<%
    final ResourceBundle r = ResourceBundle.getBundle(IConstants.MESSAGES_RESSOURCE);

    String changed = (String) request.getAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_CHANGED_MESSAGE);
    if (changed == null) {
        changed = "changed.nothing";
    }
    final String daten = (String) request.getAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_TEXT);
    final Long saveTime = (Long) request.getAttribute(IConstantsRequest.REQUEST_ATTR_EDITOR_FILE_TIMESTAMP);
    String name = (String) request.getAttribute(IConstantsRequest.REQUEST_ATTR_NAME);
    if (name == null) {
        name = IConstants.DEFAULT_NAME;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">

<meta content="width=device-width, height=device-height, initial-scale=1, maximum-scale=1, user-scalable=0" name="viewport">

<link rel="stylesheet" type="text/css" href="../css/main.css">
<script language="javascript" type="text/javascript" src="../js/forge.min.js"></script>
<script language="javascript" type="text/javascript" src="../js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" language="javascript" src="../js/pscratchpad.js"></script>

<title><%=r.getString("label.page.titel")%></title>
</head>
<body onload="javascript:doInit()">
  <div class="main">
    <div class="load">
      <form method="get" action="../<%=IConstantsRequest.URL_LOAD%>/<%=name%>">
        <input type="submit" value="<%=r.getString("btn.reload")%>" />
      </form>
    </div>
    <div id="<%=IConstantsRequest.HTML_ID_DIV_CHANGED%>" class="changedDiv"><%=StringEscapeUtils.escapeHtml4(r.getString(changed))%></div>
    <div class="editform">
      <form id="editform" method="post" action="../<%=IConstantsRequest.URL_SAVE%>/<%=name%>">
        <div class="save">
          <input type="submit" value="<%=r.getString("btn.save")%>" />
        </div>
        <div class="crypto">
          <input id="decrypt" type="button" name="decrypt" value="Decrypt" /> <input id="password" type="password" name="password"
            value="" title="Password" class="password" /> <input id="encrypt" type="button" name="encrypt" value="Encrypt" />
        </div>
        <div class="edittextarea">
          <textarea id="edittextarea" class="editor" wrap="off" rows="28" cols="35"
            name="<%=IConstantsRequest.REQUEST_PARAM_EDITOR_FORM_SCRATCHPAD%>"><%=StringEscapeUtils.escapeHtml4(daten)%></textarea>
        </div>
      </form>
    </div>
  </div>
  <div class="download">
    <form method="get" action="../<%=IConstantsRequest.URL_DOWNLOAD%>/<%=name%>">
      <input type="submit" value="<%=r.getString("btn.download")%>" />
    </form>
  </div>

</body>
</html>
