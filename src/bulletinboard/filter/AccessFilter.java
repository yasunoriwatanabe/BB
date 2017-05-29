package bulletinboard.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletinboard.beans.User;

@WebFilter({"/management", "/signup", "/settings"})
public class AccessFilter implements Filter{



	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain)throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		User loginUser =  (User) session.getAttribute("loginUser");


		if(loginUser != null){
			String department_id = loginUser.getDepartment_id();
			int i = Integer.parseInt(department_id);
			if(i!=1){
				System.out.println("権限がない");
				List<String> messages =  new ArrayList<String>();
				messages.add("アクセス権限がありません。");
				session.setAttribute("errorMessage", messages);
				((HttpServletResponse)response).sendRedirect("./");
				return;

			}else{
				System.out.println("権限があるので管理画面を開く");
			}

		}


		chain.doFilter(request,response);
	}


	@Override
	public void init(FilterConfig config) {
	}

	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}




}

