package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.app.domain.Department;
import ro.teamnet.zth.app.domain.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adina Radu on 5/6/2015.
 */
@MyController(urlPath = "/departments")
public class DepartmentController {

    @MyRequestMethod(urlPath = "/one")
    public String getOneDepartments(){
        return  "oneDepartments";
    }

    @MyRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartments(){

        List<Department> department;
        department = new ArrayList<Department>();

        Department d = new Department();
        d.setId(384);
        d.setDepartmentName("Department Name");

        Department d2 = new Department();
        d2.setId(385);
        d2.setDepartmentName("Department Name2");

        department.add(d);
        department.add(d2);

        return  department;
    }

}
