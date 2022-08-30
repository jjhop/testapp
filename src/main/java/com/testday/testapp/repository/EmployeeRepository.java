package com.testday.testapp.repository;

import com.testday.testapp.model.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    private final RowMapper<Employee> employeeMapper = (rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setDateAdded(rs.getTimestamp("date_added").toLocalDateTime());
        employee.setEmail(rs.getString("email"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setPassword(rs.getString("password"));
        return employee;
    };

    private final SimpleJdbcInsert insert;
    private final JdbcTemplate jdbc;

    public EmployeeRepository(DataSource ds) {
        this.jdbc = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds)
            .usingGeneratedKeyColumns("id")
            .usingColumns("email", "first_name", "last_name", "username", "password")
            .withTableName("employees");
    }

    public List<Employee> fetchAll() {
        return jdbc.query("SELECT * FROM employees ORDER BY username", employeeMapper);
    }

    public void save(Employee employee) {
        Map<String, Object> params = new HashMap<>() {{
           put("email", employee.getEmail());
           put("first_name", employee.getFirstName());
           put("last_name", employee.getLastName());
           put("username", employee.getUsername());
           put("password", employee.getPassword());
        }};
        Long id = this.insert.executeAndReturnKey(params).longValue();
        employee.setId(id);
    }

}
