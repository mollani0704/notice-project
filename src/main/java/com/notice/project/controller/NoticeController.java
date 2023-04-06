package com.notice.project.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notice.project.dto.AddNoticeReqDto;
import com.notice.project.dto.CMRespDto;
import com.notice.project.dto.GetNoticeListResponseDto;
import com.notice.project.dto.GetNoticeResponseDto;
import com.notice.project.service.notice.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NoticeController {
	
	@Value("${file.path}")
	private String filePath;
	
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
	
	@GetMapping("/notice/api/v1/notice/{noticeCode}")
	public ResponseEntity<?> getNotice(@PathVariable int noticeCode) {
		
		GetNoticeResponseDto getNoticeResponseDto = null;
		
		try {
			getNoticeResponseDto = noticeService.getNotice(null, noticeCode);
			if(getNoticeResponseDto == null) {
				return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "database error", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "request failed", null));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "lookup successful", getNoticeResponseDto));
	}
	
	@GetMapping("notice/api/v1/notice/file/download/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {
		
		Path path = Paths.get(filePath + "notice/" + fileName);
		String contentType = Files.probeContentType(path);
		
		log.info("contentType : {} ", contentType);
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentDisposition(ContentDisposition.builder("attachment")
											.filename(fileName, StandardCharsets.UTF_8)
											.build());
		
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		return ResponseEntity.ok().headers(headers).body(resource);
		
	}
	
}
