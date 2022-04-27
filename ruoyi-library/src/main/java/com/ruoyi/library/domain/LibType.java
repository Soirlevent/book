package com.ruoyi.library.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书分类对象 lib_type
 * 
 * @author 青栀无梦
 * @date 2022-04-24
 */
public class LibType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类类id */
    private Long typeId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String typeName;

    /** 类别图片 */
    @Excel(name = "类别图片")
    private String typeImg;

    /** 类型简介 */
    @Excel(name = "类型简介")
    private String typeIntroduction;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setTypeImg(String typeImg) 
    {
        this.typeImg = typeImg;
    }

    public String getTypeImg() 
    {
        return typeImg;
    }
    public void setTypeIntroduction(String typeIntroduction) 
    {
        this.typeIntroduction = typeIntroduction;
    }

    public String getTypeIntroduction() 
    {
        return typeIntroduction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("typeImg", getTypeImg())
            .append("typeIntroduction", getTypeIntroduction())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
