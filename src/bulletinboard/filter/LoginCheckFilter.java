package bulletinboard.filter;

import java.io.IOException;

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

@WebFilter("/*")
public class LoginCheckFilter implements Filter{



	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain)throws IOException, ServletException {
		try{
			String target = ((HttpServletRequest) request).getRequestURI();

			HttpSession session = ((HttpServletRequest)request).getSession();





			User loginCheck =  (User) session.getAttribute("loginUser");
			System.out.println(target+"←target");

			if(loginCheck==null){
				session.setAttribute("target", target);

				//System.out.println("ログインフィルター２");
				if(target.matches("/Bulletinboard/login")){
					System.out.println("URIにlogin入ってるためなにもしない");
					chain.doFilter(request, response);
				} else {
					System.out.println("URIにlogin入ってないためリダイレクト");
					if(request.getAttribute("erroMessage")==null){

					}


					((HttpServletResponse)response).sendRedirect("http://localhost:8080/Bulletinboard/login");
					return;
				}
			}else{
				chain.doFilter(request, response);
			}

		}catch(ServletException se){

		}catch(IOException e){

		}

	}



	@Override
	public void init(FilterConfig config) {
	}

	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}


}