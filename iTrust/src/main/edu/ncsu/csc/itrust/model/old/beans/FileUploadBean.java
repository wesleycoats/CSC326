package edu.ncsu.csc.itrust.model.old.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.servlet.http.Part;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;


@Named(value = "FileUploadBean")
@RequestScoped
public class FileUploadBean {
	
	private Part uploadedFile;
	private String text;
	
	public Part getUploadedFile(){
		return uploadedFile;
	}
	
	public void setUploadFile (Part uploadedFile){
		this.uploadedFile = uploadedFile;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public FileUploadBean(){
		
	}
	
	public void upload() {

        if (null != uploadedFile) {
            try {
                InputStream is = uploadedFile.getInputStream();
                text = new Scanner(is).useDelimiter(",").next();
                
            } catch (IOException ex) {
            }
        }
    }

}
