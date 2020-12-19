package services;

import models.Product;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private DataSource dataSource;
    private final static String SQL_GET_ALL = "select * from products;";
    private final static String SQL_GET_PRODUCT_BY_NAME = "select * from products where name=? and price=? and \"inStock\"=?;";

    private final static String SQL_DELETE_ALL_PRODUCTS = "delete from products;";
    private final static String SQL_DELETE_PRODUCT = "delete from products where name=? and price=? and \"inStock\"=?;";

    private final static String SQL_INSERT_PRODUCT = "insert into products (name, price, \"inStock\") values (?, ?, ?);";

    private final static String SQL_UPDATE_PRODUCT = "update products set name = ?, price = ?, \"inStock\" = ? where name = ? and price = ? and \"inStock\" = ?;";

    public ProductServiceImpl(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Product> getProducts() {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Product> allProducts = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_GET_ALL);

            try (ResultSet setOfAllProducts = statement.executeQuery()) {
                while (setOfAllProducts.next()) {
                    Product product = new Product(
                            setOfAllProducts.getString("name"),
                            setOfAllProducts.getInt("price"),
                            setOfAllProducts.getBoolean("inStock"));
                    allProducts.add(product);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException throwables) {
            }
        }
        return allProducts;
    }

    public Optional<Product> getProduct(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;
        Optional<Product> getProduct = Optional.empty();

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_GET_PRODUCT_BY_NAME);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setBoolean(3, product.getInStock());

            try (ResultSet isProduct = statement.executeQuery()) {
                if (isProduct.next()) {
                    Product prod = new Product(
                            isProduct.getString("name"),
                            isProduct.getInt("age"),
                            isProduct.getBoolean("inStock"));
                    getProduct = Optional.of(prod);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ignored) {
            }
        }

        return getProduct;
    }

    public void deleteProduct(Product product) {

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRODUCT)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setBoolean(3, product.getInStock());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Problem with deleting products");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    public void deleteProducts() {

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ALL_PRODUCTS)) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Problem with deleting products");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    public void putProduct(Product product) {

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setBoolean(3, product.getInStock());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Problem with insert product");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void updateProduct(Product oldProduct, Product newProduct) {

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT)) {
            statement.setString(1, newProduct.getName());
            statement.setInt(2, newProduct.getPrice());
            statement.setBoolean(3, newProduct.getInStock());
            statement.setString(4, oldProduct.getName());
            statement.setInt(5, oldProduct.getPrice());
            statement.setBoolean(6, oldProduct.getInStock());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Problem with update product");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
