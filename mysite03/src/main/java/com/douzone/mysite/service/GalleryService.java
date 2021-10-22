package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	private static String SAVE_PATH = "/upload-images/mysite03";
	private static String URL_BASE = "/images/mysite03";
	
	@Autowired
	private GalleryRepository galleryRepository;
	
	public boolean insert(GalleryVo vo) {
		return galleryRepository.insert(vo);
	}
	
	public List<GalleryVo> findAll() {
		return galleryRepository.findAll();
	}
	
	public String restore(MultipartFile multipartFile) {
		String url = null;

		try {
			if (multipartFile.isEmpty()) {
				return url;
			}

			String originFileName = multipartFile.getOriginalFilename();
			String extName = originFileName.substring(originFileName.lastIndexOf('.') + 1);
			String saveFileName = generateSaveFileName(extName);
			long fileSize = multipartFile.getSize();

			byte[] bytes = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(bytes);
			os.close();
			
			url = URL_BASE + "/" + saveFileName;
		} catch (IOException ex) {
			throw new RuntimeException("file upload error:" + ex);
		}
		return url;
	}

	private String generateSaveFileName(String extName) {
		String filename = "";

		Calendar clendar = Calendar.getInstance();

		filename += clendar.get(Calendar.YEAR);
		filename += clendar.get(Calendar.MONTH);
		filename += clendar.get(Calendar.DATE);
		filename += clendar.get(Calendar.HOUR);
		filename += clendar.get(Calendar.MINUTE);
		filename += clendar.get(Calendar.SECOND);
		filename += clendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		clendar.get(Calendar.YEAR);
		return filename;
	}
}
