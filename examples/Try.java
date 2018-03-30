import java.util.*;
import java.lang.*;
import java.text.DecimalFormat;
import java.io.*;
//import java.util.Arrays.*;
//java.util.stream.*;
public class Try {

	public static void main(String[] args) {
		//int[] userid = {1,2,3,4,5,6,7,8,9,10};
	//    int[] starTime = {2,3,5,9,12,13,17,20,25,28};
		
		//int nOfusers = 12;
		int totalDuration = 74;
		//int[] starTime = new starTime[nOfusers];
	    int[] starTime = {0,2,9,12,16,17};//start time of task23,
	  //  int[] relinqtime={121,76,59,39,77,136,73,134,146,122,163,162,150,88,121,197,119,177,203,222};//scenario 1...if user does not relinq, i consider relin time and end time as same
	    int[]  relinqtime = {8,10,58,16,25,33};//assumed scenario 2,3 and 4
	    int[] endTime = {46,45,65,59,70,74};//end time of task
	  //  int[] resAlloc= {147,209,126,237,53,124,61,95,173,29,94,153,49,168,129,244,102,104,22,192}; //scenario 1 resources allocated = res requested
	   // int[] resAlloc= {68,30,70,111,38,46,15,24,8,9,37,25,21,122,57,65,41,41,9,124}; //scenario 3
	    int[] resAlloc= {20,38,23,21,23,27}; // scenario 4
	    int[] resUsed = new int[totalDuration + 1]; //total resources used at time i
	    double[] elecExpense = new double[totalDuration + 1];// electricity expenses at time i
	    float[] overallUti = new float[totalDuration + 1];// overall utilization at time i
	    double[] income= new double[totalDuration + 1]; // income at time i
	    double[] relinqLoss= new double[totalDuration + 1]; // Relinquishment loss at time i
	    int[] usersAtTimei = new int[totalDuration + 1];
	  
	    
	 //   for (int i=0, i<use)
	 //   int n = 4;                 //no. of servers in the cloud for scenario 1
	  //  int n = 3;             // scenario 2
	    int n = 2;  // scenario 3 and 4
	    double pServer = n*.35;		//Total Energy consumed by servers when fully utilized (in kW/h)
	    double elecPrice = .12;
	    double elecPriceHigh = .18; 	//in dollars/kWh
	    double elecPriceMed = .132;
	    double elecPriceLow = .087;	   
	    int tRes = n*500;		//total Resources available in the cloud
	    double price = 0.1; // price of the service j
	    
	    
	   /* if (tRes <=300){
	    	pServer = .35;
	    }else if ((tRes >300 && tRes<=600)){
	    	pServer = .70;
	    }else if ((tRes >600 && tRes<=900)){
	    	pServer = 1.05;
	    }else {
	    	pServer = 1.4;
	    }
	    */
	    
	    // calculating  Relinquishment loss
	    
	   for( int z =0; z<relinqtime.length;z++){
	    for (int l = relinqtime[z]; l < endTime[z]; l++ ){
	    	relinqLoss[l] += (float) (resAlloc[z]*price);
	    	
	  //  	System.out.println(relinqLoss[l]);
	    	//System.out.println(l);
	    }
	    }
	   //calculating the no of users at time i
	   
	   int t = 0;
	   
	//   if  ( relinqtime[t]<endTime[t] ){
		   for(  t =0; t<starTime.length;t++){
		        for( int k =  starTime[t];  k < relinqtime[t];k++ ){
		        	 usersAtTimei[k]+= 1;
		            //System.out.println( usersAtTimei[k]);
		        }
		   }
		   //for(  t =0; t<usersAtTimei.length;t++){
		 //  System.out.println( usersAtTimei[t]);
	  //}
		   int[] noOfUsersRelinqTimei = new int [starTime.length];
		   
		   Map<Integer,Integer> counterMap = new HashMap<>();

		    
		   
		
		   HashMap<Integer, Integer> repetitionsR = new HashMap<Integer, Integer>();
		   HashMap<Integer, Integer> repetitionsE = new HashMap<Integer, Integer>();
		   
           for (int i = 0; i < relinqtime.length; ++i) {
              int item = relinqtime[i];
              if (item < endTime[i]){
			      if (repetitionsR.containsKey(item) )
			      {
			    	 
			    	  repetitionsR.put(item, repetitionsR.get(item) + 1);
			    	  
			      }
				      else
				      repetitionsR.put(item, 1);
			      
              } 
              else{
            	
            	      if (repetitionsE.containsKey(item) ){
            		 
            		  repetitionsE.put(item, repetitionsE.get(item) + 1);
            	  }
				      else
				      repetitionsE.put(item, 1);
            	     
            	  }
            
              
           }
           System.out.println("entered in relinquish");
		      System.out.println(repetitionsR);
		   System.out.println("entered in endtime");
 	          System.out.println(repetitionsE);
		  
		   
	  //  resUsed[0]=0;
	    for(int i=0; i<resUsed.length;i++){
	    	resUsed[i]=0;
	    }
	    
	    
	    
	   
	    //calculating resources used at time i
	    for( int j =0; j<starTime.length;j++){
	        for( int k =  starTime[j];  k < relinqtime[j];k++ ){
	            resUsed[k]+= resAlloc[j];
	            //System.out.println(resUsed[0]);
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
	 
	   System.out.println( "Time "+  i + " total resources used " +resUsed[i]+ "  overall Util " 
	  + overallUti[i] + "% " +" Elec Expense  " + "$"+elecExpense[i]+ " , income "+"$"+ income[i]+ 
	   " relinq Loss $"+ relinqLoss[i]+ "Users in system " + usersAtTimei[i]);
	    
        }
	   // System.out.println(  Arrays.toString(resUsed));
	    
	    //calulating net profit 
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

	  System.out.println("For "+totalDuration +" hours  total income is $" +sumIncome + 
	  		" ,total electricity expenses $" + sumElecExpense + " and relinqishment loss is $" + 
	  		sumRelinqLoss + ", net profit is $" + netProfit );
	    
	  
	    
	}
	
	
}
