package com.notice.project.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeRepository {
	public int saveNotice(Notice notice) throws Exception;
	public int saveNoticeFiles(List<NoticeFile> list) throws Exception;
	
	public List<Notice> getNoticeList(Map<String, Object> map) throws Exception;
	public List<Notice> getNotice(Map<String, Object> map) throws Exception; 
	
	public int updateNotice(Map<String, Object> map) throws Exception;
	public int deleteNotice(int noticeCode) throws Exception;
	
	public int countIncrement(Map<String, Object> map) throws Exception;
}
