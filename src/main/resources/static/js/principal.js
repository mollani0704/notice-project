function getPrincipal() {
	let user = null;
	
	$.ajax({
		async: false,
		type: "get",
		url: "/api/auth/principal",
		dataType: "json",
		success: (response) => {
			user = response.data
		},
		error: (error) => {
			console.log(error);
		}
	})
	
	return user;
}

function loadHeader(user) {
	
	const authItems= document.querySelector(".auth__items")
	
	if(user == null){
		authItems.innerHTML = `
			<button type="button" class="auth__button signin">로그인</button>
			<button type="button" class="auth__button signup">회원가입</button>
		`;
		
		const signin = document.querySelector('.signin');
		const signup = document.querySelector('.signup');
		
		signin.addEventListener("click", () => {
			location.href = "/signin"
		})
		
		signup.addEventListener("click", () => {
			location.href = "/signup"
		})
		
	} else {
		authItems.innerHTML = `
			<button type="button" class="auth__button username">${user.user_name}</button>
			<button type="button" class="auth__button logout">로그아웃</button>
		`;
		
		const username = document.querySelector('.username');
		const logout = document.querySelector(".logout");
		
		username.addEventListener("click", () => {
			alert(`
				<<회원 정보>>
				아이디 : ${user.user_id}
				이름 : ${user.user_name}
				이메일 : ${user.user_email}
			`)
		})
		
		logout.addEventListener('click', () => {
			location.replace('/logout')
		})
	}
}

let user = getPrincipal();
loadHeader(user);

function getUser() {
	return user;
}
