package com.ruoyi.library.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.library.domain.LibBorrow;
import com.ruoyi.library.mapper.LibBorrowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.LibLossMapper;
import com.ruoyi.library.domain.LibLoss;
import com.ruoyi.library.service.ILibLossService;

/**
 * 图书遗失订单Service业务层处理
 *
 * @author 青栀无梦
 * @date 2022-04-25
 */
@Service
public class LibLossServiceImpl implements ILibLossService
{
    @Autowired
    private LibLossMapper libLossMapper;

    @Autowired
    private LibBorrowMapper libBorrowMapper;
    /**
     * 查询图书遗失订单
     *
     * @param lossId 图书遗失订单主键
     * @return 图书遗失订单
     */
    @Override
    public LibLoss selectLibLossByLossId(Long lossId)
    {
        return libLossMapper.selectLibLossByLossId(lossId);
    }

    /**
     * 查询图书遗失订单列表
     *
     * @param libLoss 图书遗失订单
     * @return 图书遗失订单
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "us")
    public List<LibLoss> selectLibLossList(LibLoss libLoss)
    {
        System.out.println(libLossMapper.selectLibLossList(libLoss));
        return libLossMapper.selectLibLossList(libLoss);
    }

    /**
     * 新增图书遗失订单
     *
     * @param libLoss 图书遗失订单
     * @return 结果
     */
    @Override
    public int insertLibLoss(LibLoss libLoss)
    {
        libLoss.setCreateTime(DateUtils.getNowDate());
        return libLossMapper.insertLibLoss(libLoss);
    }

    /**
     * 修改图书遗失订单
     *
     * @param libLoss 图书遗失订单
     * @return 结果
     */
    @Override
    public int updateLibLoss(LibLoss libLoss)
    {
        return libLossMapper.updateLibLoss(libLoss);
    }

    /**
     * 批量删除图书遗失订单
     *
     * @param lossIds 需要删除的图书遗失订单主键
     * @return 结果
     */
    @Override
    public int deleteLibLossByLossIds(Long[] lossIds)
    {
        return libLossMapper.deleteLibLossByLossIds(lossIds);
    }

    /**
     * 删除图书遗失订单信息
     *
     * @param lossId 图书遗失订单主键
     * @return 结果
     */
    @Override
    public int deleteLibLossByLossId(Long lossId)
    {
        return libLossMapper.deleteLibLossByLossId(lossId);
    }

    @Override
    public int cancel(LibLoss libLoss)
    {
        libLoss.setIsDelete("1");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<LibLoss>list=libLossMapper.selectLibLossList(libLoss);
        LibBorrow libBorrow=new LibBorrow();
        List<LibBorrow> list1=libBorrowMapper.selectLibBorrowAll(libBorrow);
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list1.size();j++){
                if(list.get(i).getBorrowId().equals(list1.get(j).getBorrowId())){
                    libBorrow.setBorrowId(list1.get(j).getBorrowId());
                    libBorrow.setIsLoss("0");
                    Date expireTime=list1.get(j).getExpireTime();
                    System.out.println("借书表的图书Id"+list1.get(j).getBookId());
                    System.out.println("借书表的用户Id"+list1.get(j).getUserId());
                    System.out.println("借书表的过期时间"+expireTime);
                    System.out.println("当前时间"+date);
                    System.out.println("遗失表的图书Id"+list.get(i).getBookId());
                    System.out.println("遗失表的用户Id"+list.get(i).getUserId());
                    if ((sdf.format(date)).compareTo(sdf.format(expireTime)) > 0) {
                        libBorrow.setBookFlag("1");
                        System.out.println("===============================");
                        System.out.println("当前时间");
                        System.out.println(sdf.format(date));
                        System.out.println("图书到期");
                    }else{
                        libBorrow.setBookFlag("0");
                        System.out.println("图书未到期");
                        System.out.println("===============================");
                    }
                    libBorrowMapper.updateLibBorrow(libBorrow);
                }
            }

        }
        return libLossMapper.updateLibLoss(libLoss);
    }

}
