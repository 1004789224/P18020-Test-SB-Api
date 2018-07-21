package com.ly.helper;

import org.apache.commons.lang3.StringUtils;

/**
 * description
 *
 * @author lianzeng
 * @date 2018/5/24
 */
public class PhoneUtil {
    public static String getLikePhone(String phone){
        if(StringUtils.isBlank(phone)){
            return phone;
        }else if(phone.length()==11 || phone.length()==12){
            return  phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }else{
            return phone;
        }
    }

    /**
     * name 加密
     * @param name
     * @return
     */
    public static String getLikeName(String name){
        if(StringUtils.isBlank(name)){
            return name;
        }
        if(name.length()>2){
            StringBuilder middle=new StringBuilder();
            for (int i=0;i<name.length()-2;i++){
                middle.append("*");
            }
            return name.substring(0,1)+middle.toString()+name.substring(name.length()-1);
        }
        return name;
    }
    /**
     * ktp 加密
     * @param ktp
     * @return
     */
    public static String getLikeKtp(String ktp) {
        if (StringUtils.isBlank(ktp)) {
            return ktp;
        }
        if (ktp.length() > 6) {
            StringBuilder middle = new StringBuilder();
            for (int i = 0; i < ktp.length() - 6; i++) {
                middle.append("*");
            }
            return ktp.substring(0, 2) + middle.toString() + ktp.substring(ktp.length() - 4);
        }
        return ktp;
    }

    /**
     * 获取银行卡加密号码
     * @param cardNo
     * @return
     */
    public static String getLikeCardNo(String cardNo){
        if(StringUtils.isBlank(cardNo)){
            return cardNo;
        }
        if(cardNo.length()>4){
            StringBuilder middle=new StringBuilder("**** **** **** ");
            cardNo=cardNo.substring(cardNo.length()-4,cardNo.length());
            middle.append(cardNo);
            return middle.toString();
        }
        return cardNo;
    }
    public static void main(String[] args) {
        String likePhone = PhoneUtil.getLikeCardNo("6201181000118320");
        System.out.println(likePhone);
    }
}
