package com.ruoyi.library.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 借阅信息对象 lib_borrow
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@Data
public class LibBorrow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 借阅id */
    private Long borrowId;

    /** 读者号 */
    @Excel(name = "读者号")
    private Long userId;

    /** 图书id */
    @Excel(name = "图书id")
    private Long bookId;

    /** 图书状态(0:未逾期;1:已逾期) */
    @Excel(name = "图书状态(0:未逾期;1:已逾期)")
    private String bookFlag;

    /** 借阅时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借阅时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date borrowTime;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /** 还书时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "还书时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnTime;

    /** 续借状态(0:未续;1:已续) */
    @Excel(name = "续借状态(0:未续;1:已续)")
    private String isRenew;

    /** 还书状态(0:未还;1:已还) */
    @Excel(name = "还书状态(0:未还;1:已还)")
    private String isLeft;

    /** 遗失状态 */
    @Excel(name = "遗失状态")
    private String isLoss;

    /** 删除标志(0:未删除;1:已删除) */
    private String isDelete;

    private Object user;

    private Object book;

    public LibBorrow() {

    }

    public void setBorrowId(Long borrowId)
    {
        this.borrowId = borrowId;
    }

    public Long getBorrowId()
    {
        return borrowId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setBookId(Long bookId)
    {
        this.bookId = bookId;
    }

    public Long getBookId()
    {
        return bookId;
    }
    public void setBookFlag(String bookFlag)
    {
        this.bookFlag = bookFlag;
    }

    public String getBookFlag()
    {
        return bookFlag;
    }
    public void setBorrowTime(Date borrowTime)
    {
        this.borrowTime = borrowTime;
    }

    public Date getBorrowTime()
    {
        return borrowTime;
    }
    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }
    public void setReturnTime(Date returnTime)
    {
        this.returnTime = returnTime;
    }

    public Date getReturnTime()
    {
        return returnTime;
    }
    public void setIsRenew(String isRenew)
    {
        this.isRenew = isRenew;
    }

    public String getIsRenew()
    {
        return isRenew;
    }
    public void setIsLeft(String isLeft)
    {
        this.isLeft = isLeft;
    }

    public String getIsLeft()
    {
        return isLeft;
    }
    public void setIsLoss(String isLoss)
    {
        this.isLoss = isLoss;
    }

    public String getIsLoss()
    {
        return isLoss;
    }
    public void setIsDelete(String isDelete)
    {
        this.isDelete = isDelete;
    }

    public String getIsDelete()
    {
        return isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("borrowId", getBorrowId())
            .append("userId", getUserId())
            .append("bookId", getBookId())
            .append("bookFlag", getBookFlag())
            .append("borrowTime", getBorrowTime())
            .append("expireTime", getExpireTime())
            .append("returnTime", getReturnTime())
            .append("isRenew", getIsRenew())
            .append("isLeft", getIsLeft())
            .append("isLoss", getIsLoss())
            .append("isDelete", getIsDelete())
            .toString();
    }
}
