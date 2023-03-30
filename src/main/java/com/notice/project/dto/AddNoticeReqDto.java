package com.notice.project.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddNoticeReqDto {

	private String noticeTitle;
	private int userCode;
	private String content;
	private List<MultipartFile> file;
	
}
