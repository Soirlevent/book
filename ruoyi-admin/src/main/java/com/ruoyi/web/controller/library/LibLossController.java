package com.ruoyi.web.controller.library;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.library.domain.LibBorrow;
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
import com.ruoyi.library.domain.LibLoss;
import com.ruoyi.library.service.ILibLossService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书遗失订单Controller
 *
 * @author 青栀无梦
 * @date 2022-04-25
 */
@RestController
@RequestMapping("/loss/order")
public class LibLossController extends BaseController
{
    @Autowired
    private ILibLossService libLossService;

    /**
     * 查询图书遗失订单列表
     */
    @PreAuthorize("@ss.hasPermi('loss:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibLoss libLoss)
    {
        startPage();
        List<LibLoss> list = libLossService.selectLibLossList(libLoss);
        return getDataTable(list);
    }

    /**
     * 导出图书遗失订单列表
     */
    @PreAuthorize("@ss.hasPermi('loss:order:export')")
    @Log(title = "图书遗失订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibLoss libLoss)
    {
        List<LibLoss> list = libLossService.selectLibLossList(libLoss);
        ExcelUtil<LibLoss> util = new ExcelUtil<LibLoss>(LibLoss.class);
        util.exportExcel(response, list, "图书遗失订单数据");
    }

    /**
     * 获取图书遗失订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('loss:order:query')")
    @GetMapping(value = "/{lossId}")
    public AjaxResult getInfo(@PathVariable("lossId") Long lossId)
    {
        return AjaxResult.success(libLossService.selectLibLossByLossId(lossId));
    }

    /**
     * 新增图书遗失订单
     */
    @PreAuthorize("@ss.hasPermi('loss:order:add')")
    @Log(title = "图书遗失订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibLoss libLoss)
    {
        return toAjax(libLossService.insertLibLoss(libLoss));
    }

    /**
     * 修改图书遗失订单
     */
    @PreAuthorize("@ss.hasPermi('loss:order:edit')")
    @Log(title = "图书遗失订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibLoss libLoss)
    {
        return toAjax(libLossService.updateLibLoss(libLoss));
    }

    /**
     * 删除图书遗失订单
     */
    @PreAuthorize("@ss.hasPermi('loss:order:remove')")
    @Log(title = "图书遗失订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lossIds}")
    public AjaxResult remove(@PathVariable Long[] lossIds)
    {
        return toAjax(libLossService.deleteLibLossByLossIds(lossIds));
    }

    /**
     * 取消图书遗失订单
     */
    @PreAuthorize("@ss.hasPermi('loss:order:cancel')")
    @Log(title = "图书遗失订单", businessType = BusinessType.DELETE)
    @GetMapping("/cancel/{lossId}")
    public AjaxResult cancel(@PathVariable Long lossId)
    {
        LibLoss libLoss=new LibLoss();
        libLoss.setLossId(lossId);
        return toAjax(libLossService.cancel(libLoss));
    }
}
