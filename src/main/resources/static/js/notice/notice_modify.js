'use strict';

let noticeCode = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);

const noticeTitle = document.querySelector('.notice__title')
const noticeContent = document.querySelector('#summernote')
const modifyBtn = document.querySelector('.modify');

modifyBtn.addEventListener('click', () => {
	

	let modify_data = {
		noticeTitle : noticeTitle.value,
		noticeContent: noticeContent.value
	};
	
	
	
	$.ajax({
		type: "PUT",
		url: "api/v1/notice/"+noticeCode,
		data: JSON.stringify(modify_data),
		contentType: "application/json; charset=utf-8",
		dataType:"json"
	})
	.done((response) => {
		alert("글 수정이 완료 되었습니다.")
		location.href="/notice";
	})
	.fail((error) => {
		console.log(error);
	})
	
});