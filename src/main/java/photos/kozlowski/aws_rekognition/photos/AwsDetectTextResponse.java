package photos.kozlowski.aws_rekognition.photos;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AwsDetectTextResponse {

  private String detectedText;
  private Float confidence;
  private List<Point> points;

  @Data
  @Builder
  public static class Point {

    private Float x;
    private Float y;
  }
}
