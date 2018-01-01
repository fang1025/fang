package com.fang.component.upload.controller;

import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.core.define.Constants;
import com.fang.core.util.PropertiesUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping(value = "/upload")
public class UploadController extends BaseController {

    @RequestMapping(value = "/onlyUpload")
    @ResponseBody
    public Object onlyUpload(@RequestParam MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) {
        logger.info("upload start");
        FangResult result = new FangResult();
        MultipartFile file = files[0];
        try {
            String originFileName = file.getOriginalFilename();
            int lastPointPos = originFileName.lastIndexOf(".");
            String ext = "";
            if (lastPointPos != -1) {
                ext = originFileName.substring(lastPointPos).toLowerCase();
            }
            if (ArrayUtils.contains(Constants.NOTALLOWATT, ext.toLowerCase())) {//此情况一般不会出现
                request.setAttribute("result", "0");
                request.setAttribute("msg", "文件名为[" + originFileName + "]的文件类型不允许上传！");
                return "../callback";
            }
            String filename = System.currentTimeMillis() + ext;
            File targetFile = new File(PropertiesUtil.getParam("fang.baseFilePath")+PropertiesUtil.getParam("fang.attachmentPath"), filename);
            if(!targetFile.exists()){
                targetFile.createNewFile();
            }

            //保存
            file.transferTo(targetFile);
            result.put("result", "1");
            result.put("originFileName", originFileName);
            result.put("fileName", targetFile.getName());
            result.put("filePath",targetFile.getPath());
            result.put("suffix", ext);
            result.put("fileSize", file.getSize());
        } catch (Exception e) {
            result.put("result", "0");
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
