package com.notice.project.service.notice;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.notice.project.domain.Notice;
import com.notice.project.domain.NoticeFile;
import com.notice.project.domain.NoticeRepository;
import com.notice.project.dto.AddNoticeReqDto;

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

}
