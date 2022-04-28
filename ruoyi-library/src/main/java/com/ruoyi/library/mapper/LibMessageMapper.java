package com.ruoyi.library.mapper;

import java.util.List;
import com.ruoyi.library.domain.LibMessage;

/**
 * 消息通知Mapper接口
 * 
 * @author 青栀无梦
 * @date 2022-04-28
 */
public interface LibMessageMapper 
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
     * 删除消息通知
     * 
     * @param messageId 消息通知主键
     * @return 结果
     */
    public int deleteLibMessageByMessageId(Long messageId);

    /**
     * 批量删除消息通知
     * 
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibMessageByMessageIds(Long[] messageIds);
}
