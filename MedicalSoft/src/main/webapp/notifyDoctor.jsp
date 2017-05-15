<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
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
					<li><a href="${contextPath}/secretary">Add patient</a></li>
					<li ><a href="${contextPath}/editPatient">Edit patient info</a></li>
					<li ><a href="${contextPath}/addConsultation">Add consultation</a></li>
					<li><a href="${contextPath}/editConsultation">Edit consultation</a></li>
					<li><a href="${contextPath}/deleteConsultation">Delete consultation</a></li>
					<li><a href="${contextPath}/seeConsultation">See consultations</a></li>
					<li class="active"><a href="${contextPath}/notifyDoctor">Notify doctor</a></li>
				</ul>
				<a href="${contextPath}/login">
					<button class="btn btn-danger navbar-btn">Log out</button>
				</a>
			</div>
		</nav>

		<div class="container">
	    <div class="form-signin" > 
	     <form:form method="POST" modelAttribute="date" class="form-signin"> 
		<p>Please select doctor: </p>					
	     <select name="doctor" class="form-control" >
				<c:forEach var="doctor" items="${doctors}">
					<option value="${doctor.getUsername()}">${ doctor.getName()}</option>							
    			</c:forEach>
		</select>	
		
		<p>Please select patient: </p>
					
	     <select name="patient"   class="form-control" >
				<c:forEach var="patient" items="${patients}">
					<option value="${patient.getName()}">${ patient.getName()}</option>							
    					</c:forEach>
		</select>
		
		<p></p>
		<button id="do-some-action" class="btn btn-lg btn-primary btn-block" name="searchDoctor" type="submit">Notify doctor</button>
          </form:form>
        </div>
      
		       <!-- Javascript functions -->
    <!-- <script>

      /**
       * Send an action to the server.
       */
      function sendAction(event) {
        event.preventDefault();
        $.ajax({
          url: "/notifyDoctor",
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

    </script>	 -->
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
