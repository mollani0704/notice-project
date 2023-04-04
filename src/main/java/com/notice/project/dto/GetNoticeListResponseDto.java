package com.notice.project.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetNoticeListResponseDto {
	private int noticeCode;
	private String noticeTitle;
	private String userId;
	private String createDate;
	private int noticeCount;
	private int totalNoticeCount;
}
