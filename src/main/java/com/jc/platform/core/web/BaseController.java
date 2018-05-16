package com.jc.platform.core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Description 下载文件
     *
     * @param path 路径和文件名
     *             void
     * @throws
     */
    public void sendFile(String path, HttpServletResponse response) {
        sendFile(path, response, "application/octet-stream");
    }

    /**
     * Description: 下载文件
     *
     * @param path        文件路径
     * @param response
     * @param contentType response类型
     * @throws
     */
    public void sendFile(String path, HttpServletResponse response, String contentType) {
        try {
            File file = new File(path);
            String filename = file.getName();
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            if ("application/octet-stream".equals(contentType)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            }
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType(contentType);
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description: 下载文件（字符串）
     *
     * @param fileName 文件路径
     * @param response
     * @param response 类型
     */
    public void sendFileByStr(String content, String fileName, HttpServletResponse response) {
        try {
            byte[] buffer = content.getBytes();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description 判断是否为ajax请求
     *
     * @param request 请求request
     * @return 判断结果
     */
    protected boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {
            return true;
        }
        return false;
    }

}
