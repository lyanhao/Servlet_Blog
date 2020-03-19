package liyanhao.servlet;

import liyanhao.dao.ArticleDAO;
import liyanhao.exception.BusinessException;
import liyanhao.model.Article;
import liyanhao.util.Constant;
import liyanhao.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleUpdate")
public class ArticleUpdateServlet extends BaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Article article = JSONUtil.deserialize(req.getInputStream(), Article.class);
        if (!ArticleDAO.update(article))
            throw new BusinessException(Constant.UPDATE_ARTICLE_ERROR_CODE, "修改文章错误");
        return null;
    }
}
