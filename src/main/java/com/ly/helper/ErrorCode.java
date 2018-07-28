package com.ly.helper;

/**
 * ErrorCode
 * <p>
 * Created by weizhong on 2017/6/16.
 */
public enum ErrorCode {

    /**
     * 会话已过期，请重新登录
     */
    SESSION_EXPIRE("800101", "会话已过期，请重新登录"),
    /**
     * 请求参数有误
     */
    PARAMETER_ERROR("800102", "请求参数有误"),

    /**
     * 创建fav失败
     */
    CREATE_FAV_FAIL("800103", "创建fav失败"),

    /**
     * 创建fav失败
     */
    CREATE_BLUE_PAY_FAV_FAIL("800103", "Gagal membuat FAV!"),

    /**
     * FAV已经创建
     */
    FAV_ALREADY_CREATE("800104", "FAV已经创建"),

    /**
     * FAV已经创建
     */
    FAV_NO_CREATE("800105", "FAV belum dibuat!"),

    /**
     * 代付失败
     */
    DISBURSEMENT_FAIL("800106", "Pembayaran gagal!"),

    /**
     * 获取银行列表失败
     */
    GET_BANKLIST_FAIL("800107", "Get bank list fail！"),

    /**
     * 获取银行列表失败
     */
    CHECK_BANK_FAIL("800108", "Bank check error!"),

    /**
     * 银行卡为空
     */
    BANK_IS_NULL("800109", "Kartu bank kosong!"),

    /**
     * 上笔提现还未完成
     */
    LAST_WITHDRAW_UNDONE("800110", "Penarikan terakhir belum selesai!"),



    /**
     * 电话号码已经被注册
     */
    PHONE_IS_REGISTER("S00104", "Nomor telepon ini telah terdaftar！"),

    /**
     * 电话号码未注册
     */
    PHONE_IS_NOT_REGISTER("S00105", "Nomor telepon ini belum terdaftar！"),

    /**
     * 短信发送失败
     */
    SMS_SEND_FAIL("S00106", "Pesan gagal mengirim!"),

    /**
     * 验证码错误
     */
    CODE_IS_ERROR("S00107", "验证码错误!"),

    /**
     * 验证码错误
     */
    RIGISTER_FAIL("S00108", "Gagal untuk mendaftar!"),

    /**
     * 保存数据失败
     */
    SAVE_FAIL("800116", "保存数据失败!"),

    BANK_FAIL("800117","Silakan isi rekening bank yang tepat!"),

    /**
     * 删除失败
     */
    DEL_FAIL("S00201", "Gagal menghapus data!"),

    /**
     * 上传文件失败
     */
    UPLOAD_FILE_FAIL("S00202", "Gagal mengupload file!"),

    /**
     * 标的查询失败
     */
    PRODUCT_FIND_FAIL("S00203", "Permintaan tender gagal!"),

    /**
     * 资金流水保存失败
     */
    CAPITALFLOW_SAVE_FAIL("S00204", "Gagal menyimpan aliran dana!"),

    /**
     * 用户fav查询失败
     */
    USERFAV_FIND_FAIL("S00205", "Permintaan fav pengguna gagal!"),

    /**
     * 有未支付的订单
     */
    LENDORDER_WAIT_PAY("S00206", "Anda memiliki pesanan yang belum dibayar, harap bayar pesanan terlebih dahulu."),

    /**
     * 服务异常
     **/
    SERVICE_EXCEPTION("800109", "Server abnormal"),
    /**
     * 请求参数有误
     */
    FB_ALREADY_BIND("100120", "facebook already bind"),

    FACEBOOK_AUTH_FAIL_ERROR("100103", "Verifikasi FACEBOOK gagal"),

    DATA_REPEAT_ERROR("100104", "Data sudah ada"),

    /**
     * Cannot get Jedis connection
     */
    REDIS_NOT_CONN("100122","Cannot get Jedis connection!"),

    /**
     * 身份证号已被注册
     */
    KTPNO_IS_REGISTER("100112", "Nomor KTP (NIK) telah terdaftar, silakan isi ulang"),

    /**
     * 金额不一致
     */
    INCONSISTENT_AMOUNT("100113", "Jumlah pembayaran tidak konsisten dengan jumlah tender"),

    /**
     * 账户未激活
     */
    ACCOUNT_NO_ACTION("100114", "Akun tidak aktif. Silakan aktifkan akun terlebih dahulu."),

    /**
     * 当前暂不支持投资，请10:00-20:00再试
     */
    TIME_NO_INVEST("100115", "Investasi saat ini tidak bisa dilakukan, mohon coba lagi antara 10.00-20.00"),

    /**
     * 该标的正在投资中
     */
    PRODUCT_IS_INVESTING("100116", "Tender sekarang tersedia untuk investasi"),

    /**
     * 账户未激活
     */
    ACCOUNT_ACTION("100117", " Akun telah diaktifkan"),

    WRONG_PHONE_OR_PWD("100118","账号无效或密码错误"),

    UPDATE_ERROR( "100119", "更新用户信息失败" ),
    SAME_PASSWORD( "100120", "重复的密码!" ),
    INNER_WRONG( "100121", "内部错误!" )
    ;



    private String code;

    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据错误码获取错误信息
     *
     * @param code
     * @return
     */
    public static ErrorCode getByCode(String code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (code.equals(errorCode.getCode())) {
                return errorCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
