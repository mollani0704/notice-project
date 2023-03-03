'use strict';

const name = document.querySelector('.name')
const username = document.querySelector('.username')
const password = document.querySelector('.password')
const email = document.querySelector('.email')

let checkUsernameFlag = false;

const signup__btn = document.querySelector('.signup__btn');

username.addEventListener('blur', () => {
	$.ajax({
		async: false,
		type: "get",
		url: "/api/auth/signup/validation/username",
		data: {username : username.value},
		dataType: "json",
		success: (response) => {
			checkUsernameFlag = response.data;
			
			if(checkUsernameFlag) {
				alert("사용 가능한 아이디입니다.")
			} else {
				alert("이미 사용 중인 아이디 입니다.")
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

signup__btn.addEventListener('click', () => {	
	
	let signupData = {
		name : name.value,
		username : username.value,
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
				location.replace("/signin")
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
