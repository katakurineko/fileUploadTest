package jp.co.mediaseek.training.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoadPictureController {

  private final Path rootLocation;

  public LoadPictureController() {
    this.rootLocation = Paths.get("src\\main\\resources\\static\\Picture");
  }

  @GetMapping("/Picture/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

    System.out.println("debug!!");
    
    Path file = rootLocation.resolve(filename);
    Resource resource = null;
    try {
      resource = new UrlResource(file.toUri());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
  }

}
