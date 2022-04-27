package com.ruoyi.library.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书位置对象 lib_location
 * 
 * @author 青栀无梦
 * @date 2022-04-24
 */
public class LibLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 位置id */
    private Long locaId;

    /** 位置名称 */
    @Excel(name = "位置名称")
    private String locaName;

    /** 位置图片 */
    @Excel(name = "位置图片")
    private String locaImg;

    /** 位置介绍 */
    @Excel(name = "位置介绍")
    private String locaIntroduction;

    public void setLocaId(Long locaId) 
    {
        this.locaId = locaId;
    }

    public Long getLocaId() 
    {
        return locaId;
    }
    public void setLocaName(String locaName) 
    {
        this.locaName = locaName;
    }

    public String getLocaName() 
    {
        return locaName;
    }
    public void setLocaImg(String locaImg) 
    {
        this.locaImg = locaImg;
    }

    public String getLocaImg() 
    {
        return locaImg;
    }
    public void setLocaIntroduction(String locaIntroduction) 
    {
        this.locaIntroduction = locaIntroduction;
    }

    public String getLocaIntroduction() 
    {
        return locaIntroduction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("locaId", getLocaId())
            .append("locaName", getLocaName())
            .append("locaImg", getLocaImg())
            .append("locaIntroduction", getLocaIntroduction())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
