package com.fang.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.hibernate.annotations.Comment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.fang.core.annotation.AttributeInfo;
import com.fang.core.annotation.ClassInfo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

public class GeneralFile {
	private static final String EXECUTETYPE_ALL = "all";
    private static final String EXECUTETYPE_JSP = "jsp";
    private static final String EXECUTETYPE_JAVA = "java";
    private static final String EXECUTETYPE_MYBATIS_XML = "mybatisXML";

    private static String projectPath = "D:/workspace/fang/";//项目的路径
    
    //TODO 修改
    private static String packagePath = "com.fang";//java类的基础包路径
    //以下五个变量 一定要写
    private static String upperEntityName = "";//实体名
    private static String moduleName = "";//模块名称
    private static String entityName = "";//实体名第一个字母小写
    private static String packageName = "";//包名
    private static String tableName = "";//表名

    private static String field[] = {};//字段名
    private static String fieldTitle[] = {};
    private static String fieldType[] = {};
    private static String formType[] = {};
    private static String idName = "";//id字段名
    private static Object options[] = {}; 
    private static Object filedLength[] = {}; //字段长度
    private static Object filedNull[] = {}; //字段是否为null
    //TODO preFuncId自动生成权限功能数据，则修改该值，否则为null
    private static Long preFuncId = null;

    public static void main(String[] args) throws IOException, TemplateException {
    	//TODO 修改
        if(reflect("com.fang.sys.entity.DeptEntity")) {
            execute(true,true,EXECUTETYPE_JSP);
        }
    }
    private static boolean reflect(String className){
        try {
            Class clazz = Class.forName(className);
            ClassInfo classInfo = null;
            Table table = null;
            //处理类上的注解，得到相应的信息
            for(Annotation anno : clazz.getDeclaredAnnotations()) {
                if(anno instanceof ClassInfo) {
                    classInfo = (ClassInfo)anno;
                }
                if(anno instanceof Table) {
                    table = (Table)anno;
                }
            }
            if(classInfo != null || table != null) {
                String packageName_ = classInfo.packageName();
                String moduleName_ = classInfo.moduleName();
                String tableName_ = table.name();
                if("".equals(packageName_.trim()) || "".equals(moduleName_.trim()) || "".equals(tableName_.trim())) {
                    System.out.println("无效的@ClassInfo(packageName)或@AttributeInfo(moduleName)或@Table(name)");
                } else {
                    upperEntityName = clazz.getSimpleName().replace("Entity","");
                    entityName = SystemUtil.firstLetterUpper(upperEntityName);
                    packageName = packageName_;
                    moduleName = moduleName_;
                    tableName = tableName_;
                    //通过遍历来处理每一个通过AttributeInfo注解过的变量
                    List<String> filed_EN = new ArrayList<String>();
                    List<String> filed_Title = new ArrayList<String>();
                    List<String> filed_Comment = new ArrayList<String>();
                    List<String> filed_Type = new ArrayList<String>();
                    List<String> form_Type = new ArrayList<String>();
                    List<Object> options_ = new ArrayList<Object>();
                    List<Object> filed_Length = new ArrayList<Object>();
                    List<Object> filed_null = new ArrayList<Object>();
                    //处理属性
                    Field[] declaredFields = clazz.getDeclaredFields();
                    boolean isValid = true;//如果字段的类型为基本类型，则生成失败
                    for(Field field : declaredFields) {
                        AttributeInfo attributeInfo = null;
                        Column column = null;
                        Comment commentAnno = null;
                        for(Annotation anno : field.getDeclaredAnnotations()) {
                        	if(anno instanceof Column) {
                        		column = (Column)anno;
                            }
                        	if(anno instanceof Id) {
                        		idName = field.getName();
                            }
                            if(anno instanceof AttributeInfo) {
                                attributeInfo = (AttributeInfo)anno;
                            }
                            if(anno instanceof Comment) {
                            	commentAnno = (Comment)anno;
                            }
                        }
                        //如果有column注释才生成
                        if(column != null) {
                        	String fileNameZH = (attributeInfo!=null&&!attributeInfo.name().equals(""))?attributeInfo.name():null;
                        	fileNameZH = (fileNameZH==null&&commentAnno!=null)?commentAnno.value() : fileNameZH;
                        	String comment = commentAnno!=null?commentAnno.value():null;
                            String formType = attributeInfo!=null?attributeInfo.formType():null;
                            String options = attributeInfo!=null?attributeInfo.options():null;
                            String fieldName = field.getName();
                            String fieldType = field.getGenericType().toString();
                            int maxLenth = column.length();
                            Boolean nullable = column.nullable();
                            if(!fieldType.startsWith("class")) {
                                System.out.println("字段 [" + fieldName + "] 类型 [" + fieldType + "] 为基本类型~请使用对象类型~");
                                isValid = false;
                                continue;
                            }
                            filed_EN.add(fieldName);//字段英文名
                            filed_Title.add(fileNameZH);//字段中文名
                            filed_Comment.add(comment);//注释
                            form_Type.add(formType);//表单类型
                            filed_Type.add(field.getGenericType().toString());//字段的类型
                            filed_Length.add(maxLenth);//字段长度
                            filed_null.add(nullable!=null&&nullable==false?1:null);//字段是否可以为null
                            if(options != null && !"".equals(options)){
                            	List<Map<String, String>> list = new ArrayList<Map<String,String>>();
                            	String[] option = options.split(",");
                            	for (String str : option) {
                            		System.out.println(str);
									String[] item = str.split(":");
									Map<String, String> map = new HashMap<String, String>();
									map.put("val", item[0]);
									map.put("name", item[1]);
									list.add(map);
								}
                            	options_.add(list);
                            }else {
                            	options_.add(null);
                            }
                        }
                    }
                    if(!isValid) return false;

                    field = (String[])filed_EN.toArray(field);
                    fieldTitle = (String[])filed_Title.toArray(fieldTitle);
                    fieldType = (String[])filed_Type.toArray(fieldType);
                    formType = (String[])form_Type.toArray(formType);
                    options = options_.toArray(options);
                    filedLength = filed_Length.toArray(filedLength);
                    filedNull = filed_null.toArray();
                    return true;
                }
            } else {
                System.out.println("无效的实体，不能生成~");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成或者删除文件
     * @param deleteFile  是否删除文件
     * @param generalFile 是否生成文件
     * @param executeType 执行类型  生成/删除 jsp/java/xml/all 文件
     * @throws IOException
     *
     */
    private static void execute(boolean deleteFile,boolean generalFile,String executeType) throws IOException, TemplateException {
        if(executeType == null) {
            return ;
        }
        if(!new File(projectPath).exists()) {
            System.out.println(projectPath + "目录不存在");
            return ;
        }

        List<Map> fieldInfos = new ArrayList<Map>();
        for (int i = 0; i < field.length; i++) {
            Map fieldInfo = new HashMap();
            fieldInfo.put("fieldName", field[i]);
            fieldInfo.put("fieldType", fieldType[i]);
            fieldInfo.put("fieldTitle", fieldTitle[i]);
            fieldInfo.put("formType", formType[i]);
            fieldInfo.put("options", options[i]);
            fieldInfo.put("fieldLength", filedLength[i]);
            fieldInfo.put("fieldNull", filedNull[i]);
            fieldInfos.add(fieldInfo);
        }

        Map<String, Object> root = new HashMap<String, Object>();
        root.put("currentDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        root.put("entityName", entityName);
        root.put("upperEntityName", upperEntityName);
        root.put("moduleName", moduleName);
        root.put("packageName", packageName);
        root.put("tableName", tableName);
        root.put("showSub", true);
        root.put("author", "fang");
        root.put("fieldInfos", fieldInfos);
        root.put("packagePath", packagePath);
        root.put("desc", "");
        root.put("idName", idName);
        //TODO 
        String javaFolder = "src/main/java/com/fang/" ;
        String jspPath = projectPath + "src/main/webapp/" + packageName + "/";
        String jsPath = jspPath + "js/";

//        String entityPath = projectPath + javaFolder + "entity/" + (upperEntityName + "Entity.java");
        String iServicePath = projectPath + javaFolder + packageName + "/service" + ("/I" + upperEntityName + "Service.java");
        String serviceImplPath = projectPath + javaFolder + packageName + "/service" + "/impl/" + (upperEntityName + "ServiceImpl.java");
        String controllerPath = projectPath + javaFolder + packageName + "/controller" + "/" +  (upperEntityName + "Controller.java");
        String daoPath = projectPath + javaFolder + packageName + "/dao" + "/" + (upperEntityName + "Mapper.java");
        String xmlPath = projectPath + "src/main/resources/mappers/" + packageName + "/" + (upperEntityName + "Mapper.xml");
        String listJspPath = jspPath + entityName + "List.html";
        String newJspPath = jspPath + entityName + "Add.html";
        String listJsPath = jsPath + entityName + "List.js";
        String newJsPath = jsPath + entityName + "Add.js";

        String javaFtl[] = {"IService.ftl", "ServiceImpl.ftl", "controller.ftl","dao.ftl"/*, "entity.ftl"*/};
        String javaExecuteFilePath[] = {iServicePath, serviceImplPath, controllerPath,daoPath/*, entityPath*/};
        String jspFtl[] = {"list.ftl", "new.ftl", "list.js.ftl", "new.js.ftl"};
        String jspExecuteFileFtl[] = { listJspPath, newJspPath, listJsPath, newJsPath};
        String mybatisXMLFtl[] = {"mybatisXML.ftl"};
        String mybatisXMLExecuteFileFtl[] = {xmlPath};
        String[] temps = {};
        String[] filePath = {};
        if (EXECUTETYPE_JSP.equalsIgnoreCase(executeType)) {
            temps = jspFtl;
            filePath = jspExecuteFileFtl;
        } else if(EXECUTETYPE_MYBATIS_XML.equalsIgnoreCase(executeType)) {
            temps = mybatisXMLFtl;
            filePath = mybatisXMLExecuteFileFtl;
        } else if(EXECUTETYPE_JAVA.equalsIgnoreCase(executeType)) {
            temps = javaFtl;
            filePath = javaExecuteFilePath;
        } else if(EXECUTETYPE_ALL.equalsIgnoreCase(executeType)) {
            List<String> resultFtl = new ArrayList<String>();
            resultFtl.addAll(Arrays.asList(javaFtl));
            resultFtl.addAll(Arrays.asList(jspFtl));
            resultFtl.addAll(Arrays.asList(mybatisXMLFtl));
            temps = resultFtl.toArray(new String[]{});

            List<String> resultExecuteFileFtl = new ArrayList<String>();
            resultExecuteFileFtl.addAll(Arrays.asList(javaExecuteFilePath));
            resultExecuteFileFtl.addAll(Arrays.asList(jspExecuteFileFtl));
            resultExecuteFileFtl.addAll(Arrays.asList(mybatisXMLExecuteFileFtl));

            filePath = resultExecuteFileFtl.toArray(new String[]{});
            
            if(preFuncId != null){
            	try {
            		String url ="jdbc:mysql://192.168.1.181:3306/fang?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";  
            		String username = "root";  
            		String password = "fang";  
            		Class.forName("com.mysql.jdbc.Driver") ;
            		Connection conn = DriverManager.getConnection(url, username, password) ;
            		Statement stat = conn.createStatement();
            		ResultSet set = stat.executeQuery("select * from sys_function_tbl where id = " + preFuncId);
            		Long rgt = null;
            		while (set.next()) {
            			rgt = Long.parseLong(set.getObject("rgt").toString());
            		}
            		stat.executeUpdate("update sys_function_tbl set lft = lft + 10 where lft > " + rgt);
            		stat.executeUpdate("update sys_function_tbl set rgt = rgt + 10 where rgt >= " + rgt);
            		stat.execute("insert into sys_function_tbl("
            				+ "enable,functionName, functionUrl, functionId, extraUrl,htmlStr, functionClass, "
            				+ "type, lft, rgt, notes, createBy,createById,createTime,"
            				+ "lastUpdateBy,lastUpdateById,lastUpdateTime) values"
            				+ "( 0,'" + moduleName + "', '" + packageName + "/" + entityName + "List.html" + "', null, null, null, "
            				+ "null, 2, " + rgt + ", " + (Long.parseLong(rgt.toString()) + 9) + ",  null,"
            				+ "'system',0,now(),'system',0,now())");
            		stat.execute("insert into sys_function_tbl("
            				+ "enable,functionName, functionUrl, functionId, extraUrl,htmlStr, functionClass, "
            				+ "type, lft, rgt, notes, createBy,createById,createTime,"
            				+ "lastUpdateBy,lastUpdateById,lastUpdateTime) values"
            				+ "( 0,'新增', '" + packageName + "/" + entityName + "Add.html" + "', 'show" + upperEntityName + "Page', '" + packageName + "/" + entityName + "/add" + upperEntityName + "', null, "
            				+ "'glyphicon glyphicon-plus', 3, " + (rgt + 1) + ", " + (rgt + 2) + ", 'params=\"act=add\"',"
            				+ "'system',0,now(),'system',0,now())");
            		stat.execute("insert into sys_function_tbl("
            				+ "enable,functionName, functionUrl, functionId, extraUrl,htmlStr, functionClass, "
            				+ "type, lft, rgt, notes, createBy,createById,createTime,"
            				+ "lastUpdateBy,lastUpdateById,lastUpdateTime) values"
            				+ "( 0,'修改', '" + packageName + "/" + entityName + "Add.html" + "', 'show" + upperEntityName + "Page', '" + packageName + "/" + entityName + "/update" + upperEntityName + "', null, "
            				+ "'glyphicon glyphicon-edit', 3, " + (rgt + 3) + ", " + (rgt + 4) + ", 'params=\"act=edit\"',"
            				+ "'system',0,now(),'system',0,now())");
            		stat.execute("insert into sys_function_tbl("
            				+ "enable,functionName, functionUrl, functionId, extraUrl,htmlStr, functionClass, "
            				+ "type, lft, rgt, notes, createBy,createById,createTime,"
            				+ "lastUpdateBy,lastUpdateById,lastUpdateTime) values"
            				+ "( 0,'删除', null, 'del" + upperEntityName + "', '" + packageName + "/" + entityName + "/update" + upperEntityName + "Enable', null, "
            				+ "'glyphicon glyphicon-remove', 3, " + (rgt + 5) + ", " + (rgt + 6) + ", null,"
            				+ "'system',0,now(),'system',0,now())");
            		stat.execute("insert into sys_function_tbl("
            				+ "enable,functionName, functionUrl, functionId, extraUrl,htmlStr, functionClass, "
            				+ "type, lft, rgt,  notes, createBy,createById,createTime,"
            				+ "lastUpdateBy,lastUpdateById,lastUpdateTime) values"
            				+ "( 0,'详情', null, 'trDblClick', null, null, "
            				+ "'glyphicon glyphicon-th-large', 3, " + (rgt + 7) + ", " + (rgt + 8) + ", null,"
            				+ "'system',0,now(),'system',0,now())");
            	}  catch (Exception e) {
            		e.printStackTrace();
            	}
            }
    	    
        }


        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(projectPath + "src/main/webapp/ftl/"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        //循环根据模板生成对应文件到对应的目录里去
        for (int i = 0; i < filePath.length; i++) {
            String path = filePath[i];
            String dir = path.substring(0,path.lastIndexOf("/"));
            File dirF = new File(dir);
            if(!dirF.exists()) {
                dirF.mkdirs();
            }
            if (deleteFile) {
                File executeFile = new File(path);
                if (executeFile.exists())
                    new File(path).delete();
            }
            if(generalFile) {
                String temp = temps[i];
                Template template = cfg.getTemplate(temp,"UTF-8");
                Writer out = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
                template.process(root, out);
                out.flush();
            }
        }
    }
}
