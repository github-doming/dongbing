var labelTextDefaultColor = "#4A535E";
var editorDefaultColor = "#CCC";

var errorColorCode = "#B6615F";

function validateRequiredData() {
	var status = true;
	var count = 0;
	$("input,select").each(function() {
		if ($(this).attr("required") != undefined) {
			if ($(this).val() == "") {
				count++;
				$(this).css("border-color", errorColorCode);
				
				if ( $(this).attr("name") != undefined 
						&& $(this).attr("name") != "" ) {
					setLabelColor($(this).attr("name"), errorColorCode);
				}
				
				$(this).keydown(function() {
					if ($(this).val() != "") {
						$(this).css("border-color", "#999");
						setLabelColor($(this).attr("name"), labelTextDefaultColor);
						
						$(this).blur(function() {
							$(this).css("border-color", editorDefaultColor);
						});
					}
				});
			}
		}
	});
	if (count > 0)
		status = false;
	return status;
}

function setLabelColor(name, color) {
	$("label").each(function(){
		if ($(this).attr("for") != undefined 
				&& $(this).attr("for") == name) {
			$(this).css("color", color);
		}
	});
}