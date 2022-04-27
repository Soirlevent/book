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
 * 图书借阅对象 lib_book
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@Data
public class Book extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long bookId;

    /** 书名 */
    @Excel(name = "书名")
    private String bookName;

    /** ISBN号 */
    @Excel(name = "ISBN号")
    private String bookIsbn;

    /** 图书类型 */
    @Excel(name = "图书类型")
    private Long typeId;

    /** 图书封面 */
    @Excel(name = "图书封面")
    private String bookImg;

    /** 作者 */
    @Excel(name = "作者")
    private String bookAuthor;

    /** 图书介绍 */
    @Excel(name = "图书介绍")
    private String bookIntroduction;

    /** 出版社 */
    @Excel(name = "出版社")
    private String bookAddress;

    /** 出版日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bookDate;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal bookPrice;

    /** 图书剩余数量 */
    @Excel(name = "图书剩余数量")
    private Long bookNum;

    /** 位置id */
    @Excel(name = "位置id")
    private Long locaId;

    /** 删除标志(0未删除 1删除) */
    private String isDelete;

    /** 图书类型 */
    private Object type;

    /** 图书位置 */
    private Object loca;

    public Book() {
    }

    public void setBookId(Long bookId)
    {
        this.bookId = bookId;
    }

    public Long getBookId()
    {
        return bookId;
    }
    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getBookName()
    {
        return bookName;
    }
    public void setBookIsbn(String bookIsbn)
    {
        this.bookIsbn = bookIsbn;
    }

    public String getBookIsbn()
    {
        return bookIsbn;
    }
    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public Long getTypeId()
    {
        return typeId;
    }
    public void setBookImg(String bookImg)
    {
        this.bookImg = bookImg;
    }

    public String getBookImg()
    {
        return bookImg;
    }
    public void setBookAuthor(String bookAuthor)
    {
        this.bookAuthor = bookAuthor;
    }

    public String getBookAuthor()
    {
        return bookAuthor;
    }
    public void setBookIntroduction(String bookIntroduction)
    {
        this.bookIntroduction = bookIntroduction;
    }

    public String getBookIntroduction()
    {
        return bookIntroduction;
    }
    public void setBookAddress(String bookAddress)
    {
        this.bookAddress = bookAddress;
    }

    public String getBookAddress()
    {
        return bookAddress;
    }
    public void setBookDate(Date bookDate)
    {
        this.bookDate = bookDate;
    }

    public Date getBookDate()
    {
        return bookDate;
    }
    public void setBookPrice(BigDecimal bookPrice)
    {
        this.bookPrice = bookPrice;
    }

    public BigDecimal getBookPrice()
    {
        return bookPrice;
    }
    public void setBookNum(Long bookNum)
    {
        this.bookNum = bookNum;
    }

    public Long getBookNum()
    {
        return bookNum;
    }
    public void setLocaId(Long locaId)
    {
        this.locaId = locaId;
    }

    public Long getLocaId()
    {
        return locaId;
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
            .append("bookId", getBookId())
            .append("bookName", getBookName())
            .append("bookIsbn", getBookIsbn())
            .append("typeId", getTypeId())
            .append("bookImg", getBookImg())
            .append("bookAuthor", getBookAuthor())
            .append("bookIntroduction", getBookIntroduction())
            .append("bookAddress", getBookAddress())
            .append("bookDate", getBookDate())
            .append("bookPrice", getBookPrice())
            .append("bookNum", getBookNum())
            .append("locaId", getLocaId())
            .append("isDelete", getIsDelete())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
