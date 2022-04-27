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
import com.ruoyi.library.domain.LibType;
import com.ruoyi.library.service.ILibTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书分类Controller
 *
 * @author 青栀无梦
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/library/type")
public class LibTypeController extends BaseController
{
    @Autowired
    private ILibTypeService libTypeService;

    /**
     * 查询图书分类列表
     */
//    @PreAuthorize("@ss.hasPermi('library:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibType libType)
    {
        startPage();
        List<LibType> list = libTypeService.selectLibTypeList(libType);
        return getDataTable(list);
    }

    /**
     * 导出图书分类列表
     */
    @PreAuthorize("@ss.hasPermi('library:type:export')")
    @Log(title = "图书分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibType libType)
    {
        List<LibType> list = libTypeService.selectLibTypeList(libType);
        ExcelUtil<LibType> util = new ExcelUtil<LibType>(LibType.class);
        util.exportExcel(response, list, "图书分类数据");
    }

    /**
     * 获取图书分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('library:type:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId)
    {
        return AjaxResult.success(libTypeService.selectLibTypeByTypeId(typeId));
    }

    /**
     * 新增图书分类
     */
    @PreAuthorize("@ss.hasPermi('library:type:add')")
    @Log(title = "图书分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibType libType)
    {
        if (UserConstants.NOT_UNIQUE.equals(libTypeService.checkTypeNameUnique(libType))){
            return AjaxResult.error("新增图书分类'" + libType.getTypeName() + "'失败，图书分类名称已存在");
        }
        libType.setCreateBy(SecurityUtils.getUsername());
        return toAjax(libTypeService.insertLibType(libType));
    }

    /**
     * 修改图书分类
     */
    @PreAuthorize("@ss.hasPermi('library:type:edit')")
    @Log(title = "图书分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibType libType)
    {
        if (UserConstants.NOT_UNIQUE.equals(libTypeService.checkTypeNameUnique(libType))){
            return AjaxResult.error("修改图书分类'" + libType.getTypeName() + "'失败，图书分类名称已存在");
        }
        libType.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(libTypeService.updateLibType(libType));
    }

    /**
     * 删除图书分类
     */
    @PreAuthorize("@ss.hasPermi('library:type:remove')")
    @Log(title = "图书分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds)
    {
        return toAjax(libTypeService.deleteLibTypeByTypeIds(typeIds));
    }
}
