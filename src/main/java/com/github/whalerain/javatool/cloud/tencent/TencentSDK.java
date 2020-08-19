package com.github.whalerain.javatool.cloud.tencent;

import cn.hutool.json.JSONUtil;
import com.github.whalerain.javatool.json.JsonTool;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.profile.Language;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralAccurateOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralAccurateOCRResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangXi
 */
public class TencentSDK {


    /**
     *
     * @param imageBase64 图片base64字符串
     * @return OCR检测结果JSON
     */
    public static String postGeneralAccurateOCR(String imageBase64) {
        //fixme 需要设置secretId 与 secretKey
        Credential cred = new Credential("", "");

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ocr.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setDebug(true);
        clientProfile.setLanguage(Language.ZH_CN);
        clientProfile.setHttpProfile(httpProfile);

        OcrClient client = new OcrClient(cred, "ap-shanghai", clientProfile);

        Map<String,String> map = new HashMap<>(1);
        map.put("ImageBase64", imageBase64);
        String paramsJson = JSONUtil.parse(map).toString();

        GeneralAccurateOCRRequest request = GeneralAccurateOCRRequest.fromJsonString(paramsJson, GeneralAccurateOCRRequest.class);
        try {
            GeneralAccurateOCRResponse response = client.GeneralAccurateOCR(request);
            return GeneralAccurateOCRResponse.toJsonString(response);
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            return null;
        }
    }





}
