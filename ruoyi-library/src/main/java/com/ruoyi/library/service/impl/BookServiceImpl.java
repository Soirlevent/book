package com.ruoyi.library.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.BookMapper;
import com.ruoyi.library.domain.Book;
import com.ruoyi.library.service.IBookService;

/**
 * 图书借阅Service业务层处理
 * 
 * @author 青栀无梦
 * @date 2022-04-24
 */
@Service
public class BookServiceImpl implements IBookService 
{
    @Autowired
    private BookMapper bookMapper;

    /**
     * 查询图书借阅
     * 
     * @param bookId 图书借阅主键
     * @return 图书借阅
     */
    @Override
    public Book selectBookByBookId(Long bookId)
    {
        return bookMapper.selectBookByBookId(bookId);
    }

    /**
     * 查询图书借阅列表
     * 
     * @param book 图书借阅
     * @return 图书借阅
     */
    @Override
    public List<Book> selectBookList(Book book)
    {
        return bookMapper.selectBookList(book);
    }

    /**
     * 新增图书借阅
     * 
     * @param book 图书借阅
     * @return 结果
     */
    @Override
    public int insertBook(Book book)
    {
        book.setCreateTime(DateUtils.getNowDate());
        return bookMapper.insertBook(book);
    }

    /**
     * 修改图书借阅
     * 
     * @param book 图书借阅
     * @return 结果
     */
    @Override
    public int updateBook(Book book)
    {
        book.setUpdateTime(DateUtils.getNowDate());
        return bookMapper.updateBook(book);
    }

    /**
     * 批量删除图书借阅
     * 
     * @param bookIds 需要删除的图书借阅主键
     * @return 结果
     */
    @Override
    public int deleteBookByBookIds(Long[] bookIds)
    {
        return bookMapper.deleteBookByBookIds(bookIds);
    }

    /**
     * 删除图书借阅信息
     * 
     * @param bookId 图书借阅主键
     * @return 结果
     */
    @Override
    public int deleteBookByBookId(Long bookId)
    {
        return bookMapper.deleteBookByBookId(bookId);
    }
}
