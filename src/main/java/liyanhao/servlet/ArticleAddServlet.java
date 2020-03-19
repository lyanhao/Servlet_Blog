package liyanhao.servlet;

import liyanhao.dao.ArticleDAO;
import liyanhao.dao.UserDAO;
import liyanhao.exception.BusinessException;
import liyanhao.model.Article;
import liyanhao.model.User;
import liyanhao.util.Constant;
import liyanhao.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends BaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        InputStream is = req.getInputStream();
        Article article = JSONUtil.deserialize(is, Article.class);
        User user = UserDAO.queryByName(article.getUserAccout());
        if (user == null) {
            throw new BusinessException(Constant.USER_NULL_ERROR_CODE, "用户不存在，无法发表文章");
        }
        //如果用户存在，就插入文章数据
        article.setUserId(user.getId());
        if (!ArticleDAO.insert(article)) {
            throw new BusinessException(Constant.INSERT_ARTICLE_ERROR_CODE, "文章插入0条数据");
        }
        return null;
    }
}
