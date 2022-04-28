package com.ruoyi.library.service;

import java.text.ParseException;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.library.domain.LibBorrow;

/**
 * 借阅信息Service接口
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
public interface ILibBorrowService
{
    /**
     * 查询借阅信息
     *
     * @param borrowId 借阅信息主键
     * @return 借阅信息
     */
    public LibBorrow selectLibBorrowByBorrowId(Long borrowId);

    /**
     * 查询借阅信息列表
     *
     * @param libBorrow 借阅信息
     * @return 借阅信息集合
     */
    public List<LibBorrow> selectLibBorrowList(LibBorrow libBorrow);

    /**
     * 新增借阅信息
     *
     * @param libBorrow 借阅信息
     * @return 结果
     */
    public int insertLibBorrow(LibBorrow libBorrow);

    /**
     * 修改借阅信息
     *
     * @param libBorrow 借阅信息
     * @return 结果
     */
    public int updateLibBorrow(LibBorrow libBorrow);

    /**
     * 批量删除借阅信息
     *
     * @param borrowIds 需要删除的借阅信息主键集合
     * @return 结果
     */
    public int deleteLibBorrowByBorrowIds(Long[] borrowIds);

    /**
     * 删除借阅信息信息
     *
     * @param borrowId 借阅信息主键
     * @return 结果
     */
    public int deleteLibBorrowByBorrowId(Long borrowId);

    /**
     * 归还图书
     * @param libBorrow
     * @return
     * @throws ParseException
     */
    public int returnBook(LibBorrow libBorrow) throws ParseException;

    /**
     * 挂失图书
     * @param libBorrow
     * @return
     */
    public int lossBook(LibBorrow libBorrow);

    /**
     * 取挂图书
     * @param libBorrow
     * @return
     */
    public int findBook(LibBorrow libBorrow);

    /**
     * 续借图书
     * @param libBorrow
     * @return
     */
    public int renewBook(LibBorrow libBorrow);

    /**
     * 定时更新借书表信息
     * @param libBorrow
     * @return
     */
    public List<LibBorrow> selectLibBorrowAll(LibBorrow libBorrow);

    /**
     * 查找历史借阅信息
     * @param libBorrow
     * @return
     */
    public List<LibBorrow> selectLibBorrowlistHistory(LibBorrow libBorrow);
}
