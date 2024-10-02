package com.email.sendEmail.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.email.sendEmail.entities.CustomResponse;
import com.email.sendEmail.entities.EmailRequest;
import com.email.sendEmail.services.EmailService;

import jakarta.mail.Multipart;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin("*")

public class EmailController {
	
	private EmailService emailService;
	
	
	public EmailController(EmailService emailService) {
		this.emailService=emailService;
	}
	
	@GetMapping("/test")
	
	public String home() {
		return "Welcome to EmailSending Application" ;
	}
	
	
	@PostMapping("/send")
	public ResponseEntity<?>sendEmail(@RequestBody EmailRequest request){
		
		emailService.sendEmailWithHtml(request.getTo(), request.getSubject(), request.getMessage());
		
		return ResponseEntity.ok(CustomResponse.builder().message("Email Sent Successfully").httpStatus(HttpStatus.OK).success(true).build()         );
		
	}
	
	@PostMapping("/send-with-file")
	public ResponseEntity<?> sendWithFile(@RequestPart("request") EmailRequest request  ,  @RequestPart("files") MultipartFile[] files     ) throws IOException{
		
		System.out.println("Request is comming from front-end..");
		
//		InputStream[]inputStream=new InputStream[files.length];
//		
//		for(int i=0;i<files.length;i++)
//			inputStream[i]=files[i].getInputStream();
//		
		
		emailService.sendEmailWithFiles(request.getTo(), request.getSubject(), request.getMessage(), files);
		
		
		
		
		return ResponseEntity.ok(CustomResponse.builder().message("Email Sent Successfully").httpStatus(HttpStatus.OK).success(true).build()         );

		
	}
	
	
	
	

}
