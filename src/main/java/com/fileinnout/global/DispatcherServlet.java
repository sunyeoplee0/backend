package com.fileinnout.global;

import com.fileinnout.utils.JsonParser;
import com.fileinnout.utils.JwtProvider;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private AppConfig appConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.appConfig = new AppConfig();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Controller controller = appConfig.getController(uri);

        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        BaseResponse res = null;
        if (req.getRequestURI().contains("editor")){
            if (req.getCookies() != null) {
                for (Cookie cookie : req.getCookies()) {
                    if (cookie.getName().equals("ATOKEN")) {
                        System.out.println("토큰 있다.");
                        String token = cookie.getValue();
                        JwtProvider.checkToken(token);
                        res = controller.process(req, resp);
                    }
                }
            }
        } else {
            res = controller.process(req, resp);
        }

        resp.getWriter().write(JsonParser.from(res));




    }
}
