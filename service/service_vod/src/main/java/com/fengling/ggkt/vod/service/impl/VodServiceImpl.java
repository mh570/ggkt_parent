package com.fengling.ggkt.vod.service.impl;

import com.fengling.ggkt.exception.HiException;
import com.fengling.ggkt.model.vod.Video;
import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vod.service.VideoService;
import com.fengling.ggkt.vod.service.VodService;
import com.fengling.ggkt.vod.utils.ConstantPropertiesUtil;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class VodServiceImpl implements VodService {

    @Autowired
    private VideoService videoService;

    @Value("${tencent.video.appid}")
    private String appId;


    @Override
    public Result upload() {
        VodUploadClient client = new VodUploadClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        VodUploadRequest request = new VodUploadRequest();
        request.setMediaFilePath("/data/videos/Wildlife.wmv");

//        写死了,懒得写配置
        request.setProcedure("LongVideoPreset");
        try {
            VodUploadResponse response = client.upload("ap-guangzhou", request);
            log.info("Upload FileId = {}", response.getFileId());
            return Result.ok(response.getFileId());
        } catch (Exception e) {
            // 业务方进行异常处理
            e.printStackTrace();
            log.error("Upload Err", e);
        }
        return Result.fail();
    }

    @Override
    public Result remove(String fileId) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
            Credential cred = new Credential(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("vod.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            VodClient client = new VodClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(fileId);
            DeleteMediaResponse resp = client.DeleteMedia(req);
            System.out.println(DeleteMediaResponse.toJsonString(resp));
            return Result.ok();
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public Map<String, Object> getPlayAuth(Long courseId, Long videoId) {
        //根据小节id获取小节对象，获取腾讯云视频id
        Video video = videoService.getById(videoId);
        if(video == null) {
            throw new HiException(20001,"小节信息不存在");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("videoSourceId",video.getVideoSourceId());
        map.put("appId",appId);
        return map;
    }
}
