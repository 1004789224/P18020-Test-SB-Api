package com.ly.aop;

import com.alibaba.fastjson.JSON;
import com.ly.anon.AopLog;
import com.ly.anon.AopLogEntrance;
import com.ly.anon.AopLogExport;
import com.ly.helper.AppException;
import com.ly.helper.ErrorCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
public class LogOperateAop {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.ly.web..*(..)) || execution(* com.ly.student.web..*(..)) ")
    public void webLog() {
    }

    private String signature;

    private Long filterL;

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws AppException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();
        signature = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // 判断是否打印全部log
        AopLog aopLog = method.getAnnotation(AopLog.class);
        // 判断是否打印入参log
        AopLogEntrance aopLogEntrance = method.getAnnotation(AopLogEntrance.class);
        // 判断是否打印出参
        AopLogExport aopLogExport = method.getAnnotation(AopLogExport.class);
        if (null != aopLog || null != aopLogEntrance) {
            filterL = 1L;
            logger.info(signature + " request : " + Arrays.toString(joinPoint.getArgs()));
        }
        if (null != aopLog || null != aopLogExport){
            filterL = 1L;
        }

        BindingResult bindingResult = null;
        try {
            Object[] args = joinPoint.getArgs();
            bindingResult = (BindingResult) args[args.length - 1];
        } catch (Exception e) {

        }
        if (null != bindingResult) {
            if (bindingResult.hasErrors()) {
                StringBuilder sb = new StringBuilder();
                for (ObjectError objectError : bindingResult.getAllErrors()) {
                    sb.append(objectError.getDefaultMessage()).append(";");
                }
                ErrorCode.PARAMETER_ERROR.setMessage(sb.toString());
                throw new AppException(ErrorCode.PARAMETER_ERROR);
            }
        }

        // 接收到请求，记录请求内容
/*
        logger.info("WebLogAspect.doBefore()");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();


        // 记录下请求内容


        logger.info("URL : " + request.getRequestURL().toString());

        logger.info("HTTP_METHOD : " + request.getMethod());

        logger.info("IP : " + request.getRemoteAddr());

        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature
                ().getName());
*/


    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        if (null != filterL && filterL.equals(1L)){
            logger.info(signature + " response : " + JSON.toJSONString(ret));
        }
    }
}