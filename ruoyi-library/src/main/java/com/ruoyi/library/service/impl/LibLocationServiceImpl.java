package com.ruoyi.library.service.impl;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.LibLocationMapper;
import com.ruoyi.library.domain.LibLocation;
import com.ruoyi.library.service.ILibLocationService;

/**
 * 图书位置Service业务层处理
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@Service
public class LibLocationServiceImpl implements ILibLocationService
{
    @Autowired
    private LibLocationMapper libLocationMapper;

    /**
     * 查询图书位置
     *
     * @param locaId 图书位置主键
     * @return 图书位置
     */
    @Override
    public LibLocation selectLibLocationByLocaId(Long locaId)
    {
        return libLocationMapper.selectLibLocationByLocaId(locaId);
    }

    /**
     * 查询图书位置列表
     *
     * @param libLocation 图书位置
     * @return 图书位置
     */
    @Override
    public List<LibLocation> selectLibLocationList(LibLocation libLocation)
    {
        return libLocationMapper.selectLibLocationList(libLocation);
    }

    /**
     * 新增图书位置
     *
     * @param libLocation 图书位置
     * @return 结果
     */
    @Override
    public int insertLibLocation(LibLocation libLocation)
    {
        libLocation.setCreateTime(DateUtils.getNowDate());
        return libLocationMapper.insertLibLocation(libLocation);
    }

    /**
     * 修改图书位置
     *
     * @param libLocation 图书位置
     * @return 结果
     */
    @Override
    public int updateLibLocation(LibLocation libLocation)
    {
        libLocation.setUpdateTime(DateUtils.getNowDate());
        return libLocationMapper.updateLibLocation(libLocation);
    }

    /**
     * 批量删除图书位置
     *
     * @param locaIds 需要删除的图书位置主键
     * @return 结果
     */
    @Override
    public int deleteLibLocationByLocaIds(Long[] locaIds)
    {
        return libLocationMapper.deleteLibLocationByLocaIds(locaIds);
    }

    /**
     * 删除图书位置信息
     *
     * @param locaId 图书位置主键
     * @return 结果
     */
    @Override
    public int deleteLibLocationByLocaId(Long locaId)
    {
        return libLocationMapper.deleteLibLocationByLocaId(locaId);
    }

    /**
     * 效验图书位置名称是否唯一
     * @param libLocation
     * @return
     */
    @Override
    public String checkLocaNameUnique(LibLocation libLocation)
    {
        Long locaId = StringUtils.isNull(libLocation.getLocaId()) ? -1L : libLocation.getLocaId();
        LibLocation info = libLocationMapper.checkLocaNameUnique(libLocation.getLocaName());
        if(StringUtils.isNotNull(info) && info.getLocaId().longValue() != locaId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

}
