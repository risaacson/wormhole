<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <style type="text/css" title="currentStyle">
      @import "${pageContext.request.contextPath}/resources/css/eucalyptus.css";
    </style>
    <title>Wormhole</title>
  </head>
  <body>
    <div class="euca-container">
      <div class="euca-header-container">
        <div class="inner-container clearfix">
          <a id="euca-logo" class="hide-text" href="${pageContext.request.contextPath}">Eucalyptus</a>
          <div id="euca-navigator"></div>
          <div id="euca-help"></div>
          <div id="euca-user"></div>
        </div>
      </div>
      <div>
        <p>
          <i>${message}</i><br/>
        </p>
        <form:form method="POST" commandName="security-authorities" action="${pageContext.request.contextPath}/admin/security/authorities/${action}" >
          <table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0" >
            <tbody>
              <tr>
                <td>Username:</td>
                <td><form:input path="username" /></td>
                <td><form:errors path="username" cssStyle="color: red;"/></td>
              </tr>
              <tr>
                <td>Authority:</td>
                <td><form:input path="authority" /></td>
                <td><form:errors path="authority" cssStyle="color: red;"/></td>
              </tr>
              <tr>
                <td><input type="submit" value="Submit" /></td>
                <td></td>
                <td></td>
              </tr>
            </tbody>
          </table>
        </form:form>
      </div>
    </div>
  </body>
</html>