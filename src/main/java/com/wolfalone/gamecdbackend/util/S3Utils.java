package com.wolfalone.gamecdbackend.util;


import com.wolfalone.gamecdbackend.config.constant.Constant;
import com.wolfalone.gamecdbackend.config.constant.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
@Slf4j
public class S3Utils {

    private static final String BUCKET = "wolfalone";
    private Constant constant = new Constant();

    public  void uploadFile(String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        int dotIndex = fileName.lastIndexOf(".");
        String typeFile = fileName.substring(dotIndex + 1);
//        for (ContentType type: ContentType.values()) {
//            if (type.name().equalsIgnoreCase(typeFile)) {
//                contentType = ContentType.getValue(type);
//                break;
//            }
//        }
        log.info("newFilename update account: {}", fileName);
        String contentType = Arrays.stream(ContentType.values())
                .filter(
                        a -> a.name().contains(typeFile))
                .map(
                        a -> ContentType.getValue(a)
                ).findFirst()
                .get();
        log.info("Content type update account: {}", contentType);

        S3Client client = S3Client.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(
                        new AwsCredentialsProvider() {
                            @Override
                            public AwsCredentials resolveCredentials() {
                                return new AwsCredentials() {
                                    @Override
                                    public String accessKeyId() {
                                        return constant.getAws().getAccessKey();
                                    }

                                    @Override
                                    public String secretAccessKey() {
                                        return constant.getAws().getSecretKey();
                                    }
                                };
                            }
                        }
                )
                .build();
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .contentType(contentType)
//                .acl("public-read")
                .build();

        log.info("fdasdf ne {}", request.bucket());
        client.putObject(
                request,
                RequestBody.fromInputStream(
                        inputStream,
                        inputStream.available()
                )
        );
        System.out.println("asdfklhjasdlkfj asldalks djalskd jasldk jasldk jasdlkf jasdlkfj " +
                "asdlkfj asdlkfjaslkdfj asldkfj asfio awejf ");
        S3Waiter waiter = client.waiter();
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();
        log.info("asdfaSJKH AJKLSDADSF JKAKSDF JKASFJDK ASDFLAKSDF JASFJASDsdf {} {}",
                waitRequest.toString());
        WaiterResponse<HeadObjectResponse> waiterResponse = waiter.waitUntilObjectExists(waitRequest);
        waiterResponse.matched().response().ifPresent(x -> {
            log.info("asdfaSJKH AJKLSDADSF JKAKSDF JKASFJDK ASDFLAKSDF JASFJASDsdf {} {}",
                    x.contentType(), x.toString());
        });
    }
}
