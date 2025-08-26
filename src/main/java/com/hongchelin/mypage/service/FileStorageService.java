// service/FileStorageService.java
package com.hongchelin.mypage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import java.nio.file.*; import java.util.UUID;
=======
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
>>>>>>> feature/community

@Service
public class FileStorageService {
    @Value("${app.upload-dir:./uploads}")
    private String uploadDir;

    public String save(MultipartFile file, String subDir){
        try{
            String ext = getExt(file.getOriginalFilename());
<<<<<<< HEAD
            String name = java.util.UUID.randomUUID() + (ext.isEmpty()? "" : "."+ext);

            java.nio.file.Path root = java.nio.file.Paths.get(uploadDir).toAbsolutePath().normalize();
            java.nio.file.Path dir  = root.resolve(subDir).normalize();
            java.nio.file.Files.createDirectories(dir);

            java.nio.file.Path target = dir.resolve(name);
=======
            String name = UUID.randomUUID() + (ext.isEmpty()? "" : "."+ext);

            Path root = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path dir  = root.resolve(subDir).normalize();
            Files.createDirectories(dir);

            Path target = dir.resolve(name);
>>>>>>> feature/community
            file.transferTo(target.toFile());

            // 리소스 핸들러 경로 규칙과 맞추기
            return ("/files/" + subDir.replace("\\","/") + "/" + name).replace("//","/");
        } catch(Exception e){
            throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
        }
    }

    private String getExt(String fn){
        if (fn == null) return "";
        int i = fn.lastIndexOf('.');
        return (i < 0) ? "" : fn.substring(i+1);
    }
}


