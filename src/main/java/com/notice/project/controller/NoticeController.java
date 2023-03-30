package com.notice.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notice.project.dto.AddNoticeReqDto;
import com.notice.project.dto.CMRespDto;
import com.notice.project.service.notice.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@PostMapping("/notice/api/v1/notice")
	public ResponseEntity<?> addNotice(AddNoticeReqDto addNoticeReqDto) {
		log.info(">>>>>>>>>>>>>>>> {} " , addNoticeReqDto);
		log.info(">>>>>>>>>> fileName : {}", addNoticeReqDto.getFile().get(0).getOriginalFilename());
		
		int noticeCode = 0;
		
		try {
			noticeCode = noticeService.addNotice(addNoticeReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed to writing", noticeCode));
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "Completing Writing", noticeCode));
	}
	
}
