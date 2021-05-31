package com.chenxiaobo.generator.domain.VO;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @Title: TableVO
 * @Description: 数据库表
 * @Author <a href="mailto:chenxb1993@126.com">陈晓博</a>
 * @Date 2019-06-05 17:16
 * @Version V1.0
 */
@Data
public class TableVO {

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 表引擎
     */
    private String engine;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 创建时间
     */
    private Date createTime;
    //
    // /**
    //  * 表的主键
    //  */
    // private ColumnVO pk;

    /**
     * 表的主键
     */
    private List<ColumnVO> pks;

    /**
     * 表的列(不含主键)
     */
    private List<ColumnVO> noPkColumns;

    /**
     * 表的列(含主键)
     */
    private List<ColumnVO> columns;

    /**
     * 类名(第一个字母大写)，如：sys_user => SysUser
     */
    private String className;

    /**
     * 类名(第一个字母小写)，如：sys_user => sysUser
     */
    private String classname;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ColumnVO> getPks() {
        return pks;
    }

    public void setPks(List<ColumnVO> pks) {
        this.pks = pks;
    }

    public List<ColumnVO> getNoPkColumns() {
        return noPkColumns;
    }

    public void setNoPkColumns(List<ColumnVO> noPkColumns) {
        this.noPkColumns = noPkColumns;
    }

    public List<ColumnVO> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnVO> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
