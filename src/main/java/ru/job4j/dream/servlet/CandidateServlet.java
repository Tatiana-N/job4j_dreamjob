package ru.job4j.dream.servlet;

import org.apache.commons.io.FilenameUtils;
import ru.job4j.dream.Prop;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.MemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("true".equals(req.getParameter("delete"))) {
			doDelete(req, resp);
		}
		req.setAttribute("candidates", MemStore.instOf().findAllCandidates());
		req.getRequestDispatcher("candidates/candidates.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		String name = req.getParameter("name");
		String id = MemStore.instOf().save(new Candidate(Integer.valueOf(req.getParameter("id")), name));
		resp.sendRedirect(req.getContextPath() + "/upload?id=" + id);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		MemStore.instOf().deleteCandidate(Integer.parseInt(id));
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