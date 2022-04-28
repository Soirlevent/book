package com.ruoyi.quartz.task;

import com.ruoyi.library.domain.LibBorrow;
import com.ruoyi.library.service.ILibBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;


/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    @Autowired
    private ILibBorrowService libBorrowService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void selectLibBorrowAll(){
    	System.out.println("执行查询所有借阅记录");
        LibBorrow libBorrow= new LibBorrow();
    	libBorrowService.selectLibBorrowAll(libBorrow);
    }



}
