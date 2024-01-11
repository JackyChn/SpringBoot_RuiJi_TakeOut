package com.itcast.reggle.controller;

import com.itcast.reggle.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggle.path}")
    private String basePath;

    /**
     * file upload
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        log.info(file.toString());

        File path = new File(basePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();  // abc.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));  // suffix = ".jpg"
        String fileName = UUID.randomUUID().toString() + suffix;  // random fileName allocated with '.jpg' suffix
//        use UUID to generate img name
        try {
            file.transferTo(new File(basePath + fileName));  // save the file under this path
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        log.info("name: {}", name);
        try {
//            input stream read the file
            FileInputStream inputStream = new FileInputStream(new File(basePath + name));

//            output stream to write the file back to browser and show
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");  // set type, fix code for img

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

//            shut stream
            inputStream.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
