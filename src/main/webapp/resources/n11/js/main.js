var createDialog, deleteDialog, tips;

function updateTips(t) {
	tips.html(t).addClass("ui-state-highlight").addClass("error");
	setTimeout(function() {
		tips.removeClass("ui-state-highlight", 1500).removeClass("error");
	}, 500);
}

function addCustomer() {
	var data = {
		firstName : $(this).find("#firstName").val(),
		lastName : $(this).find("#lastName").val(),
		phone : $(this).find("#phone").val()
	};

	$.ajax({
		url : "./ajax",
		data : JSON.stringify(data),
		type : "POST",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			if (data.status == "ERROR") {
				var errors = data.errors;
				var message = "";
				for (var i = 0; i < errors.length; i++) {
					message += errors[i] + "<br/>";
				}
				updateTips(message);
			} else if (data.status == "OK") {

				var editButton = $("<a class='edit' customerId='"
						+ data.customer.id + "' href='#'>Edit</a>");
				editButton.button().click(function(event) {
					editCustomerClicked($(this).attr("customerId"));
				});
				var delButton = $("<a class='delete' customerId='"
						+ data.customer.id + "' href='#'>Delete</a>");
				delButton.button().click(function(event) {
					deleteCustomerClicked($(this).attr("customerId"));
				});

				var tr = $("<tr>");
				tr.append("<td class=\"fname\">" + data.customer.firstName
						+ "</td>");
				tr.append("<td class=\"lname\">" + data.customer.lastName
						+ "</td>");
				tr.append("<td class=\"phone\">" + data.customer.phone
						+ "</td>");
				tr.append($("<td>").append(editButton));
				tr.append($("<td>").append(delButton));
				$('#table').append(tr);

				$("#table").find("td.dataTables_empty").parent().remove();

				createDialog.dialog("close");
			}
		}
	});
}

function updateCustomer(data, tr) {
	$.ajax({
		url : "./ajax",
		data : JSON.stringify(data),
		type : "PUT",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(response) {
			if (response.status == "ERROR") {
				var errors = response.errors;
				var message = "";
				for (var i = 0; i < errors.length; i++) {
					message += errors[i] + "<br/>";
				}
				updateTips(message);
			} else if (response.status == "OK") {

				tr.find("td.fname").text(response.customer.firstName);
				tr.find("td.lname").text(response.customer.lastName);
				tr.find("td.phone").text(response.customer.phone);

				editDialog.dialog("close");
			}
		}
	});
}

function editCustomerClicked(id) {
	var tr = $("a.edit[customerId='" + id + "']").parent().parent();
	editDialog.find("input#firstName").val(tr.find(".fname").text());
	editDialog.find("input#lastName").val(tr.find(".lname").text());
	var phone = editDialog.find("input#phone");
	phone.mask("(999) 999 99 99");
	phone.val(tr.find(".phone").text());

	editDialog = $("#dialog-edit").dialog({
		autoOpen : false,
		height : 400,
		width : 550,
		modal : true,
		title : "Edit Customer",
		buttons : {
			"Update" : function() {
				var data = {
					id : id,
					firstName : $(this).find("#firstName").val(),
					lastName : $(this).find("#lastName").val(),
					phone : $(this).find("#phone").val()
				};
				updateCustomer(data, tr);

			},
			Cancel : function() {
				editDialog.dialog("close");
			}
		},
		close : function() {
			$(this).find("form")[0].reset();
			updateTips("");
		}
	});

	editDialog.dialog("open");
}

function deleteCustomer(data) {

	$("body").addClass("loading");
	$.ajax({
		url : "./ajax",
		data : JSON.stringify(data),
		type : "DELETE",
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(response) {
			if (response.status == "ERROR") {
				var errors = response.errors;
				var message = "";
				for (var i = 0; i < errors.length; i++) {
					message += errors[i] + "<br/>";
				}
				updateTips(message);
			} else if (response.status == "OK") {
				$("#table a.delete[customerId='" + data.id + "']").parent()
						.parent().remove();

				deleteDialog.dialog("close");
			}

			$("body").removeClass("loading");
		}
	});
}

function deleteCustomerClicked(id) {

	deleteDialog = $("#dialog-delete").dialog({
		autoOpen : false,
		height : 200,
		modal : true,
		title : "Delete Customer",
		buttons : {
			"Delete" : function() {
				var data = {
					id : id
				};
				deleteCustomer(data);
			},
			Cancel : function() {
				deleteDialog.dialog("close");
			}
		},
		close : function() {
			updateTips("");
		}
	});

	deleteDialog.dialog("open");
}

function initialize() {
	tips = $(".validateTips");

	$('#table').DataTable();

	$('#table').on('draw.dt', function() {
		$("a.edit").button().click(function(event) {
			editCustomerClicked($(this).attr("customerId"));
		});

		$("a.delete").button().click(function(event) {
			deleteCustomerClicked($(this).attr("customerId"));
		});
	});

	createDialog = $("#dialog-create").dialog({
		autoOpen : false,
		height : 400,
		width : 550,
		modal : true,
		title : "New Customer",
		buttons : {
			"Create" : addCustomer,
			Cancel : function() {
				createDialog.dialog("close");
			}
		},
		close : function() {
			$(this).find("form")[0].reset();
			updateTips("");
		}
	});

	deleteDialog = $("#dialog-delete").dialog({
		autoOpen : false
	});

	editDialog = $("#dialog-edit").dialog({
		autoOpen : false
	});

	$("a.edit").button().click(function(event) {
		editCustomerClicked($(this).attr("customerId"));
	});

	$("a.delete").button().click(function(event) {
		deleteCustomerClicked($(this).attr("customerId"));
	});

	$("a.new").button().click(function(event) {
		createDialog.dialog("open");
	});

	$("#phone").mask("(999) 999 99 99");
}
