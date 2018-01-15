/**
 * 
 */
$(function(){
  $("#buttonKL").click(function(){
	  var tableData = "                   <table class=\"table table-bordered\">\r\n" + 
		"                                     <thead>\r\n" + 
		"                                        <tr>\r\n" + 
		"                                            <th style=\"width: 25px;\">#</th>\r\n";
	  var fruits;
	  $.ajax({
			async : false,
			type : "get",
			contentType : "application/json",
			url : "http://localhost:8080/api/xmlKeyMap",
			success : function(result) {
				alert(result);
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
		$("#mytableK2v").append(tableData);
  });
});

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