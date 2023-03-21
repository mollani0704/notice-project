package com.notice.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping({"/", "/index"})
	public String loadIndex() {
		return "index";
	}

	@GetMapping("/signin")
	public String loadSignin() {
		return "signin";
	}
	
	@GetMapping("/signup")
	public String loadSignup() {
		return "signup";
	}
	
	@GetMapping("/notice")
	public String noticePage() {
		return "notice/notice";
	}
	
	@GetMapping("/notice/insert")
	public String loadNoticeInsert() {
		return "notice/notice_insert";
	}
	
	@GetMapping("/notice/{noticeCode}")
	public String loadNoticeDetail() {
		return "notice/notice_detail";
	}
	
	@GetMapping("/notice/modification/{noticeCode}")
	public String loadNoticeModify() {
		return "notice/notice_modify";
	}
}
