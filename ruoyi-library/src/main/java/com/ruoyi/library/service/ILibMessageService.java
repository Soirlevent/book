package com.ruoyi.library.service;

import java.util.List;
import com.ruoyi.library.domain.LibMessage;

/**
 * 消息通知Service接口
 * 
 * @author 青栀无梦
 * @date 2022-04-28
 */
public interface ILibMessageService 
{
    /**
     * 查询消息通知
     * 
     * @param messageId 消息通知主键
     * @return 消息通知
     */
    public LibMessage selectLibMessageByMessageId(Long messageId);

    /**
     * 查询消息通知列表
     * 
     * @param libMessage 消息通知
     * @return 消息通知集合
     */
    public List<LibMessage> selectLibMessageList(LibMessage libMessage);

    /**
     * 新增消息通知
     * 
     * @param libMessage 消息通知
     * @return 结果
     */
    public int insertLibMessage(LibMessage libMessage);

    /**
     * 修改消息通知
     * 
     * @param libMessage 消息通知
     * @return 结果
     */
    public int updateLibMessage(LibMessage libMessage);

    /**
     * 批量删除消息通知
     * 
     * @param messageIds 需要删除的消息通知主键集合
     * @return 结果
     */
    public int deleteLibMessageByMessageIds(Long[] messageIds);

    /**
     * 删除消息通知信息
     * 
     * @param messageId 消息通知主键
     * @return 结果
     */
    public int deleteLibMessageByMessageId(Long messageId);
}
