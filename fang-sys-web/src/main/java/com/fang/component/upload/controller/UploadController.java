package com.fang.component.upload.controller;

import com.fang.core.commoms.FangResult;
import com.fang.core.controller.BaseController;
import com.fang.core.define.Constants;
import com.fang.core.util.PropertiesUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping(value = "/upload")
public class UploadController extends BaseController {

    @RequestMapping(value = "/onlyUploadMultis")
    @ResponseBody
    public Map onlyUpload(@RequestParam MultipartFile[] files, @RequestParam ModelMap params, HttpServletRequest request, HttpServletResponse response) {
        logger.info("upload start");
        FangResult result = new FangResult();
        try {
            List<Map<String,Object>> filesResult = new ArrayList<Map<String, Object>>();
            String path = request.getParameter("path");
            if(path == null) path = PropertiesUtil.getParam("fang.attachmentPath");
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                String originFileName = file.getOriginalFilename();
                int lastPointPos = originFileName.lastIndexOf(".");
                String ext = "";
                if (lastPointPos != -1) {
                    ext = originFileName.substring(lastPointPos).toLowerCase();
                }
                if (ArrayUtils.contains(Constants.NOTALLOWATT, ext.toLowerCase())) {//此情况一般不会出现
                    result.buildErrorResult("文件名为[" + originFileName + "]的文件类型不允许上传！");
                    return result;
                }
                String filename = System.currentTimeMillis() + ext;
                File targetFile = new File(PropertiesUtil.getParam("fang.baseFilePath") +path, filename);
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }
                file.transferTo(targetFile);
                Map<String,Object> s = new HashMap<String, Object>();
                s.put("originFileName", originFileName);
                s.put("fileName", targetFile.getName());
                s.put("filePath", path + filename);
                s.put("suffix", ext);
                s.put("fileSize", file.getSize());
                filesResult.add(s);
            }

            result.buildSuccessResult();
        } catch (Exception e) {
            result.buildErrorResult(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @RequestMapping(value = "/onlyUploadSingle")
    @ResponseBody
    public Map onlyUpload2(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        logger.info("upload start");
        FangResult result = new FangResult();
        try {
            String originFileName = file.getOriginalFilename();
            int lastPointPos = originFileName.lastIndexOf(".");
            String ext = "";
            if (lastPointPos != -1) {
                ext = originFileName.substring(lastPointPos).toLowerCase();
            }
            if (ArrayUtils.contains(Constants.NOTALLOWATT, ext.toLowerCase())) {//此情况一般不会出现
                result.buildErrorResult("文件名为[" + originFileName + "]的文件类型不允许上传！");
                return result;
            }
            String filename = System.currentTimeMillis() + ext;
            String path = request.getParameter("path");
            if(path == null) path = PropertiesUtil.getParam("fang.attachmentPath");
            File targetFile = new File(PropertiesUtil.getParam("fang.baseFilePath") + path, filename);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }

            //保存
            file.transferTo(targetFile);

            result.buildSuccessResult();
            result.put("originFileName", originFileName);
            result.put("fileName", targetFile.getName());
            result.put("filePath",  path+filename);
            result.put("suffix", ext);
            result.put("fileSize", file.getSize());
        } catch (Exception e) {
            result.buildErrorResult(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result;
    }

}
