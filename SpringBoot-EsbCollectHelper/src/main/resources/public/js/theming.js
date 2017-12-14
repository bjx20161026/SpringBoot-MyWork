$("input:radio[name=radio-navbar]").bind(
		"click",
		function() {
			var value;
			value = $(this).val();
			if (value === "default") {
				setCookie('EsbNavbar', 'default', 'd7');
				return $("#navbar").addClass("navbar-default").removeClass(
						"navbar-inverse");
			} else if (value === "inverse") {
				setCookie('EsbNavbar', 'inverse', 'd7');
				return $("#navbar").removeClass("navbar-default").addClass(
						"navbar-inverse");
			}
		});

$("input:radio[name=radio-sidebar]").bind("click", function() {
	var value;
	value = $(this).val();
	if (value === "default") {
		setCookie('sidebar', 'default', 'd7');
		return $("#sidebar").removeClass("sidebar-inverse");
	} else if (value === "inverse") {
		setCookie('sidebar', 'inverse', 'd7');
		return $("#sidebar").addClass("sidebar-inverse");
	}
});

$("input:radio[name=radio-color]").bind("click", function() {
	var value;
	value = $(this).val();
	if (value === "blue") {
		setCookie('body', 'blue', 'd7');
		return $("body").removeClass("flat-green").addClass("flat-blue");
	} else if (value === "green") {
		setCookie('body', 'green', 'd7');
		return $("body").removeClass("flat-blue").addClass("flat-green");
	}
});

$(document).ready(function() {
	$(".img-responsive").click(function() {
		var src = $(this).attr("src");
		setCookie('background', src, 'd7');
		$("body").css("background-image", "url(" + src + ")");
	});
});

function setCookie(name, value, time) {
	var strsec = getsec(time);
	var exp = new Date();
	exp.setTime(exp.getTime() + strsec * 1);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString() + ";path=/";
}
function getsec(str) {
	var str1 = str.substring(1, str.length) * 1;
	var str2 = str.substring(0, 1);
	if (str2 == "s") {
		return str1 * 1000;
	} else if (str2 == "h") {
		return str1 * 60 * 60 * 1000;
	} else if (str2 == "d") {
		return str1 * 24 * 60 * 60 * 1000;
	}
}
