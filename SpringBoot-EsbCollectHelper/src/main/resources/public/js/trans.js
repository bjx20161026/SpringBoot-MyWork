$(function() {
	var data;
	function messageComponents() {
		$.ajax({
			async : false,
			type : "get",
			contentType : "application/json",
			url : "http://localhost:8080/api/errorLeftCount",
			success : function(result) {
				if (result != null) {
					data = result;
				}
			}
		});
	}
	messageComponents();
	$("#INLIST").text(data.INLIST);
	$("#VTLLIST").text(data.VTLLIST);
	$("#FLFLIST").text(data.FLFLIST);
	$("#OELIST").text(data.OELIST);
});

$(document).ready(function() {
	$("#valueTooLarge").click(function() {
		var url = "http://localhost:8080/api/valueTooLarge";
		window.location.href = url;
	});
});

$(document).ready(function() {
	$("#otherError").click(function() {
		var url = "http://localhost:8080/api/otherError";
		window.location.href = url;
	});
});