package photos.kozlowski.aws_rekognition.photos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

@Slf4j
@Service
public class PhotosServiceImpl implements PhotosService {

  private static final String FOLDER = "C:/Users/Public/";

  @Override
  public List<AwsDetectTextResponse> detectText(String filename) {

    // 1. Initialize rekognition client
    RekognitionClient rekClient = RekognitionClient.builder().region(Region.EU_WEST_1).build();

    // 2. Load image and send to AWS Rekognition API
    try (InputStream sourceStream = new FileInputStream(FOLDER + filename)) {

      SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);
      Image souImage = Image.builder().bytes(sourceBytes).build();

      // 2.1. Prepare request
      DetectTextRequest textRequest = DetectTextRequest.builder().image(souImage).build();

      // 2.2. Get response from API
      DetectTextResponse detectTextResponse = rekClient.detectText(textRequest);

      return detectTextResponse.textDetections().stream()
          .map(
              d ->
                  AwsDetectTextResponse.builder()
                      .detectedText(d.detectedText())
                      .confidence(d.confidence())
                      .points(
                          d.geometry().polygon().stream()
                              .map(
                                  p ->
                                      AwsDetectTextResponse.Point.builder()
                                          .x(p.x())
                                          .y(p.y())
                                          .build())
                              .toList())
                      .build())
          .toList();

    } catch (RekognitionException | IOException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public AwsDetectTextResponse detectOneText(String filename) {
    List<AwsDetectTextResponse> detectedText = detectText(filename);
    return detectedText.stream()
        .filter(
            text ->
                text.getDetectedText().matches("\\d+") && text.getConfidence().compareTo(95f) > 0)
        .sorted(Comparator.comparingDouble(AwsDetectTextResponse::getConfidence).reversed())
        .findFirst()
        .orElse(AwsDetectTextResponse.builder().build());
  }
}
