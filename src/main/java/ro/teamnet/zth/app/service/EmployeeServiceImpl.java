package ro.teamnet.zth.app.service;

import ro.teamnet.zth.app.dao.EmployeeDao;
import ro.teamnet.zth.app.domain.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adina Radu on 5/7/2015.
 */
public class EmployeeServiceImpl implements EmployeeService{

    public List< Employee> findAllEmployees(){
       EmployeeDao empAll = new EmployeeDao();
        return empAll.getAllEmployees();

    }

    @Override
    public Employee findOneEmployee(Integer id) {
        EmployeeDao empOne = new EmployeeDao();
        return empOne.getEmployeeById(id);
    }

}
