package com.ruoyi.web.controller.library;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.library.domain.LibNotice;
import com.ruoyi.library.service.ILibNoticeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 消息管理Controller
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@RestController
@RequestMapping("/message/notice")
@Api(tags={"【消息管理】Controller"})
public class LibNoticeController extends BaseController
{
    @Autowired
    private ILibNoticeService libNoticeService;

    /**
     * 查询消息管理列表
     */
    @PreAuthorize("@ss.hasPermi('message:notice:list')")
    @GetMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" ,dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(LibNotice libNotice) throws ParseException {
        startPage();
        List<LibNotice> list = libNoticeService.selectLibNoticeList(libNotice);
        return getDataTable(list);
    }

    /**
     * 导出消息管理列表
     */
    @PreAuthorize("@ss.hasPermi('message:notice:export')")
    @Log(title = "消息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出消息管理列表")
    public void export(HttpServletResponse response, LibNotice libNotice) throws ParseException {
        List<LibNotice> list = libNoticeService.selectLibNoticeList(libNotice);
        ExcelUtil<LibNotice> util = new ExcelUtil<LibNotice>(LibNotice.class);
        util.exportExcel(response, list, "消息管理数据");
    }

    /**
     * 获取消息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:notice:query')")
    @GetMapping(value = "/{noticeId}")
    @ApiOperation("根据消息编号获取消息管理详细信息")
    public AjaxResult getInfo(
            @ApiParam(name = "noticeId", value = "消息编号", required = true)
            @PathVariable("noticeId") Long noticeId)
    {
        return AjaxResult.success(libNoticeService.selectLibNoticeByNoticeId(noticeId));
    }

    /**
     * 新增消息管理
     */
    @PreAuthorize("@ss.hasPermi('message:notice:add')")
    @Log(title = "消息管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增消息管理")
    public AjaxResult add(@RequestBody LibNotice libNotice)
    {
        return toAjax(libNoticeService.insertLibNotice(libNotice));
    }

    /**
     * 修改消息管理
     */
    @PreAuthorize("@ss.hasPermi('message:notice:edit')")
    @Log(title = "消息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改消息管理")
    public AjaxResult edit(@RequestBody LibNotice libNotice)
    {
        return toAjax(libNoticeService.updateLibNotice(libNotice));
    }

    /**
     * 删除消息管理
     */
    @PreAuthorize("@ss.hasPermi('message:notice:remove')")
    @Log(title = "消息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{noticeIds}")
    @ApiOperation("删除消息管理")
    public AjaxResult remove(
            @ApiParam(name = "noticeIds", value = "消息编号ids{逗号分隔}", required = true)
            @PathVariable Long[] noticeIds)
    {
        return toAjax(libNoticeService.deleteLibNoticeByNoticeIds(noticeIds));
    }
}
