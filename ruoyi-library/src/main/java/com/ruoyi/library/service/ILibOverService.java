package com.ruoyi.library.service;

import java.util.List;
import com.ruoyi.library.domain.LibOver;

/**
 * 图书逾期Service接口
 * 
 * @author 青栀无梦
 * @date 2022-04-25
 */
public interface ILibOverService 
{
    /**
     * 查询图书逾期
     * 
     * @param overId 图书逾期主键
     * @return 图书逾期
     */
    public LibOver selectLibOverByOverId(Long overId);

    /**
     * 查询图书逾期列表
     * 
     * @param libOver 图书逾期
     * @return 图书逾期集合
     */
    public List<LibOver> selectLibOverList(LibOver libOver);

    /**
     * 新增图书逾期
     * 
     * @param libOver 图书逾期
     * @return 结果
     */
    public int insertLibOver(LibOver libOver);

    /**
     * 修改图书逾期
     * 
     * @param libOver 图书逾期
     * @return 结果
     */
    public int updateLibOver(LibOver libOver);

    /**
     * 批量删除图书逾期
     * 
     * @param overIds 需要删除的图书逾期主键集合
     * @return 结果
     */
    public int deleteLibOverByOverIds(Long[] overIds);

    /**
     * 删除图书逾期信息
     * 
     * @param overId 图书逾期主键
     * @return 结果
     */
    public int deleteLibOverByOverId(Long overId);
}
