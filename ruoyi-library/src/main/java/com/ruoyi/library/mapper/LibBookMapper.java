package com.ruoyi.library.mapper;

import java.util.List;
import com.ruoyi.library.domain.LibBook;

/**
 * 书籍管理Mapper接口
 *
 * @author 青栀无梦
 * @date 2022-04-23
 */
public interface LibBookMapper
{
    /**
     * 查询书籍管理
     *
     * @param bookId 书籍管理主键
     * @return 书籍管理
     */
    public LibBook selectLibBookByBookId(Long bookId);

    /**
     * 查询书籍管理列表
     *
     * @param libBook 书籍管理
     * @return 书籍管理集合
     */
    public List<LibBook> selectLibBookList(LibBook libBook);

    /**
     * 新增书籍管理
     *
     * @param libBook 书籍管理
     * @return 结果
     */
    public int insertLibBook(LibBook libBook);

    /**
     * 修改书籍管理
     *
     * @param libBook 书籍管理
     * @return 结果
     */
    public int updateLibBook(LibBook libBook);

    /**
     * 删除书籍管理
     *
     * @param bookId 书籍管理主键
     * @return 结果
     */
    public int deleteLibBookByBookId(Long bookId);

    /**
     * 批量删除书籍管理
     *
     * @param bookIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibBookByBookIds(Long[] bookIds);

    /**
     * 效验书籍编号是否唯一
     * @param bookIsbn
     * @return
     */
    public LibBook checkBookIsbnUnique(String bookIsbn);

    public LibBook selectLibBookByBookIsbn(String bookIsbn);
}
