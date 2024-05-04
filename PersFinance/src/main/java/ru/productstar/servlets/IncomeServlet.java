package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Expense;
import ru.productstar.servlets.model.Income;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/incomes/add")
public class IncomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        var income = new ArrayList<Income>((List) context.getAttribute("income"));
        var transactions = (List<Transaction>) context.getAttribute("transactions");

        if (transactions == null) {
            transactions = new ArrayList<>();
        }

        for (var k : req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k));
            freeMoney -= value;
            income.add(new Income(k, value));
            transactions.add(new Transaction(new Expense(k, value), null));
        }

        context.setAttribute("income", income);
        context.setAttribute("freeMoney", freeMoney);
        context.setAttribute("transactions", transactions);

        resp.sendRedirect("/summary");
    }
}
