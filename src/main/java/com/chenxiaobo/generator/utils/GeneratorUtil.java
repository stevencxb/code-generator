package com.chenxiaobo.generator.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.CollectionUtils;

import com.chenxiaobo.generator.domain.VO.ColumnVO;
import com.chenxiaobo.generator.domain.VO.TableVO;

public class GeneratorUtil {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("templates/generator/Do.java.vm");
        templates.add("templates/generator/KeyDo.java.vm");
        templates.add("templates/generator/Dao.java.vm");
        templates.add("templates/generator/DaoImpl.java.vm");
        templates.add("templates/generator/Example.java.vm");
        templates.add("templates/generator/Mapper.xml.vm");
        templates.add("templates/generator/Mapper.java.vm");
        templates.add("templates/generator/Query.java.vm");
        return templates;
    }

    /**
     * 生成代码
     *
     * @param table
     * @param columns
     * @param zip
     */
    public static void generatorCode(TableVO table, List<ColumnVO> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig();
        //表名转换成Java类名
        String className = tableToJava(table.getTableName());
        table.setClassName(className);
        table.setClassname(StringUtils.uncapitalize(className));

        //列信息
        table.setColumns(new ArrayList<>());
        table.setNoPkColumns(new ArrayList<>());
        table.setPks(new ArrayList<>());
        for (ColumnVO column : columns) {

            //列名转换成Java属性名
            String attrName = columnToJava(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(column.getDataType(), "unknowType");
            column.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.getColumnKey())) {
                table.getPks()
                    .add(column);
            } else {
                table.getNoPkColumns()
                    .add(column);
            }

            table.getColumns().add(column);
        }


        //没主键，则第一个字段为主键
        if (CollectionUtils.isEmpty(table.getPks())){
            table.getPks().add(table.getColumns()
                .get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", table.getTableName());
        map.put("comments", table.getTableComment());
        map.put("pks", table.getPks());
        map.put("noPkColumns", table.getNoPkColumns());
        map.put("className", table.getClassName());
        map.put("classname", table.getClassname());
        map.put("pathName", config.getString("package")
            .substring(config.getString("package")
                .lastIndexOf(".") + 1));
        map.put("columns", columns);
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtil.formatDateToString(new Date(), DateUtil.DATE_FORMAT_FULL));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, table.getClassname(), table.getClassName(),
                    config.getString("package"))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[] {'_'})
            .replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName) {
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname, String className, String packageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        //String modulesname=config.getString("packageName");
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("/Query.java.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "query" + File.separator + className
                + "Query.java";
        }
        if (template.contains("/Do.java.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "model" + File.separator + className
                + "Do.java";
        }
        if (template.contains("/Example.java.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "model" + File.separator + className
                + "Example.java";
        }

        if (template.contains("/KeyDo.java.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "model" + File.separator + className
                + "KeyDo.java";
        }

        if (template.contains("/Dao.java.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "intf" + File.separator + className
                + "Dao.java";
        }

        if (template.contains("/DaoImpl.java.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "impl" + File.separator + className
                + "DaoImpl.java";
        }

        if (template.contains("/Mapper.java.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "mapper" + File.separator + className
                + "Mapper.java";
        }

        if (template.contains("/Mapper.xml.vm")) {
            return packagePath + "dal" + File.separator + "dao" + File.separator + "sqlmap"+ File.separator + packageName
                + File.separator + className + "Mapper.xml";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }



        if (template.contains("list.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
                + File.separator + classname + File.separator + classname + ".html";
            //				+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".html";
        }
        if (template.contains("add.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
                + File.separator + classname + File.separator + "add.html";
        }
        if (template.contains("edit.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + packageName
                + File.separator + classname + File.separator + "edit.html";
        }

        if (template.contains("list.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
                + File.separator + "appjs" + File.separator + packageName + File.separator + classname + File.separator
                + classname + ".js";
            //		+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }
        if (template.contains("add.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
                + File.separator + "appjs" + File.separator + packageName + File.separator + classname + File.separator
                + "add.js";
        }
        if (template.contains("edit.js.vm")) {
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js"
                + File.separator + "appjs" + File.separator + packageName + File.separator + classname + File.separator
                + "edit.js";
        }

        //		if(template.contains("menu.sql.vm")){
        //			return className.toLowerCase() + "_menu.sql";
        //		}

        return null;
    }
}
