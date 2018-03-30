package org.cloudbus.cloudsim.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class AAA {
	static int[] timestamp = new int[16];
	static int[] expiretime1 = {3};
	static int[] alloc = {40};
	static int[] av = new int [3];
	static HashMap<Integer, ArrayList<Integer>> utii = new HashMap<Integer,ArrayList<Integer>>();
	static  ArrayList<Integer> acpu = new ArrayList<Integer> ();
	static HashMap<Integer, Double> uti = new HashMap<Integer,Double>();
	static TreeMap<Integer, Double> utireal = new TreeMap<Integer,Double>();
	static HashMap<Integer, Double> chk = new HashMap<Integer,Double>();
	
	static double utili=30;
	
	public static final void  availableRes(int[]timestamp, int[]expiretime1,int[]alloc,ArrayList<Integer> acpu){
		
		for (int i=0;i<expiretime1.length; i++){
		  for (int z=timestamp[i]; z<expiretime1[i]; z++){
			av[z]-=  acpu.get(i) ;
			if (av[z]<0)
				av[z]=0;
			acpu.add(alloc[i]);
		//	System.out.println("time "+ z + "av" + av[z]);
		   }
		}
		
		  
	}

	public static void main(String[] args)throws IOException  {
		// TODO Auto-generated method stub
		timestamp[0] = 0;
		ExponentialDistribution exp = new ExponentialDistribution(2.0);
		ExponentialDistribution exp1 = new ExponentialDistribution(4.0);
		for(int i = 1; i <8; i++){
			timestamp[i] = (int)exp1.sample()+1 + timestamp[i-1];
			
		}
		
		for(int i = 8; i <16; i++){
			timestamp[i] = (int)exp.sample()+1 + timestamp[i-1];
			
		}
		
		for(int i = 1; i <16; i++){
			System.out.println(timestamp[i] );
			
		}
		
		}
		
	}

