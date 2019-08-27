package jp.co.mediaseek.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeishiRegisterPictureController {
  @GetMapping("/meishiRegisterPicture")
  public String get() {
    return "meishiRegisterPicture";
  }
}
