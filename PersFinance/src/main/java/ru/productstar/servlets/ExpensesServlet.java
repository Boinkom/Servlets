package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Expense;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpensesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var context = req.getServletContext();

            var session = req.getSession(false);
            if (session == null) {
                resp.getWriter().println("Not authorized");
                try {
                    Thread.sleep(2000);
                    resp.sendRedirect("/summary");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            int freeMoney = (int) context.getAttribute("freeMoney");
            var expenses = new ArrayList<Expense>((List) context.getAttribute("expenses"));
            var transactions = (List<Transaction>) context.getAttribute("transactions");

            // Если список транзакций еще не создан, создаем его
            if (transactions == null) {
                transactions = new ArrayList<>();
            }

            for (var k : req.getParameterMap().keySet()) {
                int value = Integer.parseInt(req.getParameter(k));
                freeMoney -= value;
                expenses.add(new Expense(k, value));
                transactions.add(new Transaction(new Expense(k, value), null));
            }

            context.setAttribute("expenses", expenses);
            context.setAttribute("freeMoney", freeMoney);
            context.setAttribute("transactions", transactions); // Устанавливаем список транзакций в атрибут контекста

            resp.sendRedirect("/summary");
        } catch (Exception e) {
            handleException(resp, e);
        }
    }

    private void handleException(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Устанавливаем статус ответа 500

        // Выводим сообщение об ошибке
        resp.getWriter().println("Error (500) — " + e.getClass().getName() + ": " + e.getMessage());
    }
}
