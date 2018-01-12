/**
 * 
 */
var fileNum=0;

function intipreview(filenName){
	var tableData =  "                   <table class=\"table table-bordered\">\r\n" + 
		"                                     <thead>\r\n" + 
		"                                        <tr>\r\n" + 
		"                                            <th style=\"width: 25px;\">#</th>\r\n"+
		"                                            <th>FileContent</th>\r\n"+
		"                                        </tr>\r\n" + 
		"                                   	</thead>\r\n" + 
		"                                    	<tbody>\r\n";
	var fruits;
	$.ajax({
		async : false,
		type : "get",
		contentType : "application/json",
		url : "http://localhost:8080/api/filePreview?fileName="+filenName+"&start=0&charset=UTF-8",
		success : function(result) {
			fruits = result;
		}
	});
	tableData += fruits.datas.mydata();
	tableData +="                                    </tbody>\r\n" + 
				"                                </table>"; 
	fileNum=500;
	$("#fileInfo").html(fruits.fileInfo);
	$("#mytable").html(tableData);
}

Array.prototype.mydata=function(){
    var str = "";
	for (i=0;i<this.length;i++){
		var temp = escape2Html(this[i]);
			str +=	"										<tr>\r\n"+ 
					"                                            <th scope=\"row\">"+(i+1)+"</th>\r\n"+
					"                                            <td>"+temp+"</td>\r\n"+
					"                                       </tr>\r\n";
	}
	return str;
}

function escape2Html(str){  
    var s = "";
    if(str.length == 0) return "";
    s = str.replace(/&/g,"&amp;");
    s = s.replace(/</g,"&lt;");
    s = s.replace(/>/g,"&gt;");
    s = s.replace(/ /g,"&nbsp;");
    s = s.replace(/\'/g,"&#39;");
    s = s.replace(/\"/g,"&quot;");
    return s;  
}
$(function(){
	 $("#test1").click(function(fileName){
		 fileNum=0;
		 intipreview(filenName);
		 $('#modalCardExample').modal('show');
	 });	
});	

$('#mydecode').bootstrapSwitch({  
                onText:'GBK',  
                offText:'UTF8'  
});  

function preview(fileName){
	 fileNum=0;
	 intipreview(fileName);
	 $('#modalCardExample').modal('show');
}

$('#mydecode').on('switchChange.bootstrapSwitch', function (event,state) {  
	showMsg = state;  
	if(showMsg==true){
		alert("1234");
	}else{
		alert("abcd");
	}
});