package com.fengling.ggkt.vod.service.impl;

import com.fengling.ggkt.vod.service.FileService;
import com.fengling.ggkt.vod.service.SubjectService;
import com.fengling.ggkt.vod.utils.ConstantPropertiesUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {

        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(ConstantPropertiesUtil.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
// 这里建议设置使用 https 协议
// 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


// 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
// 对象键(Key)是对象在存储桶中的唯一标识。

        String originalFilename = file.getOriginalFilename();
        String dateTime = new DateTime().toString("yyyy/MM/dd");
        String fileName = dateTime + "/" +
                UUID.randomUUID().toString().replaceAll("-", "") + "." +
                StringUtils.substringAfterLast(originalFilename, ".");


        try {
            InputStream inputStream = file.getInputStream();


            ObjectMetadata objectMetadata = new ObjectMetadata();
// 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
// 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
//            objectMetadata.setContentLength(inputStreamLength);

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    fileName,
                    inputStream,
                    objectMetadata);


            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
//            Upload upload = transferManager.upload(putObjectRequest);
//            UploadResult uploadResult = upload.waitForUploadResult();
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            String fileNameUrl = "https://" + bucketName + "." + "cos." + ConstantPropertiesUtil.END_POINT + ".myqcloud.com" + "/" + fileName;
            return fileNameUrl;
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
