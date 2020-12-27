package com.chenxiaobo.generator.domain.response;

import lombok.Data;

import java.util.List;

/**
 * @Title: PageResponse
 * @Description: 分页信息返回
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2018-06-08 15:47
 * @Version V1.0
 */
@Data
public class PageResponse<T> {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> rows;

}
