package photos.kozlowski.aws_rekognition.photos.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Slf4j
@Configuration
public class AwsCredentialsProviderConfig {

  @Autowired AwsCredentialsProperties awsCredentialsProperties;

  @Bean
  public AwsCredentialsProvider awsCredentialsProvider() {
    AwsBasicCredentials awsBasicCredentials =
        AwsBasicCredentials.create(
            awsCredentialsProperties.getAccessKey(), awsCredentialsProperties.getSecretKey());
    log.info("AWS credentials provider created.");
    return StaticCredentialsProvider.create(awsBasicCredentials);
  }
}
