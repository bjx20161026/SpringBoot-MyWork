/**
 * 
 */
$(function() {
	if (getCookie("EsbNavbar") == "inverse") {
		return $("#navbar").removeClass("navbar-default").addClass(
				"navbar-inverse");
	}
});

$(function() {
	if (getCookie("sidebar") == "default") {
		return $("#sidebar").removeClass("sidebar-inverse");
	}
});

$(function() {
	if (getCookie("background") != "") {
		return $("body").css("background-image", "url(" + getCookie("background") + ")");
	}
});

//$(function() {
//	return $(".card").css("background-color","transparent" )
//});

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name) == 0)
			return c.substring(name.length, c.length);
	}
	return "";
}
