package com.sign.in.controller;

import com.sign.in.common.R;
import com.sign.in.entity.ILog;
import com.sign.in.service.IMTLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/imt/log")
@RequiredArgsConstructor
public class ILogController {

    @GetMapping(value = "/list", name = "操作日志-分页")
    public R list(ILog log) {
        return R.ok();
    }

    @DeleteMapping(value = "/{operIds}", name = "操作日志-删除")
    public R remove(@PathVariable Long[] operIds) {
        return R.ok();
    }

    @DeleteMapping(value = "/clean", name = "操作日志-清空")
    public R clean() {
        return R.ok();
    }

}
