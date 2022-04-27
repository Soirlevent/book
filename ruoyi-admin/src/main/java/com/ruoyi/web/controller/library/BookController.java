package com.ruoyi.web.controller.library;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.library.domain.Book;
import com.ruoyi.library.service.IBookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书借阅Controller
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/borrow/book")
public class BookController extends BaseController
{
    @Autowired
    private IBookService bookService;

    /**
     * 查询图书借阅列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Book book)
    {
        startPage();
        List<Book> list = bookService.selectBookList(book);
        return getDataTable(list);
    }

    /**
     * 导出图书借阅列表
     */
    @Log(title = "图书借阅", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Book book)
    {
        List<Book> list = bookService.selectBookList(book);
        ExcelUtil<Book> util = new ExcelUtil<Book>(Book.class);
        util.exportExcel(response, list, "图书借阅数据");
    }

    /**
     * 获取图书借阅详细信息
     */
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") Long bookId)
    {
        return AjaxResult.success(bookService.selectBookByBookId(bookId));
    }

    /**
     * 新增图书借阅
     */
    @PreAuthorize("@ss.hasPermi('borrow:book:add')")
    @Log(title = "图书借阅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Book book)
    {
        return toAjax(bookService.insertBook(book));
    }

    /**
     * 修改图书借阅
     */
    @PreAuthorize("@ss.hasPermi('borrow:book:edit')")
    @Log(title = "图书借阅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Book book)
    {
        return toAjax(bookService.updateBook(book));
    }

    /**
     * 删除图书借阅
     */
    @PreAuthorize("@ss.hasPermi('borrow:book:remove')")
    @Log(title = "图书借阅", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable Long[] bookIds)
    {
        return toAjax(bookService.deleteBookByBookIds(bookIds));
    }
}
