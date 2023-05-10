package com.notice.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.notice.project.service.UserService;
import com.notice.project.service.notice.NoticeService;
import com.notice.project.user.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {
	
	private final NoticeService noticeService;
	private final UserService userService;
	
	@GetMapping({"/", "/index"})
	public String loadIndex(Model model) {
		
		List<User> data = null;
		try {
			data = userService.getUserList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("userList", data);
		
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
	
	@GetMapping("/notice/modify/{noticeCode}")
	public String loadNoticeModify(@PathVariable int noticeCode, Model model) {
		
		try {
			model.addAttribute("notice", noticeService.getNotice(null, noticeCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "notice/notice_modify";
	}
}
