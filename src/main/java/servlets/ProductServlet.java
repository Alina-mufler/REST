package servlets;

import models.Product;
import services.ProductService;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) {
        productService = (ProductService) config.getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8), true);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (req.getParameter("name") == null
                && req.getParameter("price") == null
                && req.getParameter("inStock") == null) {
            out.print(productService.getProducts());
        } else {
            out.print(productService.getProduct(
                    new Product(
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("price")),
                            Boolean.parseBoolean(req.getParameter("inStock"))
                    )));
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8), true);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");


        if (req.getParameter("name") == null
                && req.getParameter("price") == null
                && req.getParameter("inStock") == null) {
            out.print("Введены не все данные");
        } else {
            productService.putProduct(
                    new Product(
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("price")),
                            Boolean.parseBoolean(req.getParameter("inStock"))
                    ));
            out.print("Добавление прошло успешно");
        }
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8), true);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");


        if (req.getParameter("oldName") == null
                && req.getParameter("oldPrice") == null
                && req.getParameter("oldInStock") == null
                && req.getParameter("newName") == null
                && req.getParameter("newPrice") == null
                && req.getParameter("newInStock") == null) {
            out.print("Введены не все данные");
        } else {
            productService.updateProduct(
                    new Product(
                            req.getParameter("oldName"),
                            Integer.parseInt(req.getParameter("oldPrice")),
                            Boolean.parseBoolean(req.getParameter("newInStock"))),
                    new Product(
                            req.getParameter("newName"),
                            Integer.parseInt(req.getParameter("newPrice")),
                            Boolean.parseBoolean(req.getParameter("oldInStock"))
                    ));
            out.print("Изменение прошло успешно");
        }
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8), true);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");


        if (req.getParameter("name") == null
                && req.getParameter("price") == null
                && req.getParameter("inStock") == null) {
            productService.deleteProducts();
            out.print("Удаление всех продуктов прошло успешно");
        } else {
            productService.deleteProduct(
                    new Product(
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("price")),
                            Boolean.parseBoolean(req.getParameter("inStock"))
                    ));
            out.print("Удаление продукта прошло успешно");
        }
        out.flush();
    }
}
