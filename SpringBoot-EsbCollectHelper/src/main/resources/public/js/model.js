/**
 * 
 */
var fileNum=0;
var keytotal=0;
var cachefileName;
var cacheEncode="UTF-8";
var cacheFileInfo;

function intipreview(fileName,start,charset){
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
		url : "http://localhost:8080/api/filePreview?fileName="+fileName+"&start="+start+"&charset="+charset,
		success : function(result) {
			fruits = result;
		}
	});
	tableData += fruits.datas.mydata();
	tableData +="                                    </tbody>\r\n" + 
				"                                </table>"; 
	cachefileName = fileName;
	cacheFileInfo=fruits.fileInfo;
	$("#myfileName").text(fileName);
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
	 $("#buttonL").click(function(){
		 fileNum=fileNum+500;
		 intipreview(cachefileName,fileNum,cacheEncode);
		 $('#modalCardExample').modal('show');
	 });	
});	

$(function(){
	$("body").css("overflow", " auto");
	$(".modal").css("overflow", " auto");
});

$('#mydecode').bootstrapSwitch({  
                onText:'UTF8',  
                offText:'GBK'  
});  

function preview(fileName){
	 fileNum=0;
	 intipreview(fileName,0,cacheEncode);
	 $('#modalCardExample').modal('show');
}

$('#mydecode').on('switchChange.bootstrapSwitch', function (event,state) {  
	showMsg = state;  
	if(showMsg==true){
		cacheEncode="GBK";
		intipreview(cachefileName,fileNum,cacheEncode);
		$('#modalCardExample').modal('show');
	}else{
		cacheEncode="UTF-8";
		intipreview(cachefileName,fileNum,cacheEncode);
		$('#modalCardExample').modal('show');
	}
});


/*key value start*/
$(function(){
	  $("#buttonK").click(function(){
		  if(!cachefileName.endWith("xml")&&!cachefileName.endWith("xml.gz")){
			  alert("Now key value model only for xml file!");
			  return;
		  }
		  keytotal=0;
		  keyvaluemodel(0);
	  });
});

$(function(){
	  $("#buttonKL").click(function(){
		  keytotal=keytotal+10;
		  keyvaluemodel(keytotal);
	  });
});



	function keyvaluemodel(start){
		  var tableData = "                   <table class=\"table table-bordered\">\r\n" + 
			"                                     <thead>\r\n" + 
			"                                        <tr>\r\n" + 
			"                                            <th style=\"width: 25px;\">#</th>\r\n";
		  var fruits;
		  $.ajax({
				async : false,
				type : "get",
				contentType : "application/json",
				url : "http://localhost:8080/api/xmlKeyMap?fileName="+cachefileName+"&charset="+cacheEncode+"&start="+start,
				success : function(result) {
					fruits = result;
				}
			});
		    tableData += fruits.heads.myheads();
		    tableData +="                                        </tr>\r\n" + 
			"                                   </thead>\r\n" + 
			"                                    <tbody>\r\n" ;
			tableData += fruits.datas.mydatas();
			tableData +="                                    </tbody>\r\n" + 
			"                                </table>"; 
			
			$("#fileNameK2v").text(cachefileName);
			$("#fileInfok2v").html(cacheFileInfo);
			$("#mytableK2v").html(tableData);
			$('#modalCardExample').modal('hide');
			$('#key2value').modal('show');
	}
	

	String.prototype.endWith = function(str) {
		if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
		if (this.substring(this.length - str.length) == str)
			return true;
		else
			return false;
		return true;
	}

	Array.prototype.myheads=function(){
	    var str = "";
		for (i=0;i<this.length;i++){
				str +="                                            <th>"+this[i]+"</th>\r\n";
			
		}
		return str;
	}

	Array.prototype.mydatas=function(){
	    var str = "";
		for (i=0;i<this.length;i++){
			str +="										<tr>\r\n"+ 
			"                                            <th scope=\"row\">"+(i+1)+"</th>\r\n";
			for(j=0;j<this[i].length;j++){
				this[i][j]=this[i][j];
				str +="                                            <td>"+this[i][j]+"</td>\r\n";
			}
			str+="                                        </tr>\r\n";
		}
		return str;
	}
/*key value end*/	

		











