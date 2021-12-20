package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import ru.job4j.dream.Prop;
import ru.job4j.dream.store.DbStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> images = new ArrayList<>();
		File file = new File(Prop.getDataFromProperties("path.to.photo"));
		for (File name : file.listFiles()) {
			images.add(FilenameUtils.removeExtension(name.getName()));
		}
		String id = req.getParameter("id");
		req.setAttribute("images", images);
		req.setAttribute("id", id);
		req.setAttribute("name", DbStore.instOf().findByIdCandidate(Integer.parseInt(id)).getName());
		RequestDispatcher dispatcher = req.getRequestDispatcher("/upload.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File file = new File(Prop.getDataFromProperties("path.to.photo"));
		DiskFileItemFactory factory = new DiskFileItemFactory();
		String id = req.getParameter("id");
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(req);
			if (!file.exists()) {
				file.mkdir();
			}
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String extension = FilenameUtils.getExtension(item.getName()).length() > 0 ? "." + FilenameUtils.getExtension(item.getName()) : "";
					File saveFile = new File(file + File.separator + id + extension);
					try (FileOutputStream out = new FileOutputStream(saveFile)) {
						out.write(item.getInputStream().readAllBytes());
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		resp.sendRedirect(req.getContextPath() + "/candidates.do");
	}
}