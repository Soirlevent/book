package com.ruoyi.library.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 消息通知对象 lib_message
 *
 * @author 青栀无梦
 * @date 2022-04-28
 */
@Data
public class LibMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    /** 接收人ID */
    @Excel(name = "接收人ID")
    private Long userId;

    /** 图书ID */
    @Excel(name = "图书ID")
    private Long bookId;

    /** 消息标题 */
    @Excel(name = "消息标题")
    private String messageTitle;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageContent;

    /** 阅读标志（1未读，2已读） */
    @Excel(name = "阅读标志", readConverterExp = "1=未读，2已读")
    private String isRead;

    /** 删除标志(0未删除  已删除) */
    private String isDelete;

    private Object user;

    private Object book;

    public void LibMessage(){

    }

    public void setMessageId(Long messageId)
    {
        this.messageId = messageId;
    }

    public Long getMessageId()
    {
        return messageId;
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
    public void setMessageTitle(String messageTitle)
    {
        this.messageTitle = messageTitle;
    }

    public String getMessageTitle()
    {
        return messageTitle;
    }
    public void setMessageContent(String messageContent)
    {
        this.messageContent = messageContent;
    }

    public String getMessageContent()
    {
        return messageContent;
    }
    public void setIsRead(String isRead)
    {
        this.isRead = isRead;
    }

    public String getIsRead()
    {
        return isRead;
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
            .append("messageId", getMessageId())
            .append("userId", getUserId())
            .append("bookId", getBookId())
            .append("messageTitle", getMessageTitle())
            .append("messageContent", getMessageContent())
            .append("isRead", getIsRead())
            .append("isDelete", getIsDelete())
            .append("createTime", getCreateTime())
            .toString();
    }
}
