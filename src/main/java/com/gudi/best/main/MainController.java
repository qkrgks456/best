package com.gudi.best.main;

import com.gudi.best.util.S3Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    S3Uploader s3Uploader;

    @GetMapping(value = "/")
    public String main() {
        return "main";
    }

    @PostMapping(value = "/test")
    public String test(MultipartFile file, Model model) throws Exception {
        String path = s3Uploader.upload(file);
        model.addAttribute("path", path);
        return "main";
    }
}
