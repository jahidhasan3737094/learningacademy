package com.experment.learningacademy.controller;

import com.experment.learningacademy.entity.LoginEntity;
import com.experment.learningacademy.model.Student;
import com.experment.learningacademy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public String registerStudent(@RequestBody Student student){
        return this.studentService.registerStudent(student);
    }

    @GetMapping("/confirm/{confirmToken}")
    public String confirmRegistration(@PathVariable Integer confirmToken){
        return this.studentService.confirmRegistration(confirmToken);
    }
    @PostMapping("/auth/user")
    public String getLoggedInUserDetails(){
        return "login success";
    }

}
