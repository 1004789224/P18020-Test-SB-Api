package com.ly.job;

import com.ly.enums.PayChannelEnum;
import com.ly.helper.AppException;
import com.ly.service.PayService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author lzh
 * @since 2017/12/28
 */
public class FavFixJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(FavFixJob.class);

    @Value("${repayment.pay.channel}")
    private String payChannel;

//    @Autowired
//    private PayService payService;
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        LOGGER.info(" payChannel =====  {} ", payChannel);
//        if (payChannel.equalsIgnoreCase(PayChannelEnum.XENDIT.name())){
//            try{
//                payService.fixFVA();
//            }catch (AppException ex){
//                LOGGER.error("fixFVA error: {},{}", ex.getErrorCode(), ex.getMessage());
//            }
//        }
//    }
}
