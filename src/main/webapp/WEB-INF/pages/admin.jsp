<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <p>
          <a href="${pageContext.request.contextPath}/admin/companyToBucket">Companies</a>
        </p>
        <p>
          <a href="${pageContext.request.contextPath}/admin/emailToBucket/create">New</a>
        </p>
        <table style="text-align: center;" border="1px" cellpadding="0" cellspacing="0" >
          <thead>
            <tr>
              <th width="200px">E-Mail</th>
              <th width="200px">Bucket</th>
              <th width="200px">Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="emailToBucket" items="${emailToBucketList}">
              <tr>
                <td>${emailToBucket.email}</td>
                <td>${emailToBucket.bucket}</td>
                <td>
                  <a href="${pageContext.request.contextPath}/admin/emailToBucket/edit/${emailToBucket.id}">Edit</a><br/>
                  <a href="${pageContext.request.contextPath}/admin/emailToBucket/delete/${emailToBucket.id}">Delete</a><br/>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>