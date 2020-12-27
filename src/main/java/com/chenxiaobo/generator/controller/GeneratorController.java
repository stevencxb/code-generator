package com.chenxiaobo.generator.controller;

import com.chenxiaobo.generator.domain.VO.TableVO;
import com.chenxiaobo.generator.domain.response.PageResponse;
import com.chenxiaobo.generator.service.IGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: GeneratorController
 * @Description: 代码生成
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-06-05 16:03
 * @Version V1.0
 */
@Controller
@RequestMapping("/")
public class GeneratorController {

    @Autowired
    private IGeneratorService generatorService;

    @GetMapping
    public String generator(){
        return "generator/generator";
    }

    @ResponseBody
    @GetMapping("/getTableList")
    public PageResponse<TableVO> getTableList(@RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize,
                                              @RequestParam(value = "tableName",required = false) String tableName) {
        return generatorService.getTableList(pageNum,pageSize,tableName);
    }

    @GetMapping("/code/{tableName}")
    public void code(HttpServletRequest request, HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        String[] tableNames = new String[] { tableName };
        byte[] data = generatorService.generatorCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

}
