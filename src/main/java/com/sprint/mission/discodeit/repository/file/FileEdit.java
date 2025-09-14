package com.sprint.mission.discodeit.repository.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileEdit {

    // 싱글 톤이면 한곳에 몰리수 있어어 인스턴스로 생성 생각중
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

    public <T> void save(Path directory,UUID id, T date){
        Path filePath = directory.resolve(id+".ser");
        try(FileOutputStream fos = new FileOutputStream(filePath.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(date);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    };

    public <T> Optional<T> load(Path directory, UUID id){
        Path filePath = directory.resolve(id+".ser");
        Optional<T> date = Optional.empty();
        if(!Files.exists(filePath)){
            try(
                    FileInputStream fis = new FileInputStream(filePath.toFile());
                    ObjectInputStream ois = new ObjectInputStream(fis);
            ) {
                Object dateO = ois.readObject();
                return  (Optional<T>) ois.readObject();

            }catch (IOException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
        return date;
    }

    public <T> List<T> loadAll(Path directory){
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

    public Boolean delete(Path directory, UUID id){
        Path filePath = directory.resolve(id+".ser");
         return  filePath.toFile().delete();
    }

}
