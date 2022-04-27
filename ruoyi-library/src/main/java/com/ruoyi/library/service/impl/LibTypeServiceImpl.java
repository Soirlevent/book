package com.ruoyi.library.service.impl;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.LibTypeMapper;
import com.ruoyi.library.domain.LibType;
import com.ruoyi.library.service.ILibTypeService;

/**
 * 图书分类Service业务层处理
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@Service
public class LibTypeServiceImpl implements ILibTypeService
{
    @Autowired
    private LibTypeMapper libTypeMapper;

    /**
     * 查询图书分类
     *
     * @param typeId 图书分类主键
     * @return 图书分类
     */
    @Override
    public LibType selectLibTypeByTypeId(Long typeId)
    {
        return libTypeMapper.selectLibTypeByTypeId(typeId);
    }

    /**
     * 查询图书分类列表
     *
     * @param libType 图书分类
     * @return 图书分类
     */
    @Override
    public List<LibType> selectLibTypeList(LibType libType)
    {
        return libTypeMapper.selectLibTypeList(libType);
    }

    /**
     * 新增图书分类
     *
     * @param libType 图书分类
     * @return 结果
     */
    @Override
    public int insertLibType(LibType libType)
    {
        libType.setCreateTime(DateUtils.getNowDate());
        return libTypeMapper.insertLibType(libType);
    }

    /**
     * 修改图书分类
     *
     * @param libType 图书分类
     * @return 结果
     */
    @Override
    public int updateLibType(LibType libType)
    {
        libType.setUpdateTime(DateUtils.getNowDate());
        return libTypeMapper.updateLibType(libType);
    }

    /**
     * 批量删除图书分类
     *
     * @param typeIds 需要删除的图书分类主键
     * @return 结果
     */
    @Override
    public int deleteLibTypeByTypeIds(Long[] typeIds)
    {
        return libTypeMapper.deleteLibTypeByTypeIds(typeIds);
    }

    /**
     * 删除图书分类信息
     *
     * @param typeId 图书分类主键
     * @return 结果
     */
    @Override
    public int deleteLibTypeByTypeId(Long typeId)
    {
        return libTypeMapper.deleteLibTypeByTypeId(typeId);
    }

    public String checkTypeNameUnique(LibType libType)
    {
        Long typeId= StringUtils.isNull(libType.getTypeId())?-1L:libType.getTypeId();
        LibType info=libTypeMapper.checkTypeNameUnique(libType.getTypeName());
        if (StringUtils.isNotNull(info)&&info.getTypeId().longValue()!=typeId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
