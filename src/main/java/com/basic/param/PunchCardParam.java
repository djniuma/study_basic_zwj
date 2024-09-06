package com.basic.param;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 打卡
 */
public class PunchCardParam {

    /**
     * 打卡时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date punchCardDate;

    /**
     * 打卡用语
     */
    private String punchCardContent;

    public Date getPunchCardDate() {
        return punchCardDate;
    }

    public void setPunchCardDate(Date punchCardDate) {
        this.punchCardDate = punchCardDate;
    }

    public String getPunchCardContent() {
        return punchCardContent;
    }

    public void setPunchCardContent(String punchCardContent) {
        this.punchCardContent = punchCardContent;
    }
}
