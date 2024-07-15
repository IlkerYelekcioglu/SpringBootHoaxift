package com.hoaxify.ws;

import com.hoaxify.ws.configuration.HoaxifyProperties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  @Autowired
  HoaxifyProperties hoaxifyProperties;

  Tika tika = new Tika();

  public String save64BaseStringAsFile(String image) {
    String fileName = UUID.randomUUID().toString();


    //Path path = Paths.get("uploads","profile",fileName); -> bu şekilde olması gerekir.path i tanımlayabilmemiz için
   // Path path = Paths.get(hoaxifyProperties.getStorage().getRoot(),hoaxifyProperties.getStorage().getProfile(),fileName);
    Path path = getProfileImagePath(fileName);

    try {
      OutputStream outputStream = new FileOutputStream(path.toFile());
      outputStream.write(decodedImage(image));
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return fileName;
  }

  public String detectType(String value) {
    return  tika.detect(decodedImage(value));
  }

  private byte[] decodedImage(String encodedImage) {
    return Base64.getDecoder().decode(encodedImage.split(",")[1]);
  }

  public void deleteProfileImage(String image) {
    if(image != null) return;
    Path path = getProfileImagePath(image);
    try {
      Files.deleteIfExists(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  private Path getProfileImagePath(String filename) {
    return Paths.get(hoaxifyProperties.getStorage().getRoot(),hoaxifyProperties.getStorage().getProfile(),filename);
  }
}
