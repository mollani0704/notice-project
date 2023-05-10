'use strict';

function getUserList() {
	$.ajax({
		async: false,
		type: "get",
		url: "/api/users",
		dataType: "json",
		success: (response) => {
			console.log(response)	
		},
		
		error: (error) => {
			console.log(error)
		}
	})
}

getUserList();