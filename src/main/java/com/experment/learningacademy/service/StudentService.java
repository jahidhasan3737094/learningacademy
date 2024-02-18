package com.experment.learningacademy.service;

import com.experment.learningacademy.model.Student;
import com.experment.learningacademy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    //private StudentRepository studentRepository;
    private JavaMailSender mailSender;
    public String registerStudent(Student student){

        this.studentRepository.save(student);

        String toEmail=student.getEmail();
        System.out.println(student.getStudentId());

        String text="hi Mr."+student.getFirstName() + " Thank you for registration,please confirm" +
                "your registrtion by click on the below link ";
        String link=" http://localhost:8080/"+"confirm/"+student.getStudentId();

        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("jahidhasansaif095@gmail.com");
        message.setTo(toEmail);
        message.setSubject("mail for confirmation registration.");
        message.setText(text+link);
        this.mailSender.send(message);

        return "mail sent successfully";
    }

    public String confirmRegistration(Integer confirmToken) {

        Student student=this.studentRepository.findById(confirmToken).get();
        student.setRegistrationConfirmation(true);
        this.studentRepository.save(student);
        System.out.println("confirmation");
        return "confirmation successfull... now you can login...";
    }
}
