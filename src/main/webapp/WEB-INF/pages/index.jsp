<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <style type="text/css" title="currentStyle">
      @import "resources/css/eucalyptus.css";
    </style>
    <title>Wormhole</title>
  </head>
  <body>
    <div class="euca-container">
      <div class="euca-header-container">
        <div class="inner-container clearfix">
          <a id="euca-logo" class="hide-text" href="#">Eucalyptus</a>
          <div id="euca-navigator"></div>
          <div id="euca-help"></div>
          <div id="euca-user"></div>
        </div>
      </div>
      <div>
        <h1>Wormhole</h1>
        <p>
          <i>${message}</i><br/>
          <a href="${pageContext.request.contextPath}/admin/">Administration</a><br/>
          <a href="${pageContext.request.contextPath}/bucket/list">Buckets</a><br/>
        </p>
      </div>
    </div>
  </body>
</html>