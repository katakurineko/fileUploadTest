package jp.co.mediaseek.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MeishiRegistPictureController {

  @GetMapping("/meishiRegistPicture")
  public String get(@RequestParam(name = "test",required = false)String test) {
    return "meishiRegistPicture";
  }

}
