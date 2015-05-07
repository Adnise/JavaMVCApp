package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.app.domain.Employee;
import ro.teamnet.zth.app.service.EmployeeService;
import ro.teamnet.zth.app.service.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adina Radu on 5/6/2015.
 */
@MyController(urlPath = "/employees")
public class EmployeeController {


@MyRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees(){
        EmployeeServiceImpl emp =  new EmployeeServiceImpl();
        return emp.findAllEmployees();
    }

    @MyRequestMethod(urlPath = "/one")
    public Employee getOneEmployee (@MyRequestParam(name ="idEmployee",type=String.class)String id){
        EmployeeService empOne = new EmployeeServiceImpl();
        return empOne.findOneEmployee(Integer.valueOf(id));
        }


}
