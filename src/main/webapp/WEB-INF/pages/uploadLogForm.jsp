<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>Sample Form</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <style>
      body { background-color: #eee; font: helvetica; }
      #container { width: 500px; background-color: #fff; margin: 30px auto; padding: 30px; border-radius: 5px; box-shadow: 5px; }
      .green { font-weight: bold; color: green; }
      .message { margin-bottom: 10px; }
      label {width:70px; display:inline-block;}
      form {line-height: 160%; }
      .hide { display: none; }
    </style>
  </head>
  <body>

  <div id="container">

    <form:form modelAttribute="uploadLog">
      <label for="trackerIdInput">trackerId: </label>
      <form:input path="trackerId" id="trackerIdInput" />
      <br/>

      <label for="dateTimeInput">dateTime: </label>
      <form:input path="dateTime" id="dateTimeInput" />
      <br/>

      <label for="emailInput">email: </label>
      <form:input path="email" id="emailInput" />
      <br/>

      <label for="bucketInput">bucket: </label>
      <form:input path="bucket" id="bucketInput" />
      <br/>

      <label for="fileNameInput">fileName: </label>
      <form:input path="fileName" id="fileNameInput" />
      <br/>

      <input type="submit" value="Submit" />
    </form:form>
  </div>

  </body>
</html>