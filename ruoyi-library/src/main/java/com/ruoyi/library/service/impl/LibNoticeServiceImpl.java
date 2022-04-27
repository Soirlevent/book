package com.ruoyi.library.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.library.domain.LibNotice;
import com.ruoyi.library.mapper.LibNoticeMapper;
import com.ruoyi.library.service.ILibNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 消息管理Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Service
public class LibNoticeServiceImpl implements ILibNoticeService
{
    @Autowired
    private LibNoticeMapper libNoticeMapper;

    /**
     * 查询消息管理
     *
     * @param noticeId 消息管理主键
     * @return 消息管理
     */
    @Override
    public LibNotice selectLibNoticeByNoticeId(Long noticeId)
    {
        return libNoticeMapper.selectLibNoticeByNoticeId(noticeId);
    }

    /**
     * 查询消息管理列表
     *
     * @param libNotice 消息管理
     * @return 消息管理
     */
    @Override
    public List<LibNotice> selectLibNoticeList(LibNotice libNotice) throws ParseException {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<LibNotice> list=libNoticeMapper.selectLibNoticeList(libNotice);
        for (int i = 0; i < list.size(); i++) {
            if (sdf.format(date).compareTo(sdf.format(list.get(i).getEndTime()))>0){
                list.get(i).setStatus("2");
                System.out.println("结束时间小于当前时间");
                System.out.println(list.get(i).getEndTime());
                System.out.println("结束时间："+list.get(i).getEndTime());
                System.out.println("当前时间："+date);
                long time1=sdf.parse(sdf.format(list.get(i).getEndTime())).getTime();
                long time2=sdf.parse(sdf.format(date)).getTime();
                int result=(int) ((time1 - time2) / (1000 * 3600 * 24));
                System.out.println("相差"+result+"天");
                System.out.println("==========================");
                libNoticeMapper.updateLibNotice(list.get(i));
            }else if(sdf.format(date).compareTo(sdf.format(list.get(i).getStartTime()))<0){
                list.get(i).setStatus("0");
                System.out.println("开始时间小于当前时间");
                libNoticeMapper.updateLibNotice(list.get(i));
            }
            else {
                list.get(i).setStatus("1");
                System.out.println("结束时间大于当前时间");
                libNoticeMapper.updateLibNotice(list.get(i));
            }
        }
        return libNoticeMapper.selectLibNoticeList(libNotice);
    }

    /**
     * 新增消息管理
     *
     * @param libNotice 消息管理
     * @return 结果
     */
    @Override
    public int insertLibNotice(LibNotice libNotice)
    {
        libNotice.setCreateTime(DateUtils.getNowDate());
        return libNoticeMapper.insertLibNotice(libNotice);
    }

    /**
     * 修改消息管理
     *
     * @param libNotice 消息管理
     * @return 结果
     */
    @Override
    public int updateLibNotice(LibNotice libNotice)
    {
        libNotice.setUpdateTime(DateUtils.getNowDate());
        return libNoticeMapper.updateLibNotice(libNotice);
    }

    /**
     * 批量删除消息管理
     *
     * @param noticeIds 需要删除的消息管理主键
     * @return 结果
     */
    @Override
    public int deleteLibNoticeByNoticeIds(Long[] noticeIds)
    {
        return libNoticeMapper.deleteLibNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除消息管理信息
     *
     * @param noticeId 消息管理主键
     * @return 结果
     */
    @Override
    public int deleteLibNoticeByNoticeId(Long noticeId)
    {
        return libNoticeMapper.deleteLibNoticeByNoticeId(noticeId);
    }
}
