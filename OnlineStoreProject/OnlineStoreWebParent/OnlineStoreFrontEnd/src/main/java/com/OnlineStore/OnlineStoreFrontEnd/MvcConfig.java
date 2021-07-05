package com.OnlineStore.OnlineStoreFrontEnd;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
public class MvcConfig implements WebMvcConfigurer {



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        Path currentDirectoryPath = Paths.get("").toAbsolutePath();

        String currentPathString = currentDirectoryPath.toString();
        String result = currentPathString.split("OnlineStoreFrontEnd")[0];
        String windowsCompliantPath =  result.replace("\\", "/");
        String myPath = windowsCompliantPath + "/OnlineStoreProject/OnlineStoreWebParent/";



        String categoryDirName = "category-images";

        String categoryPhotosPath = myPath  + categoryDirName + "/";

        registry.addResourceHandler("/" + categoryDirName + "/**")
                .addResourceLocations("file:/" + categoryPhotosPath );



        String productDirName = "product-images";

        String productPhotosPath = myPath  + "product-images/" + "/";

        registry.addResourceHandler("/" + productDirName + "/**")
                .addResourceLocations("file:/" + productPhotosPath  );


    }






}
