package org.cloudbus.cloudsim.examples;

//import java.util.*;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import java.lang.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.io.*;
//import java.util.Arrays.*;
//java.util.stream.*;
public class EconomicModel {

	public static void main(String[] args) {
		//int[] userid = {1,2,3,4,5,6,7,8,9,10};
	//    int[] starTime = {2,3,5,9,12,13,17,20,25,28};
		
		
		int nOfusers = 20;
		
		int[] starTime = new int[nOfusers];
	    //int[] starTime = {1,3,4,5,7,9,13,15,16,19,22,23};//start time of task
		int[] relinqtime = new int[nOfusers];
	  //  int[] relinqtime={6,8,5,7,9,16,19,20,20,21,23,23};//relinquish time of task//
		int[] endTime = new int[nOfusers];
	  //  int[] endTime = {9,10,9,8,17,20,19,21,22,22,24,24};//end time of task10
		int[] duration = new int[40];
	   int[] resAlloc=  {101,9,56,89,43,30,8,60,13,41,60, 20, 32,71,76,7,31,46,30,99 }; //resources allocated to the task at its start time
	   double pServer = .3;		//Energy consumed by servers when fully utilized (in kW/h)
	   
	    double elecPrice = .12;   //in dollars/kWh
	    
	    int tRes = 300;		//total Resources available in the cloud
	    
	    double price = 0.1; // price of the service j
	    
	  
	  
	   
	    Random r = new Random();
	 
	   
	    
	    // Giving start times randomly
	    
	   starTime[0] = 0;
	    //   System.out.println(starTime[0]);
			ExponentialDistribution exp = new ExponentialDistribution(4.0);
			for(int j = 1; j < nOfusers; j++){
				starTime[j] = (int)exp.sample() + 1+starTime[j-1];
				//System.out.println(starTime[j]);
			}
			// Giving duration corresponding to the start time to each task
			for(int k = 0; k < nOfusers;k ++){
				duration[k] = 20 + (int)(Math.random() * ((120 - 10) + 1));
			}
			
			
			// giving end times to each task
			for(int k = 0; k < 20; k++){
			    endTime[k] = starTime[k] + duration[k];
			  //  System.out.println( "ff"+endTime[k]);
			}
			
			for(int i = 0; i < 20; i++){
			  relinqtime[i] = r.nextInt((endTime[i] - starTime[i])+ starTime[i] ) + starTime[i];
			//  System.out.println( relinqtime[i]);
			}
			
			
			// total duration = maximum value of end time 
			
			 Arrays.sort(endTime);
			 int totalDuration = endTime[endTime.length - 1];
			System.out.println(totalDuration);
			 
				
				 int[] resUsed = new int[100]; //total resources used at time i
				    double[] elecExpense = new double[100];// electricity expenses at time i
				    float[] overallUti = new float[100];// overall utilization at time i
				    double[] income= new double[100]; // income at time i
				    double[] relinqLoss= new double[100]; // Relinquishment loss at time i
	    // calculating  Relinquishment loss
	    
	   for( int z =0; z<relinqtime.length;z++){
	    for (int l = relinqtime[z]; l < endTime[z]; l++ ){
	    	relinqLoss[l] += (float) (resAlloc[z]*price);
	    	
	    	//System.out.println(relinqLoss[l]);
	    	//System.out.println(l);
	    }
	    }
	 //  for (int i=0 ; i<relinqLoss.length;i++){
		//   System.out.println(relinqLoss[i]);
	   
	   
	  //  resUsed[0]=0;
	   // for(int i=0; i<resUsed.length;i++){
	    //	resUsed[i]=0;
	    //}
	    
	    
	    
	     
	   
	   //calculating resources used at time i
	    for( int j =0; j<starTime.length;j++){
	        for( int k =  starTime[j];  k < relinqtime[j];k++ ){
	        	
	        	
	          resUsed[k]+= resAlloc[j];
	        //    System.out.println(resUsed[k]);
	        }
	      
	    }
	   
	   // Calculating income, electricity expenses, overall utilization at time i
	    for(int i=1; i<resUsed.length;i++)
        {
	    	overallUti[i]= (float) resUsed[i]/tRes*100;
	    	//System.out.println(overallUti[i]/100);
	    	
	   
	    	elecExpense[i]= pServer*elecPrice*((overallUti[i]/100) +((2/3.0)*(1-overallUti[i]/100)));
	    //	System.out.println(elecExpense[i]);
	    	//Scanner s = new Scanner(System.in);
	    	//String abcd= s.next();
	    	income[i] = resUsed[i]*price;
	    	 DecimalFormat df = new DecimalFormat("#.####");      
	  	   elecExpense[i] = Double.valueOf(df.format(elecExpense[i]));
	  	 income[i]= Double.valueOf(df.format( income[i]));
	  	relinqLoss[i]= Double.valueOf(df.format( relinqLoss[i]));
	 
	   /*  System.out.println( "Time "+  i + " total resourcs used " +resUsed[i]+ "  overall Util " 
	    + overallUti[i] + "% " +" Elec Expense  " + "$"+elecExpense[i]+ " , income "+"$"+ income[i]+ 
	    " relinq Loss $"+ relinqLoss[i]);*/
	    
        }
	   // System.out.println(  Arrays.toString(resUsed));
	    
	    //Calculating net profit 
	    double sumIncome = 0;
	    for (double i : income)
	    	sumIncome += i;
	    //System.out.println(sumIncome);
	    double sumElecExpense = 0;
	    for (double i :elecExpense)
	    	sumElecExpense += i;
	    //System.out.println(sumElecExpense);
	   double sumRelinqLoss = 0;
	    for (double i : relinqLoss)
	    	sumRelinqLoss += i;
	   // System.out.println(sumRelinqLoss );
	    double netProfit = (double) (sumIncome-(sumRelinqLoss+sumElecExpense));
	    DecimalFormat df = new DecimalFormat("#.##");      
	    netProfit = Double.valueOf(df.format(netProfit));
	    sumElecExpense = Double.valueOf(df.format(sumElecExpense));
	    sumIncome = Double.valueOf(df.format(sumIncome));
	    sumRelinqLoss = Double.valueOf(df.format(sumRelinqLoss));

	  /*  System.out.println("For "+totalDuration +" hours  total income is $" +sumIncome + 
	    		" ,total electricity expenses $" + sumElecExpense + " and relinqishment loss is $" + 
	    		sumRelinqLoss + ", net profit $" + netProfit );*/
	    
	    
	}
}