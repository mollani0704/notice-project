let noticeCode = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
const modifyBtn = document.querySelector('.modify');
const deleteBtn = document.querySelector('.delete');

modifyBtn.addEventListener('click', () => {
	location.href = `/notice/modify/${noticeCode}`;
})

deleteBtn.addEventListener('click', () => {
	$.ajax({
		type: "delete",
		url: "/notice/api/v1/notice/" + noticeCode,
		dataType: "json"
	})
	.done((reponse) => {
		alert("글 삭제가 완료 되었습니다.")
		location.href = '/notice'
	})
	.fail((error) => {
		console.log(error);
	})
})

load("api/v1/notice/");

function load(uri) {
	$.ajax({
		async: false,
		type: "get",
		url: uri + noticeCode,
		dataType: "json",
		success: (response) => {
			getNotice(response.data);
			console.log(response.data);
		}, 
		
		error: (error) => {
			console.log(error);
		}
	})
}

function getNotice(notice) {
	const noticeDetailTitle = document.querySelector(".notice__detail--title");
	const noticeDetailDescriptions = document.querySelectorAll(".notice__detail--description h3");
	const noticeContent = document.querySelector('.notice__content');
	const noticeFile = document.querySelector(".notice__file");
	
	noticeCode = notice.noticeCode;
	
	noticeDetailTitle.innerHTML = notice.noticeTitle;
	
	noticeDetailDescriptions[0].innerHTML = "글번호: " + notice.noticeCode;
	noticeDetailDescriptions[1].innerHTML = "작성자: " + notice.userId;
	noticeDetailDescriptions[2].innerHTML = "작성일: " + notice.createDate;
	noticeDetailDescriptions[3].innerHTML = "조회수: " + notice.noticeCount;
	
	noticeContent.innerHTML = notice.noticeContent;
	
	noticeFile.innerHTML = "<h3>첨부파일 : </h3>";
	
	let noticeFileArray = new Array();
	
	notice.downloadFiles.forEach(file => {
		if(file.fileCode != undefined) {
			noticeFileArray.push(`
				<a href="/notice/api/v1/notice/file/download/${file.fileTempName}">${file.fileOriginName}</a>`
			)
		}
	});
	
	noticeFile.innerHTML += noticeFileArray.join(" / ")
}