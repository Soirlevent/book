package com.ruoyi.library.service;

import java.util.List;
import com.ruoyi.library.domain.LibLocation;

/**
 * 图书位置Service接口
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
public interface ILibLocationService
{
    /**
     * 查询图书位置
     *
     * @param locaId 图书位置主键
     * @return 图书位置
     */
    public LibLocation selectLibLocationByLocaId(Long locaId);

    /**
     * 查询图书位置列表
     *
     * @param libLocation 图书位置
     * @return 图书位置集合
     */
    public List<LibLocation> selectLibLocationList(LibLocation libLocation);

    /**
     * 新增图书位置
     *
     * @param libLocation 图书位置
     * @return 结果
     */
    public int insertLibLocation(LibLocation libLocation);

    /**
     * 修改图书位置
     *
     * @param libLocation 图书位置
     * @return 结果
     */
    public int updateLibLocation(LibLocation libLocation);

    /**
     * 批量删除图书位置
     *
     * @param locaIds 需要删除的图书位置主键集合
     * @return 结果
     */
    public int deleteLibLocationByLocaIds(Long[] locaIds);

    /**
     * 删除图书位置信息
     *
     * @param locaId 图书位置主键
     * @return 结果
     */
    public int deleteLibLocationByLocaId(Long locaId);


    public String checkLocaNameUnique(LibLocation libLocation);
}
