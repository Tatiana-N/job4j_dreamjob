package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {
	
	/**
	 * В методу doGet мы загружаем в request список вакансий.
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("true".equals(req.getParameter("delete"))) {
			doDelete(req, resp);
		}
		req.setAttribute("posts", DbStore.instOf().findAllPosts());
		req.setAttribute("user", req.getSession().getAttribute("user"));
		req.getRequestDispatcher("post/post.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		DbStore.instOf().save(new Post(Integer.parseInt(req.getParameter("id")), req.getParameter("name")));
		resp.sendRedirect(req.getContextPath() + "/post.do");
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DbStore.instOf().deletePost(Integer.parseInt(req.getParameter("id")));
	}
}