package jp.co.mediaseek.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class meishiEditController {
  @GetMapping("/meishiEdit")
  public String get() {
    return "meishiEdit";
  }
}
