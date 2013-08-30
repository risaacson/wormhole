<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>New Shop page</title>
  </head>
  <body>
    <h1>New Shop page</h1>
    <form:form method="POST" commandName="emailToBucket" action="${pageContext.request.contextPath}/admin/emailToBucket/create" >
      <table>
        <tbody>
          <tr>
            <td>Shop name:</td>
            <td><form:input path="email" /></td>
            <td><form:errors path="email" cssStyle="color: red;"/></td>
          </tr>
          <tr>
            <td>Employees number:</td>
            <td><form:input path="bucket" /></td>
            <td><form:errors path="bucket" cssStyle="color: red;"/></td>
          </tr>
          <tr>
            <td><input type="submit" value="Create" /></td>
            <td></td>
            <td></td>
          </tr>
        </tbody>
      </table>
    </form:form>
    <a href="${pageContext.request.contextPath}/">Home page</a>
  </body>
</html>