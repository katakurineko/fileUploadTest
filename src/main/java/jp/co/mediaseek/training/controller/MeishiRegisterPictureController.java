package jp.co.mediaseek.training.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jp.co.mediaseek.training.exception.StorageException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MeishiRegisterPictureController {

  @GetMapping("/meishiRegisterPicture")
  public String get() {
    return "meishiRegisterPicture";
  }

  @PostMapping("/meishiRegisterPicture")
  public String handleFileUpload(@RequestParam("file") MultipartFile file) {

    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.println("filename:" + filename);

    Path rootLocation = Paths.get("src/main/webapp/Picture");
    System.out.println(rootLocation);

    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file " + filename);
      }
      if (filename.contains("..")) {
        // This is a security check
        throw new StorageException(
            "Cannot store file with relative path outside current directory " + filename);
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, rootLocation.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }

    return "meishiEdit";
  }

}
