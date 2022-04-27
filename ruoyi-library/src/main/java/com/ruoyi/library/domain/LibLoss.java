package com.ruoyi.library.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书遗失订单对象 lib_loss
 *
 * @author 青栀无梦
 * @date 2022-04-25
 */
@Data
public class LibLoss extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long lossId;

    /** 借书ID */
    private Long borrowId;

    /** 图书Id */
    @Excel(name = "图书Id")
    private Long bookId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal lossMoney;

    /** 支付状态(0未支付 1支付成功 2支付失败) */
    @Excel(name = "支付状态(0未支付 1支付成功 2支付失败)")
    private String status;

    /** 逻辑删除 */
    private String isDelete;

    private Object user;

    private Object book;


    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updataTime;

    public void setLossId(Long lossId)
    {
        this.lossId = lossId;
    }

    public Long getLossId()
    {
        return lossId;
    }
    public void setBorrowId()
    {
        this.borrowId = borrowId;
    }
    public Long getBorrowId()
    {
        return borrowId;
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
    public void setLossMoney(BigDecimal lossMoney)
    {
        this.lossMoney = lossMoney;
    }

    public BigDecimal getLossMoney()
    {
        return lossMoney;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIsDelete(String isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete()
    {
        return isDelete;
    }
    public void setUpdataTime(Date updataTime)
    {
        this.updataTime = updataTime;
    }

    public Date getUpdataTime()
    {
        return updataTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("lossId", getLossId())
            .append("bookId", getBookId())
            .append("userId", getUserId())
            .append("lossMoney", getLossMoney())
            .append("status", getStatus())
            .append("isDelete", getIsDelete())
            .append("createTime", getCreateTime())
            .append("updataTime", getUpdataTime())
            .toString();
    }
}
