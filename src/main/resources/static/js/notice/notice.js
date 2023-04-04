'use strict';

let nowPage = 1;

load(nowPage)

function load(nowPage) {
	const searchFlag = document.querySelector(".search__select").value;
	const searchValue = document.querySelector(".search__input").value;
	
	$.ajax({
		async: false,
		type: "get",
		url: "/notice/api/v1/notice/list/" + nowPage,
		data : {
			"searchFlag" : searchFlag,
			"searchValue" : searchValue
		},
		
		dataType: "json",
		success: (response) => {
			console.log(response)
			getList(response.data);
		},
		error: (error) => {
			console.log(error);
		}
	})
}

function getList(list) {
	const tbody = document.querySelector("tbody");
	tbody.innerHTML = "";
	list.forEach(notice => {
		tbody.innerHTML += `
			<tr class="notice__row">
				<td>${notice.noticeCode}</td>
				<td>${notice.noticeTitle}</td>
				<td>${notice.userId}</td>
				<td>${notice.createDate}</td>
				<td>${notice.noticeCount}</td>
			</tr>
		`
	})
} 





