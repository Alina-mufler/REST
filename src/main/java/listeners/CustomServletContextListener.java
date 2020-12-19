package listeners;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import services.ProductService;
import services.ProductServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CustomServletContextListener implements ServletContextListener {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Rest";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Lovelovelove%5";
    private static final String DB_DRIVER = "org.postgresql.Driver";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        ProductService productService = new ProductServiceImpl(dataSource);
        servletContext.setAttribute("userService", productService);
    }
}
