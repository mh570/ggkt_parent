package com.fengling.ggkt.vod.controller;

import com.fengling.ggkt.result.Result;
import com.fengling.ggkt.vod.service.VodService;
import com.fengling.ggkt.vod.utils.ConstantPropertiesUtil;
import com.fengling.ggkt.vod.utils.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/admin/vod")
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("/upload")
    public Result upload() {
        return vodService.upload();
    }

    @DeleteMapping("remove/{fileId}")
    public Result remove(@PathVariable("fileId") String fileId) {
        return vodService.remove(fileId);
    }

    @GetMapping("sign")
    public Result sign() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            return Result.fail();
        }
    }
}
