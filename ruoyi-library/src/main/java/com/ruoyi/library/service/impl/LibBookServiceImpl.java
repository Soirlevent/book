package com.ruoyi.library.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.LibBookMapper;
import com.ruoyi.library.domain.LibBook;
import com.ruoyi.library.service.ILibBookService;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

/**
 * 书籍管理Service业务层处理
 *
 * @author 青栀无梦
 * @date 2022-04-23
 */
@Service
public class LibBookServiceImpl implements ILibBookService
{
    @Autowired
    private LibBookMapper libBookMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询书籍管理
     *
     * @param bookId 书籍管理主键
     * @return 书籍管理
     */
    @Override
    public LibBook selectLibBookByBookId(Long bookId)
    {
        return libBookMapper.selectLibBookByBookId(bookId);
    }

    /**
     * 查询书籍管理列表
     *
     * @param libBook 书籍管理
     * @return 书籍管理
     */
    @Override
    public List<LibBook> selectLibBookList(LibBook libBook)
    {
        return libBookMapper.selectLibBookList(libBook);
    }

    /**
     * 新增书籍管理
     *
     * @param libBook 书籍管理
     * @return 结果
     */
    @Override
    public int insertLibBook(LibBook libBook)
    {
        libBook.setCreateTime(DateUtils.getNowDate());
        return libBookMapper.insertLibBook(libBook);
    }

    /**
     * 修改书籍管理
     *
     * @param libBook 书籍管理
     * @return 结果
     */
    @Override
    public int updateLibBook(LibBook libBook)
    {
        libBook.setUpdateTime(DateUtils.getNowDate());
        return libBookMapper.updateLibBook(libBook);
    }

    /**
     * 批量删除书籍管理
     *
     * @param bookIds 需要删除的书籍管理主键
     * @return 结果
     */
    @Override
    public int deleteLibBookByBookIds(Long[] bookIds)
    {
        return libBookMapper.deleteLibBookByBookIds(bookIds);
    }

    /**
     * 删除书籍管理信息
     *
     * @param bookId 书籍管理主键
     * @return 结果
     */
    @Override
    public int deleteLibBookByBookId(Long bookId)
    {
        return libBookMapper.deleteLibBookByBookId(bookId);
    }

    /**
     * 效验图书编号是否唯一
     * @param libBook
     * @return
     */
    public String checkBookIsbnUnique(LibBook libBook){
        Long bookId= StringUtils.isNull(libBook.getBookId())?-1L:libBook.getBookId();
        LibBook info=libBookMapper.checkBookIsbnUnique(libBook.getBookIsbn());
        if(StringUtils.isNotNull(info)&&info.getBookId().longValue()!=bookId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    public String importLibBook(List<LibBook> bookList, boolean updateSupport, String operName)
    {
        if (StringUtils.isNull(bookList) || bookList.size() == 0)
        {
            throw new ServiceException("导入书籍管理数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (LibBook book : bookList)
        {
            try {
                LibBook libBook = libBookMapper.selectLibBookByBookIsbn(book.getBookIsbn());
                if (StringUtils.isNull(libBook))
                {
                    BeanValidators.validateWithException(validator, book);
                    book.setCreateBy(operName);
                    this.insertLibBook(book);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、图书 " + book.getBookIsbn() + " 导入成功");
                }
                else if (updateSupport)
                {
                    BeanValidators.validateWithException(validator, book);
                    book.setUpdateBy(operName);
                    book.setUpdateTime(new Date());
                    this.updateLibBook(book);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、图书 " + book.getBookIsbn() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、图书 " + book.getBookIsbn() + " 已存在");
                }
            }
            catch (ConstraintViolationException e)
            {
                failureNum++;
                String msg="<br/>"+failureNum+"、图书"+book.getBookIsbn()+"导入失败";
                failureMsg.append(msg+e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
