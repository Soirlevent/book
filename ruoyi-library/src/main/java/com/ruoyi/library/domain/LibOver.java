package com.ruoyi.library.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书逾期对象 lib_over
 *
 * @author 青栀无梦
 * @date 2022-04-25
 */
@Data
public class LibOver extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 逾期订单id */
    private Long overId;

    /** 图书id */
    @Excel(name = "图书id")
    private Long bookId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /**图书价格*/
    @Excel(name = "图书价格")
    private BigDecimal bookPrice;

    /** 逾期天数 */
    @Excel(name = "逾期天数")
    private Long overData;

    /** 逾期率 */
    @Excel(name = "逾期率")
    private BigDecimal overOneday;

    /** 处罚金额 */
    @Excel(name = "处罚金额")
    private BigDecimal overMoney;

    /** 支付状态(0未支付 1支付成功 2支付失败) */
    @Excel(name = "支付状态(0未支付 1支付成功 2支付失败)")
    private String status;

    /** 逻辑删除 */
    private String isDelet;

    private Object user;

    private Object book;

    public void setOverId(Long overId)
    {
        this.overId = overId;
    }

    public Long getOverId()
    {
        return overId;
    }
    public void setBookId(Long bookId)
    {
        this.bookId = bookId;
    }

    public Long getBookId()
    {
        return bookId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setOverData(Long overData)
    {
        this.overData = overData;
    }

    public Long getOverData()
    {
        return overData;
    }
    public void setOverOneday(BigDecimal overOneday)
    {
        this.overOneday = overOneday;
    }

    public BigDecimal getOverOneday()
    {
        return overOneday;
    }
    public void setOverMoney(BigDecimal overMoney)
    {
        this.overMoney = overMoney;
    }

    public BigDecimal getOverMoney()
    {
        return overMoney;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIsDelet(String isDelet)
    {
        this.isDelet = isDelet;
    }

    public String getIsDelet()
    {
        return isDelet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("overId", getOverId())
            .append("bookId", getBookId())
            .append("userId", getUserId())
            .append("overData", getOverData())
            .append("overOneday", getOverOneday())
            .append("overMoney", getOverMoney())
            .append("status", getStatus())
            .append("isDelet", getIsDelet())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
