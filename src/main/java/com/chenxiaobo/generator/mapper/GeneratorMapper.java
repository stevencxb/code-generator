package com.chenxiaobo.generator.mapper;

import com.chenxiaobo.generator.domain.VO.ColumnVO;
import com.chenxiaobo.generator.domain.VO.TableVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: GeneratorMapper
 * @Description: TODO
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-06-05 17:06
 * @Version V1.0
 */
@Repository
public interface GeneratorMapper {

    List<TableVO> getTableList(@Param("tableName") String tableName);

    TableVO getTableByTableName(@Param("tableName") String tableName);

    List<ColumnVO> getColumnsByTableName(@Param("tableName") String tableName);
}
