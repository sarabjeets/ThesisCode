package org.cloudbus.cloudsim.examples;

import java.io.*;
import java.util.*;
import java.lang.*;

public class CreateFile {
	
	private Formatter x;
	
	public void openfile(){
		
	
		try {
		      x = new Formatter("DreadFile.txt");
		   }
				
				catch (Exception e){
					System.out.println("you have an error");
				}
	}
	
}
	
	

	
	


