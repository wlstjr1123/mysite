package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.GalleryServiceException;
import com.douzone.mysite.repository.AdminRepository;
import com.douzone.mysite.vo.AdminVo;

@Service
public class AdminService {
	private static String SAVE_PATH = "/upload-mysite/admin";
	private static String URL_BASE = "/admin/images";	
	
	@Autowired
	private AdminRepository adminRepository;
	
	public AdminVo getAdminContent() {
		return adminRepository.findAll();
	}
	
	public boolean setContentModify(MultipartFile file, Long no, String title, String message, String description) {
		boolean result;
		try {
			File fileImage = new File(SAVE_PATH);
			if (!fileImage.exists()) {
				fileImage.mkdir();
			}
			
			if (file.isEmpty()) {
				 throw new GalleryServiceException();
			}
			
			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1);
			String saveFilename = generateSaveFilename(extName);
			
			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();
			
			AdminVo vo = new AdminVo();
			vo.setTitle(title);
			vo.setWelome(message);
			vo.setDescription(description);
			vo.setProfile(URL_BASE + "/" + saveFilename);
			vo.setNo(no);
			
			result = adminRepository.update(vo);
			
		} catch(IOException ex) {
			throw new GalleryServiceException("file upload error:" + ex);
		}
		
		return result;
	}
	
	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}
}
