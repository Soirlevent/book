package com.ruoyi.library.service;

import java.util.List;
import com.ruoyi.library.domain.LibLoss;

/**
 * 图书遗失订单Service接口
 *
 * @author 青栀无梦
 * @date 2022-04-25
 */
public interface ILibLossService
{
    /**
     * 查询图书遗失订单
     *
     * @param lossId 图书遗失订单主键
     * @return 图书遗失订单
     */
    public LibLoss selectLibLossByLossId(Long lossId);

    /**
     * 查询图书遗失订单列表
     *
     * @param libLoss 图书遗失订单
     * @return 图书遗失订单集合
     */
    public List<LibLoss> selectLibLossList(LibLoss libLoss);

    /**
     * 新增图书遗失订单
     *
     * @param libLoss 图书遗失订单
     * @return 结果
     */
    public int insertLibLoss(LibLoss libLoss);

    /**
     * 修改图书遗失订单
     *
     * @param libLoss 图书遗失订单
     * @return 结果
     */
    public int updateLibLoss(LibLoss libLoss);

    /**
     * 批量删除图书遗失订单
     *
     * @param lossIds 需要删除的图书遗失订单主键集合
     * @return 结果
     */
    public int deleteLibLossByLossIds(Long[] lossIds);

    /**
     * 删除图书遗失订单信息
     *
     * @param lossId 图书遗失订单主键
     * @return 结果
     */
    public int deleteLibLossByLossId(Long lossId);


    public int cancel(LibLoss libLoss);
}
