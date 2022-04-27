package com.ruoyi.library.service;

import java.util.List;
import com.ruoyi.library.domain.LibType;

/**
 * 图书分类Service接口
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
public interface ILibTypeService
{
    /**
     * 查询图书分类
     *
     * @param typeId 图书分类主键
     * @return 图书分类
     */
    public LibType selectLibTypeByTypeId(Long typeId);

    /**
     * 查询图书分类列表
     *
     * @param libType 图书分类
     * @return 图书分类集合
     */
    public List<LibType> selectLibTypeList(LibType libType);

    /**
     * 新增图书分类
     *
     * @param libType 图书分类
     * @return 结果
     */
    public int insertLibType(LibType libType);

    /**
     * 修改图书分类
     *
     * @param libType 图书分类
     * @return 结果
     */
    public int updateLibType(LibType libType);

    /**
     * 批量删除图书分类
     *
     * @param typeIds 需要删除的图书分类主键集合
     * @return 结果
     */
    public int deleteLibTypeByTypeIds(Long[] typeIds);

    /**
     * 删除图书分类信息
     *
     * @param typeId 图书分类主键
     * @return 结果
     */
    public int deleteLibTypeByTypeId(Long typeId);

    public String checkTypeNameUnique(LibType libType);
}
