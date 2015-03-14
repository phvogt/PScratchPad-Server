<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%><%@page 
import="com.github.phvogt.pscratchpad.server.config.IConstantsRequest"%><%
    request.setCharacterEncoding("UTF-8");

    final String text = (String) request.getAttribute(IConstantsRequest.REQUEST_ATTR_TEXT);
%><%=text%>