const submitButton = document.querySelector('.submit');

submitButton.addEventListener('click', () => {
	
	// const textAreaValue = document.querySelector("#summernote").value;
	
	let formData = new FormData(document.querySelector("form"));
	
	formData.append("userCode", getUser().user_code);
	formData.forEach((value, key) => {
		console.log("key : " + key);
		console.log("value : " + value);
	});
	
	$.ajax({
		async : false,
		type: "post",
		url: "api/v1/notice",
		enctype: "multipart/form-data",
		contentType: false,
		processData: false,
		data: formData,
		dataType: "json",
		success: (response) => {
			console.log(response);
		},
		error : (error) => {
			console.log(error);
		}
	})
	
})