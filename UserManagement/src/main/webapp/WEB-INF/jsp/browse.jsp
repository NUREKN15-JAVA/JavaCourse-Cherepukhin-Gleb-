<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/style.css">
<title>Browse page</title>
<script src="js/selection.js"></script>
</head>
<body>
	<c:if test="${not empty error}">
		<div class="error-panel">
			<c:out value="${error}"></c:out>
		</div>
	</c:if>
	<div id="content">
		<div>
			<table id="userTable">
				<thead>
					<tr>
						<td>ID</td>
						<td>First name</td>
						<td>Last name</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users}">
						<tr>
							<td><c:out value="${user.id}" /></td>
							<td><c:out value="${user.firstName}" /></td>
							<td><c:out value="${user.lastName}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div>
			<table id="controls">
				<tr>
					<td><a id="addBtn" class="btn" href="add">Add</a></td>
					<td><a id="editBtn" class="btn" href="edit">Edit</a></td>
					<td><a id="detailsBtn" class="btn" href="details">Details</a></td>
					<td><a id="deleteBtn" class="btn" href="delete">Delete</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>