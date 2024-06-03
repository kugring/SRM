package com.kugring.boardback.service.implement;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kugring.boardback.service.FileService;

@Service
public class FileServiceImplement implements FileService {

    @Value("${file.path}")
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;

    @Override
    public String upload(MultipartFile file) {


        // 파일이 비어있으면 리턴
        if (file.isEmpty()) return null;


        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = filePath + saveFileName;
        try{
            // 해당 경로로 파일이 저장됨
            file.transferTo(new File(savePath));
        }catch(Exception exception){
            
        System.out.println("fileservice catch");
            exception.printStackTrace();
            return null;
        }

        String url = fileUrl + saveFileName;
        return url;
    }

    @Override
    public Resource getImage(String fileName) {

        Resource resource = null;

        try{
            
            // 스프링의 urlRessource이다!
            resource = new UrlResource("file:"+filePath+fileName);
        } catch(Exception exception){
            exception.printStackTrace();
            return null;
        }
        return resource;
    }
    
}
