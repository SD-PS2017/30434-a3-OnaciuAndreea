<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Create an account</title>

<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
        <script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="sockjs-0.3.4.min.js"></script>
    <script src="stomp.min.js"></script>
</head>

<body>

	<div class="container">
		<h2>Welcome, ${pageContext.request.userPrincipal.name} !</h2>
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="${contextPath}">MedicalSoft</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="${contextPath}/secretary">Add patient</a></li>
					<li><a href="${contextPath}/editPatient">Edit patient info</a></li>
					<li><a href="${contextPath}/addConsultation">Add consultation</a></li>
					<li><a href="${contextPath}/editConsultation">Edit consultation</a></li>
					<li><a href="${contextPath}/deleteConsultation">Delete consultation</a></li>
					<li><a href="${contextPath}/seeConsultation">See consultations</a></li>
					<li><a href="${contextPath}/notifyDoctor">Notify doctor</a></li>
				</ul>
				<a href="${contextPath}/login">
					<button class="btn btn-danger navbar-btn">Log out</button>
				</a>
			</div>
		</nav>


		<div class="container">

			<form:form method="POST" modelAttribute="patientForm"
				class="form-signin">
				<h2 class="form-signin-heading">Add patient</h2>
				<p>Insert name:</p>
				<spring:bind path="name">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="text" path="name" class="form-control"
							placeholder="Name" autofocus="true"></form:input>
						<form:errors path="name"></form:errors>
					</div>
				</spring:bind>
				<p>Insert CNP:</p>
				<spring:bind path="cnp">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="text" path="cnp" class="form-control"
							placeholder="CNP"></form:input>
						<form:errors path="cnp"></form:errors>
					</div>
				</spring:bind>
				<p>Insert identity card number:</p>
				<spring:bind path="idcn">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input type="text" path="idcn"
							class="form-control" placeholder="Identity card number"></form:input>
						<form:errors path="idcn"></form:errors>
					</div>
				</spring:bind>

				<p>Insert date of Birth:</p>
				<input name="dateOfBirth" type="date" class="form-control"
					placeholder="dateOfBirth" autofocus="true" />
				<p>Insert address:</p>
				<input name="address" type="text" class="form-control"
					placeholder="Address" autofocus="true" />	
					<p></p>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
				<h2>
					<span style="color: red">${error }</span>
				</h2>
				<h2>
					<span style="color: green">${success}</span>
				</h2>
		   </form:form>
   <!-- <input id="do-some-action" type="button" value="Click me" onclick="sendAction" />
		       <!-- Javascript functions -->
    <!-- <script>

      /**
       * Send an action to the server.
       */
      function sendAction(event) {
        event.preventDefault();
        $.ajax({
          url: "/some-action",
          type: "POST"
        });
        return;
      } // function sendAction
      
      /**
       * Init operations.
       */
      $(document).ready(function() {
        // Bind the on-click event for the button element.
        $("#do-some-action").on("click", sendAction);
        
      });

    </script>-->
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
