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
          <a href="${pageContext.request.contextPath}/admin/emailToBucket">Administration</a><br/>
          <a href="${pageContext.request.contextPath}/bucket/list">Buckets</a><br/>
        </p>
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
                  <a href="${pageContext.request.contextPath}/bucket/redirect/${uploadLog.bucket}/${uploadLog.fileName}/">${uploadLog.fileName}</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>