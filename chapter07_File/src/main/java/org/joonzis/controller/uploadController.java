package org.joonzis.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;import java.util.List;
import java.util.UUID;

import org.joonzis.domain.AttachFileDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
public class uploadController {
	
	@GetMapping("/uploadForm") //동기 방식
	public String uploadForm() {
		log.info("upload form");
		return "uploadForm";
	}
	
	/*
	 * MultipartFile의 메소드
	 * String getName() - 파라미터의 이름 <input> 태그의 이름
	 * String getOriginalFileName() - 업로드 파일의 이름
	 * boolean isEmpty() -파일이 존재하지 않는 경우 true
	 * long getSize() - 업로드 파일의 크기
	 * byte[] getBytes() - byte[]로 파일 데이터 변환
	 * InputStream getInputStream() -파일 데이터와 연결된 
	 * 								InputStream 변환
	 * transferTo(File file) - 파일 저장
	 * */
	@PostMapping("uploadFormAction")
	   public void uploadFormPost(MultipartFile[] uploadFile) {
	      for(MultipartFile multipartFile : uploadFile) {
	         log.info("================");
	         log.info("Upload File Name : " + multipartFile.getOriginalFilename());
	         log.info("Upload File Size : " + multipartFile.getSize());
	      }
	   }
	
	@GetMapping("/uploadAsync") //비동기 방식
	public String uploadAsync() {
		log.info("upload async");
		return "uploadAsync";
	}
		@ResponseBody
	   @PostMapping(value = "/uploadAsyncAction",
	            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   public ResponseEntity<List<AttachFileDTO>> uploadAsyncPost(MultipartFile[] uploadFile) {
	      log.info("upload async post......");
	      
	      List<AttachFileDTO> list = new ArrayList<>();
	      
	      String uploadFolder = "c:\\upload";
	      
	      //make folder---------------
	      File uploadPath = new File(uploadFolder, getFolder());//getFolder(): 연월일에 맞게 폴더 만들어짐
	      if(!uploadPath.exists()) {
	    	  uploadPath.mkdirs();
	      }
	      
	      
	      for(MultipartFile multipartFile : uploadFile) {
	    	 AttachFileDTO attachDTO = new AttachFileDTO();
	         log.info("================");
	         log.info("Upload File Name : " + multipartFile.getOriginalFilename());
	         log.info("Upload File Size : " + multipartFile.getSize());
	         
	         String uploadFileName = multipartFile.getOriginalFilename();
	         uploadFileName = uploadFileName.substring
	               (uploadFileName.lastIndexOf("\\")+1
	         );
	         log.info("only file name : " + uploadFileName);
	         
	         UUID uuid = UUID.randomUUID();
	         uploadFileName = uuid.toString()+ "_"+ uploadFileName;
	         
	         try {
	            File saveFile = new File(uploadPath,uploadFileName);//uploadFolder->uploadPath로 변경
	            multipartFile.transferTo(saveFile);
	            
	            attachDTO.setUuid(uuid.toString());
	            attachDTO.setUploadPath(getFolder());
	            attachDTO.setFileName(multipartFile.getOriginalFilename());
	            
	            list.add(attachDTO);
	           
	         } catch (Exception e) {
	            log.error(e.getMessage());
	         }
	      }//end for loop
	      return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
	   }
		
	//파일 다운로드
		@GetMapping(value = "/download",
				produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		@ResponseBody
		public ResponseEntity<Resource> downloadFile(String fileName){
			//Resource => springframework.core.io 패키지
			log.info("download file ... :" + fileName);
			Resource resource =
					new FileSystemResource("C:\\upload\\" + fileName);
			log.info("resource :" + resource);
			
			String resourceName = resource.getFilename();
			HttpHeaders headers = new HttpHeaders();
			
			try {
		         headers.add("Content-Disposition",
		            "attachment; fileName=" + new String(resourceName.getBytes("utf-8"),
		            "ISO-8859-1"));
		      } catch (UnsupportedEncodingException e) {
		         e.printStackTrace();
		      }
			return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		}
			
		//파일 삭제
		@PostMapping("/deleteFile")
		@ResponseBody
		public ResponseEntity<String> deleteFile(@RequestBody String fileName) {
			log.info("deleteFile:" + fileName);
			
			File file;
			
			 try {
		         file = 
		         new File("C:\\upload\\" + URLDecoder.decode(fileName, "utf-8"));
		         file.delete();
		      } catch (Exception e) {
		         e.printStackTrace();
		         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		      }
		      return new ResponseEntity<String>("delete", HttpStatus.OK);
		   }
		
	//오늘 날짜의 경로를 문자열로 생성
	public String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator); //separator: 운영체제에 맞게 나눔
	}
}