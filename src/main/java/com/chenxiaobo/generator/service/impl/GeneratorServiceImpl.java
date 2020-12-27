package com.chenxiaobo.generator.service.impl;

import com.chenxiaobo.generator.mapper.GeneratorMapper;
import com.chenxiaobo.generator.domain.VO.ColumnVO;
import com.chenxiaobo.generator.domain.VO.TableVO;
import com.chenxiaobo.generator.domain.response.PageResponse;
import com.chenxiaobo.generator.service.IGeneratorService;
import com.chenxiaobo.generator.utils.GeneratorUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @Title: GeneratorServiceImpl
 * @Description: TODO
 * @Author <a href="mailto:mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-06-05 17:05
 * @Version V1.0
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements IGeneratorService {

    @Autowired
    private GeneratorMapper generatorMapper;

    @Override
    public PageResponse<TableVO> getTableList(Integer pageNum, Integer pageSize, String tableName) {
        Page page = PageHelper.startPage(pageNum,pageSize);
        List<TableVO> articles = generatorMapper.getTableList(tableName);
        PageResponse response = new PageResponse();
        BeanUtils.copyProperties(page,response);
        response.setRows(articles);
        return response;
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for(String tableName : tableNames){
            //查询表信息
            TableVO table = generatorMapper.getTableByTableName(tableName);
            //查询列信息
            List<ColumnVO> columns = generatorMapper.getColumnsByTableName(tableName);
            //生成代码
            GeneratorUtil.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

}