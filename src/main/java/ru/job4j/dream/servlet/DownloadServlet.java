package ru.job4j.dream.servlet;

import org.apache.commons.io.FilenameUtils;
import ru.job4j.dream.Prop;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File file = new File(Prop.getDataFromProperties("path.to.photo"));
		String name = req.getParameter("name");
		File downloadFile = null;
		for (File oneFile : file.listFiles()) {
			if (name.equals(FilenameUtils.removeExtension(oneFile.getName()))) {
				downloadFile = oneFile;
				break;
			}
		}
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
		try (FileInputStream stream = new FileInputStream(downloadFile)) {
			resp.getOutputStream().write(stream.readAllBytes());
		}
	}
}
