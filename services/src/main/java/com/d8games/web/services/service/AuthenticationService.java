package com.d8games.web.services.service;

import com.d8games.web.services.config.ConfigManager;
import com.d8games.web.services.model.dto.AuthenticatedEmployeeDto;
import com.d8games.web.services.model.dto.AuthenticationDto;
import com.d8games.web.services.model.entity.Authentication;
import com.d8games.web.services.model.entity.Employee;
import com.d8games.web.services.repository.AuthenticationRepository;
import com.d8games.web.services.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 14400000)
    public void removeAuthenticationsEachNight() {
        Date currentDate = DateUtil.getCurrentDate();
        DateUtil dateUtil = new DateUtil(currentDate);

        if (DateUtil.isNight(dateUtil.getIntegerHour())) {
            authenticationRepository.deleteAll();
        }
    }

    public List<Authentication> getAll() {
        return authenticationRepository.findAll();
    }

    public Authentication getById(String id) {
        return authenticationRepository.getAuthenticationById(id);
    }

    public Authentication getLastAuthenticationByEmployeeId(String employeeId) {
        List<Authentication> authenticationList = authenticationRepository.
                getAuthenticationListByEmployeeId(employeeId);
        return authenticationList.get(0);
    }

    public void update(String id, String ip, String mobilePhoneId) {
        Authentication authentication = authenticationRepository.getAuthenticationById(id);

        authentication.setIp(ip);
        authentication.setMobilePhoneId(mobilePhoneId);

        authenticationRepository.save(authentication);
    }

    public void save() {
        Authentication authentication = new Authentication();
        authentication.setCreatedDate(DateUtil.getCurrentDate());

        authenticationRepository.save(authentication);
    }

    public Employee requestAuth(String employeeId) throws IOException, MessagingException {
        Authentication authentication = new Authentication();
        Employee employee = employeeService.getById(employeeId);

        authentication.setCreatedDate(DateUtil.getCurrentDate());
        authentication.setMobilePhoneId(employee.getMobilePhoneId());

        authenticationRepository.save(authentication);
        sendEmail(authentication, employee);

        return employee;
    }

    private void sendEmail(Authentication authentication, Employee employee) throws IOException, MessagingException {
        final List<String> recipients = employeeService.getAllAdminEmails();
        final String employeeFullName = employee.getName() + " " + employee.getSurname();
        final String authKey = authentication.getId();

        emailService.sendMail(recipients, employeeFullName, authKey);
    }

    public AuthenticatedEmployeeDto getAuthenticatedEmployeeDto(String authenticationId) throws TimeoutException {
        int authenticationTimeout = ConfigManager.getAuthenticationTimeout();
        long start = System.currentTimeMillis();

        String mobilePhoneId = null;
        AuthenticationDto authenticationDto = null;

        while (mobilePhoneId == null) {
            long timeElapsed = System.currentTimeMillis() - start;

            authenticationDto = authenticationRepository.getAuthenticationDto(authenticationId);
            mobilePhoneId = authenticationDto.getMobilePhoneId();

            if (timeElapsed >= authenticationTimeout) {
                String message = "You have to take an action in " + authenticationTimeout / 1000 + " seconds!";
                throw new TimeoutException(message);
            }
        }

        String employeeId = employeeService.getEmployeeIdByMobilePhoneId(mobilePhoneId);
        String ip = authenticationDto.getIp();

        return new AuthenticatedEmployeeDto(employeeId, ip);
    }
}
