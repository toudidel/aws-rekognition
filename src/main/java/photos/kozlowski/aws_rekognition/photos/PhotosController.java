package photos.kozlowski.aws_rekognition.photos;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhotosController {

  @Autowired PhotosService photosService;

  @GetMapping("/recognize/{filename}")
  public ResponseEntity<List<AwsDetectTextResponse>> detectText(@PathVariable String filename) {
    return ResponseEntity.ok(photosService.detectText(filename));
  }
}
