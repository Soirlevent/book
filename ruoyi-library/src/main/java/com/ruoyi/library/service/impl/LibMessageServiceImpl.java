package com.ruoyi.library.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.LibMessageMapper;
import com.ruoyi.library.domain.LibMessage;
import com.ruoyi.library.service.ILibMessageService;

/**
 * 消息通知Service业务层处理
 *
 * @author 青栀无梦
 * @date 2022-04-28
 */
@Service
public class LibMessageServiceImpl implements ILibMessageService
{
    @Autowired
    private LibMessageMapper libMessageMapper;

    /**
     * 查询消息通知
     *
     * @param messageId 消息通知主键
     * @return 消息通知
     */
    @Override
    public LibMessage selectLibMessageByMessageId(Long messageId)
    {
        return libMessageMapper.selectLibMessageByMessageId(messageId);
    }

    /**
     * 查询消息通知列表
     *
     * @param libMessage 消息通知
     * @return 消息通知
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "us")
    public List<LibMessage> selectLibMessageList(LibMessage libMessage)
    {
        return libMessageMapper.selectLibMessageList(libMessage);
    }

    /**
     * 新增消息通知
     *
     * @param libMessage 消息通知
     * @return 结果
     */
    @Override
    public int insertLibMessage(LibMessage libMessage)
    {
        libMessage.setCreateTime(DateUtils.getNowDate());
        return libMessageMapper.insertLibMessage(libMessage);
    }

    /**
     * 修改消息通知
     *
     * @param libMessage 消息通知
     * @return 结果
     */
    @Override
    public int updateLibMessage(LibMessage libMessage)
    {
        return libMessageMapper.updateLibMessage(libMessage);
    }

    /**
     * 批量删除消息通知
     *
     * @param messageIds 需要删除的消息通知主键
     * @return 结果
     */
    @Override
    public int deleteLibMessageByMessageIds(Long[] messageIds)
    {
        return libMessageMapper.deleteLibMessageByMessageIds(messageIds);
    }

    /**
     * 删除消息通知信息
     *
     * @param messageId 消息通知主键
     * @return 结果
     */
    @Override
    public int deleteLibMessageByMessageId(Long messageId)
    {
        return libMessageMapper.deleteLibMessageByMessageId(messageId);
    }
}
