package run.halo.app.service.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CpDataTest {

    private Connection srcConn;
    private Connection desConn;

    @Before
    public void setup() throws SQLException {
        System.out.println("建立链接");
        HikariConfig src = new HikariConfig();
        src.setJdbcUrl("jdbc:h2:file:~/.halo/db/halo");
        src.setUsername("admin");
        src.setPassword("123456");
        HikariDataSource srcData = new HikariDataSource(src);
        srcConn = srcData.getConnection();

        HikariConfig des = new HikariConfig();
        des.setJdbcUrl("jdbc:mysql://10.10.92.123:3306/blog2?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        des.setUsername("blog");
        des.setPassword("");
        HikariDataSource desData = new HikariDataSource(des);
        desConn = desData.getConnection();
    }

    @After
    public void destroy() throws SQLException {
        System.out.println("关闭链接");
        srcConn.close();
        desConn.close();
    }

    @Test
    @Transactional
    public void posts() throws Exception {

        PreparedStatement destStat = desConn.prepareStatement("insert into posts (type,id,create_time,deleted," +
                "update_time,create_from,disallow_comment,edit_time,format_content,likes," +
                "original_content,password,status,summary,template," +
                "thumbnail,title,top_priority,url,visits) values (?,?,?,?,?, ?,?,?,?,?" +
                ",?,?,?,?,?,  ?,?,?,?,?)");

        PreparedStatement pstat = srcConn.prepareStatement("select * from posts");
        ResultSet rs = pstat.executeQuery();
        while (rs.next()){
            int type = rs.getInt("type");
            int id = rs.getInt("id");
            Timestamp create_time = rs.getTimestamp("create_time");
            int deleted = rs.getInt("deleted");
            Timestamp update_time = rs.getTimestamp("update_time");

            int create_from = rs.getInt("create_from");
            int disallow_comment = rs.getInt("disallow_comment");
            Timestamp edit_time = rs.getTimestamp("edit_time");
            String format_content = rs.getString("format_content");
            long likes = rs.getLong("likes");

            String original_content = rs.getString("original_content");
            String password = rs.getString("password");
            int status = rs.getInt("status");
            String summary = rs.getString("summary");
            String template = rs.getString("template");

            String thumbnail = rs.getString("thumbnail");
            String title = rs.getString("title");
            int top_priority = rs.getInt("top_priority");
            String url = rs.getString("url");
            long visits = rs.getLong("visits");


            int i=1;
            destStat.setInt(i++,type);
            destStat.setInt(i++,id);
            destStat.setTimestamp(i++,create_time);
            destStat.setInt(i++,deleted);
            destStat.setTimestamp(i++,update_time);

            destStat.setInt(i++,create_from);
            destStat.setInt(i++,disallow_comment);
            destStat.setTimestamp(i++,edit_time);
            destStat.setString(i++,format_content);
            destStat.setLong(i++,likes);

            destStat.setString(i++,original_content);
            destStat.setString(i++,password);
            destStat.setInt(i++,status);
            destStat.setString(i++,summary);
            destStat.setString(i++,template);

            destStat.setString(i++,thumbnail);
            destStat.setString(i++,title);
            destStat.setInt(i++,top_priority);
            destStat.setString(i++,url);
            destStat.setLong(i++,visits);

            int aff = destStat.executeUpdate();
            System.out.println("id:" + id + ",summary:" + summary + ",aff=" + aff);
        }
        pstat.close();
        destStat.close();
    }

    @Test
    @Transactional
    public void post_categories() throws Exception {

        PreparedStatement destStat = desConn.prepareStatement(
                "insert into post_categories (id,create_time,deleted,update_time,category_id,post_id) values (?,?,?,?,?,?)");

        PreparedStatement pstat = srcConn.prepareStatement("select * from post_categories");
        ResultSet rs = pstat.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            Timestamp create_time = rs.getTimestamp("create_time");
            int deleted = rs.getInt("deleted");
            Timestamp update_time = rs.getTimestamp("update_time");

            int category_id = rs.getInt("category_id");
            int post_id = rs.getInt("post_id");

            int i=1;
            destStat.setInt(i++,id);
            destStat.setTimestamp(i++,create_time);
            destStat.setInt(i++,deleted);
            destStat.setTimestamp(i++,update_time);

            destStat.setInt(i++,category_id);
            destStat.setInt(i++,post_id);

            int aff = destStat.executeUpdate();
            System.out.println("id:" + id + ",post_id:" + post_id + ",aff=" + aff);
        }
        pstat.close();
        destStat.close();
    }

    @Test
    @Transactional
    public void post_tags() throws Exception {

        PreparedStatement destStat = desConn.prepareStatement(
                "insert into post_tags (id,create_time,deleted,update_time,post_id,tag_id) values (?,?,?,?,?,?)");

        PreparedStatement pstat = srcConn.prepareStatement("select * from post_tags");
        ResultSet rs = pstat.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            Timestamp create_time = rs.getTimestamp("create_time");
            int deleted = rs.getInt("deleted");
            Timestamp update_time = rs.getTimestamp("update_time");
            int post_id = rs.getInt("post_id");
            int tag_id = rs.getInt("tag_id");

            int i=1;
            destStat.setInt(i++,id);
            destStat.setTimestamp(i++,create_time);
            destStat.setInt(i++,deleted);
            destStat.setTimestamp(i++,update_time);
            destStat.setInt(i++,post_id);
            destStat.setInt(i++,tag_id);

            int aff = destStat.executeUpdate();
            System.out.println("id:" + id + ",post_id:" + post_id + ",aff=" + aff);
        }
        pstat.close();
        destStat.close();
    }
}