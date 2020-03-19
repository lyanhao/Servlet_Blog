package liyanhao.servlet;

import liyanhao.dao.ArticleDAO;
import liyanhao.exception.BusinessException;
import liyanhao.util.Constant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleDelete")
public class ArticleDeleteServlet extends BaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String[] idsString = req.getParameter("ids").split(",");
        int[] ids = new int[idsString.length];
        for (int i = 0; i < idsString.length; i++) {
            ids[i] = Integer.parseInt(idsString[i]);
        }
        if (!ArticleDAO.delete(ids))
            throw new BusinessException(Constant.DELETE_ARTICLE_ERROR_CODE, "删除文章错误");
        return null;
    }
}
