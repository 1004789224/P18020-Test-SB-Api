package com.ly.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ly.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lw
 * @version 1.0
 * @description com.ly.web
 * @date 2018/7/22
 */
@Controller
public class KaptchaController {
    @Autowired
    private DefaultKaptcha captchaProducer;

    private static Logger log = LoggerFactory.getLogger( KaptchaController.class );
    /**
     * 获取验证码 的 请求路径
     * @param httpServletRequest
     * @param response
     * @throws Exception
     */
    @GetMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            log.debug( "生产验证码字符串",createText );
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * TODO 对图片验证码的验证  上线删除
     * @param request
     * @param vrifyCode
     * @return
     */
    @GetMapping("vc")
    @ResponseBody
    private Map<String, Object> vc(HttpServletRequest request,String vrifyCode) throws IOException {
        Map<String, Object> modelMap = new HashMap<>();
        boolean verificationCode = VerificationUtil.VerificationCode( request,vrifyCode );
        modelMap.put( "success", verificationCode );
        return modelMap;
    }
}
