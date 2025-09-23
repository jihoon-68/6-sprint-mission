package com.sprint.mission.discodeit.utilit;

import com.sprint.mission.discodeit.DTO.BinaryContent.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileUpload {

    @Value("${file.upload.mac.path}")
    private String uploadPath;

    public List<FileDTO> upload(List<MultipartFile> multipartFile){
        List<FileDTO> fileDTOS = new ArrayList<>();


        try{
            //밑에 에러나는 부눈 내일 글로벌 에러 처리 함수 작성 하고 try-catch 밖으로 빼기
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                // 경로가 존재하지 않을 때
                boolean ok= directory.mkdirs();
                if(!ok){
                    System.out.println("디랙토리 생성 안됨");
                }
            }

            for(MultipartFile file : multipartFile){
                if(file.isEmpty()){
                    log.warn("파일 비었음: {}",file.getOriginalFilename());
                    continue;
                }

                String fileName = file.getOriginalFilename();

                if( fileName == null || !fileName.contains(".")){
                    log.warn("잘못된 파일 양식: {}",fileName);
                    continue;
                }

                String ext = fileName.substring(fileName.lastIndexOf("."));
                String saveName = UUID.randomUUID().toString().replace("-", "") + ext;

                File destFile = new File(directory+"/"+saveName);
                file.transferTo(destFile);
                fileDTOS.add(new FileDTO(fileName,saveName,destFile));
            }
        }catch (Exception e){
            log.warn("파일 업로드 실패",e);

            for(FileDTO fileDTO : fileDTOS){
                if(fileDTO.file().exists()) {
                    boolean deleted = fileDTO.file().delete();
                    log.info("파일 롤백 삭제: {} - {}", fileDTO.FileName(), deleted);
                }
            }
        }
        return fileDTOS;
    }
}
