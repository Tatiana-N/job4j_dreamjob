package ru.job4j.dream.servlet;

import org.apache.commons.io.FilenameUtils;
import ru.job4j.dream.Prop;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("true".equals(req.getParameter("delete"))) {
			doDelete(req, resp);
		}
		req.setAttribute("candidates", DbStore.instOf().findAllCandidates());
		req.setAttribute("user", req.getSession().getAttribute("user"));
		req.getRequestDispatcher("candidates/candidates.jsp").forward(req, resp);
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		String name = req.getParameter("name");
		String id = DbStore.instOf().save(new Candidate(Integer.valueOf(req.getParameter("id")), name));
		resp.sendRedirect(req.getContextPath() + "/upload?id=" + id);
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		DbStore.instOf().deleteCandidate(Integer.parseInt(id));
		File folder = new File(Prop.getDataFromProperties("path.to.photo"));
		if (!folder.exists()) {
			folder.mkdir();
		}
		for (File item : folder.listFiles()) {
			if (FilenameUtils.removeExtension(item.getName()).equals(id)) {
				item.delete();
				break;
			}
		}
	}
}