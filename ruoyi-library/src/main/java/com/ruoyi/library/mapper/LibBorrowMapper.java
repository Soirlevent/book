package com.ruoyi.library.mapper;

import java.util.List;
import com.ruoyi.library.domain.LibBorrow;

/**
 * 借阅信息Mapper接口
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
public interface LibBorrowMapper
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

    public List<LibBorrow> selectLibBorrowAll(LibBorrow libBorrow);
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
     * 删除借阅信息
     *
     * @param borrowId 借阅信息主键
     * @return 结果
     */
    public int deleteLibBorrowByBorrowId(Long borrowId);

    /**
     * 批量删除借阅信息
     *
     * @param borrowIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLibBorrowByBorrowIds(Long[] borrowIds);

    public List<LibBorrow> selectLibBorrowlistHistory(LibBorrow libBorrow);
}
