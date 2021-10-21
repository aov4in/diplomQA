package ru.netology.test;

import lombok.val;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlUtils {

    private static final String user = System.getProperty("user");;
    private static final String password = System.getProperty("pass");;
    private static final String database = System.getProperty("dbUrl");
    private static Statement countStmt = null;
    private static Connection conn = null;

    public static void dbConnect() {
        try {
            conn = DriverManager.getConnection(database, user, password);
            countStmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Невозможно подключить к БД");
        }
    }

    public static void dbCloseConnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Невозможно завершить соединение");
        }
    }

    static int countApprovedPayment() {
        val count = "select count(1) as rownum from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='APPROVED';";
        int countApprovedPayment = 0;
        try (val rs = countStmt.executeQuery(count)) {
            while (rs.next()) {
                countApprovedPayment = rs.getInt("rownum");
            }
        } catch (SQLException e) {
            System.err.println("Невозможно получить значение rownum столбца");
        }
        return countApprovedPayment;
    }

    static int countDeclinedPayment() {
        val count = "select count(1) as rownum from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='DECLINED';";
        int countDeclinedPayment = 0;
        try (val rs = countStmt.executeQuery(count)) {
            while (rs.next()) {
                countDeclinedPayment = rs.getInt("rownum");
            }
        } catch (SQLException e) {
            System.err.println("Невозможно получить значение rownum столбца");
        }
        return countDeclinedPayment;
    }

    static int countPayment() {
        val count = "select count(1) as rownum from order_entity o, payment_entity p where o.payment_id=p.transaction_id;";
        int countPayment = 0;
        try (val rs = countStmt.executeQuery(count)) {
            while (rs.next()) {
                countPayment = rs.getInt("rownum");
            }
        } catch (SQLException e) {
            System.err.println("Невозможно получить значение rownum столбца");
        }
        return countPayment;
    }

    static int countApprovedCredit() {
        val count = "select count(1) as rownum from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='APPROVED';";
        int countApprovedCredit = 0;
        try (val rs = countStmt.executeQuery(count)) {
            while (rs.next()) {
                countApprovedCredit = rs.getInt("rownum");
            }
        } catch (SQLException e) {
            System.err.println("Невозможно получить значение rownum столбца");
        }
        return countApprovedCredit;
    }

    static int countDeclinedCredit() {
        val count = "select count(1) as rownum from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='DECLINED';";
        int countDeclinedCredit = 0;
        try (val rs = countStmt.executeQuery(count)) {
            while (rs.next()) {
                countDeclinedCredit = rs.getInt("rownum");
            }
        } catch (SQLException e) {
            System.err.println("Невозможно получить значение rownum столбца");
        }
        return countDeclinedCredit;
    }

    static int countCredit() {
        val count = "select count(1) as rownum from order_entity o, credit_request_entity c where o.payment_id=c.bank_id;";
        int countCredit = 0;
        try (val rs = countStmt.executeQuery(count)) {
            while (rs.next()) {
                countCredit = rs.getInt("rownum");
            }
        } catch (SQLException e) {
            System.err.println("Невозможно получить значение rownum столбца");
        }
        return countCredit;
    }

}
