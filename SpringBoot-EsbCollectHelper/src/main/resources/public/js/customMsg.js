var CharSet = "gbk";

$("input:radio[name=radiochar]").bind("click", function() {
	var value;
	value = $(this).val();
	CharSet = value;
});

$(document).ready(function() {
	$("#submit").click(function() {
		                                var  ConnectionString = $("#ConnectionString").val();
										if (ConnectionString == "") {
											alert("ConnectionString can not be null!");
											return;
										}
										var  Path = $("#Path").val();
										if ($("#Path").val() == "") {
											alert("Path can not be null!");
											return;
										}
										var  userName = $("#userName").val();
										if ($("#userName").val() == "") {
											alert("userName can not be null!");
											return;
										}
										var  password = $("#password").val();
										if ($("#password").val() == "") {
											alert("password can not be null!");
											return;
										}
										var  fileName = $("#fileName").val();

										if ($("#fileName").val() == "") {
											alert("fileName can not be null!");
											return;
										}
										var  FileFormat = $("#FileFormat").val();

										if ($("#FileFormat").val() == "") {
											alert("FileFormat can not be null!");
											return;
										}
										var  FieldSeparator = $("#FieldSeparator").val();

										if (fileName.toLowerCase().indexOf(".txt") > 0
												|| fileName.toLowerCase().indexOf(".csv") > 0) {
											if (FieldSeparator == "") {
												alert("FieldSeparator can not be null!");
												return;
											}
										}
										
										var data = {"ConnectionString":ConnectionString,
												     "Path":Path,
												     "userName":userName,
												     "password":password,
												     "fileName":fileName,
												     "FileFormat":FileFormat,
												     "FieldSeparator":FieldSeparator,
												     "CharSet":CharSet
												     };
									$.ajax({
											async : true,
											type : "POST",
											data: JSON.stringify(data),
											contentType : "application/json",
											url : "http://192.168.1.193:8080/api/customMsg",
											success : function(result) {
												alert(result);
											}
										});
										
	});
});