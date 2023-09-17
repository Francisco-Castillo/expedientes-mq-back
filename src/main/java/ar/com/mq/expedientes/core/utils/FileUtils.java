package ar.com.mq.expedientes.core.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class FileUtils {

    public static List<MultipartFile> getMultipartFiles(String metodo, HttpServletRequest request) {
        Map<String, List<MultipartFile>> fileMap = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        Set<String> keys = fileMap.keySet();
        List<MultipartFile> uploadingFiles = new ArrayList<>();
        for (String key : keys) {
            if (key.contains(metodo)) {
                uploadingFiles.addAll(fileMap.get(key));
            }
        }
        return uploadingFiles;
    }

    /**
     * @param fullPath
     * @throws IOException
     */
    public static void createDirectory(String fullPath) throws IOException {
        Path path = Paths.get(fullPath);
        if (!Files.exists(path))
            Files.createDirectory(path);
    }

    public static void saveMultipleFiles(List<MultipartFile> files, String uploadDirectory) throws Exception {

        try {
            if (CollectionUtils.isNotEmpty(files)) {

                for (MultipartFile file : files) {

                    byte[] bytes = file.getBytes();

                    String fileName = file.getOriginalFilename();

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(uploadDirectory + fileName)));

                    stream.write(bytes);
                    stream.close();
                }

            }
        } catch (Exception e) {
            throw e;
        }
    }
}
