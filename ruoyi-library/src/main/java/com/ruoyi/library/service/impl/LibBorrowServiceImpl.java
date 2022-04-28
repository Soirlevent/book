package com.ruoyi.library.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.library.domain.*;
import com.ruoyi.library.mapper.*;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.service.ILibBorrowService;


/**
 * 借阅信息Service业务层处理
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@Service
public class LibBorrowServiceImpl implements ILibBorrowService
{
    @Autowired
    private LibBorrowMapper libBorrowMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private LibLossMapper libLossMapper;

    @Autowired
    private LibOverMapper libOverMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private LibMessageMapper libMessageMapper;

    /**
     * 查询借阅信息
     *
     * @param borrowId 借阅信息主键
     * @return 借阅信息
     */
    @Override
    public LibBorrow selectLibBorrowByBorrowId(Long borrowId)
    {
        return libBorrowMapper.selectLibBorrowByBorrowId(borrowId);
    }

    /**
     * 查询借阅信息列表
     *
     * @param libBorrow 借阅信息
     * @return 借阅信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "us")
    public List<LibBorrow> selectLibBorrowList(LibBorrow libBorrow)
    {
        return libBorrowMapper.selectLibBorrowList(libBorrow);
    }

    /**
     * 新增借阅信息
     *
     * @param libBorrow 借阅信息
     * @return 结果
     */
    @Override
    public int insertLibBorrow(LibBorrow libBorrow)
    {
        Long userId= SecurityUtils.getUserId();
        libBorrow.setUserId(userId);
        libBorrow.setBookFlag("0");
        libBorrow.setBorrowTime(DateUtils.getNowDate());
        libBorrow.setIsLoss("0");
        libBorrow.setIsRenew("0");
        libBorrow.setIsLeft("0");
        libBorrow.setIsDelete("0");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DateUtils.getNowDate());
        }catch (Exception e){
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH,30);
        libBorrow.setExpireTime(calendar.getTime());
        int i=libBorrowMapper.insertLibBorrow(libBorrow);
        if (i>0){
            bookMapper.reduceSurplus(libBorrow.getBookId());
            System.out.println("借书成功");
        }
        return i;
    }

    /**
     * 修改借阅信息
     *
     * @param libBorrow 借阅信息
     * @return 结果
     */
    @Override
    public int updateLibBorrow(LibBorrow libBorrow)
    {
        return libBorrowMapper.updateLibBorrow(libBorrow);
    }

    /**
     * 批量删除借阅信息
     *
     * @param borrowIds 需要删除的借阅信息主键
     * @return 结果
     */
    @Override
    public int deleteLibBorrowByBorrowIds(Long[] borrowIds)
    {
        return libBorrowMapper.deleteLibBorrowByBorrowIds(borrowIds);
    }

    /**
     * 删除借阅信息信息
     *
     * @param borrowId 借阅信息主键
     * @return 结果
     */
    @Override
    public int deleteLibBorrowByBorrowId(Long borrowId)
    {
        return libBorrowMapper.deleteLibBorrowByBorrowId(borrowId);
    }

    @Override
    public int returnBook(LibBorrow libBorrow) throws ParseException {
        libBorrow.setIsLeft("1");
        Date date = new Date();
        libBorrow.setReturnTime(date);
        LibBorrow libBorrow1=libBorrowMapper.selectLibBorrowByBorrowId(libBorrow.getBorrowId());
        if (libBorrow1.getBookFlag().equals("1")){
            Book book=bookMapper.selectBookByBookId(libBorrow1.getBookId());
            Date expireTime = libBorrow1.getExpireTime();
            Long day=calculateDay(expireTime,date);
            LibOver due=new LibOver();
            due.setBookId(libBorrow1.getBookId());
            due.setUserId(libBorrow1.getUserId());
            due.setBookPrice(book.getBookPrice());
            due.setOverData(day);
            double money=book.getBookPrice().doubleValue()*day*0.02;
            BigDecimal overMoney=BigDecimal.valueOf(money);
            due.setOverMoney(overMoney);
            due.setStatus("0");
            due.setIsDelet("0");
            due.setCreateTime(date);
            libOverMapper.insertLibOver(due);
        }
        libBorrow.setReturnTime(DateUtils.getNowDate());
        int i=libBorrowMapper.updateLibBorrow(libBorrow);
        if (i>0){
            LibBorrow borrow=libBorrowMapper.selectLibBorrowByBorrowId(libBorrow.getBorrowId());
            bookMapper.addSurplus(borrow.getBookId());
            System.out.println("还书成功");
        }
        return i;
    }

    private static Long calculateDay(Date expireTime, Date date) throws ParseException {
        Long result = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long time1=sdf.parse(sdf.format(expireTime)).getTime();
        long time2=sdf.parse(sdf.format(date)).getTime();
         result=(Long) ((time2 - time1) / (1000 * 3600 * 24));
        return result;
    }

    @Override
    public int lossBook(LibBorrow libBorrow)
    {
        libBorrow.setIsLoss("1");
        LibBorrow libBorrow1 = libBorrowMapper.selectLibBorrowByBorrowId(libBorrow.getBorrowId());
        Book book = bookMapper.selectBookByBookId(libBorrow1.getBookId());
        LibLoss libLoss = new LibLoss();
        libLoss.setBorrowId(libBorrow.getBorrowId());
        libLoss.setUserId(libBorrow1.getUserId());
        libLoss.setBookId(book.getBookId());
        libLoss.setLossMoney(book.getBookPrice());
        libLoss.setStatus("0");
        libLoss.setIsDelete("0");
        libLoss.setCreateTime(DateUtils.getNowDate());
        libLossMapper.insertLibLoss(libLoss);
        int i=libBorrowMapper.updateLibBorrow(libBorrow);
        return i;
    }


//    @Override
//    public int lossBook(LibBorrow libBorrow)
//    {
//        libBorrow.setIsLoss("1");
//        List<LibBorrow> list=libBorrowMapper.selectLibBorrowList(libBorrow);
//        Book book = new Book();
//        List<Book> books=bookMapper.selectBookList(book);
//        for(int i=0; i<list.size(); i++) {
//            LibLoss libLoss = new LibLoss();
//            libLoss.setBookId(list.get(i).getBookId());
//            libLoss.setUserId(list.get(i).getUserId());
//            libLoss.setBorrowId(list.get(i).getBorrowId());
//            for (int j = 0; j < books.size(); j++) {
//                if(books.get(j).getBookId().equals(list.get(i).getBookId())){
//                    libLoss.setLossMoney(books.get(j).getBookPrice());
//                }
//            }
//            libLoss.setIsDelete("0");
//            libLoss.setStatus("0");
//            libLoss.setCreateTime(DateUtils.getNowDate());
//            System.out.println("===========================");
//            System.out.println(libLoss);System.out.println("===========================");
//            System.out.println("图书编号："+list.get(i).getBookId());
//            System.out.println("用户编号："+list.get(i).getUserId());
//            System.out.println("图书挂失成功");
//            System.out.println("===========================");
//            libLossMapper.insertLibLoss(libLoss);
//        }
//        int i=libBorrowMapper.updateLibBorrow(libBorrow);
//        return i;
//    }

    @Override
    public int findBook(LibBorrow libBorrow)
    {
        Date date=new Date();
        libBorrow.setIsLoss("0");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<LibBorrow> list=libBorrowMapper.selectLibBorrowList(libBorrow);
        for (int i=0; i<list.size();i++){
            if(libBorrow.getBorrowId()==list.get(i).getBorrowId()){
                Date expireTime=list.get(i).getExpireTime();
                if ((sdf.format(date)).compareTo(sdf.format(expireTime)) > 0) {
                    libBorrow.setBookFlag("1");
                }else{
                    libBorrow.setBookFlag("0");
                }
            }
        }
        int i=libBorrowMapper.updateLibBorrow(libBorrow);
        return i;
    }

    @Override
    public int renewBook(LibBorrow libBorrow)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<LibBorrow> list=libBorrowMapper.selectLibBorrowList(libBorrow);
        for (int i=0; i<list.size();i++){
            if(libBorrow.getBorrowId()==list.get(i).getBorrowId())
            {
                Date expireTime=list.get(i).getExpireTime();
                Date newDate=addDate(expireTime,15);
                System.out.println("=======================");
                System.out.println("更新后的时间为："+newDate);
                System.out.println("更新后的时间为："+sdf.format(newDate));
                System.out.println("=======================");
                libBorrow.setExpireTime(newDate);
                libBorrow.setIsRenew("1");
            }
        }
        int i= libBorrowMapper.updateLibBorrow(libBorrow);
        return i;
    }

    private static Date addDate(Date expireTime, long day) {
        long time=expireTime.getTime();
        day=day*24*60*60*1000;
        time+=day;
        return new Date(time);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "us")
    public List<LibBorrow> selectLibBorrowlistHistory(LibBorrow libBorrow)
    {
        return libBorrowMapper.selectLibBorrowlistHistory(libBorrow);
    }

    @Override
    public List<LibBorrow> selectLibBorrowAll(LibBorrow libBorrow)
    {
        Date date=new Date();
        List<LibBorrow> list=libBorrowMapper.selectLibBorrowAll(libBorrow);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,3);
        Date newDate=calendar.getTime();
        for (int i=0; i<list.size();i++){
                Date expireTime=list.get(i).getExpireTime();
                LibMessage libMessage=new LibMessage();
                if (((sdf.format(date)).compareTo(sdf.format(expireTime)) >= 0)&&(list.get(i).getBookFlag().equals("0"))){
                    LibBorrow libBorrow1=new LibBorrow();
                    libBorrow1.setBorrowId(list.get(i).getBorrowId());
                    System.out.println("=======================");
                    System.out.println("更新前的图书状态为："+list.get(i).getBookFlag());
                    System.out.println("您的图书已经到期，请及时归还！");
                    System.out.println("=======================");
                    libBorrow1.setBookFlag("1");
                    libBorrowMapper.updateLibBorrow(libBorrow1);
                    libMessage.setUserId(list.get(i).getUserId());
                    libMessage.setBookId(list.get(i).getBookId());
                    libMessage.setMessageTitle("图书逾期期提醒");
                    libMessage.setMessageContent("您的图书已经到期，请及时归还！");
                    libMessage.setIsRead("0");
                    libMessage.setIsDelete("0");
                    libMessage.setCreateTime(new Date());
                    libMessageMapper.insertLibMessage(libMessage);
                }
                System.out.println("=======================");
                System.out.println("更新后的时间为："+newDate);
                System.out.println("更新后的时间为："+sdf.format(newDate));
                System.out.println("=======================");
                if(sdf.format(newDate).compareTo(sdf.format(expireTime))==0&&list.get(i).getBookFlag().equals("0")){
                    SysUser sysUser= userMapper.selectUserById(list.get(i).getUserId());
                    Book book=bookMapper.selectBookByBookId(list.get(i).getBookId());
                    System.out.println("=======================");
                    System.out.println("到期图书为："+book.getBookName());
                    System.out.println("借书人邮箱为："+sysUser.getEmail());
                    System.out.println("您的图书将三天后到期，请及时归还或者续借！");
                    System.out.println("=======================");
                    libMessage.setUserId(list.get(i).getUserId());
                    libMessage.setBookId(list.get(i).getBookId());
                    libMessage.setMessageTitle("图书到期提醒");
                    libMessage.setMessageContent("您的图书将三天后到期，请及时归还或者续借！");
                    libMessage.setIsRead("0");
                    libMessage.setIsDelete("0");
                    libMessage.setCreateTime(new Date());
                    libMessageMapper.insertLibMessage(libMessage);
                }
        }
        System.out.println("===========================");
        System.out.println(list);
        System.out.println("===========================");
        return list;
    }
}
