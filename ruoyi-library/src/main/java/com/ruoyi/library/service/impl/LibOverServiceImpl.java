package com.ruoyi.library.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.LibOverMapper;
import com.ruoyi.library.domain.LibOver;
import com.ruoyi.library.service.ILibOverService;

/**
 * 图书逾期Service业务层处理
 *
 * @author 青栀无梦
 * @date 2022-04-25
 */
@Service
public class LibOverServiceImpl implements ILibOverService
{
    @Autowired
    private LibOverMapper libOverMapper;

    /**
     * 查询图书逾期
     *
     * @param overId 图书逾期主键
     * @return 图书逾期
     */
    @Override
    public LibOver selectLibOverByOverId(Long overId)
    {
        return libOverMapper.selectLibOverByOverId(overId);
    }

    /**
     * 查询图书逾期列表
     *
     * @param libOver 图书逾期
     * @return 图书逾期
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "us")
    public List<LibOver> selectLibOverList(LibOver libOver)
    {
        return libOverMapper.selectLibOverList(libOver);
    }

    /**
     * 新增图书逾期
     *
     * @param libOver 图书逾期
     * @return 结果
     */
    @Override
    public int insertLibOver(LibOver libOver)
    {
        libOver.setCreateTime(DateUtils.getNowDate());
        return libOverMapper.insertLibOver(libOver);
    }

    /**
     * 修改图书逾期
     *
     * @param libOver 图书逾期
     * @return 结果
     */
    @Override
    public int updateLibOver(LibOver libOver)
    {
        libOver.setUpdateTime(DateUtils.getNowDate());
        return libOverMapper.updateLibOver(libOver);
    }

    /**
     * 批量删除图书逾期
     *
     * @param overIds 需要删除的图书逾期主键
     * @return 结果
     */
    @Override
    public int deleteLibOverByOverIds(Long[] overIds)
    {
        return libOverMapper.deleteLibOverByOverIds(overIds);
    }

    /**
     * 删除图书逾期信息
     *
     * @param overId 图书逾期主键
     * @return 结果
     */
    @Override
    public int deleteLibOverByOverId(Long overId)
    {
        return libOverMapper.deleteLibOverByOverId(overId);
    }
}
