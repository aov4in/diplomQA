package ru.netology.data;

import lombok.val;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;
import java.text.SimpleDateFormat;

public class SqlUtils {
    static String url = getUrl();
    static String user = "app";
    static String password = "pass";

    public static String getUrl ()
    {
        return System.getProperty("db.url");
    }

    public static String getPaymentStatus () throws SQLException {

        val statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        String paymentStatus;
        try (
                val conn = DriverManager.getConnection(
                        url, user, password
                );
        ) {
            paymentStatus = runner.query(conn, statusSQL, new ScalarHandler<>());
        }
        return paymentStatus;
    }

    public static String getRequestStatus () throws SQLException {

        val statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        String requestStatus;
        try (
                val conn = DriverManager.getConnection(
                        url, user, password
                );
        ) {
            requestStatus = runner.query(conn, statusSQL, new ScalarHandler<>());
        }
        return requestStatus;
    }

    public static String getCreatedOrderStatus () throws SQLException {

        val statusSQL = "SELECT created FROM order_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        Timestamp createdOrderStatus;
        String createdOrderStatusSQL;
        try (
                val conn = DriverManager.getConnection(
                        url, user, password
                );
        ) {
            createdOrderStatus = runner.query(conn, statusSQL, new ScalarHandler<>());
            createdOrderStatusSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdOrderStatus);
        }
        return createdOrderStatusSQL;
    }

    public static String getCreatedPaymentStatus () throws SQLException {

        val statusSQL = "SELECT created FROM payment_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        Timestamp createdPaymentStatus;
        String createdPaymentStatusSQL;
        try (
                val conn = DriverManager.getConnection(
                        url, user, password
                );
        ) {
            createdPaymentStatus = runner.query(conn, statusSQL, new ScalarHandler<>());
            createdPaymentStatusSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdPaymentStatus);
        }
        return createdPaymentStatusSQL;
    }

    public static String getCreatedRequestStatus () throws SQLException {

        val statusSQL = "SELECT created FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        Timestamp createdRequestStatus;
        String createdRequestStatusSQL;
        try (
                val conn = DriverManager.getConnection(
                        url, user, password
                );
        ) {
            createdRequestStatus = runner.query(conn, statusSQL, new ScalarHandler<>());
            createdRequestStatusSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createdRequestStatus);
        }
        return createdRequestStatusSQL;
    }

    public static void cleanDB () throws SQLException {

        val cleanOrderSQL = "DELETE FROM order_entity;";
        val cleanPaymentSQL = "DELETE FROM payment_entity;";
        val cleanCreditSQL = "DELETE FROM credit_request_entity;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        url, user, password
                );
        ) {
            val cleaningOrder = runner.update(conn, cleanOrderSQL);
            val cleaningPayment = runner.update(conn, cleanPaymentSQL);
            val cleaningCredit = runner.update(conn, cleanCreditSQL);
        } finally {
            Connection conn = DriverManager.getConnection(url, user, password);
            DbUtils.closeQuietly(conn);
        }
    }
}
