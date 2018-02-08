/**
 * 
 */
var cacheName;
var cacheId="0";
$(function() {
	$("#refresh").click(function() {
		$('#tt').treegrid('reload', cacheId);
	});
	$("#preview").click(function() {
		preview(cacheName);
	});
	$("#downLoad").click(function() {
		download(cacheName);
	});
});


function download(fileName){
    var url = "http://"+urlandport+"/api/downByFileName?fileName="+fileName;
    window.location.href=url;
}

$(function() {
	$('#tt').treegrid({
		url: "http://"+urlandport+"/api/folderBrowser?id="+cacheId,
		method: 'get',
		rownumbers: false,
		idField: 'id',
		treeField: 'name',	
 	    onExpand : function(node) {
	    	//alert(node.id);
	    	cacheId=node.id;
	    },

        onContextMenu : function(e,node) {
            e.preventDefault();//阻止默认的事件，浏览器的右键菜单
            //$(this).tree('select', node.target);            
  //          alert(node.name);
            cacheName=node.name;
            cacheId=node.id;
            if ($('#tt').treegrid('isLeaf',node.target)) {
            	$('#mm').menu('show', {
					left : e.pageX, //显示的位置
					top : e.pageY
           		});
            } else {
				alert("这是一个文件夹");
            }
        },
        
	});	
});
