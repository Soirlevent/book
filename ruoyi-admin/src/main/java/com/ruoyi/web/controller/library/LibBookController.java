package com.ruoyi.web.controller.library;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.library.domain.LibBook;
import com.ruoyi.library.service.ILibBookService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 书籍管理Controller
 *
 * @author 青栀无梦
 * @date 2022-04-23
 */
@RestController
@RequestMapping("/library/book")
public class LibBookController extends BaseController
{
    @Autowired
    private ILibBookService libBookService;

    /**
     * 查询书籍管理列表
     */
    @PreAuthorize("@ss.hasPermi('library:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibBook libBook)
    {
        startPage();
        List<LibBook> list = libBookService.selectLibBookList(libBook);
        return getDataTable(list);
    }

    /**
     * 导出书籍管理列表
     */
    @PreAuthorize("@ss.hasPermi('library:book:export')")
    @Log(title = "书籍管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibBook libBook)
    {
        List<LibBook> list = libBookService.selectLibBookList(libBook);
        ExcelUtil<LibBook> util = new ExcelUtil<LibBook>(LibBook.class);
        util.exportExcel(response, list, "书籍管理数据");
    }

    /**
     * 获取书籍管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('library:book:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") Long bookId)
    {
        return AjaxResult.success(libBookService.selectLibBookByBookId(bookId));
    }

    /**
     * 新增书籍管理
     */
    @PreAuthorize("@ss.hasPermi('library:book:add')")
    @Log(title = "书籍管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibBook libBook)
    {
        if (UserConstants.NOT_UNIQUE.equals(libBookService.checkBookIsbnUnique(libBook)))
        {
            return AjaxResult.error("新增书籍编号'" +libBook.getBookIsbn()+"的"+libBook.getBookName() + "'失败，书籍编码已存在");
        }
        libBook.setCreateBy(SecurityUtils.getUsername());
        return toAjax(libBookService.insertLibBook(libBook));
    }

    /**
     * 修改书籍管理
     */
    @PreAuthorize("@ss.hasPermi('library:book:edit')")
    @Log(title = "书籍管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibBook libBook)
    {
        if (UserConstants.NOT_UNIQUE.equals(libBookService.checkBookIsbnUnique(libBook))){
            return AjaxResult.error("修改书籍编号'" +libBook.getBookIsbn()+"的"+libBook.getBookName() + "'失败，书籍编码已存在");
        }
        libBook.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(libBookService.updateLibBook(libBook));
    }

    /**
     * 删除书籍管理
     */
    @PreAuthorize("@ss.hasPermi('library:book:remove')")
    @Log(title = "书籍管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable Long[] bookIds)
    {
        return toAjax(libBookService.deleteLibBookByBookIds(bookIds));
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    @ApiOperation("下载图书模板")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<LibBook> util = new ExcelUtil<LibBook>(LibBook.class);
        util.importTemplateExcel(response,"图书数据");
    }

    /**
     * 导入图书
     */
    @PostMapping("/importData")
    @ApiOperation("导入图书")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<LibBook> util = new ExcelUtil<LibBook>(LibBook.class);
        List<LibBook> bookList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = libBookService.importLibBook(bookList, updateSupport,operName);
        return AjaxResult.success(message);
    }
}

