// service/FileStorageService.java
package com.hongchelin.mypage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*; import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${app.upload-dir}") private String uploadDir;

    public String save(MultipartFile file, String subDir){
        try{
            String ext = getExt(file.getOriginalFilename());
            String name = UUID.randomUUID() + (ext.isEmpty()? "" : "."+ext);
            Path dir = Path.of(uploadDir, subDir); Files.createDirectories(dir);
            file.transferTo(dir.resolve(name).toFile());
            return ("/files/"+subDir+"/"+name).replace("//","/");
        }catch(Exception e){ throw new RuntimeException(e); }
    }
    private String getExt(String fn){ if (fn==null) return ""; int i=fn.lastIndexOf('.'); return (i<0)?"":fn.substring(i+1); }
}


