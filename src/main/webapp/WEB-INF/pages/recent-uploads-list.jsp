<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Recent Uploads List</title>
  </head>
  <body>
    <h1>Recent Uploads List</h1>
    <table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0" >
      <thead>
        <tr>
          <th width="150px">Date/Time</th>
          <th width="200px">E-Mail</th>
          <th width="200px">Bucket</th>
          <th width="200px">File Name</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="uploadLog" items="${uploadLogList}">
          <tr>
            <td>
              ${uploadLog.dateTime}
            </td>
            <td>
              ${uploadLog.email}
            </td>
            <td>
              ${uploadLog.bucket}
            </td>
            <td>
              <a href="${pageContext.request.contextPath}/bucket/redirect/${uploadLog.bucket}/${uploadLog.fileName}">${uploadLog.fileName}</a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/">Home page</a>
  </body>
</html>