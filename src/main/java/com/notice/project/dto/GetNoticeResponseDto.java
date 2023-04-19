package com.notice.project.dto;

import java.util.List;
import java.util.Map;

import com.notice.project.domain.Notice;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetNoticeResponseDto {
	private int noticeCode;
	private String noticeTitle;
	private int userCode;
	private String userId;
	private String createDate;
	private int noticeCount;
	private String noticeContent;
	private List<Map<String, Object>> downloadFiles;
	
	public Notice toEntiy() {
		return Notice.builder()
				.notice_code(noticeCode)
				.notice_title(noticeTitle)
				.notice_content(noticeContent)
				.build();
	}
}
