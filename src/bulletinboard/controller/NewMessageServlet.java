package bulletinboard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinboard.beans.Messages;
import bulletinboard.beans.UserMessage;
import bulletinboard.service.MessageService;



@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request,HttpServletResponse response)
			throws IOException,ServletException {
				request.getRequestDispatcher("newMessage.jsp").forward(request, response);




				new MessageService();
				List<UserMessage> messages = MessageService.getMessage();
				System.out.println(messages+"めっさげす");
				request.setAttribute("messages", messages);


	}

	@Override
	protected void doPost (HttpServletRequest request,HttpServletResponse response)
	throws IOException,ServletException {
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		String titleSerch = request.getParameter("title");
		String categorySerch = request.getParameter("category");
		String bodySerch = request.getParameter("body");
		request.setAttribute("serchTitle",titleSerch);
		request.setAttribute("serchCategory",categorySerch);
		request.setAttribute("serchBody",bodySerch);
		if (isValid(request, messages) == true) {
			Messages message = new Messages();
			message.setUser_id(Integer.parseInt(request.getParameter("userId")));
			if(request.getParameter("title") == null || request.getParameter("title").length()>50)  {
				System.out.println("タイトルがおかしい");
				request.getRequestDispatcher("newMessage.jsp").forward(request, response);
			}
			String i=request.getParameter("title");
			if(i.isEmpty()){
				System.out.println("タイトルがkara");
			}else{
				message.setTitle(request.getParameter("title"));
			}
			if(request.getParameter("body") == null || request.getParameter("body").length()>1000){
				System.out.println("ぼでーがおかしい");
			}
			String b=request.getParameter("body");
			if(b.isEmpty()){
				System.out.println("ぼでーがkara");
			}else{
				message.setBody(request.getParameter("body"));
			}
			if(request.getParameter("category") == null || request.getParameter("category").length()>10){
				System.out.println("カテゴリーがおかしい");
			}
			String c=request.getParameter("category");
			if(c.isEmpty()){
				System.out.println("カテゴリがkara");
			}else{
				message.setCategory(request.getParameter("category"));
			}


			new MessageService().register(message);

			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessage", messages);
			request.getRequestDispatcher("newMessage.jsp").forward(request, response);
		}
	}
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String message = request.getParameter("body");
		String title =   request.getParameter("title");
		String category =request.getParameter("category");
		if (StringUtils.isEmpty(title) == true) {
			messages.add("タイトルを入力してください");
			System.out.println("タイトルが空");
		}
		if (50 < title.length()) {
			messages.add("タイトルは50文字以下で入力してください");
		}
		if (StringUtils.isEmpty(message) == true) {
			messages.add("メッセージを入力してください");
			System.out.println("メッセージが空");
		}
		if (1000 < message.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}
		if (StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリを入力してください");
			System.out.println("カテゴリが空");
		}
		if (10 < category.length()) {
			messages.add("カテゴリは10文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}