package com.d8games.web.services.controller;

import com.d8games.web.services.exception.EmployeeNotFound;
import com.d8games.web.services.model.dto.ContactCardDto;
import com.d8games.web.services.model.dto.DashboardCardDto;
import com.d8games.web.services.model.dto.EmployeeCardDto;
import com.d8games.web.services.model.entity.Employee;
import com.d8games.web.services.service.EmployeeService;
import com.d8games.web.services.service.TitleService;
import com.d8games.web.services.config.ConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/services/controller/employee")
@SuppressWarnings("unused")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TitleService titleService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping(value = "/get")
    public Employee getById(@RequestParam String id) {
        return employeeService.getById(id);
    }

    @PutMapping(value = "/save")
    public String save(@RequestParam String mobilePhoneId, @RequestParam String name, @RequestParam String surname,
                       @RequestParam String email, @RequestParam String phoneNumber,
                       @RequestParam String joinDateAsString, @RequestParam String titleId) throws ParseException {
        Employee employee = new Employee();

        employee.setMobilePhoneId(mobilePhoneId);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);

        Date employeeJoinDate = new Date(); // Get the current date

        if (joinDateAsString != null)
            employeeJoinDate =  new SimpleDateFormat(ConfigManager.getDateFormat()).parse(joinDateAsString);

        employee.setJoinDate(employeeJoinDate);
        employee.setTitle(titleService.getById(titleId));

        employeeService.save(employee);
        return employee.getId();
    }

    @GetMapping(value = "/dashboardCard")
    public List<DashboardCardDto> getDashboardCardDtoList() {
        return employeeService.getDashboardCardDtoList();
    }

    @GetMapping(value = "/contactCard")
    public List<ContactCardDto> getContactCardDtoList() {
        return employeeService.getContactCardDtoList();
    }

    @GetMapping(value = "/employeeCard")
    public EmployeeCardDto getEmployeeCardDto(@RequestParam String employeeId) throws EmployeeNotFound {
        EmployeeCardDto employeeCardDto = employeeService.getEmployeeCardDto(employeeId);

        if (employeeCardDto == null)
            throw new EmployeeNotFound(employeeId);

        return employeeCardDto;
    }
}
