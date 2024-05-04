package ru.productstar.servlets.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandlingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // Пропускаем запрос через цепочку фильтров
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable t) {
            // Если возникло исключение, обрабатываем его
            handleException((HttpServletResponse) servletResponse, t);
        }
    }

    @Override
    public void destroy() {
        // Метод destroy не требуется для этого фильтра
    }

    private void handleException(HttpServletResponse resp, Throwable t) throws IOException {
        if (resp.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            // Если статус ответа 404, выводим соответствующее сообщение
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("Error (404) — page not found");
        } else {
            // Выводим сообщение об ошибке 500 для всех остальных исключений
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Error (500) — " + t.getClass().getName() + ": " + t.getMessage());
        }
    }
}
