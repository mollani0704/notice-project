package com.notice.project.service.notice;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.notice.project.domain.Notice;
import com.notice.project.domain.NoticeFile;
import com.notice.project.domain.NoticeRepository;
import com.notice.project.dto.AddNoticeReqDto;
import com.notice.project.dto.GetNoticeListResponseDto;
import com.notice.project.dto.GetNoticeResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

	private final NoticeRepository noticeRepository;
	
	@Value("${file.path}")
	private String filePath;
	
	@Override
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception {
		
		Predicate<String> predicate = (filename) -> !filename.isBlank();
		
		Notice notice = null;
		
		notice = Notice.builder()
				.notice_title(addNoticeReqDto.getNoticeTitle())
				.user_code(addNoticeReqDto.getUserCode())
				.notice_content(addNoticeReqDto.getContent())
				.build();
		
		noticeRepository.saveNotice(notice);
		
		if(predicate.test(addNoticeReqDto.getFile().get(0).getOriginalFilename())) {
			List<NoticeFile> noticeFiles = new ArrayList<NoticeFile>();
			
			for(MultipartFile file : addNoticeReqDto.getFile()) {
				String originalFileName = file.getOriginalFilename();
				String tempFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFileName;
				
				log.info(tempFileName);
				
				Path uploadPath = Paths.get(filePath, "notice/" + tempFileName);
				
				File f = new File(filePath + "notice");
				
				if(!f.exists()) {
					f.mkdirs();
				}
				
				try {
					Files.write(uploadPath, file.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				noticeFiles.add(NoticeFile.builder()
						.notice_code(notice.getNotice_code())
						.file_name(tempFileName)
						.build());
			}
			
			noticeRepository.saveNoticeFiles(noticeFiles);
		}
		
		return notice.getNotice_code();
	}

	@Override
	public List<GetNoticeListResponseDto> getNoticeList(int page, String searchFlag, String searchValue)
			throws Exception {
		
		int index = (page - 1) * 10;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("search_flag", searchFlag);
		map.put("search_value", searchValue == null ? "" : searchValue);
		
		List<GetNoticeListResponseDto> list = new ArrayList<GetNoticeListResponseDto>();
		
		noticeRepository.getNoticeList(map).forEach(notice -> {
			list.add(notice.toListDto());
		});
		
		return list;
	}

	@Override
	public GetNoticeResponseDto getNotice(String flag, int noticeCode) throws Exception {
		
		GetNoticeResponseDto getNoticeResponseDto = null;
		
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("flag", flag);
		reqMap.put("notice_code", noticeCode);
		
		noticeRepository.countIncrement(reqMap);
		
		List<Notice> notices = noticeRepository.getNotice(reqMap);
		
		if(!notices.isEmpty()) {
			List<Map<String, Object>> downloadFiles = new ArrayList<Map<String,Object>>();
			notices.forEach(notice -> {
				Map<String, Object> fileMap = new HashMap<String, Object>();
				
				String fileName = notice.getFile_name();
				if(fileName != null) {
					fileMap.put("fileCode", notice.getFile_code());
					fileMap.put("fileOriginName", fileName.substring(fileName.indexOf("_") + 1));
					fileMap.put("fileTempName", fileName);
				}
				
				downloadFiles.add(fileMap);
			});
			
			Notice firstNotice = notices.get(0);
			
			getNoticeResponseDto = GetNoticeResponseDto.builder()
										.noticeCode(notices.get(0).getNotice_code())
										.noticeTitle(firstNotice.getNotice_title())
										.userCode(firstNotice.getUser_code())
										.userId(firstNotice.getUser_id())
										.createDate(firstNotice.getCreate_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
										.noticeCount(firstNotice.getNotice_count())
										.noticeContent(firstNotice.getNotice_content())
										.downloadFiles(downloadFiles)
										.build();
			
		}
		
		return getNoticeResponseDto;
	}

	@Override
	public int modifyNotice(int noticeCode, GetNoticeResponseDto getNoticeResponseDto) throws Exception {
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("noticeCode", noticeCode);
		data.put("updateNotice", getNoticeResponseDto.toEntiy());
		
		noticeRepository.updateNotice(data);
		
		return 1;
	}

}
