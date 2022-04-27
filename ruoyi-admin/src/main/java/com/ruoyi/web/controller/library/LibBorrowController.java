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
import com.ruoyi.library.domain.LibBorrow;
import com.ruoyi.library.service.ILibBorrowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 借阅信息Controller
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/borrow/borrow")
public class LibBorrowController extends BaseController
{
    @Autowired
    private ILibBorrowService libBorrowService;

    /**
     * 查询借阅信息列表
     */
    @PreAuthorize("@ss.hasPermi('borrow:borrow:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibBorrow libBorrow)
    {
        startPage();
        List<LibBorrow> list = libBorrowService.selectLibBorrowList(libBorrow);
        return getDataTable(list);
    }

    /**
     * 导出借阅信息列表
     */
    @PreAuthorize("@ss.hasPermi('borrow:borrow:export')")
    @Log(title = "借阅信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibBorrow libBorrow)
    {
        List<LibBorrow> list = libBorrowService.selectLibBorrowList(libBorrow);
        ExcelUtil<LibBorrow> util = new ExcelUtil<LibBorrow>(LibBorrow.class);
        util.exportExcel(response, list, "借阅信息数据");
    }

    /**
     * 获取借阅信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('borrow:borrow:query')")
    @GetMapping(value = "/{borrowId}")
    public AjaxResult getInfo(@PathVariable("borrowId") Long borrowId)
    {
        return AjaxResult.success(libBorrowService.selectLibBorrowByBorrowId(borrowId));
    }

    /**
     * 新增借阅信息
     */
    @PreAuthorize("@ss.hasPermi('borrow:borrow:add')")
    @Log(title = "借阅信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibBorrow libBorrow)
    {
        return toAjax(libBorrowService.insertLibBorrow(libBorrow));
    }

    /**
     * 修改借阅信息
     */
    @PreAuthorize("@ss.hasPermi('borrow:borrow:edit')")
    @Log(title = "借阅信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibBorrow libBorrow)
    {
        return toAjax(libBorrowService.updateLibBorrow(libBorrow));
    }

    /**
     * 删除借阅信息
     */
    @PreAuthorize("@ss.hasPermi('borrow:borrow:remove')")
    @Log(title = "借阅信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{borrowIds}")
    public AjaxResult remove(@PathVariable Long[] borrowIds)
    {
        return toAjax(libBorrowService.deleteLibBorrowByBorrowIds(borrowIds));
    }

    /**
     * 借阅
     */
    @PreAuthorize("@ss.hasPermi('borrow:book:rent') " )
    @GetMapping("/addBorrowByBookId/{bookId}")
    public AjaxResult addBorrowByBookId(@PathVariable("bookId") Long bookId)
    {
        LibBorrow libBorrow = new LibBorrow();
        libBorrow.setBookId(bookId);
        return toAjax(libBorrowService.insertLibBorrow(libBorrow));
    }

    /**
     * 归还
     */
    @PreAuthorize("@ss.hasPermi('borrow:borrow:return') " )
    @GetMapping("/returnBorrowByBorrowId/{id}")
    public AjaxResult returnBorrowByBorrowId(@PathVariable("id") Long id)
    {
        LibBorrow libBorrow = new LibBorrow();
        libBorrow.setBorrowId(id);
        return toAjax(libBorrowService.returnBook(libBorrow));
    }

    @PreAuthorize("@ss.hasPermi('borrow:borrow:loss')")
    @GetMapping("/updateFlaglossByborrowId/{id}")
    public AjaxResult updateFlaglossByborrowId(@PathVariable("id") Long id)
    {
        LibBorrow libBorrow = new LibBorrow();
        libBorrow.setBorrowId(id);
        return toAjax(libBorrowService.lossBook(libBorrow));
    }

    @PreAuthorize("@ss.hasPermi('borrow:borrow:find')")
    @GetMapping("/updateFlagfindByborrowId/{borrowId}")
    public AjaxResult updateFlagfindByborrowId(@PathVariable("borrowId")Long borrowId)
    {
        LibBorrow libBorrow= new LibBorrow();
        libBorrow.setBorrowId(borrowId);
        return toAjax(libBorrowService.findBook(libBorrow));
    }

    @PreAuthorize("(@ss.hasPermi('borrow:borrow:renew'))")
    @GetMapping("/updateRenewByborrowId/{borrowId}")
    public AjaxResult updateRenewByborrowId(@PathVariable("borrowId") Long borrowId)
    {
        LibBorrow libBorrow = new LibBorrow();
        libBorrow.setBorrowId(borrowId);
        return toAjax(libBorrowService.renewBook(libBorrow));
    }
}
