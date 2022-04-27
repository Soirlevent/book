package com.ruoyi.web.controller.library;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.library.domain.LibLocation;
import com.ruoyi.library.service.ILibLocationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书位置Controller
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/library/location")
public class LibLocationController extends BaseController
{
    @Autowired
    private ILibLocationService libLocationService;

    /**
     * 查询图书位置列表
     */
//    @PreAuthorize("@ss.hasPermi('library:location:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibLocation libLocation)
    {
        startPage();
        List<LibLocation> list = libLocationService.selectLibLocationList(libLocation);
        return getDataTable(list);
    }

    /**
     * 导出图书位置列表
     */
    @PreAuthorize("@ss.hasPermi('library:location:export')")
    @Log(title = "图书位置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibLocation libLocation)
    {
        List<LibLocation> list = libLocationService.selectLibLocationList(libLocation);
        ExcelUtil<LibLocation> util = new ExcelUtil<LibLocation>(LibLocation.class);
        util.exportExcel(response, list, "图书位置数据");
    }

    /**
     * 获取图书位置详细信息
     */
    @PreAuthorize("@ss.hasPermi('library:location:query')")
    @GetMapping(value = "/{locaId}")
    public AjaxResult getInfo(@PathVariable("locaId") Long locaId)
    {
        return AjaxResult.success(libLocationService.selectLibLocationByLocaId(locaId));
    }

    /**
     * 新增图书位置
     */
    @PreAuthorize("@ss.hasPermi('library:location:add')")
    @Log(title = "图书位置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibLocation libLocation)
    {
        if(UserConstants.NOT_UNIQUE.equals(libLocationService.checkLocaNameUnique(libLocation))){
            return AjaxResult.error("新增图书位置'" + libLocation.getLocaName() + "'失败，图书位置名称已存在");
        }
        libLocation.setCreateBy(SecurityUtils.getUsername());
        return toAjax(libLocationService.insertLibLocation(libLocation));
    }

    /**
     * 修改图书位置
     */
    @PreAuthorize("@ss.hasPermi('library:location:edit')")
    @Log(title = "图书位置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibLocation libLocation)
    {
        if(UserConstants.NOT_UNIQUE.equals(libLocationService.checkLocaNameUnique(libLocation))){
            return AjaxResult.error("修改图书位置'" + libLocation.getLocaName() + "'失败，图书位置名称已存在");
        }
        libLocation.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(libLocationService.updateLibLocation(libLocation));
    }

    /**
     * 删除图书位置
     */
    @PreAuthorize("@ss.hasPermi('library:location:remove')")
    @Log(title = "图书位置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{locaIds}")
    public AjaxResult remove(@PathVariable Long[] locaIds)
    {
        return toAjax(libLocationService.deleteLibLocationByLocaIds(locaIds));
    }
}
