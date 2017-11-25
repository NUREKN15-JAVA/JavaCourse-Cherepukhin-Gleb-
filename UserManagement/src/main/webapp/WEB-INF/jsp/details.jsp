<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/style.css">
<title>Details of user</title>
</head>
<body>
	<c:if test="${not empty error}">
		<div class="error-panel">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>
	<div id="content">
		<div>
			<form id="form" action="browse" method="get">
				<table>
					<tr>
						<td>Id</td>
						<td><input disabled="disabled" name="id" type="text"
							value="<c:out value="${user.id}" escapeXml="false"/>" /></td>
					</tr>
					<tr>
						<td>First Name</td>
						<td><input disabled="disabled" name="firstName" type="text"
							value="<c:out value="${user.firstName}" />" /></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><input disabled="disabled" name="lastName" type="text"
							value="<c:out value="${user.lastName}" />" /></td>
					</tr>
					<tr>
						<td>Date of birth</td>
						<td><input disabled="disabled" name="dateBirth" type="text"
							value="<c:out value="${user.dateBirth}" />" /></td>
					</tr>
				</table>
			</form>
		</div>
		<jsp:include page="/WEB-INF/jsp/common/okCancelControls.jsp"></jsp:include>
	</div>
</body>
</html>