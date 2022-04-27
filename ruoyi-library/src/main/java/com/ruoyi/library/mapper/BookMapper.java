package com.ruoyi.library.mapper;

import java.util.List;
import com.ruoyi.library.domain.Book;

/**
 * 图书借阅Mapper接口
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
public interface BookMapper
{
    /**
     * 查询图书借阅
     *
     * @param bookId 图书借阅主键
     * @return 图书借阅
     */
    public Book selectBookByBookId(Long bookId);

    /**
     * 查询图书借阅列表
     *
     * @param book 图书借阅
     * @return 图书借阅集合
     */
    public List<Book> selectBookList(Book book);

    /**
     * 新增图书借阅
     *
     * @param book 图书借阅
     * @return 结果
     */
    public int insertBook(Book book);

    /**
     * 修改图书借阅
     *
     * @param book 图书借阅
     * @return 结果
     */
    public int updateBook(Book book);

    /**
     * 删除图书借阅
     *
     * @param bookId 图书借阅主键
     * @return 结果
     */
    public int deleteBookByBookId(Long bookId);

    /**
     * 批量删除图书借阅
     *
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookByBookIds(Long[] bookIds);


    public int reduceSurplus(Long bookId);

    public int addSurplus(Long bookId);
}
