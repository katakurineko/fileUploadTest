package jp.co.mediaseek.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MeishiRegisterPictureController {

  @GetMapping("/meishiRegisterPicture")
  public String get() {
    return "meishiRegisterPicture";
  }

  @PostMapping("/meishiRegisterPicture")
  public String handleFileUpload(@RequestParam("file") MultipartFile file) {

    // storageService.store(file);
    System.out.println(file);

    return "meishiEdit";
  }

}
