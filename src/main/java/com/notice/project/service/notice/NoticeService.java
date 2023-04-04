package com.notice.project.service.notice;

import java.util.List;

import com.notice.project.dto.AddNoticeReqDto;
import com.notice.project.dto.GetNoticeListResponseDto;

public interface NoticeService {

	public List<GetNoticeListResponseDto> getNoticeList(int page, String searchFlag, String searchValue) throws Exception;
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
	
}
