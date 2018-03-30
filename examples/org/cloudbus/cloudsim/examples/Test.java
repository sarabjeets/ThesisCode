package org.cloudbus.cloudsim.examples;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Test {
	
	
/**
 * Read the file and input the parameters into the program
 *
 * @param the filepath of the parameter(the text file)
 *
 * @return the array of each kind of parameter
 */



	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
		// TODO Auto-generated method stub
	
	
	double[]r5 = new double[5];
	double[]r10 = new double[10];
	double[]r20 = new double[20];
	double[]r40 = new double[40];
	double[]r60 = new double[60];
	double[]r80 = new double[80];
	double[]r100 = new double[100];
	
	double[]goodAvg6 = new double[50];
	double[]goodAvg11 = new double[goodAvg6.length];
	double[]goodAvg21 = new double[goodAvg6.length];
	double[]goodAvg41 = new double[goodAvg6.length];
	double[]goodAvg61 = new double[goodAvg6.length];
	double[]goodAvg81 = new double[goodAvg6.length];
	double[]goodAvg101 = new double[goodAvg6.length];
	
	double[]badAvg6 = new double[50];
	double[]badAvg11 = new double[goodAvg6.length];
	double[]badAvg21 = new double[goodAvg6.length];
	double[]badAvg41 = new double[goodAvg6.length];
	double[]badAvg61 = new double[goodAvg6.length];
	double[]badAvg81 = new double[goodAvg6.length];
	double[]badAvg101 = new double[goodAvg6.length];
	
	double[]avgAvg6 = new double[50];
	double[]avgAvg11 = new double[goodAvg6.length];
	double[]avgAvg21 = new double[goodAvg6.length];
	double[]avgAvg41 = new double[goodAvg6.length];
	double[]avgAvg61 = new double[goodAvg6.length];
	double[]avgAvg81 = new double[goodAvg6.length];
	double[]avgAvg101 = new double[goodAvg6.length];
	
	
    ArrayList<Double> relinp = new ArrayList<Double> ();	
	//String[] objFile = new String[7];
	String[] path = new String[3];
	//PrintWriter[] printWriter = new PrintWriter[7];
		     
	Random r = new Random();
	double sd = 0.2;
	double meanRpGood = 0.3, meanRpBad = 0.7, meanRpAvg = 0.5 ; 
	
		     
	int[] hisLength={5,10,20,40,60,80,100};
	String pathGood = "/home/singh/CsimSj/preDataGood/r";
	String pathBad = "/home/singh/CsimSj/preDataBad/r";
	String pathAvg = "/home/singh/CsimSj/preDataAvg/r";
	path[0]= pathGood;
	path[1]= pathBad;
	path[2]= pathAvg;
	
	// userBehaviour 0 = good user, 	userBehaviour 1 = bad user, userBehaviour 2 = avg user     
	// for(int userBehavior = 0; userBehavior < path.length; userBehavior++){	    
	
	
	
		   //good user 
		   for (int run=1; run<51; run++ ){
			   
			   
			   
			  
		    
			 
			
		    	 for(int i = 1 ; i < 103; i++){
						
				   double a = r.nextGaussian() * sd + meanRpGood;
					  
						if (a < 0 ){
						 a = 0 ;
						 }
						
						else if (a > 1 ){
						 a = 1;
						}
					//	 System.out.println("a " + a); 
						relinp.add(a);
					 }
		    	 
		    	  
		    	 
		 	       String file1 = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[0])+".txt" ;
		 	       String file2 = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[1])+".txt" ;
		 	       String file3 = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[2])+".txt" ;
		 	       String file4 = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[3])+".txt" ;
		 	       String file5 = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[4])+".txt" ;
		 	       String file6 = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[5])+".txt" ;
		 	       String file7 = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[6])+".txt" ;
		 	      
		 	     
		 	  //    System.out.println(objFile[z]);  
		 	   //   System.out.println(printWriter[z]);   
		 	      PrintWriter h1 = new PrintWriter (file1);
		 	      PrintWriter h2 = new PrintWriter (file2);
		 	      PrintWriter h3 = new PrintWriter (file3);
		 	      PrintWriter h4 = new PrintWriter (file4);
		 	      PrintWriter h5 = new PrintWriter (file5);
		 	      PrintWriter h6 = new PrintWriter (file6);
		 	      PrintWriter h7 = new PrintWriter (file7);
		 	      
		 		
		 		 
		 		// for (int z = 0; z <hisLength.length; z++){
		 			 
		 			
		 			 
		 	         for (int i = 0; i < r5.length; i++){
			    		r5[i]=relinp.get(i) ;
					    h1.println(r5[i]+",");
					    r5[i] = 0;
			           }
				    
					  for (int i = 0; i < r10.length; i++)  {
					   
					    r10[i]=relinp.get(i) ;
					    h2.println(r10[i]+",");
					    r10[i] = 0;
				       }
			       
					  for (int i = 0; i < r20.length; i++){
					   
					    r20[i]=relinp.get(i) ;
					    h3.println(r20[i]+",");
					    r20[i] = 0;
				        }
			       
			       
					   for (int i = 0; i < r40.length; i++){
					   
					    r40[i]=relinp.get(i) ;
					    h4.println(r40[i]+",");
					    r40[i] = 0;
				       }
					   
					   for (int i = 0; i < r60.length; i++){
						   
						    r60[i]=relinp.get(i) ;
						    h5.println(r60[i]+",");
						    r60[i] = 0;
					    }
					   
					   for (int i = 0; i < r80.length; i++){
						   
						    r80[i]=relinp.get(i) ;
						    h6.println(r80[i]+",");
						    r80[i] = 0;
					    }
					   
					   for (int i = 0; i < r100.length; i++){
						   
						    r100[i]=relinp.get(i) ;
						    h7.println(r100[i]+",");
						    r100[i] = 0;
					       }
					  

					  h1.close();
					  h2.close();
					  h3.close();
					  h4.close();
					  h5.close();
					  h6.close();
					  h7.close();
		    	 
		    			
		 		/** for (int z = 0; z <objFile.length; z++){
		 	       String file = path[0] + String.valueOf(run) +"/"+String.valueOf(hisLength[z])+".txt" ;
		 	       objFile[z] = file;
		 	      PrintWriter h = new PrintWriter (objFile[z]);
		 	       printWriter[z] = h;
		 	  //    System.out.println(objFile[z]);  
		 	   //   System.out.println(printWriter[z]);   
		 		  }
		 		//Scanner s =new Scanner(System.in);
				//int aaaaaa = s.nextInt(); 
		 		 for (int z = 0; z <hisLength.length; z++){
		 			 
		 			
		 			 
		 	         for (int i = 0; i < r5.length; i++){
			    		r5[i]=relinp.get(i) ;
					    printWriter[z].println(r5[i]+",");
					    r5[i] = 0;
			           }
				    
					  for (int i = 0; i < r10.length; i++)  {
					   
					    r10[i]=relinp.get(i) ;
					    printWriter[z].println(r10[i]+",");
					    r10[i] = 0;
				       }
			       
					  for (int i = 0; i < r20.length; i++){
					   
					    r20[i]=relinp.get(i) ;
					    printWriter[z].println(r20[i]+",");
					    r20[i] = 0;
				        }
			       
			       
					   for (int i = 0; i < r40.length; i++){
					   
					    r40[i]=relinp.get(i) ;
					    printWriter[z].println(r40[i]+",");
					    r40[i] = 0;
				       }
					   
					   for (int i = 0; i < r60.length; i++){
						   
						    r60[i]=relinp.get(i) ;
						    printWriter[z].println(r60[i]+",");
						    r60[i] = 0;
					    }
					   
					   for (int i = 0; i < r80.length; i++){
						   
						    r80[i]=relinp.get(i) ;
						    printWriter[z].println(r80[i]+",");
						    r80[i] = 0;
					    }
					   
					   for (int i = 0; i < r100.length; i++){
						   
						    r100[i]=relinp.get(i) ;
						    printWriter[z].println(r100[i]+",");
						    r100[i] = 0;
					       }
			       
					   printWriter[z].close();**/
			           goodAvg6[run-1] = relinp.get(6);
			           goodAvg11[run-1] = relinp.get(11);
			           goodAvg21[run-1] = relinp.get(21);
			           goodAvg41[run-1] = relinp.get(41);
			           goodAvg61[run-1] = relinp.get(61);
			           goodAvg81[run-1] = relinp.get(81);
			           goodAvg101[run-1] = relinp.get(101);
			         
			           //      System.out.println("6 for run " + run +" = " + relinp.get(6));
			           //      System.out.println("11 for run " + run + " = " +relinp.get(11));
			           //      System.out.println("21 for run " + run +" = " + relinp.get(21));
			           //      System.out.println("41 for run " + run + " = " +relinp.get(41));
			           //      System.out.println("61 for run " + run + " = " +relinp.get(61));
			           //     System.out.println("81 for run " + run + " = " +relinp.get(81));
			           //      System.out.println("101 for run " + run + " = " +relinp.get(101));
			         
			           //System.out.println("");  
					 
			           relinp.clear();
			    }
		 	// }
		   
		   //bad user
		   for (int run=1; run<51; run++ ){
			   
			   
			   
				  
			    
				 
				
		    	 for(int i = 1 ; i < 103; i++){
						
				   double a = r.nextGaussian() * sd + meanRpBad;
					  
						if (a < 0 ){
						 a = 0 ;
						 }
						
						else if (a > 1 ){
						 a = 1;
						}
					//	 System.out.println("a " + a); 
						relinp.add(a);
					 }
		    	 
		    	  
		      
		    	 
		    			
		    	 
		 	       String file1 = path[1] + String.valueOf(run) +"/"+String.valueOf(hisLength[0])+".txt" ;
		 	       String file2 = path[1] + String.valueOf(run) +"/"+String.valueOf(hisLength[1])+".txt" ;
		 	       String file3 = path[1] + String.valueOf(run) +"/"+String.valueOf(hisLength[2])+".txt" ;
		 	       String file4 = path[1] + String.valueOf(run) +"/"+String.valueOf(hisLength[3])+".txt" ;
		 	       String file5 = path[1] + String.valueOf(run) +"/"+String.valueOf(hisLength[4])+".txt" ;
		 	       String file6 = path[1] + String.valueOf(run) +"/"+String.valueOf(hisLength[5])+".txt" ;
		 	       String file7 = path[1] + String.valueOf(run) +"/"+String.valueOf(hisLength[6])+".txt" ;
		 	      
		 	     
		 	  //    System.out.println(objFile[z]);  
		 	   //   System.out.println(printWriter[z]);   
		 	      PrintWriter h1 = new PrintWriter (file1);
		 	      PrintWriter h2 = new PrintWriter (file2);
		 	      PrintWriter h3 = new PrintWriter (file3);
		 	      PrintWriter h4 = new PrintWriter (file4);
		 	      PrintWriter h5 = new PrintWriter (file5);
		 	      PrintWriter h6 = new PrintWriter (file6);
		 	      PrintWriter h7 = new PrintWriter (file7);
		 	      
		 		
		 		 
		 		// for (int z = 0; z <hisLength.length; z++){
		 			 
		 			
		 			 
		 	         for (int i = 0; i < r5.length; i++){
			    		r5[i]=relinp.get(i) ;
					    h1.println(r5[i]+",");
					    r5[i] = 0;

			           }
				    
					  for (int i = 0; i < r10.length; i++)  {
					   
					    r10[i]=relinp.get(i) ;
					    h2.println(r10[i]+",");
					    r10[i] = 0;
				       }
			       
					  for (int i = 0; i < r20.length; i++){
					   
					    r20[i]=relinp.get(i) ;
					    h3.println(r20[i]+",");
					    r20[i] = 0;
				        }
			       
			       
					   for (int i = 0; i < r40.length; i++){
					   
					    r40[i]=relinp.get(i) ;
					    h4.println(r40[i]+",");
					    r40[i] = 0;
				       }
					   
					   for (int i = 0; i < r60.length; i++){
						   
						    r60[i]=relinp.get(i) ;
						    h5.println(r60[i]+",");
						    r60[i] = 0;
					    }
					   
					   for (int i = 0; i < r80.length; i++){
						   
						    r80[i]=relinp.get(i) ;
						    h6.println(r80[i]+",");
						    r80[i] = 0;
					    }
					   
					   for (int i = 0; i < r100.length; i++){
						   
						    r100[i]=relinp.get(i) ;
						    h7.println(r100[i]+",");
						    r100[i] = 0;
					       }
			       
					  h1.close();
					  h2.close();
					  h3.close();
					  h4.close();
					  h5.close();
					  h6.close();
					  h7.close();
			           
					   badAvg6[run-1] = relinp.get(6);
					   badAvg11[run-1] = relinp.get(11);
					   badAvg21[run-1] = relinp.get(21);
					   badAvg41[run-1] = relinp.get(41);
					   badAvg61[run-1] = relinp.get(61);
					   badAvg81[run-1] = relinp.get(81);
					   badAvg101[run-1] = relinp.get(101);
					  
					   //   System.out.println("6 for run " + run +" = " + relinp.get(6));
			           //  System.out.println("11 for run " + run + " = " +relinp.get(11));
			           //  System.out.println("21 for run " + run +" = " + relinp.get(21));
			           //    System.out.println("41 for run " + run + " = " +relinp.get(41));
			           //    System.out.println("61 for run " + run + " = " +relinp.get(61));
			           //     System.out.println("81 for run " + run + " = " +relinp.get(81));
			           //     System.out.println("101 for run " + run + " = " +relinp.get(101));
			         
					   //   System.out.println("");  
					 
			           relinp.clear();
			    }
		 	// }
		   
		   
		   //avg user
		   for (int run=1; run<51; run++ ){
			   
			   
			   
				  
			    
				 
				
		    	 for(int i = 1 ; i < 103; i++){
						
				   double a = r.nextGaussian() * sd + meanRpAvg;
					  
						if (a < 0 ){
						 a = 0 ;
						 }
						
						else if (a > 1 ){
						 a = 1;
						}
					//	 System.out.println("a " + a); 
						relinp.add(a);
					 }
		    	 
		    	  
		      
		    	 
		    			
		 		 
		 	       String file1 = path[2] + String.valueOf(run) +"/"+String.valueOf(hisLength[0])+".txt" ;
		 	       String file2 = path[2] + String.valueOf(run) +"/"+String.valueOf(hisLength[1])+".txt" ;
		 	       String file3 = path[2] + String.valueOf(run) +"/"+String.valueOf(hisLength[2])+".txt" ;
		 	       String file4 = path[2] + String.valueOf(run) +"/"+String.valueOf(hisLength[3])+".txt" ;
		 	       String file5 = path[2] + String.valueOf(run) +"/"+String.valueOf(hisLength[4])+".txt" ;
		 	       String file6 = path[2] + String.valueOf(run) +"/"+String.valueOf(hisLength[5])+".txt" ;
		 	       String file7 = path[2] + String.valueOf(run) +"/"+String.valueOf(hisLength[6])+".txt" ;
		 	      
		 	     
		 	  //    System.out.println(objFile[z]);  Average of good for 6 : 0.253640175993542
		 	   //   System.out.println(printWriter[z]);   
		 	      PrintWriter h1 = new PrintWriter (file1);
		 	      PrintWriter h2 = new PrintWriter (file2);
		 	      PrintWriter h3 = new PrintWriter (file3);
		 	      PrintWriter h4 = new PrintWriter (file4);
		 	      PrintWriter h5 = new PrintWriter (file5);
		 	      PrintWriter h6 = new PrintWriter (file6);
		 	      PrintWriter h7 = new PrintWriter (file7);
		 	      
		 		
		 		 
		 		// for (int z = 0; z <hisLength.length; z++){
		 			 
		 			
		 			 
		 	         for (int i = 0; i < r5.length; i++){
			    		r5[i]=relinp.get(i) ;
					    h1.println(r5[i]+",");
					    r5[i] = 0;
			           }
				    
					  for (int i = 0; i < r10.length; i++)  {
					   
					    r10[i]=relinp.get(i) ;
					    h2.println(r10[i]+",");
					    r10[i] = 0;
				       }
			       
					  for (int i = 0; i < r20.length; i++){
					   
					    r20[i]=relinp.get(i) ;
					    h3.println(r20[i]+",");
					    r20[i] = 0;
				        }
			       
			       
					   for (int i = 0; i < r40.length; i++){
					   
					    r40[i]=relinp.get(i) ;
					    h4.println(r40[i]+",");
					    r40[i] = 0;
				       }
					   
					   for (int i = 0; i < r60.length; i++){
						   
						    r60[i]=relinp.get(i) ;
						    h5.println(r60[i]+",");
						    r60[i] = 0;
					    }
					   
					   for (int i = 0; i < r80.length; i++){
						   
						    r80[i]=relinp.get(i) ;
						    h6.println(r80[i]+",");
						    r80[i] = 0;
					    }
					   
					   for (int i = 0; i < r100.length; i++){
						   
						    r100[i]=relinp.get(i) ;
						    h7.println(r100[i]+",");
						    r100[i] = 0;
					       }
			       
					  h1.close();
					  h2.close();
					  h3.close();
					  h4.close();
					  h5.close();
					  h6.close();
					  h7.close();
			           
			         
					   avgAvg6[run-1] = relinp.get(6);
					   avgAvg11[run-1] = relinp.get(11);
					   avgAvg21[run-1] = relinp.get(21);
					   avgAvg41[run-1] = relinp.get(41);
					   avgAvg61[run-1] = relinp.get(61);
					   avgAvg81[run-1] = relinp.get(81);
					   avgAvg101[run-1] = relinp.get(101);
					  
			        //   System.out.println("6 for run " + run +" = " + relinp.get(6));
					   //   System.out.println("11 for run " + run + " = " +relinp.get(11));
					   //   System.out.println("21 for run " + run +" = " + relinp.get(21));
					   //  System.out.println("41 for run " + run + " = " +relinp.get(41));
					   //  System.out.println("61 for run " + run + " = " +relinp.get(61));
					   //  System.out.println("81 for run " + run + " = " +relinp.get(81));
					   //  System.out.println("101 for run " + run + " = " +relinp.get(101));
			         
					   //  System.out.println("");  
					 
			           relinp.clear();
			    }
		 	// }
	// }	  
		    	  
		 //	    System.out.println(file);
		 			    
			 
		    	// Scanner s =new Scanner(System.in);
				//	int aaaaaa = s.nextInt();      
				//	  String file = path + String.valueOf(run) +"/"+String.valueOf(run)+".txt"  ;				
					 // PrintWriter h5 = new PrintWriter (objFile[0]);
			    	//  PrintWriter h10 = new PrintWriter (objFile[1]);
			    	 // PrintWriter h20 = new PrintWriter (objFile[2]);
			    	//  PrintWriter h40 = new PrintWriter (objFile[3]);
			    	//  PrintWriter h60 = new PrintWriter (objFile[i]);
			    	//  PrintWriter h80 = new PrintWriter (objFile[i]);
			    	//  PrintWriter h100 = new PrintWriter (objFile[i]);
			    	  
		     double sum6g = 0,sum11g = 0,sum21g = 0,sum41g = 0,sum61g = 0,sum81g = 0,sum101g = 0;
		     double sum6b = 0,sum11b = 0,sum21b = 0,sum41b = 0,sum61b = 0,sum81b = 0,sum101b = 0;
		     double sum6a = 0,sum11a = 0,sum21a = 0,sum41a = 0,sum61a = 0,sum81a = 0,sum101a = 0;
		     
		     double v6g=0, v11g=0, v21g=0, v41g=0, v61g=0, v81g=0, v101g=0,v6b=0, v11b=0, v21b=0, v41b=0, v61b=0, v81b=0, v101b=0,v6a=0, v11a=0, v21a=0, v41a=0, v61a=0, v81a=0, v101a  = 0;
		     double sd6g=0,sd11g=0,sd21g=0,sd41g=0,sd61g=0,sd81g=0,sd101g=0,sd6b=0,sd11b=0,sd21b=0,sd41b=0,sd61b=0,sd81=0,sd101b=0,sd6a=0,sd11a=0,sd21a=0,sd41a=0,sd61a=0,sd81a=0,sd101a=0;
             double l6g=0,l11g=0,l21g=0,l41g=0,l61g=0,l81g=0,l101g=0,l6b=0,l11b=0,l21b=0,l61b=0,l41b=0,l81b=0,l101b=0,l6a=0,l21a=0,l41a=0,l61a=0,l81a=0,l11a=0,l101a=0;
             double u6g=0,u11g=0,u21g=0,u41g=0,u61g=0,u81g=0,u101g=0,u6b=0,u11b=0,u21b=0,u61b=0,u41b=0,u81b=0,u101b=0,u6a=0,u21a=0,u41a=0,u61a=0,u81a=0,u11a=0,u101a=0;
             
             
             for(int i=0; i < avgAvg6.length ; i++){
                     sum6g = sum6g + goodAvg6[i];
                     sum6b = sum6b + badAvg6[i];
                     sum6a = sum6a + avgAvg6[i];
             }
             
             for(int i=0; i < avgAvg11.length ; i++){
                 sum11g = sum11g + goodAvg11[i];
                 sum11b = sum11b + badAvg11[i];
                 sum11a = sum11a + avgAvg11[i];
         }
             for(int i=0; i < avgAvg21.length ; i++){
                 sum21g = sum21g + goodAvg21[i];
                 sum21b = sum21b + badAvg21[i];
                 sum21a = sum21a + avgAvg21[i];
         }
             for(int i=0; i < avgAvg41.length ; i++){
                 sum41g = sum41g + goodAvg41[i];
                 sum41b = sum41b + badAvg41[i];
                 sum41a = sum41a + avgAvg41[i];
         }
             for(int i=0; i < avgAvg61.length ; i++){
                 sum61g = sum61g + goodAvg61[i];
                 sum61b = sum61b + badAvg61[i];
                 sum61a = sum61a + avgAvg61[i];
         }
             for(int i=0; i < avgAvg81.length ; i++){
                 sum81g = sum81g + goodAvg81[i];
                 sum81b = sum81b + badAvg81[i];
                 sum81a = sum81a + avgAvg81[i];
         }
             for(int i=0; i < avgAvg101.length ; i++){
                 sum101g = sum101g + goodAvg101[i];
                 sum101b = sum101b + badAvg101[i];
                 sum101a = sum101a + avgAvg101[i];
         }
             
             
             //calculate CI
             double average41g = sum41g / avgAvg41.length;
             double average41b = sum41b / avgAvg41.length;
             double average41a = sum41a / avgAvg41.length;
             //good
             for (int i=0; i < avgAvg41.length; i++){
            	 v41g += (goodAvg41[i]- average41g)*(goodAvg41[i]- average41g);
             }
              sd41g = Math.sqrt(v41g/avgAvg41.length);
              l41g = average41g  - ((1.96* sd41g)/Math.sqrt(avgAvg41.length));
              u41g = average41g  + ((1.96* sd41g)/Math.sqrt(avgAvg41.length));
              
              //bad
              for (int i=0; i < avgAvg41.length; i++){
             	 v41b += (badAvg41[i]- average41b)*(badAvg41[i]- average41b);
              }
               sd41b = Math.sqrt(v41b/avgAvg41.length);
               l41b = average41b  - ((1.96* sd41b)/Math.sqrt(avgAvg41.length));
               u41b = average41b  + ((1.96* sd41b)/Math.sqrt(avgAvg41.length));
               
               
               //avg
               for (int i=0; i < avgAvg41.length; i++){
              	 v41a += (avgAvg41[i]- average41a)*(avgAvg41[i]- average41a);
               }
                sd41a = Math.sqrt(v41a/avgAvg41.length);
                l41a = average41a  - ((1.96* sd41a)/Math.sqrt(avgAvg41.length));
                u41a = average41a  + ((1.96* sd41a)/Math.sqrt(avgAvg41.length));
              
                
                
                
                //61
              double average61g = sum61g / avgAvg61.length;
              double average61b = sum61b / avgAvg61.length;
              double average61a = sum61a / avgAvg61.length;
              
              for (int i=0; i < avgAvg61.length; i++){
             	 v61g += (goodAvg61[i]- average61g)*(goodAvg61[i]- average61g);
              }
               sd61g = Math.sqrt(v61g/avgAvg61.length);
               l61g = average61g  - ((1.96* sd61g)/Math.sqrt(avgAvg61.length));
               u61g = average61g  + ((1.96* sd61g)/Math.sqrt(avgAvg61.length));
              
               
               //bad
               for (int i=0; i < avgAvg61.length; i++){
              	 v61b += (badAvg61[i]- average61b)*(badAvg61[i]- average61b);
               }
                sd61b = Math.sqrt(v61b/avgAvg61.length);
                l61b = average61b  - ((1.96* sd61b)/Math.sqrt(avgAvg61.length));
                u61b = average61b  + ((1.96* sd61b)/Math.sqrt(avgAvg61.length));
                
                
                //avg
                for (int i=0; i < avgAvg61.length; i++){
               	 v61a += (avgAvg61[i]- average61a)*(avgAvg61[i]- average61a);
                }
                 sd61a = Math.sqrt(v41a/avgAvg41.length);
                 l61a = average61a  - ((1.96* sd61a)/Math.sqrt(avgAvg61.length));
                 u61a = average61a  + ((1.96* sd61a)/Math.sqrt(avgAvg61.length));
               
               
               
               
               double average81g = sum81g / avgAvg81.length;
               double average81b = sum81b / avgAvg81.length;
               double average81a = sum81a / avgAvg81.length;
               
               for (int i=0; i < avgAvg81.length; i++){
              	 v81g += (goodAvg81[i]- average81g)*(goodAvg81[i]- average81g);
               }
                sd81g = Math.sqrt(v81g/avgAvg81.length);
                l81g = average81g  - ((1.96* sd81g)/Math.sqrt(avgAvg81.length));
                u81g = average81g  + ((1.96* sd81g)/Math.sqrt(avgAvg81.length));
                
                
                //bad
                for (int i=0; i < avgAvg81.length; i++){
               	 v81b += (badAvg81[i]- average81b)*(badAvg81[i]- average81b);
                }
                double sd81b = Math.sqrt(v81b/avgAvg81.length);
                 l81b = average81b  - ((1.96* sd81b)/Math.sqrt(avgAvg81.length));
                 u81b = average81b  + ((1.96* sd81b)/Math.sqrt(avgAvg81.length));
                 
                 
                 //avg
                 for (int i=0; i < avgAvg81.length; i++){
                	 v81a += (avgAvg81[i]- average81a)*(avgAvg81[i]- average81a);
                 }
                  sd81a = Math.sqrt(v81a/avgAvg81.length);
                  l81a = average81a  - ((1.96* sd81a)/Math.sqrt(avgAvg81.length));
                  u81a = average81a  + ((1.96* sd81a)/Math.sqrt(avgAvg81.length));
                
                
                
                
                
                double average101g = sum101g / avgAvg101.length;
                double average101b = sum101b / avgAvg101.length;
                double average101a = sum101a / avgAvg101.length;
                
                for (int i=0; i < avgAvg101.length; i++){
               	 v101g += (goodAvg101[i]- average101g)*(goodAvg101[i]- average101g);
                }
                 sd101g = Math.sqrt(v101g/avgAvg101.length);
                 l101g = average101g  - ((1.96* sd101g)/Math.sqrt(avgAvg101.length));
                 u101g = average101g  + ((1.96* sd101g)/Math.sqrt(avgAvg101.length));
              
              
                 //bad
                 for (int i=0; i < avgAvg101.length; i++){
                	 v101b += (badAvg101[i]- average101b)*(badAvg101[i]- average101b);
                 }
                  sd101b = Math.sqrt(v101b/avgAvg101.length);
                  l101b = average101b  - ((1.96* sd101b)/Math.sqrt(avgAvg101.length));
                  u101b = average101b  + ((1.96* sd101b)/Math.sqrt(avgAvg101.length));
                  
                  
                  //avg
                  for (int i=0; i < avgAvg101.length; i++){
                 	 v101a += (avgAvg101[i]- average101a)*(avgAvg101[i]- average101a);
                  }
                   sd101a = Math.sqrt(v101a/avgAvg101.length);
                   l101a = average101a  - ((1.96* sd101a)/Math.sqrt(avgAvg101.length));
                   u101a = average101a  + ((1.96* sd101a)/Math.sqrt(avgAvg101.length));
              
              
              
              
              
              
              
                 double average6g = sum6g / avgAvg6.length;
                 double average6b = sum6b / avgAvg6.length;
                 double average6a = sum6a / avgAvg6.length;
                 
                 double average11g = sum11g / avgAvg11.length;
                 double average11b = sum11b / avgAvg11.length;
                 double average11a = sum11a / avgAvg11.length;
                 
                 double average21g = sum21g / avgAvg21.length;
                 double average21b = sum21b / avgAvg21.length;
                 double average21a = sum21a / avgAvg21.length;
              
             System.out.println("Average of good for 6 : " + average6g);  
             System.out.println("Average of bad for 6 : " + average6b);  
             System.out.println("Average of avg for 6 : " + average6a);  
             System.out.println(" " );
             
            
             System.out.println("Average of good for 11 : " + average11g);  
             System.out.println("Average of bad for 11 : " + average11b);  
             System.out.println("Average of avg for 11 : " + average11a);  
             System.out.println(" " );
             
             System.out.println("Average of good for 21 : " + average21g);  
             System.out.println("Average of bad for 21 : " + average21b);  
             System.out.println("Average of avg for 21 : " + average21a);  
             System.out.println(" " );
             
            
             System.out.println("Average of good for 41 : " + average41g + " CI " + l41g + " - " + u41g); 
             System.out.println("Average of bad for 41 : " + average41b+ " CI " + l41b + " - " + u41b);  
             System.out.println("Average of avg for 41 : " + average41a+ " CI " + l41a + " - " + u41a);  
             System.out.println(" " );
             
            
             System.out.println("Average of good for 61: " + average61g+ " CI " + l61g + " - " + u61g);  
             System.out.println("Average of bad for 61 : " + average61b+ " CI " + l61b + " - " + u61b);   
             System.out.println("Average of avg for 61 : " + average61a+ " CI " + l61a + " - " + u61a);  
             System.out.println(" " );
             
            
             System.out.println("Average of good for 81 : " + average81g+ " CI " + l81g + " - " + u81g);   
             System.out.println("Average of bad for 81 : " + average81b+ " CI " + l81b + " - " + u81b);   
             System.out.println("Average of avg for 81 : " + average81a+ " CI " + l81a + " - " + u81a);   
             System.out.println(" " ); 
             
            
             System.out.println("Average of good for 101 : " + average101g+ " CI " + l101g + " - " + u101g);   
             System.out.println("Average of bad for 101 : " + average101b+ " CI " + l101b + " - " + u101b);  
             System.out.println("Average of avg for 101 : " + average101a+ " CI " + l101a + " - " + u101a);     
             
           
             
             
             
             
             
             
             
             
             
          
				
            
             
			       
					
		}
			
	}
		
		   
		    
	
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		 
		 
			 
			 
		
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 




				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			