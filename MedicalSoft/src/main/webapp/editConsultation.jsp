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

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
					<li><a href="${contextPath}/addConsultation">Add consultation</a></li>
					<li  class="active"><a href="${contextPath}/editConsultation">Edit consultation</a></li>
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
	    <form:form method="POST" modelAttribute="editPatient"  class="form-signin" >  
	     	     
	      <p>Please select doctor: </p>
					
	        <select name="doctor" >
			    	<c:forEach var="doctor" items="${doctors}">
				    	<option value="${doctor.getUsername()}">${ doctor.getName()}</option>							
    				 </c:forEach>
	    	</select>
		
		 <p>Please select patient: </p>
					
	     <select name="patient" >
				<c:forEach var="patient" items="${patients}">
					<option value="${patient.getCnp()}">${ patient.getName()}</option>							
    					</c:forEach>
		</select>
		
	     <p>Please select date: </p>					
		<input name="date" type="date" class="form-control"
					placeholder="date" autofocus="true" />
		
         <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
         <h2><span style="color:red" >${error }</span></h2>
         </form:form>	
		
		
		
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
