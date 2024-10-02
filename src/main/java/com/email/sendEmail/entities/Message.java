package com.email.sendEmail.entities;

import java.util.Arrays;
import java.util.List;


public class Message {
    
    private String from;
    private String content;
    private List<String> files;
    private String subject;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Message(String content, List<String> files, String subject) {
		super();
		this.content = content;
		this.files = files;
		this.subject = subject;
	}
    
    
    
    
    
	
 
}
