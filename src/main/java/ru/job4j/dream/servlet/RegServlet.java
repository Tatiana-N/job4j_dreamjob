package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("reg.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		int id = Integer.parseInt(DbStore.instOf().save(user));
		if (id == 0) {
			req.setAttribute("error", "Пользователь уже зарегистрирован");
			req.getRequestDispatcher("reg.jsp?id=0").forward(req, resp);
		} else {
			HttpSession sc = req.getSession();
			sc.setAttribute("user", user);
			resp.sendRedirect(req.getContextPath() + "/");
		}
	}
}
