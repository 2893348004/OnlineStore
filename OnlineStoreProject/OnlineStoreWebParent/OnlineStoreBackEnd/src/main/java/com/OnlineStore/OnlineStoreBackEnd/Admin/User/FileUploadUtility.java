package com.OnlineStore.OnlineStoreBackEnd.Admin.User;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtility {

   // private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtility.class);

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }



        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex){
         //   LOGGER.error("Could not save file: " + fileName, ex);
            throw new IOException("Could not save file: " + fileName, ex);

        }

    }



    public static void cleanDirectoryOfOldFile(String dir){
        Path dirPath = Paths.get(dir);

        try{Files.list(dirPath).forEach(file -> {
            if (!Files.isDirectory(file)) {
                try
                { Files.delete(file); }
                catch (IOException ex)
                {
                 //   LOGGER.error("Could not delete file: " + file);
                    System.out.println("Could not delete file: " + file);
                }
            }
        });


        } catch (IOException e)
        {
        //    LOGGER.error("Could not delete file: " + dirPath);
            System.out.println("Could not delete file: " + dirPath);
        }


    }

    public static void cleanDirectory(String dir){
        Path dirPath = Paths.get(dir);

        try{
            Files.deleteIfExists(dirPath);

        }catch(IOException ex){
      //      LOGGER.error("Could not delete : " + dirPath);
            System.out.println("Could not delete file: " + dirPath);
        }

    }
}






