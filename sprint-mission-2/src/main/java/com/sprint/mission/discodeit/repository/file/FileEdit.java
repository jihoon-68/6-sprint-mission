package com.sprint.mission.discodeit.repository.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileEdit {

    public void init(Path directory){
        //저장할 경로의 파일 초기화
        if(!Files.exists(directory)){
            try{
                Files.createDirectories(directory);
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    public <T> void save(Path filePath, T date){
        try(FileOutputStream fos = new FileOutputStream(filePath.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(date);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    };

    public <T> List<T> load(Path directory){
        if(Files.exists(directory)){
            try{
                List<T> list = Files.list(directory)
                        .map(path ->{
                            try(FileInputStream fis = new FileInputStream(path.toFile());
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                            ){
                                Object data = ois.readObject();
                                return (T) data;
                            }catch(IOException | ClassNotFoundException e){
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();
                return list;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            return new ArrayList<>();
        }
    };

    public Boolean delete(Path filePath){
         return  filePath.toFile().delete();
    }

}
