$(function() {
	var data;
	function messageComponents() {
		$.ajax({
			async : false,
			type : "get",
			contentType : "application/json",
			url : "http://192.168.1.193:8080/api/messageComponents",
			success : function(result) {
				if (result != null) {
					data = result;
				}
			}
		});
	}
	messageComponents();
	$("#Error").text(data.ERR);
	$("#Invalid").text(Math.round((data.TOT-data.EFF)/1000) +"K");
	$("#handled").text(Math.round(data.EFF/1000)+"K");
	$("#Total").text(Math.round(data.TOT/1000)+"K");	
});

$(function() {
	var ctx, data, myLineChart, options;
	Chart.defaults.global.responsive = true;
	ctx = $('#jumbotron-line-chart').get(0).getContext('2d');
	options = {
		scaleShowGridLines : true,
		scaleGridLineColor : "rgba(0,0,0,.05)",
		scaleGridLineWidth : 1,
		scaleShowHorizontalLines : true,
		scaleShowVerticalLines : true,
		bezierCurve : false,
		bezierCurveTension : 0.4,
		pointDot : true,
		pointDotRadius : 4,
		pointDotStrokeWidth : 1,
		pointHitDetectionRadius : 20,
		datasetStroke : true,
		datasetStrokeWidth : 2,
		datasetFill : true,
		legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].strokeColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
	};
	function msgCount() {
		$.ajax({
			async : false,
			type : "get",
			contentType : "application/json",
			url : "http://192.168.1.193:8080/api/msgCount",
			success : function(result) {
				if (result != null) {
					data = result;
				}
			}
		});
	}
	msgCount();
	myLineChart = new Chart(ctx).Line(data, options);
});

$(function() {
	var ctx, data, myBarChart, option_bars;
	Chart.defaults.global.responsive = true;
	ctx = $('#jumbotron-bar-chart').get(0).getContext('2d');
	option_bars = {
		showScale : true,
		scaleShowGridLines : true,
		//scaleBeginAtZero : true,
		//scaleShowGridLines : true,
		scaleGridLineColor : "rgba(0,0,0,.05)",
		scaleGridLineWidth : 1,
		scaleShowHorizontalLines : true,
		scaleShowVerticalLines : true,
		barShowStroke : true,
		barStrokeWidth : 1,
		barValueSpacing : 7,
		barDatasetSpacing : 3,
		legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].fillColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
	};
/*	data = {
		labels : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul' ],
		datasets : [ {
			label : "My First dataset",
			fillColor : "rgba(26, 188, 156,0.6)",
			strokeColor : "#1ABC9C",
			pointColor : "#1ABC9C",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "#1ABC9C",
			data : [ 65, 59, 80, 81, 56, 55, 40 ]
		}, {
			label : "My Second dataset",
			fillColor : "rgba(34, 167, 240,0.6)",
			strokeColor : "#22A7F0",
			pointColor : "#22A7F0",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "#22A7F0",
			data : [ 28, 48, 40, 19, 86, 27, 90 ]
		} ]
	};
	*/
	function transError(){
		$.ajax({
			async : false,
			type : "get",
			contentType : "application/json",
			url : "http://192.168.1.193:8080/api/transError",
			success : function(result) {
				if (result != null) {
					data = result;
				}
			}
		});
	}
	transError();
	myBarChart = new Chart(ctx).Bar(data, option_bars);
});