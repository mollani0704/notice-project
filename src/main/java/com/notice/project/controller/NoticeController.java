package com.notice.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notice.project.dto.AddNoticeReqDto;
import com.notice.project.dto.CMRespDto;
import com.notice.project.dto.GetNoticeListResponseDto;
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
	
	@GetMapping("/notice/api/v1/notice/list/{page}")
	public ResponseEntity<?> getNoticeList(@PathVariable int page, @RequestParam String searchFlag, @RequestParam String searchValue) {
		
		List<GetNoticeListResponseDto> listDto = null;
		
		log.info("{}, {}", searchFlag, searchValue);
		
		try {
			listDto = noticeService.getNoticeList(page, searchFlag, searchValue);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database error", listDto));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "lookup successful", listDto));
	}
	
}
