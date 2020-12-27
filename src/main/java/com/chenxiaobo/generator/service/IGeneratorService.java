package com.chenxiaobo.generator.service;

import com.chenxiaobo.generator.domain.VO.TableVO;
import com.chenxiaobo.generator.domain.response.PageResponse;

/**
 * @Title: GeneratorService
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-06-05 17:05
 * @Version V1.0
 */
public interface IGeneratorService {

    PageResponse<TableVO> getTableList(Integer pageNum, Integer pageSize, String tableName);

    byte[] generatorCode(String[] tableNames);
}