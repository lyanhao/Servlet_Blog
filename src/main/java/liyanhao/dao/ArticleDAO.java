package liyanhao.dao;

import liyanhao.exception.BusinessException;
import liyanhao.model.Article;
import liyanhao.util.Constant;
import liyanhao.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDAO {
    //插入文章
    public static boolean insert(Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into article(title, content, user_id, create_time)" +
                    " values(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setInt(3, article.getUserId());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            int num = preparedStatement.executeUpdate();
            return num >= 1;
        } catch (Exception e) {
            throw new BusinessException(Constant.INSERT_ARTICLE_ERROR_CODE, "插入文章出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    public static List<Article> queryByUserId(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Article> articles = new ArrayList<>();
        try {
            connection = DBUtil.getConnection();
            String sql = "select id, title, content, user_id, create_time" +
                    " from article where user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setUserId(resultSet.getInt("id"));
                article.setCreatTime(new Date(resultSet.getTimestamp("create_time").getTime()));
                articles.add(article);
            }
            return articles;
        } catch (Exception e) {
            throw new BusinessException(Constant.SELECT_ARTICLELIST_ERROR_CODE, "查询文章列表出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
    }

    public static Article queryByArticleId(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Article article = new Article();
        try {
            connection = DBUtil.getConnection();
            String sql = "select id, title, content, user_id, create_time" +
                    " from article where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setUserId(resultSet.getInt("id"));
                article.setCreatTime(new Date(resultSet.getTimestamp("create_time").getTime()));
            }
            return article;
        } catch (Exception e) {
            throw new BusinessException(Constant.SELECT_ARTICLEDETAIL_ERROR_CODE, "查询文章详情出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
    }

    //修改文章
    public static boolean update(Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "update article set title=?, content=? where id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setInt(3, article.getId());
            int num = preparedStatement.executeUpdate();
            return num >= 1;
        } catch (Exception e) {
            throw new BusinessException(Constant.UPDATE_ARTICLE_ERROR_CODE, "修改文章出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }

    public static boolean delete(int[] ids) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            //可以改为StringBuilder提升效率
            String sql = "delete from article where id in (";
            for (int i = 0; i < ids.length; i++) {
                if (i == 0) {
                    sql += "?";
                } else {
                    sql += ", ?";
                }
            }
            sql += ")";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < ids.length; i++) {
                preparedStatement.setInt(i + 1, ids[i]);
            }
            int num = preparedStatement.executeUpdate();
            return num >= 1;
        } catch (Exception e) {
            throw new BusinessException(Constant.DELETE_ARTICLE_ERROR_CODE, "删除文章出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement);
        }
    }
}
