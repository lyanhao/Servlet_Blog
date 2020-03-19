package liyanhao.dao;

import liyanhao.exception.SystemException;
import liyanhao.model.User;
import liyanhao.util.Constant;
import liyanhao.util.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static User queryByName(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select id, name, create_time from user where name=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setCreatTime(
                        new Date(resultSet.getTimestamp("create_time").getTime()));
                return user;
            }
            return null;
        } catch (Exception e) {
            throw new SystemException(Constant.QUERY_USER_ERROR_CODE, "查询用户信息出错", e);
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
    }
}
