package hmart.customer.repository;

import hmart.customer.domain.Customer;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//CREATE TABLE CUSTOMER2 (
//    "ID" VARCHAR2(30 BYTE),
//	"NAME" VARCHAR2(30 BYTE),
//	"AGE" NUMBER(10,0),
//	"LEVELS" VARCHAR2(15 BYTE),
//	"JOB" VARCHAR2(15 BYTE),
//	"POINT" NUMBER(10,0),
//PRIMARY KEY ("ID")
//    );
//
//INSERT INTO customer2
//select * from 고객;

//EXEC DBMS_XDB.SETHTTPPORT(9090);

@Repository
public class JdbcCustomerRepository implements CustomerRepository{
    private final DataSource dataSource;
    public JdbcCustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customer values(?, ?, ?, ?, ?, ?)";
        Connection conn = null;        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();  // private method
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.getId());
            pstmt.setString(2, customer.getName());
            pstmt.setInt(3, customer.getAge());
            pstmt.setString(4, customer.getLevels());
            pstmt.setString(5, customer.getJob());
            pstmt.setInt(6, customer.getPoint());
            int num = pstmt.executeUpdate();
            if (num == 1) {                return customer;             }
            else {        throw new SQLException("id 조회 실패");         }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Customer> findById(String id) {
        String sql = "select * from customer where id = ?";
        Connection conn = null;        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setAge(rs.getInt("age"));
                customer.setLevels(rs.getString("levels"));
                customer.setJob(rs.getString("job"));
                customer.setPoint(rs.getInt("point"));
                return Optional.of(customer);
            } else {       return Optional.empty();            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {   close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while(rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setAge(rs.getInt("age"));
                customer.setLevels(rs.getString("levels"));
                customer.setJob(rs.getString("job"));
                customer.setPoint(rs.getInt("point"));
                customers.add(customer);
            }
            return customers;
        } catch (Exception e) {   throw new IllegalStateException(e);
        } finally {            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Customer> findByName(String name) {
        String sql = "select * from customer where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setAge(rs.getInt("age"));
                customer.setLevels(rs.getString("levels"));
                customer.setJob(rs.getString("job"));
                customer.setPoint(rs.getInt("point"));
                return Optional.of(customer);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
        //하나의 일관된 connection 관리용. close도 마찬가지
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        //rs, stmt, conn 순서대로 처리
        try {
            if (rs != null) {                rs.close();
            }
        } catch (SQLException e) {            e.printStackTrace();
        }
        try {
            if (pstmt != null) {                pstmt.close();
            }
        } catch (SQLException e) {            e.printStackTrace();
        }
        try {
            if (conn != null) {                close(conn);  // private method
            }
        } catch (SQLException e) {            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }    //스프링부트용 DB Connection 풀관리 :
    // DataSourceUtils.getConnection/releaseConnection을 사용해야 함
}
