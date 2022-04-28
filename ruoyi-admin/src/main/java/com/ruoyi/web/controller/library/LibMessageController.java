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
import com.ruoyi.library.domain.LibMessage;
import com.ruoyi.library.service.ILibMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息通知Controller
 *
 * @author 青栀无梦
 * @date 2022-04-28
 */
@RestController
@RequestMapping("/news/message")
public class LibMessageController extends BaseController
{
    @Autowired
    private ILibMessageService libMessageService;

    /**
     * 查询消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('news:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(LibMessage libMessage)
    {
        startPage();
        List<LibMessage> list = libMessageService.selectLibMessageList(libMessage);
        return getDataTable(list);
    }

    /**
     * 导出消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('news:message:export')")
    @Log(title = "消息通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LibMessage libMessage)
    {
        List<LibMessage> list = libMessageService.selectLibMessageList(libMessage);
        ExcelUtil<LibMessage> util = new ExcelUtil<LibMessage>(LibMessage.class);
        util.exportExcel(response, list, "消息通知数据");
    }

    /**
     * 获取消息通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('news:message:query')")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId)
    {
        return AjaxResult.success(libMessageService.selectLibMessageByMessageId(messageId));
    }

    /**
     * 新增消息通知
     */
    @PreAuthorize("@ss.hasPermi('news:message:add')")
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LibMessage libMessage)
    {
        return toAjax(libMessageService.insertLibMessage(libMessage));
    }

    /**
     * 修改消息通知
     */
    @PreAuthorize("@ss.hasPermi('news:message:edit')")
    @Log(title = "消息通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LibMessage libMessage)
    {
        return toAjax(libMessageService.updateLibMessage(libMessage));
    }

    /**
     * 删除消息通知
     */
    @PreAuthorize("@ss.hasPermi('news:message:remove')")
    @Log(title = "消息通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds)
    {
        return toAjax(libMessageService.deleteLibMessageByMessageIds(messageIds));
    }
}
