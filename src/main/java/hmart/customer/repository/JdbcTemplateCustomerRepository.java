package hmart.customer.repository;

import hmart.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateCustomerRepository implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public JdbcTemplateCustomerRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        //JdbcTemplate은 DI 대상이 아님. DI 받는 DataSource로부터 Derive
    }
    @Override
    public Customer save(Customer customer) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        //jdbcInsert.withTableName("customer").usingGeneratedKeyColumns("id");
        jdbcInsert.withTableName("customer");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", customer.getId());
        parameters.put("name", customer.getName());
        parameters.put("age", customer.getName());
        parameters.put("levels", customer.getName());
        parameters.put("job", customer.getName());
        parameters.put("point", customer.getName());
        jdbcInsert.execute(parameters);
        // 앞의 JDBCTemplate 예제의 insert와는 다른 접근방법. insert문을 사용
//        String insertQuery = "INSERT INTO customer (id, name) VALUES (?, ?)";
//        jdbcTemplate.update(insertQuery, customer.getId(), customer.getName());
        return customer;
    }
    @Override
    public Optional<Customer> findById(String id) {
        List<Customer> result = jdbcTemplate.query("select * from customer where id = ?",
                customerRowMapper(), id);
        return result.stream().findAny();
    }
    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer", customerRowMapper());
    }
    @Override
    public Optional<Customer> findByName(String name) {
        List<Customer> result = jdbcTemplate.query("select * from customer where name = ?",
                customerRowMapper(), name);
        return result.stream().findAny();
    }
    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
            customer.setName(rs.getString("name"));
            customer.setAge(rs.getInt("age"));
            customer.setLevels(rs.getString("levels"));
            customer.setJob(rs.getString("job"));
            customer.setPoint(rs.getInt("point"));
            return customer;
            // 결과가 나오늘 것을 RowMapper가 매핑.역 ROM (DB 결과를 객체(Domain Entity)로 변환
            // RS와 행번호로부터 하나의 객체를 생성. 건별 처리
        };
    }
    // 위의 함수는 아래의 함수로부터 람다 축약된 것임
//    private RowMapper<Customer> customerRowMapper() {
//        return new RowMapper<Customer>() {
//            @Override
//            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Customer customer = new Customer();
//                customer.setId(rs.getString("id"));
//                customer.setName(rs.getString("name"));
//                customer.setAge(rs.getInt("age"));
//                customer.setLevels(rs.getString("levels"));
//                customer.setJob(rs.getString("job"));
//                customer.setPoint(rs.getInt("point"));
//                return customer;
//            }
//        }
//    }
}