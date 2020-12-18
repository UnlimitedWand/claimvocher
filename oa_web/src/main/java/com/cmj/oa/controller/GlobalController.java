package com.cmj.oa.controller;

import com.cmj.oa.biz.GlobalBiz;
import com.cmj.oa.entity.Employee;
import com.cmj.oa.util.Base64Util;
import com.cmj.oa.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@Controller("globalController")
public class GlobalController {

    @Autowired
    private GlobalBiz globalBiz;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login1";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpSession session, HttpServletRequest request) throws Exception{
        String sn = request.getParameter("username");
        String password = request.getParameter("password");
        Employee employee = globalBiz.login(sn,password);
        if (employee == null) {
            return "false";
        }
        session.setAttribute("employee",employee);
        return "true";
    }

    @RequestMapping("/self")
    public String self(){
        return "self";
    }

    @RequestMapping("/quit")
    public String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }

    @RequestMapping("/to_change_password")
    public String toChangePassword(){
        return "change_password";
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1,@RequestParam String new2){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getPassword().equals(old)) {
            if(new1.equals(new2)){
                employee.setPassword(new1);
                globalBiz.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";
    }

    @RequestMapping("/decode")
    @ResponseBody
    public String qr_decode(HttpServletRequest request){
        // 请求url
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
        MultipartFile uploadFile = multipartRequest.getFile("image");
        try {
            String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/qrcode";
            byte[] imgData = uploadFile.getBytes();
            if(imgData.length == 0)
                return "upload image failed";
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.edd821edb577992b3df047e0fbac03d5.2592000.1610449572.282335-23145794";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println("result:" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload_file(MultipartFile imgfile){
        //构建文件目录
        File uploadDir = new File("F:/DreamCinemaImg");
        //如果目录不存在，则创建
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        //获取上传的文件名
        String fileName = imgfile.getOriginalFilename();
        //String fileName = name;
        //构建一个完整的文件上传对象
        File uploadFile = new File(uploadDir.getAbsolutePath() + "/" + fileName);
        //判断文件是否存在
        if(!uploadFile.exists()) {
            try {
                //通过transferTo方法进行上传
                imgfile.transferTo(uploadFile);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }else{
            return "upload failed";
        }
        return "upload succeed";
    }

}
