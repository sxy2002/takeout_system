package com.sky.utils;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Data
@AllArgsConstructor
@Slf4j
public class HwObsUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建ObSClient实例。
        ObsClient obsClient = new ObsClient(accessKeyId, accessKeySecret, endpoint);

        try {
            // 创建PutObject请求。
            obsClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (ObsException oe) {
            System.out.println("Caught an ObsException, which means your request made it to OBS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getErrorRequestId());
            System.out.println("Host ID:" + oe.getErrorHostId());
        } finally {
            if (obsClient != null) {
                try {
                    obsClient.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }
}
