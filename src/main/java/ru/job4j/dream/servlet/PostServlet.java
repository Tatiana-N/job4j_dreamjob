package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemStore;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("true".equals(req.getParameter("delete"))) {
			doDelete(req, resp);
		}
		req.setAttribute("posts", MemStore.instOf().findAllPosts());
		req.getRequestDispatcher("post/post.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		MemStore.instOf().save(new Post(Integer.valueOf(req.getParameter("id")), req.getParameter("name")));
		resp.sendRedirect(req.getContextPath() + "/post.do");
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemStore.instOf().deletePost(Integer.parseInt(req.getParameter("id")));
	}
}