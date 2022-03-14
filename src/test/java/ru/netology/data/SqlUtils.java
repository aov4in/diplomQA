package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SqlUtils {
    static String url = getUrl();
    static String user = getUser();
    static String password = getPass();

    public static String getUrl ()
    {
        return System.getProperty("db.url");
    }
    public static String getUser() { return System.getProperty("user"); }
    public static String getPass() { return System.getProperty("password"); }

    public static String getPaymentStatus () {

        val statusSQL = "SELECT status FROM payment_entity;";
        return getData(statusSQL);
    }

    private static String getData(String query) {
        String data = "";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection(url, user, password)) {
            data = runner.query(conn, query, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getRequestStatus () {

        val statusSQL = "SELECT status FROM credit_request_entity;";
        return getData(statusSQL);
    }

    public static String getCreatedOrderStatus () {
        Long count = null;
        val statusSQL = "SELECT count(*) FROM order_entity;";
        val runner = new     QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            count = runner.query(conn, statusSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Long.toString(count);
    }


    public static void cleanDB () {

        val cleanOrderSQL = "DELETE FROM order_entity;";
        val cleanPaymentSQL = "DELETE FROM payment_entity;";
        val cleanCreditSQL = "DELETE FROM credit_request_entity;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        url, user, password
                );
        ) {
            runner.update(conn, cleanOrderSQL);
            runner.update(conn, cleanPaymentSQL);
            runner.update(conn, cleanCreditSQL);
        } catch (Exception e){
            System.out.println("SQL Exception in cleanDB");
        }
    }
}
