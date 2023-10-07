package com.easybbs.controller;

import com.easybbs.CreateImageCode;
import com.easybbs.enums.ResponseCodeEnum;
import com.easybbs.exceptions.BusinessException;
import com.easybbs.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@RestController
public class AccountController{
    protected <T> ResponseVO getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus("success");
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws IOException {
        CreateImageCode vCode = new CreateImageCode();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = vCode.getCode();
        if(type == null || type == 0){
            session.setAttribute("check_code_key", code);
        }
        else{
            //check code for getting email
            session.setAttribute("check_code_key_email", code);
        }
        vCode.write(response.getOutputStream());
    }

//    @RequestMapping("sendEmailCode")
//    public ResponseVO sendEmailCode(HttpSession session, String email, String checkCode, Integer type){
//
//    }

    @RequestMapping("/register")
    public ResponseVO register(HttpSession session, String checkCode){
        if(checkCode == null || "".equalsIgnoreCase(checkCode)){
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

//
////        session.getAttribute("check_code_key").equals(checkCode);
        try {
            String sessionCode = (String) session.getAttribute("check_code_key");
            if (sessionCode.equalsIgnoreCase(checkCode)) {
                return getSuccessResponseVO("successful");
            } else {
                throw new BusinessException("fail");
            }
        }catch(Exception e){
            throw new RuntimeException(e);

        }finally {
            System.out.println("I don't know"+session.getAttribute("check_code_key"));
        }

    }


}
