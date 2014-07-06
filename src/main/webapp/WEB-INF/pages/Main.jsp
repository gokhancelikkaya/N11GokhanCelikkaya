<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>

<link rel="stylesheet" type="text/css"
	href="//code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/plug-ins/be7019ee387/integration/jqueryui/dataTables.jqueryui.css">

<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.min.js"></script>
<script
	src="//cdn.datatables.net/plug-ins/be7019ee387/integration/jqueryui/dataTables.jqueryui.js"></script>
<script src="<c:url value="/resources/js/jquery.maskedinput.min.js" />"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/main.css" />">
<script src="<c:url value="/resources/js/main.js" />"></script>

<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		initialize();
	});
</script>

</head>
<body>
	<h2>Customer List</h2>

	<table id="table" class="display">
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Phone</th>
				<td>Edit</td>
				<td>Delete</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${customers}" var="customer">
				<tr>
					<td class="fname"><c:out value="${customer.firstName}" /></td>
					<td class="lname"><c:out value="${customer.lastName}" /></td>
					<td class="phone"><c:out value="${customer.phone}" /></td>
					<td><a class="edit" customerId="${customer.id}" href="#">Edit</a></td>
					<td><a class="delete" customerId="${customer.id}" href="#">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5" align="center"><a class="new">New Customer</a></td>
			</tr>
		</tfoot>
	</table>


	<div id="dialog-create" class="dialog">
		<p class="validateTips">All form fields are required.</p>

		<form>
			<fieldset>
				<label for="firstName">First Name</label> <input type="text"
					name="firstName" id="firstName"
					class="text ui-widget-content ui-corner-all"> <label
					for="lastName">Last Name</label> <input type="text" name="lastName"
					id="lastName" class="text ui-widget-content ui-corner-all">
				<label for="phone">Phone</label> <input type="text" name="phone"
					id="phone" class="text ui-widget-content ui-corner-all">

				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px">
			</fieldset>
		</form>
	</div>

	<div id="dialog-edit" class="dialog">
		<p class="validateTips">All form fields are required.</p>

		<form>
			<fieldset>
				<label for="firstName">First Name</label> <input type="text"
					name="firstName" id="firstName"
					class="text ui-widget-content ui-corner-all"> <label
					for="lastName">Last Name</label> <input type="text" name="lastName"
					id="lastName" class="text ui-widget-content ui-corner-all">
				<label for="phone">Phone</label> <input type="text" name="phone"
					id="phone" class="text ui-widget-content ui-corner-all">

				<!-- Allow form submission with keyboard without duplicating the dialog button -->
				<input type="submit" tabindex="-1"
					style="position: absolute; top: -1000px">
			</fieldset>
		</form>
	</div>

	<div id="dialog-delete" class="dialog">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Customer will be
			permanently deleted and cannot be recovered. Are you sure?
		</p>
	</div>

	<div class="loadingModal"></div>
</body>
</html>