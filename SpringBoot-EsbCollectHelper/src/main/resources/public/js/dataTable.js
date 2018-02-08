var showMsg = false;

$(function() {
	table = $('#esbMsg').DataTable();
	table.column( 3 ).visible( false );
	table.column( 4 ).visible( false );
	table.column( 5 ).visible( false );
	});

$('#my-checkbox').bootstrapSwitch();  

$('#my-checkbox').on('switchChange.bootstrapSwitch', function (event,state) {  
	showMsg = state;  
	if(showMsg==true){
		table = $('#esbMsg').DataTable();
		table.column( 4 ).visible( true );
	}else{
		table = $('#esbMsg').DataTable();
		table.column( 4 ).visible( false );
	}
});

flatpickr("#startTime", {
enableTime:true,
enableSeconds:true
});

flatpickr("#endTime", {
enableTime:true,
enableSeconds:true
});

$(document).ready(function() {
	$("#button").click(function() {
		$("#button").removeClass("btn btn-success").addClass("btn btn-primary").attr('disabled',true);
		$("#loader").addClass("card loader");
		var data;
		var table;
		var protocol = $("#content").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		function queryData() {
			$.ajax({
						async : true,
						type : "get",
						contentType : "application/json",
						url : "http://"+urlandport+"/api/messageKeyInfo?protocol="+protocol+"&startTime="+startTime+"&endTime="+endTime,
						success : function(result) {
							if (result != null) {
								data = result;
								table = $('#esbMsg').DataTable({
									destroy : true,
									bAutoWidth : true,
									data : data,
									columns : [ 
										{data : 'TIMES'},
										{data : 'CHARSET'},
										{data : 'FILENAME'},
										{data : 'DOWNLOAD'},
										{data : 'SENDMSG'},
										{data : 'PREVIEW'},
										]
								});
								$("#button").removeClass("btn btn-primary").addClass("btn btn-success").attr('disabled',false);
								$("#loader").removeClass("card loader");
								table.column( 3 ).visible( true );
								table.column( 5 ).visible( true );
								if (showMsg == false) {
								table.column( 4 ).visible( false );
								}
							}
						}
					});
		}
		queryData();
	});
});

function sendMsg(fileName){
	$.ajax({
		async : false,
		type : "get",
		contentType : "application/json",
		url : "http://"+urlandport+"/api/sendMsg?fileName="+fileName,
		success : function(result) {
			alert(result);
		}
	});
}

function download(fileName){
    var url = "http://"+urlandport+"/api/downByFileName?fileName="+fileName;
    window.location.href=url;
}


