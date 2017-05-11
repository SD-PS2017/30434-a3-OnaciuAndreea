<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en" >
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
    <script src="stomp.js"></script>
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
					<li class="active"><a href="${contextPath}/doctor">Add patient consultation</a></li>
					<li ><a href="${contextPath}/viewConsultation">View patient consultation</a></li>
				</ul>
					<a href="${contextPath}/login">
					<button class="btn btn-danger navbar-btn">Log out</button>
				</a>
			</div>
		</nav>
				<form:form method="POST"   class="form-signin" >  
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
	

   <!-- <textarea id="notifications-area" cols="60" rows="10" readonly="readonly"></textarea>  --> 

    <!-- Javascript functions -->
    <script>

      /**
       * Open the web socket connection and subscribe the "/notify" channel.
       */
      function connect() {

        // Create and init the SockJS object
        var socket = new SockJS('/ws');
        var stompClient = Stomp.over(socket);

        // Subscribe the '/notify' channell
        stompClient.connect({}, function(frame) {
          stompClient.subscribe('/user/queue/notify', function(notification) {			
            // Call the notify function when receive a notification
            notify(JSON.parse(notification.body).content);

          });
        });
        
        return;
      } // function connect
      
      /**
       * Display the notification message.
       */
      function notify(message) {
        alert(message);//$("#notifications-area").append(message + "\n");
        return;
      }
      
      /**
       * Init operations.
       */
      $(document).ready(function() {
        
        // Start the web socket connection.
        connect();
        
      });
    </script>
	
	
	
	
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
