package jp.co.mediaseek.training.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoadPictureController {

//  private final Path rootLocation;
//
//  public LoadPictureController() {
//    this.rootLocation = Paths.get("src\\main\\resources\\static\\Picture");
//  }
//
//  @GetMapping("/Picture/{filename:.+}")
//  @ResponseBody
//  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//    System.out.println("debug!!");
//
//    Path file = rootLocation.resolve(filename);
//    Resource resource = null;
//    try {
//      resource = new UrlResource(file.toUri());
//    } catch (MalformedURLException e) {
//      e.printStackTrace();
//    }
//    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
//  }

  @GetMapping("/Picture/{filename:.+}")
  public void getImageAsByteArray(@PathVariable String filename,
      HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    InputStream in = request.getServletContext()
        .getResourceAsStream("/Picture/"+filename);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    IOUtils.copy(in, response.getOutputStream());
  }

}
