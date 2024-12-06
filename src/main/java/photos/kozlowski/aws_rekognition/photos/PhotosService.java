package photos.kozlowski.aws_rekognition.photos;

import java.util.List;

public interface PhotosService {

  List<AwsDetectTextResponse> detectText(String filename);
}
