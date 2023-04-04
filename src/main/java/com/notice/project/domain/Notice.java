package com.notice.project.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.notice.project.dto.GetNoticeListResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
	private int notice_code;
	private String notice_title;
	private String user_id;
	private int user_code;
	private String notice_content;
	private int notice_count;
	private int file_code;
	private String file_name;
	private LocalDateTime create_date;
	
	private int total_notice_count;
	
	public GetNoticeListResponseDto toListDto() {
		return GetNoticeListResponseDto.builder()
				.noticeCode(notice_code)
				.noticeTitle(notice_title)
				.userId(user_id)
				.createDate(create_date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.noticeCount(notice_count)
				.totalNoticeCount(total_notice_count)
				.build();
	}
}
