<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Wormhole Admin</title>
  </head>
  <body>
    <h1>Wormhole Admin</h1>
    <p>
      <i>${message}</i><br/>
      <a href="${pageContext.request.contextPath}/admin/emailToBucket/list">E-Mail to Bucket</a><br/>
      <a href="${pageContext.request.contextPath}/admin/companyToBucket/list">Company to Bucket</a><br/>
    </p>
  </body>
</html>