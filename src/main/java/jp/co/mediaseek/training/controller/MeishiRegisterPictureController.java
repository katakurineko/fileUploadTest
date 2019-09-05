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

  /**
   * <a href="https://spring.io/guides/gs/uploading-files/">Spring入門コンテンツ</a>を参考に作成.
   * 
   * @param file システム実行中では、MultipartFileを実装したStandardMultipartFileインスタンスが渡される想定.
   * @return
   */
  @PostMapping("/meishiRegisterPicture")
  public String handleFileUpload(@RequestParam("file") MultipartFile file) {

    /*
     * ファイルのパスを正規化する。 https://www.baeldung.com/java-nio-2-path などを参照。
     */
    String filename = StringUtils.cleanPath(file.getOriginalFilename());

    // 保存先をrootLocationに代入。
    // TODO : 保存先のパスはapplication.propertiesなどの外部ファイルから読み込む
    Path rootLocation = Paths.get("src/main/resources/static/Picture");

    try {
      if (file.isEmpty()) {
        // TODO : アップロードされたファイルが空だった場合の処理
        return "meishiRegisterPicture";
      } else if (!file.getContentType().equals("image/png")
          && !file.getContentType().equals("image/jpeg")) {
        // アップロードされたファイルのMIMEタイプが、jpgもしくはpngでなかった場合の処理
        // TODO : アップロードされたファイルがjpgもしくはpngでなかった場合の処理。
        return "meishiRegisterPicture";
      } else if (1048576 <= file.getSize()) {
        //TODO : アップロードされたファイルが10MBを超えた場合の処理
        return "meishiRegisterPicture";
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
