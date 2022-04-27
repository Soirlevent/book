package com.ruoyi.library.mapper;

import com.ruoyi.library.domain.LibNotice;

import java.util.List;

/**
 * 消息管理Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public interface LibNoticeMapper
{
    /**
     * 查询消息管理
     *
     * @param noticeId 消息管理主键
     * @return 消息管理
     */
    public LibNotice selectLibNoticeByNoticeId(Long noticeId);

    /**
     * 查询消息管理列表
     *
     * @param libNotice 消息管理
     * @return 消息管理集合
     */
    public List<LibNotice> selectLibNoticeList(LibNotice libNotice);

    /**
     * 新增消息管理
     *
     * @param libNotice 消息管理
     * @return 结果
     */
    public int insertLibNotice(LibNotice libNotice);

    /**
     * 修改消息管理
     *
     * @param libNotice 消息管理
     * @return 结果
     */
    public int updateLibNotice(LibNotice libNotice);

    /**
     * 删除消息管理
     *
     * @param noticeId 消息管理主键
     * @return 结果
     */
    public int deleteLibNoticeByNoticeId(Long noticeId);

    /**
     * 批量删除消息管理
     *
     * @param noticeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibNoticeByNoticeIds(Long[] noticeIds);
}
