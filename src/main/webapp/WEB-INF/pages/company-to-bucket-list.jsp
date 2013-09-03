<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>E-Mail to Bucket</title>
  </head>
  <body>
    <h1>Company to Bucket</h1>
    <a href="${pageContext.request.contextPath}/admin/companyToBucket/create">New</a><br/>
    <table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0" >
      <thead>
        <tr>
          <th width="25px">id</th>
          <th width="150px">E-Mail</th>
          <th width="25px">Bucket</th>
          <th width="50px">Actions</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="companyToBucket" items="${companyToBucketList}">
          <tr>
            <td>${companyToBucket.id}</td>
            <td>${companyToBucket.company}</td>
            <td>${companyToBucket.bucket}</td>
            <td>
              <a href="${pageContext.request.contextPath}/admin/companyToBucket/edit/${companyToBucket.id}">Edit</a><br/>
              <a href="${pageContext.request.contextPath}/admin/companyToBucket/delete/${companyToBucket.id}">Delete</a><br/>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/">Home page</a>
  </body>
</html>
