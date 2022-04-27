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
import com.ruoyi.library.domain.LibOver;
import com.ruoyi.library.service.ILibOverService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书逾期Controller
 *
 * @author 青栀无梦
 * @date 2022-04-25
 */
@RestController
@RequestMapping("/over/order")
public class LibOverController extends BaseController
{
    @Autowired
    private ILibOverService libOverService;

    /**
     * 查询图书逾期列表
     */
    @PreAuthorize("@ss.hasPermi('over:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibOver libOver)
    {
        startPage();
        List<LibOver> list = libOverService.selectLibOverList(libOver);
        return getDataTable(list);
    }

    /**
     * 导出图书逾期列表
     */
    @PreAuthorize("@ss.hasPermi('over:order:export')")
    @Log(title = "图书逾期", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibOver libOver)
    {
        List<LibOver> list = libOverService.selectLibOverList(libOver);
        ExcelUtil<LibOver> util = new ExcelUtil<LibOver>(LibOver.class);
        util.exportExcel(response, list, "图书逾期数据");
    }

    /**
     * 获取图书逾期详细信息
     */
    @PreAuthorize("@ss.hasPermi('over:order:query')")
    @GetMapping(value = "/{overId}")
    public AjaxResult getInfo(@PathVariable("overId") Long overId)
    {
        return AjaxResult.success(libOverService.selectLibOverByOverId(overId));
    }

    /**
     * 新增图书逾期
     */
    @PreAuthorize("@ss.hasPermi('over:order:add')")
    @Log(title = "图书逾期", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibOver libOver)
    {
        return toAjax(libOverService.insertLibOver(libOver));
    }

    /**
     * 修改图书逾期
     */
    @PreAuthorize("@ss.hasPermi('over:order:edit')")
    @Log(title = "图书逾期", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibOver libOver)
    {
        return toAjax(libOverService.updateLibOver(libOver));
    }

    /**
     * 删除图书逾期
     */
    @PreAuthorize("@ss.hasPermi('over:order:remove')")
    @Log(title = "图书逾期", businessType = BusinessType.DELETE)
	@DeleteMapping("/{overIds}")
    public AjaxResult remove(@PathVariable Long[] overIds)
    {
        return toAjax(libOverService.deleteLibOverByOverIds(overIds));
    }
}
