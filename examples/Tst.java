import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import org.cloudbus.cloudsim.examples.Write;

import ilog.concert.IloException;


public class Tst {
	public static final double[] DreadFile(String filePath) {
        File f = new File(filePath);
        if(!f.exists()) {
          //  System.err.println("File " + f.getName() + " not found");
            return null;
        }
        

        StringBuilder buffer = new StringBuilder();
        byte[] bytes = new byte[1024];
        int readBytesCount = 0;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(f));
            while( (readBytesCount = in.read(bytes)) != -1) {
                buffer.append(new String(bytes, 0, readBytesCount));
            }
        } catch(Exception exc) {
            exc.printStackTrace();
            return null;
        }
        
        String s = buffer.toString().trim();
        String[] a = s.split(",");
        int aa = a.length;
        double[] b = new double[aa];
        for(int i=0;i<a.length;i++){
        		b[i] = Double.parseDouble(a[i]);
        }
        return b;
    }
	
	public static void main(String[] args)throws IOException{
		
	 
	double[] aveRP = DreadFile("C:\\Program Files\\cloudplex\\CloudSim\\data\\DreadFile.txt");	
	ArrayList<Double> relinp = new ArrayList<Double> ();
	
	relinp.clear();
	Random r = new Random();
	//int randr = r.nextInt(100) + 2;
	//int randr = 20;
	//if(aveRP[z] == 0){} //checks whether first element of aveRP is zero, if zero, does not execute the code
	//else  if(aveRP[z] > 0){    //nested loop   
	System.out.println("start");
		 for(int i = 0 ; i < 5000; i++){
			int duration = 3600+(int)(Math.random() * ((14400 - 3600) + 1));//time user asks for max 9 hours min 3 hrs
				//	duration[i] =i+2;
			int		resask = 500+ (int)(Math.random() * ((600-250) + 1));;
			double a = r.nextGaussian() * 0.35 + .5;
			if (a < 0 ){
				a=0.01;
			}
			else if( a>1){
				a = .99;
			}
			
		//	System.out.println(resask);
				relinp.add(a);  // big arraylist of filterd out ave rp 
			
		    
		 }
		 int bs = 5; // initialize batch size
		 int a = 2;
		
 for (int t = 0; t<6; t++) {	// no. of batches 
	 
	
	 int b = a+bs-1;       // last row to be counted
	 System.out.println(  );
	 
	  System.out.println( "for" + bs );
	  
	for (int tt = 0; tt < 20; tt++) {    // no. of times to be run
		 
		   System.out.println(a + "_" + b );
		   
		   a +=bs;
		   b = a + bs-1;
		   
	   }
	bs = bs*2;
	if (bs > 100) {bs = 100;}
	
  }   	
	     	 
	}
   
    }
    	
