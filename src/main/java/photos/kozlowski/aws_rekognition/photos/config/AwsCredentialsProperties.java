package photos.kozlowski.aws_rekognition.photos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "cloud.aws.credentials")
public class AwsCredentialsProperties {

  private String accessKey;

  private String secretKey;
}
