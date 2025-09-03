package com.sprint.mission.discodeit.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileUtil
{
    private static FileUtil fileService;
    public static FileUtil getInstance()
    {
        if(fileService == null)
            fileService = new FileUtil();

        return fileService;
    }

    public <UUID,V> Map<UUID,V> getDataFromFile(String fileName, Class<UUID> keyClass, Class<V> valueClass)
    {
        Map<UUID,V> map = new HashMap<>();
        try (FileInputStream f = new FileInputStream(fileName);
             BufferedInputStream b = new BufferedInputStream(f);
             ObjectInputStream o = new ObjectInputStream(b)
        ) {
            Object obj = o.readObject();
            Map<Object,Object> temp = (Map<Object, Object>) obj;
            for (Map.Entry<Object,Object> entry : temp.entrySet()) {
                UUID key = (UUID) entry.getKey();
                V value = (V) entry.getValue();
                map.put(key, value);
            }

            return map;
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage() + e.getStackTrace());
            return null;
        } catch (IOException e) {
            System.out.println("Error reading file"+ e.getMessage() + e.getStackTrace());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Error reading file"+ e.getMessage() + e.getStackTrace());
            return null;
        }
    }

    public <V> boolean save(String fileName, UUID id, V item)
    {
        if(item == null)
        {
            System.out.println("item is null");
            return false;
        }

        Map<UUID, V> map = (Map<UUID, V>) getDataFromFile(fileName, id.getClass(), item.getClass());
        if(map == null){
            map = new HashMap<>();
        }

        map.put(id, item);
        return saveDataToFile(fileName,map);
        
    }

    public <K> boolean updateDeleteProcess(String fileName, UUID id, FileProcessType type, Class<K> classItem, K item) {


        Map<UUID, K> map = (Map<UUID, K>) FileUtil.getInstance().getDataFromFile(fileName, UUID.class, classItem);
        if(map == null){
            System.out.println("Error reading file");
            return false;
        }

        if(map.containsKey(id) == false){
            System.out.println("data does not found " + id + classItem);
            return false;
        }

        switch (type){
            case Delete:
                map.remove(id);
                break;
            case Update:
                map.put(id, item);
                break;
            default:
                System.out.println("type not matched");
                return false;
        }

        return saveDataToFile(fileName,map);
    }

    public void deleteAll(String fileName)
    {
        try
        {
            File file = new File(fileName);
            if(file.exists()== false)
                return;

            file.delete();
        }
        catch (Exception e)
        {
            System.out.println("Error deleting file : " + fileName + " " + e.getMessage() + e.getStackTrace());
        }
    }

    private <UUID,V> boolean saveDataToFile(String fileName, Map<UUID,V> map)
    {
        try (FileOutputStream f = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(f);
             ObjectOutputStream o = new ObjectOutputStream(bos);
        )
        {
            o.writeObject(map);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage() + e.getStackTrace());
            return false;
        } catch (IOException e) {
            System.out.println("Error reading file"+ e.getMessage() + e.getStackTrace());
            return false;
        }
    }
}

