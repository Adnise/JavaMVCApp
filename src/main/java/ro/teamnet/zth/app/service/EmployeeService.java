package ro.teamnet.zth.app.service;

import ro.teamnet.zth.app.domain.Employee;

import java.util.List;

/**
 * Created by Adina Radu on 5/7/2015.
 */
public interface EmployeeService {

    public List<Employee> findAllEmployees();
    public Employee findOneEmployee(Integer id);

}
