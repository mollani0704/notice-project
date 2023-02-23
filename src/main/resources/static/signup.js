'use strict';

const name = document.querySelector('.name')
const username = document.querySelector('.username')
const password = document.querySelector('.password')
const email = document.querySelector('.email')

const signup__btn = document.querySelector('.signup__btn');

signup__btn.addEventListener('click', () => {	
	
	let signupData = {
		name : name.value,
		useranme : username.value,
		password : password.value,
		email : email.value
	}
	
	$.ajax({
		async : false,
		type : "post",
		url : "/api/auth/signup",
		contentType : "application/json",
		data : JSON.stringify(signupData),
		dataType : "json",
		success : (response) => {
			if(response.data) {
				alert("회원가입이 완료되었습니다.")
				location.replace("/auth/signin")
			}
		},
		error: (error) => {
			if(error.status == 400) {			
				alert(JSON.stringify(error.responseJSON.data))
			} else {
			console.log("요청실패")
			}
		} 	
	})
})
