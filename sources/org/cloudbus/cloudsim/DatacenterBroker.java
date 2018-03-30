/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.lists.CloudletList;
import org.cloudbus.cloudsim.lists.HostList;
import org.cloudbus.cloudsim.lists.UserList;
import org.cloudbus.cloudsim.lists.VmList;

import ilog.concert.*;
import ilog.cplex.*;
/**
 * DatacentreBroker represents a broker acting on behalf of a user. It hides VM management, as vm
 * creation, sumbission of cloudlets to this VMs and destruction of VMs.
 * 
 * @author Rodrigo N. Calheiros
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 1.0
 */
public class DatacenterBroker extends SimEntity {

	/** The vm list. */
	protected List<? extends Vm> vmList;
	
	//adrian
	protected List<? extends User> userList;
	
	protected List<? extends Host> hostList;

	/** The vms created list. */
	protected List<? extends Vm> vmsCreatedList;

	/** The cloudlet list. */
	protected List<? extends Cloudlet> cloudletList;

	/** The cloudlet submitted list. */
	protected List<? extends Cloudlet> cloudletSubmittedList;

	/** The cloudlet received list. */
	protected List<? extends Cloudlet> cloudletReceivedList;

	/** The cloudlets submitted. */
	protected int cloudletsSubmitted;

	/** The vms requested. */
	protected int vmsRequested;

	/** The vms acks. */
	protected int vmsAcks;

	/** The vms destroyed. */
	protected int vmsDestroyed;

	/** The datacenter ids list. */
	protected List<Integer> datacenterIdsList;

	/** The datacenter requested ids list. */
	protected List<Integer> datacenterRequestedIdsList;

	/** The vms to datacenters map. */
	protected Map<Integer, Integer> vmsToDatacentersMap;

	/** The datacenter characteristics list. */
	protected Map<Integer, DatacenterCharacteristics> datacenterCharacteristicsList;
	//adrian
	
	public void setHostList(List<? extends Host> hostlist){
		this.hostList = hostlist;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Host> List<T> getHostList() {
		return (List<T>) hostList;
	}
	
	public void BindUserToHost(int UserId, int hostId){
		UserList.getById(getUserList(), UserId).sethostId(hostId);
	}
	
	
	
	public double REpledge(int userId, int hostId, int id){
		User user = UserList.getById(getUserList(), userId);
		int numCPU = user.getnumCPU();
		int numRAM = user.getnumRAM();
		long numStorage = user.getnumStorage();
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);
		
		double aop = calculateScoreMeanForAOP(a);
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
		double sop = calculateScoreMean(b);
		double variance = calculatevariance(b);
		double resAzam;
		double y = (1-sop)-(variance*variance);
		 resAzam = y*(1-aop);
		double x = 2*sop/3 + aop/3;
	
		int ACPU=0 ;
		int ARAM=0 ;
		long AStorage = 0;
		if(id == 1){
			double num1 = (1 - x) * (1 - x);
	//	System.out.println(sop + "      " + aop + "      " );
			ACPU = (int)(num1 * numCPU);
			ARAM = (int)(num1 * numRAM);
			AStorage = (long)(num1 * numStorage);
			if(ARAM >2048){ARAM = 2048;}
			if(AStorage >500000) {AStorage = 500000;}
		}
		else if(id == 2){
			double num2 = 1 - x * x;
			ACPU = (int)(num2 * numCPU);
			ARAM = (int)(num2 * numRAM);
			AStorage = (long)(num2 * numStorage);
			if(ARAM >2048){ARAM = 2048;}
			if(AStorage >500000) {AStorage = 500000;}
		}
		else if(id == 3){
			if(x>=0 && x<=0.5){
				double num3 = 0.5 + 0.5 * (1 - 2 * x) * (1 - 2 * x);
				ACPU = (int)(num3 * numCPU);
				ARAM = (int)(num3 * numRAM);
				AStorage = (long)(num3 * numStorage);
				if(ARAM >2048){ARAM = 2048;}
				if(AStorage >500000) {AStorage = 500000;
				}
			}
			else if(x>0.5 && x<=1){
				double num3 = 0.5 - 2 * (x - 0.5) * (x - 0.5);
				ACPU = (int)(num3 * numCPU);
				ARAM = (int)(num3 * numRAM);
				AStorage = (long)(num3 * numStorage);
				if(ARAM >2048){ARAM = 2048;}
				if(AStorage >500000) {AStorage = 500000;}
			}
		}
		else if(id ==4){
			if(x>=0 && x<=0.5){
				double num4 = 1 - 2 * x * x;
				ACPU = (int)(num4 * numCPU);
				ARAM = (int)(num4 * numRAM);
				AStorage = (long)(num4 * numStorage);
				if(ARAM >2048){ARAM = 2048;}
				if(AStorage >500000) {AStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num4 = 0.5 * (2 - 2 * x) * (2 - 2 * x);
				ACPU = (int)(num4 * numCPU);
				ARAM = (int)(num4 * numRAM);
				AStorage = (long)(num4 * numStorage);
				if(ARAM >2048){ARAM = 2048;}
				if(AStorage >500000) {AStorage = 500000;}
			}
		}
	//	System.out.println("sop is " + sop + "aop is " + aop + "aazam " + resAzam);
		user.setnumCPU(numCPU);
		user.setnumRAM(numRAM);
		user.setnumStorage(numStorage);
		
		
		return ACPU*0.01+ARAM*0.01+AStorage*0.001;
		
	}
	public void CalNumOfResIdeal(int userId, int available){
		//here I didn't consider the situation where the user is willing to give pledge on this service;
		User user = UserList.getById(getUserList(), userId);
		int numCPU2 = user.getnumCPU2();
	
		//System.out.println("calculated ideal " + numCPU2);
		
		//int numRAM = user.getnumRAM2();
		//long numStorage = user.getnumStorage2();
	//	System.out.println("From method req " + user.getnumCPU());
		 
			if(numCPU2>available){numCPU2=0;}
			
		//	if(numRAM >available){numRAM = available;}
			//if(numStorage >available) {numStorage = available;}
			user.setnumCPU2(numCPU2);
			
	//		System.out.println("given ideal " + numCPU2);
			
		//	System.out.println("From method alloc" + numCPU);
			//user.setnumRAM2(numRAM);
			//user.setnumStorage2(numStorage);
	}	
	public void CalNumOfResRelinq(int userId, int available){
		//here I didn't consider the situation where the user is willing to give pledge on this service;
		User user = UserList.getById(getUserList(), userId);
		
		int numCPU3 = user.getnumCPU3();
		
	//	System.out.println("calculated  relinq " + numCPU3);
		//int numRAM = user.getnumRAM2();
		//long numStorage = user.getnumStorage2();
	//	System.out.println("From method req " + user.getnumCPU());
		 
			
			if(numCPU3>available){numCPU3=0;}
		//	if(numRAM >available){numRAM = available;}
			//if(numStorage >available) {numStorage = available;}
			
			user.setnumCPU3(numCPU3);
		//	System.out.println("alloc relinq " + numCPU3);		
		//	System.out.println("given relinq " + numCPU3);
		//	System.out.println("From method alloc" + numCPU);
			//user.setnumRAM2(numRAM);
			//user.setnumStorage2(numStorage);
	}	
	public void calNumOfResSj(int userId, int hostId,int available){ //l is value from linear regression
		User user = UserList.getById(getUserList(), userId);
		int numCPU4 = user.getnumCPU4();
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
		double x = calculateMax(a);
		double y = calculateMin(a);
		
		
		double aop = calculateScoreMeanForAOP(a);
		double sop = calculateScoreMean(b);
		double l = (2*sop/3) + (aop/ 3);
		double z = (l+y)/2;
		if (l >= 0 && l<=0.4){
			numCPU4 = (int)(numCPU4);
		}
		else if (l > 0.4 && l<0.7){
			numCPU4 = (int)(y*numCPU4);
		}
		else if (l >= 0.7 && l<=1){
			numCPU4 = (int)(y * numCPU4);
		}
		
		if(numCPU4>available){numCPU4=0;}
		
		user.setnumCPU4(numCPU4);
		//System.out.println("alloc sj " + numCPU4);
		//System.out.println("min " + y);
	}
	
	public double CalNumOfResAzam(int userId, int hostId, int available){
		User user = UserList.getById(getUserList(), userId);
		int numCPU = user.getnumCPU1();
		int numRAM = user.getnumRAM1();
		long numStorage = user.getnumStorage1();
	//	System.out.println("From method req " + user.getnumCPU());
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);
		double aop = calculateScoreMeanForAOP(a);
		//System.out.println(" aazam " + a);
		//System.out.println("From method aop " + aop);
		//add variance for aop, just for test
		//double vaop = calculatevariance(a);
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
		double sop = calculateScoreMean(b);
	//	System.out.println("From method sop " + sop );
	//	System.out.println(" aazam " + b);
		double variance = calculatevariance(b);
	//	System.out.println("From method var " +variance);
		//ask what is the meaning of that 0 to n;
		//double num =(1 - sop - variance) * (1 - aop);
		//double ddd = sop * aop;
		double resAzam;
		double y = (1-sop)-(variance*variance);
	//	System.out.println("From method y=(1-sop)-(variance*variance) " +y );
		 resAzam = y*(1-aop);
		 double p = 1-resAzam;
		// System.out.println(" aazam " + p);
	//	 System.out.println("From method y*(1-aop)" + resAzam);
		 numCPU = (int)(resAzam * numCPU);
			numRAM = (int)(resAzam * numRAM);
			numStorage = (long)(resAzam* numStorage);
			if(numCPU>available){numCPU=0;}
			if(numRAM >available){numRAM = 0;}
			if(numStorage >available) {numStorage = 0;}
			user.setnumCPU1(numCPU);
		//	System.out.println("From method alloc" + numCPU);
			user.setnumRAM1(numRAM);
			user.setnumStorage1(numStorage);
		//
		//	System.out.println("sop is " + sop + "aop is " + aop + "variance  is" + variance + "aazam" + resAzam );
			return p;
	}
	
	public double actualRelinp(int userId, int hostId) {
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);		
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
		double x = relinp(b);
		
		return x;
	}
	
	public double predict(int userId, int hostId) {
		
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);
		double aop = calculateScoreMeanForAOP(a);
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
		
			double sop = calculateScoreMean(b);
			
			return sop;
		
	}
	
	
	// basic
	public double CalNumOfRes(int userId, int hostId, int id, int availableRes){
		//here I didn't consider the situation where the user is willing to give pledge on this service;
		User user = UserList.getById(getUserList(), userId);
		int numCPU = user.getnumCPU();
		int numRAM = user.getnumRAM();
		long numStorage = user.getnumStorage();
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);
		double aop = calculateScoreMeanForAOP(a);
		//System.out.println("qi " + a);
		//add variance for aop, just for test
		//double vaop = calculatevariance(a);
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
	//	System.out.println(" qi " + b);
		double sop = calculateScoreMean(b);
		double variance = calculatevariance(b);
		//ask what is the meaning of that 0 to n;
		//double num =(1 - sop - variance) * (1 - aop);
		//double ddd = sop * aop;
	//	double resAzam;
		//double y = (1-sop)-(variance*variance);
	//	 resAzam = y*(1-aop);
		double x = 2*sop/3 + aop/ 3;
		       //double p = 1-x;
		 //      System.out.println( x+",");
		    //   System.out.println(" " );
		if(id == 1){
			double num1 = (1 - x) * (1 - x);
		//System.out.println(sop + "      " + aop + "      " + num1 );
			numCPU = (int)(num1 * numCPU);
			numRAM = (int)(num1 * numRAM);
			numStorage = (long)(num1 * numStorage);
		//	System.out.println("calculated " + numCPU);
			if(numCPU >availableRes) {numCPU = 0;}
			if(numRAM >availableRes){numRAM = 0;}
			if(numStorage >availableRes) {numStorage = 0;}
			
			if(numRAM >2048){numRAM = 2048;}
			if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 2){
			double num2 = 1 - x * x;
		//	System.out.println(sop + "      " + aop + "      " + num2 );
			numCPU = (int)(num2 * numCPU);
			numRAM = (int)(num2 * numRAM);
			numStorage = (long)(num2 * numStorage);
		//	System.out.println("calculated " + numCPU);
			if(numCPU >availableRes) {numCPU = 0;}
			if(numRAM >availableRes){numRAM = 0;}
			if(numStorage >availableRes) {numStorage = 0;}
		}
		else if(id == 3){
			if(x>=0 && x<=0.5){
				double num3 = 0.5 + 0.5 * (1 - 2 * x) * (1 - 2 * x);
		//		System.out.println(sop + "      " + aop + "      " + num3 );
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
			//	System.out.println("calculated " + numCPU);
				if(numCPU >availableRes) {numCPU = availableRes;}
				if(numRAM >availableRes){numRAM = availableRes;}
				if(numStorage >availableRes) {numStorage = availableRes;}
			}
			else if(x>0.5 && x<=1){
				double num3 = 0.5 - 2 * (x - 0.5) * (x - 0.5);
       	    //	System.out.println(sop + "      " + aop + "      " + num3 );
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
		//		System.out.println("calculated " + numCPU);
				numStorage = (long)(num3 * numStorage);
				if(numCPU >availableRes) {numCPU = 0;}
				if(numRAM >availableRes){numRAM = 0;}
				if(numStorage >availableRes) {numStorage = 0;}
			}
		}
		else if(id ==4){
			if(x>=0 && x<=0.5){
				double num4 = 1 - 2 * x * x;
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
			//	System.out.println("calculated " + numCPU);
				
				if(numCPU >availableRes) {numCPU = 0;}
				if(numRAM >availableRes){numRAM = 0;}
				if(numStorage >availableRes) {numStorage = 0 ;}
			}
			else if(x>0.5 && x<=1){
				double num4 = 0.5 * (2 - 2 * x) * (2 - 2 * x);
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				if(numCPU >availableRes) {numCPU = 0;}
				if(numRAM >availableRes){numRAM = 0;}
				if(numStorage >availableRes) {numStorage = 0;}
			}
		}
		else if(id==5){
			numCPU = 0;
		    numRAM = 0;
			numStorage = 0;
		}
		
		
		//System.out.println("sop is " + sop + "aop is " + aop + "variance  is" + variance + "aazam" + resAzam  );
		user.setnumCPU(numCPU);
		user.setnumRAM(numRAM); 
		user.setnumStorage(numStorage);
		return x;
		
	}
	
	// have aop, don't have sop
	public void CalNumOfRes(int userId, int hostId, double sop, int id){
		//here I didn't consider the situation where the user is willing to give pledge on this service;
		User user = UserList.getById(getUserList(), userId);
		int numCPU = user.getnumCPU();
		int numRAM = user.getnumRAM();
		long numStorage = user.getnumStorage();
		List<Host> templist = getHostList();
	//	System.out.println("host "+templist.size());
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);
		//System.out.println(a.size()+"zzz");
		double aop = calculateScoreMeanForAOP(a);
		//double variance = calculatevariance(b);
		//double vaop = calculatevariance(a);
		//ask what is the meaning of that 0 to n;
		//double num = (1 - sop) * (1 - aop + vaop);
		//double num1 =(1 - sop) * (1 - aop);
		
		double x = 2*sop/3 + aop/3;
		if(id == 1){
			double num1 = (1 - x) * (1 - x);
		//	System.out.println(sop + "      " + aop + "      " );
			numCPU = (int)(num1 * numCPU);
			numRAM = (int)(num1 * numRAM);
			numStorage = (long)(num1 * numStorage);
			//if(numCPU >=availableRes) {numCPU = availableRes;}
			//if(numRAM >=availableRes){numRAM = availableRes;}
			//if(numStorage >=availableRes) {numStorage = availableRes;}
			//if(numRAM >2048){numRAM = 2048;}
			//if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 2){
			double num2 = 1 - x * x;
			numCPU = (int)(num2 * numCPU);
			numRAM = (int)(num2 * numRAM);
			numStorage = (long)(num2 * numStorage);
		//	if(numCPU >=availableRes) {numCPU = availableRes;}
			//if(numRAM >=availableRes){numRAM = availableRes;}
			//if(numStorage >=availableRes) {numStorage = availableRes;}
			//if(numRAM >2048){numRAM = 2048;}
			//if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 3){
			if(x>=0 && x<=0.5){
				double num3 = 0.5 + 0.5 * (1 - 2 * x) * (1 - 2 * x);
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
			//	if(numCPU >=availableRes) {numCPU = availableRes;}
			//	if(numRAM >=availableRes){numRAM = availableRes;}
			//	if(numStorage >=availableRes) {numStorage = availableRes;}
			//	if(numRAM >2048){numRAM = 2048;}
			//	if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num3 = 0.5 - 2 * (x - 0.5) * (x - 0.5);
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
				//if(numCPU >=availableRes) {numCPU = availableRes;}
				//if(numRAM >=availableRes){numRAM = availableRes;}
				//if(numStorage >=availableRes) {numStorage = availableRes;}
				//if(numRAM >2048){numRAM = 2048;}
				//if(numStorage >500000) {numStorage = 500000;}
			}
		}
		else if(id ==4){
			if(x>=0 && x<=0.5){
				double num4 = 1 - 2 * x * x;
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				//if(numCPU >=availableRes) {numCPU = availableRes;}
				//if(numRAM >=availableRes){numRAM = availableRes;}
				//if(numStorage >=availableRes) {numStorage = availableRes;}
				//if(numRAM >2048){numRAM = 2048;}
				//if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num4 = 0.5 * (2 - 2 * x) * (2 - 2 * x);
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				//if(numCPU >=availableRes) {numCPU = availableRes;}
				//if(numRAM >=availableRes){numRAM = availableRes;}
				//if(numStorage >=availableRes) {numStorage = availableRes;}
				//if(numRAM >2048){numRAM = 2048;}
				//if(numStorage >500000) {numStorage = 500000;}
			}
		}

		//System.out.println("sop is " + sop + "aop is " + aop + "aazam" + resAzam);//"variance  is" + variance
		user.setnumCPU(numCPU);// setting the allocated cpu by calling a method from user class
		user.setnumRAM(numRAM);
		user.setnumStorage(numStorage);
	}
	
	//don't have aop or sop
	public void CalNumOfRes(int userId, int hostId, double sop, double aop, int id, int availableRes){
		//here I didn't consider the situation where the user is willing to give pledge on this service;
		User user = UserList.getById(getUserList(), userId);
		int numCPU = user.getnumCPU();
		int numRAM = user.getnumRAM();
		long numStorage = user.getnumStorage();
		//ask what is the meaning of that 0 to n;
		//double num1 = (1 - sop) * (1 - aop);
		double x = 2*sop/3 + aop/3;
		if(id == 1){
			double num1 = (1 - x) * (1 - x);
		//	System.out.println(sop + "      " + aop + "      " );
			numCPU = (int)(num1 * numCPU);
			numRAM = (int)(num1 * numRAM);
			numStorage = (long)(num1 * numStorage);
			if(numCPU >=availableRes) {numCPU = availableRes;}
			if(numRAM >=availableRes){numRAM = availableRes;}
			if(numStorage >=availableRes) {numStorage = availableRes;}
			if(numRAM >2048){numRAM = 2048;}
			if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 2){
			double num2 = 1 - x * x;
		//	System.out.println(sop + "      " + aop + "      " );
			numCPU = (int)(num2 * numCPU);
			numRAM = (int)(num2 * numRAM);
			numStorage = (long)(num2 * numStorage);
			if(numCPU >=availableRes) {numCPU = availableRes;}
			if(numRAM >=availableRes){numRAM = availableRes;}
			if(numStorage >=availableRes) {numStorage = availableRes;}
			if(numRAM >2048){numRAM = 2048;}
			if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 3){
			if(x>=0 && x<=0.5){
				double num3 = 0.5 + 0.5 * (1 - 2 * x) * (1 - 2 * x);
			//	System.out.println(sop + "      " + aop + "      " );
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num3 = 0.5 - 2 * (x - 0.5) * (x - 0.5);
			//	System.out.println(sop + "      " + aop + "      " );
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
		}
		else if(id ==4){
			if(x>=0 && x<=0.5){
				double num4 = 1 - 2 * x * x;
			//	System.out.println(sop + "      " + aop + "      " + num4);
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num4 = 0.5 * (2 - 2 * x) * (2 - 2 * x);
			//	System.out.println(sop + "      " + aop + "      " + num4);
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
		}
		
		
	//	System.out.println("sop is " + sop + "aop is " + aop );
		user.setnumCPU(numCPU);
		user.setnumRAM(numRAM);
		user.setnumStorage(numStorage);
	}
	
	//second time;
	public void CalNumOfRes1(int userId, int hostId, int id,int availableRes){
		//here I didn't consider the situation where the user is willing to give pledge on this service;
		User user = UserList.getById(getUserList(), userId);
		int numCPU = user.getnumCPU();
		int numRAM = user.getnumRAM();
		long numStorage = user.getnumStorage();
		Host host = HostList.getById(getHostList(), hostId);
		HashMap<Integer, ArrayList<Double>> hm = host.getmaop();
		int i = user.getuserId();
		ArrayList<Double> a = hm.get(i);
		//ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(cloudlet.getuser().getuserId());
		double aop = calculateScoreMeanForAOP(a);
		double vaop = calculatevariance(a);
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
		double sop = calculateScoreMean(b);
		sop = (double) ((0.3 + sop) / 2);//average divide by 2
		//ask what is the meaning of that 0 to n;
		
		double x = 2*sop/3 + aop/3;
		if(id == 1){
			double num1 = (1 - x) * (1 - x);
		//	System.out.println(sop + "      " + aop + "      " + num1);
			numCPU = (int)(num1 * numCPU);
			numRAM = (int)(num1 * numRAM);
			numStorage = (long)(num1 * numStorage);
			if(numCPU >=availableRes) {numCPU = availableRes;}
			if(numRAM >=availableRes){numRAM = availableRes;}
			if(numStorage >=availableRes) {numStorage = availableRes;}
			if(numRAM >2048){numRAM = 2048;}
			if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 2){
			double num2 = 1 - x * x;
			//System.out.println(sop + "      " + aop + "      " + num2);
			numCPU = (int)(num2 * numCPU);
			numRAM = (int)(num2 * numRAM);
			numStorage = (long)(num2 * numStorage);
			if(numCPU >=availableRes) {numCPU = availableRes;}
			if(numRAM >=availableRes){numRAM = availableRes;}
			if(numStorage >=availableRes) {numStorage = availableRes;}
			if(numRAM >2048){numRAM = 2048;}
			if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 3){
			if(x>=0 && x<=0.5){
				double num3 = 0.5 + 0.5 * (1 - 2 * x) * (1 - 2 * x);
			//	System.out.println(sop + "      " + aop + "      " + num3);
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num3 = 0.5 - 2 * (x - 0.5) * (x - 0.5);
			//	System.out.println(sop + "      " + aop + "      " + num3);
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
		}
		else if(id ==4){
			if(x>=0 && x<=0.5){
				double num4 = 1 - 2 * x * x;
		//		System.out.println(sop + "      " + aop + "      " + num4);
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num4 = 0.5 * (2 - 2 * x) * (2 - 2 * x);
			//	System.out.println(sop + "      " + aop + "      " + num4);
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
			
		}
//		
		//System.out.println("sop is " + sop + "aop is " + aop  );//+ "variance  is" + variance 
		user.setnumCPU(numCPU);
		user.setnumRAM(numRAM);
		user.setnumStorage(numStorage);
	}
	
	//third time;
	public void CalNumOfRes2(int userId, int hostId, int id, int availableRes){
		//here I didn't consider the situation where the user is willing to give pledge on this service;
		User user = UserList.getById(getUserList(), userId);
		int numCPU = user.getnumCPU();
		int numRAM = user.getnumRAM();
		long numStorage = user.getnumStorage();
		ArrayList<Double> a = HostList.getById(getHostList(), hostId).getmaop().get(userId);
		double aop = calculateScoreMeanForAOP(a);
		//double vaop = calculatevariance(a);
		ArrayList<Double> b = HostList.getById(getHostList(), hostId).getmsop().get(userId);
		double sop = calculateScoreMean(b);
		sop = (double) ((0.3 + sop * 2) / 3);
		//ask what is the meaning of that 0 to n;
		//double num = (1 - sop) * (1 - aop + vaop);
		//double num1 =(1 - sop) * (1 - aop);
		double x = 2*sop/3 + aop/3;
		if(id == 1){
			double num1 = (1 - x) * (1 - x);
			//System.out.println(sop + "      " + aop + "      " + num1);
			numCPU = (int)(num1 * numCPU);
			numRAM = (int)(num1 * numRAM);
			numStorage = (long)(num1 * numStorage);
			if(numCPU >=availableRes) {numCPU = availableRes;}
			if(numRAM >=availableRes){numRAM = availableRes;}
			if(numStorage >=availableRes) {numStorage = availableRes;}
			if(numRAM >2048){numRAM = 2048;}
			if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 2){
			double num2 = 1 - x * x;
			numCPU = (int)(num2 * numCPU);
			numRAM = (int)(num2 * numRAM);
			numStorage = (long)(num2 * numStorage);
			if(numCPU >=availableRes) {numCPU = availableRes;}
			if(numRAM >=availableRes){numRAM = availableRes;}
			if(numStorage >=availableRes) {numStorage = availableRes;}
			if(numRAM >2048){numRAM = 2048;}
			if(numStorage >500000) {numStorage = 500000;}
		}
		else if(id == 3){
			if(x>=0 && x<=0.5){
				double num3 = 0.5 + 0.5 * (1 - 2 * x) * (1 - 2 * x);
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num3 = 0.5 - 2 * (x - 0.5) * (x - 0.5);
				numCPU = (int)(num3 * numCPU);
				numRAM = (int)(num3 * numRAM);
				numStorage = (long)(num3 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
		}
		else if(id ==4){
			if(x>=0 && x<=0.5){
				double num4 = 1 - 2 * x * x;
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
			else if(x>0.5 && x<=1){
				double num4 = 0.5 * (2 - 2 * x) * (2 - 2 * x);
				numCPU = (int)(num4 * numCPU);
				numRAM = (int)(num4 * numRAM);
				numStorage = (long)(num4 * numStorage);
				if(numCPU >=availableRes) {numCPU = availableRes;}
				if(numRAM >=availableRes){numRAM = availableRes;}
				if(numStorage >=availableRes) {numStorage = availableRes;}
				if(numRAM >2048){numRAM = 2048;}
				if(numStorage >500000) {numStorage = 500000;}
			}
		}
//		
		//System.out.println("sop is " + sop + "aop is " + aop   );//+ "variance  is" + variance
		user.setnumCPU(numCPU);
		user.setnumRAM(numRAM);
		user.setnumStorage(numStorage);
	}
	
	
	
	//optimization model
  public ArrayList<Integer> model(int batchNo,int n, int fUserofBatch, int lUserofBatch,int capacity, int[]demand, double[] Prob, int[] duration, double[]relinq) throws IloException{
		
		double[] inc = new double[demand.length];
		double[] loss = new double[demand.length];
		double[] minLim = new double [demand.length];
		ArrayList<Integer> ncpu= new ArrayList<Integer>();
		
		
		
		
		
	 
			
		//    System.out.println("batch " + batchNo + " from user " + fUserofBatch + " to " + lUserofBatch);	
			
			
			for (int jj= fUserofBatch; jj<lUserofBatch; jj++ ){
				
			     inc[jj]=  0.000027771*(1-Prob[jj])*duration[jj];
			     
			   //  loss[jj]= 0.000027771*64;
			     loss[jj]= 0.000027771*Prob[jj]*duration[jj];
			     
			     double xx = Prob[jj]*duration[jj];
			     if (xx > 64) {xx = 16;}
			     loss[jj]= 0.000027771*xx;   
			     minLim[jj] = 0.2*demand[jj];
			    // System.out.println("inc = " + inc[jj]);     
			}
			
			//create a model
			
			IloCplex cplex = new IloCplex();
			
			
			
			
		
	        //variables
			     IloNumVar[] processed = cplex.boolVarArray(n);       
		         IloNumVar[] proc = new IloNumVar[n];
		         IloNumVar[] alloc = new IloNumVar[n];
			
			
				
				
					for(int j=0; j<n; j++) {
						
						alloc[j]= cplex.numVar( 0, 1000);
						proc[j]= cplex.boolVar();
					
                     }
				
					
				
							
				
	 //expressions
		 
			IloLinearNumExpr  income = cplex.linearNumExpr();
			IloLinearNumExpr  rLoss = cplex.linearNumExpr();
			IloNumExpr  np = cplex.linearNumExpr();
			IloNumExpr  pr = cplex.linearNumExpr();
			
			
			
			for (int j= 0, jj = fUserofBatch;( j<n && jj < lUserofBatch); j++,jj++ ){
				
				
				income.addTerm(inc[jj],alloc[j] );
				
				rLoss.addTerm(loss[jj], alloc[j]);
				
				
			}
			
			
			pr = cplex.sum(proc);
			np = cplex.diff(income, rLoss);
				
			
					
	 //define objective
			
			IloNumExpr objective = cplex.linearNumExpr();
				
			 objective = cplex.sum(np , pr );
			
			
			 cplex.addMaximize(objective); 

			
					
			
			 
	 //constraints
			
			
			//capacity 
			IloNumExpr  cCap = cplex.linearNumExpr();
		  
			cCap= cplex.sum(alloc);   //sum of allocated resources to the batch 
		    
			cplex.addLe(cCap, capacity); // less than available capacity
			
			
			
			
			//demand

			for(int j= 0, jj = fUserofBatch;( j<n && jj < lUserofBatch); j++,jj++){
	           
				cplex.addLe(alloc[j], demand[jj]);
				
			}
				
			
			//min limit
			
			
			for(int j= 0, jj = fUserofBatch;( j<n && jj < lUserofBatch); j++ , jj++){
				
			//	IloNumExpr  cLim = cplex.linearNumExpr();
				
			   // cLim = cplex.prod;
				
				cplex.ge(alloc[j], minLim[jj]);
				//System.out.println("min   " + minLim[jj]);
			   
			} 
				
			
			
			//solve
			
			boolean isSolve = cplex.solve();
			if(isSolve){
				
				 for (int j= 0, jj = fUserofBatch;( j<n && jj < lUserofBatch); j++ , jj++){
					
					 System.out.println("user " + (jj) + " with prob " + Prob[jj] + "actual  "+ relinq[jj] +" demanded " + demand[jj] + "and got " +cplex.getValue(alloc[j]));
					
					 ncpu.add((int) cplex.getValue(alloc[j]));
					 
					 }
				
				
				
				 System.out.println("obj of batch " +     batchNo +" = " + cplex.getObjValue());
				 System.out.println("pred inc of batch "+  batchNo +" = "+ cplex.getValue(income));
				 System.out.println("pred loss of batch "+ batchNo +" = "+ cplex.getValue(rLoss));
				 System.out.println("pred np of batch "  + batchNo +"  = " + cplex.getValue(np));
				
				
				
			      
			      
			      
			}
			else{}
			cplex.end();
			
			//System.out.println(lUserofBatch);
		    
		
		return ncpu;
}// method cplex
	
	////////è¿™æ˜¯å�•ä»·ï¼›

//there is no need to code for second time and third time user because for aop, it doesn't have special rules for second and third time users;
	
	
	
	
	public double calculateScoreMean(ArrayList<Double> SOPRecord) {
		double scoreAll = 0.0;
		int i =0;
		while (i< SOPRecord.size()) {
			scoreAll += SOPRecord.get(i);
			i++;
		}
		return scoreAll/SOPRecord.size();
	}
	
	public double relinp(ArrayList<Double> AOPRecord) {
		
		return (AOPRecord.get(AOPRecord.size()-2));
	}
	
	
	
	private double calculateScoreMeanForAOP(ArrayList<Double> AOPRecord) {
		double scoreAll = 0.0;
		if(AOPRecord.size() == 1){
			return AOPRecord.get(0);
		}
		else{
			int i = 0;
		while (i < AOPRecord.size() - 2) {
			scoreAll += AOPRecord.get(i);
			i++;
		}
		scoreAll = scoreAll/(i);
		scoreAll = (scoreAll + AOPRecord.get(i))/2;
		return scoreAll;
		}
		
	}
	   
	public double calculatevariance(ArrayList<Double> SOPRecord) {
		double allSquare = 0.0;
		double scoreMean = calculateScoreMean(SOPRecord);
		for (Double rawScore : SOPRecord) {
			allSquare += (rawScore - scoreMean)*(rawScore - scoreMean);
		}
		double denominator = SOPRecord.size();
		return allSquare/denominator;
	} 
	
	public double calculateMax(ArrayList<Double> record){
		//int min = list.get(0);
		double max = record.get(0);

		for(double i: record) {
		   // if(i < min) min = i;
		    if(i > max) max = i;
		}
		return max;
	}
	public double calculateMin(ArrayList<Double> record){
		
		double min = record.get(0);

		for(double i: record) {
		    if(i < min) min = i;
		   
		}
		return min;
	}
	
	
	/**
	 * Created a new DatacenterBroker object.
	 * 
	 * @param name name to be associated with this entity (as required by Sim_entity class from
	 *            simjava package)
	 * @throws Exception the exception
	 * @pre name != null
	 * @po9st $none
	 */
	public DatacenterBroker(String name) throws Exception {
		super(name);
		setUserList(new ArrayList<User>());
		setVmList(new ArrayList<Vm>());
		setVmsCreatedList(new ArrayList<Vm>());
		setCloudletList(new ArrayList<Cloudlet>());
		setCloudletSubmittedList(new ArrayList<Cloudlet>());
		setCloudletReceivedList(new ArrayList<Cloudlet>());

		cloudletsSubmitted = 0;
		setVmsRequested(0);
		setVmsAcks(0);
		setVmsDestroyed(0);

		setDatacenterIdsList(new LinkedList<Integer>());
		setDatacenterRequestedIdsList(new ArrayList<Integer>());
		setVmsToDatacentersMap(new HashMap<Integer, Integer>());
		setDatacenterCharacteristicsList(new HashMap<Integer, DatacenterCharacteristics>());
	}

	
	
	//adrian
	/**
	 * This method is used to send to the broker the list with users that must be
	 * created.
	 * 
	 * @param list the list
	 * @pre list !=null
	 * @post $none
	 */
	public void submitUserList(List<? extends User> list) {
		getUserList().addAll(list);
	}
	
	/**
	 * This method is used to send to the broker the list with virtual machines that must be
	 * created.
	 * 
	 * @param list the list
	 * @pre list !=null
	 * @post $none
	 */
	public void submitVmList(List<? extends Vm> list) {
		getVmList().addAll(list);
	}

	/**
	 * This method is used to send to the broker the list of cloudlets.
	 * 
	 * @param list the list
	 * @pre list !=null
	 * @post $none
	 */
	public void submitCloudletList(List<? extends Cloudlet> list) {
		getCloudletList().addAll(list);
	}

	/**
	 * Specifies that a given cloudlet must run in a specific virtual machine.
	 * 
	 * @param cloudletId ID of the cloudlet being bount to a vm
	 * @param vmId the vm id
	 * @pre cloudletId > 0
	 * @pre id > 0
	 * @post $none
	 */
	public void bindCloudletToVm(int cloudletId, int vmId) {
		CloudletList.getById(getCloudletList(), cloudletId).setVmId(vmId);
	}

	/**
	 * Processes events available for this Broker.
	 * 
	 * @param ev a SimEvent object
	 * @pre ev != null
	 * @post $none
	 */
	@Override
	public void processEvent(SimEvent ev) {
		switch (ev.getTag()) {
		// Resource characteristics request
			case CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST:
				processResourceCharacteristicsRequest(ev);
				break;
			// Resource characteristics answer
			case CloudSimTags.RESOURCE_CHARACTERISTICS:
				processResourceCharacteristics(ev);
				break;
			// VM Creation answer
			case CloudSimTags.VM_CREATE_ACK:
				processVmCreate(ev);
				break;
			// A finished cloudlet returned
			case CloudSimTags.CLOUDLET_RETURN:
				processCloudletReturn(ev);
				break;
			// if the simulation finishes
			case CloudSimTags.END_OF_SIMULATION:
				shutdownEntity();
				break;
			// other unknown tags are processed by this method
			default:
				processOtherEvent(ev);
				break;
		}
	}

	/**
	 * Process the return of a request for the characteristics of a PowerDatacenter.
	 * 
	 * @param ev a SimEvent object
	 * @pre ev != $null
	 * @post $none
	 */
	protected void processResourceCharacteristics(SimEvent ev) {
		DatacenterCharacteristics characteristics = (DatacenterCharacteristics) ev.getData();
		getDatacenterCharacteristicsList().put(characteristics.getId(), characteristics);

		if (getDatacenterCharacteristicsList().size() == getDatacenterIdsList().size()) {
			setDatacenterRequestedIdsList(new ArrayList<Integer>());
			createVmsInDatacenter(getDatacenterIdsList().get(0));
		}
	}

	/**
	 * Process a request for the characteristics of a PowerDatacenter.
	 * 
	 * @param ev a SimEvent object
	 * @pre ev != $null
	 * @post $none
	 */
	protected void processResourceCharacteristicsRequest(SimEvent ev) {
		setDatacenterIdsList(CloudSim.getCloudResourceList());
		setDatacenterCharacteristicsList(new HashMap<Integer, DatacenterCharacteristics>());

	//	Log.printLine(CloudSim.clock() + ": " + getName() + ": Cloud Resource List received with "
			//	+ getDatacenterIdsList().size() + " resource(s)");

		for (Integer datacenterId : getDatacenterIdsList()) {
			sendNow(datacenterId, CloudSimTags.RESOURCE_CHARACTERISTICS, getId());
		}
	}

	/**
	 * Process the ack received due to a request for VM creation.
	 * 
	 * @param ev a SimEvent object
	 * @pre ev != null
	 * @post $none
	 */
	protected void processVmCreate(SimEvent ev) {
		int[] data = (int[]) ev.getData();
		int datacenterId = data[0];
		int vmId = data[1];
		int result = data[2];

		if (result == CloudSimTags.TRUE) {
			getVmsToDatacentersMap().put(vmId, datacenterId);
			getVmsCreatedList().add(VmList.getById(getVmList(), vmId));
		//	Log.printLine(CloudSim.clock() + ": " + getName() + ": VM #" + vmId
			//		+ " has been created in Datacenter #" + datacenterId + ", Host #"
				//	+ VmList.getById(getVmsCreatedList(), vmId).getHost().getId());
		} else {
		//	Log.printLine(CloudSim.clock() + ": " + getName() + ": Creation of VM #" + vmId
		//			+ " failed in Datacenter #" + datacenterId);
		}

		incrementVmsAcks();

		// all the requested VMs have been created
		if (getVmsCreatedList().size() == getVmList().size() - getVmsDestroyed()) {
			submitCloudlets();
		} else {
			// all the acks received, but some VMs were not created
			if (getVmsRequested() == getVmsAcks()) {
				// find id of the next datacenter that has not been tried
				for (int nextDatacenterId : getDatacenterIdsList()) {
					if (!getDatacenterRequestedIdsList().contains(nextDatacenterId)) {
						createVmsInDatacenter(nextDatacenterId);
						return;
					}
				}

				// all datacenters already queried
				if (getVmsCreatedList().size() > 0) { // if some vm were created
					submitCloudlets();
				} else { // no vms created. abort
				//	Log.printLine(CloudSim.clock() + ": " + getName()
						//	+ ": none of the required VMs could be created. Aborting");
					finishExecution();
				}
			}
		}
	}

	/**
	 * Process a cloudlet return event.
	 * 
	 * @param ev a SimEvent object
	 * @pre ev != $null
	 * @post $none
	 */
	protected void processCloudletReturn(SimEvent ev) {
		Cloudlet cloudlet = (Cloudlet) ev.getData();
		getCloudletReceivedList().add(cloudlet);
	//	Log.printLine(CloudSim.clock() + ": " + getName() + ": Cloudlet " + cloudlet.getCloudletId()
			//	+ " received");
		cloudletsSubmitted--;
		if (getCloudletList().size() == 0 && cloudletsSubmitted == 0) { // all cloudlets executed
		//	Log.printLine(CloudSim.clock() + ": " + getName() + ": All Cloudlets executed. Finishing...");
			clearDatacenters();
			finishExecution();
		} else { // some cloudlets haven't finished yet
			if (getCloudletList().size() > 0 && cloudletsSubmitted == 0) {
				// all the cloudlets sent finished. It means that some bount
				// cloudlet is waiting its VM be created
				clearDatacenters();
				createVmsInDatacenter(0);
			}

		}
	}

	/**
	 * Overrides this method when making a new and different type of Broker. This method is called
	 * by {@link #body()} for incoming unknown tags.
	 * 
	 * @param ev a SimEvent object
	 * @pre ev != null
	 * @post $none
	 */
	protected void processOtherEvent(SimEvent ev) {
		if (ev == null) {
		//	Log.printLine(getName() + ".processOtherEvent(): " + "Error - an event is null.");
			return;
		}

		//Log.printLine(getName() + ".processOtherEvent(): "
			//	+ "Error - event unknown by this DatacenterBroker.");
	}

	/**
	 * Create the virtual machines in a datacenter.
	 * 
	 * @param datacenterId Id of the chosen PowerDatacenter
	 * @pre $none
	 * @post $none
	 */
	protected void createVmsInDatacenter(int datacenterId) {
		// send as much vms as possible for this datacenter before trying the next one
		int requestedVms = 0;
		String datacenterName = CloudSim.getEntityName(datacenterId);
		for (Vm vm : getVmList()) {
			if (!getVmsToDatacentersMap().containsKey(vm.getId())) {
				//Log.printLine(CloudSim.clock() + ": " + getName() + ": Trying to Create VM #" + vm.getId()
					//	+ " in " + datacenterName);
				sendNow(datacenterId, CloudSimTags.VM_CREATE_ACK, vm);
				requestedVms++;
			}
		}

		getDatacenterRequestedIdsList().add(datacenterId);

		setVmsRequested(requestedVms);
		setVmsAcks(0);
	}

	/**
	 * Submit cloudlets to the created VMs.
	 * 
	 * @pre $none
	 * @post $none
	 */
	protected void submitCloudlets() {
		int vmIndex = 0;
		for (Cloudlet cloudlet : getCloudletList()) {
			Vm vm;
			// if user didn't bind this cloudlet and it has not been executed yet
			if (cloudlet.getVmId() == -1) {
				vm = getVmsCreatedList().get(vmIndex);
			} else { // submit to the specific vm
				vm = VmList.getById(getVmsCreatedList(), cloudlet.getVmId());
				if (vm == null) { // vm was not created
				//	Log.printLine(CloudSim.clock() + ": " + getName() + ": Postponing execution of cloudlet "
						//	+ cloudlet.getCloudletId() + ": bount VM not available");
					continue;
				}
			}

			//Log.printLine(CloudSim.clock() + ": " + getName() + ": Sending cloudlet "
			//		+ cloudlet.getCloudletId() + " to VM #" + vm.getId());
			cloudlet.setVmId(vm.getId());
			sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
			cloudletsSubmitted++;
			vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
			getCloudletSubmittedList().add(cloudlet);
		}

		// remove submitted cloudlets from waiting list
		for (Cloudlet cloudlet : getCloudletSubmittedList()) {
			getCloudletList().remove(cloudlet);
		}
	}

	/**
	 * Destroy the virtual machines running in datacenters.
	 * 
	 * @pre $none
	 * @post $none
	 */
	protected void clearDatacenters() {
		for (Vm vm : getVmsCreatedList()) {
		//	Log.printLine(CloudSim.clock() + ": " + getName() + ": Destroying VM #" + vm.getId());
			sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.VM_DESTROY, vm);
		}

		getVmsCreatedList().clear();
	}

	/**
	 * Send an internal event communicating the end of the simulation.
	 * 
	 * @pre $none
	 * @post $none
	 */
	protected void finishExecution() {
		sendNow(getId(), CloudSimTags.END_OF_SIMULATION);
	}

	/*
	 * (non-Javadoc)
	 * @see cloudsim.core.SimEntity#shutdownEntity()
	 */
	@Override
	public void shutdownEntity() {
	//	Log.printLine(getName() + " is shutting down...");
	}

	/*
	 * (non-Javadoc)
	 * @see cloudsim.core.SimEntity#startEntity()
	 */
	@Override
	public void startEntity() {
	//	Log.printLine(getName() + " is starting...");
		schedule(getId(), 0, CloudSimTags.RESOURCE_CHARACTERISTICS_REQUEST);
	}

	/**
	 * Gets the vm list.
	 * 
	 * @param <T> the generic type
	 * @return the vm list
	 */
	@SuppressWarnings("unchecked")
	public <T extends Vm> List<T> getVmList() {
		return (List<T>) vmList;
	}

	/**
	 * Sets the vm list.
	 * 
	 * @param <T> the generic type
	 * @param vmList the new vm list
	 */
	protected <T extends Vm> void setVmList(List<T> vmList) {
		this.vmList = vmList;
	}

	/**
	 * Gets the cloudlet list.
	 * 
	 * @param <T> the generic type
	 * @return the cloudlet list
	 */
	@SuppressWarnings("unchecked")
	public <T extends Cloudlet> List<T> getCloudletList() {
		return (List<T>) cloudletList;
	}

	
	//adrian
	@SuppressWarnings("unchecked")
	public <T extends User> List<T> getUserList() {
		return (List<T>) userList;
	}
	
	protected <T extends User> void setUserList(List<T> userList) {
		this.userList = userList;
	}
	/**
	 * Sets the cloudlet list.
	 * 
	 * @param <T> the generic type
	 * @param cloudletList the new cloudlet list
	 */
	protected <T extends Cloudlet> void setCloudletList(List<T> cloudletList) {
		this.cloudletList = cloudletList;
	}

	/**
	 * Gets the cloudlet submitted list.
	 * 
	 * @param <T> the generic type
	 * @return the cloudlet submitted list
	 */
	@SuppressWarnings("unchecked")
	public <T extends Cloudlet> List<T> getCloudletSubmittedList() {
		return (List<T>) cloudletSubmittedList;
	}

	/**
	 * Sets the cloudlet submitted list.
	 * 
	 * @param <T> the generic type
	 * @param cloudletSubmittedList the new cloudlet submitted list
	 */
	protected <T extends Cloudlet> void setCloudletSubmittedList(List<T> cloudletSubmittedList) {
		this.cloudletSubmittedList = cloudletSubmittedList;
	}

	/**
	 * Gets the cloudlet received list.
	 * 
	 * @param <T> the generic type
	 * @return the cloudlet received list
	 */
	@SuppressWarnings("unchecked")
	public <T extends Cloudlet> List<T> getCloudletReceivedList() {
		return (List<T>) cloudletReceivedList;
	}

	/**
	 * Sets the cloudlet received list.
	 * 
	 * @param <T> the generic type
	 * @param cloudletReceivedList the new cloudlet received list
	 */
	protected <T extends Cloudlet> void setCloudletReceivedList(List<T> cloudletReceivedList) {
		this.cloudletReceivedList = cloudletReceivedList;
	}

	/**
	 * Gets the vm list.
	 * 
	 * @param <T> the generic type
	 * @return the vm list
	 */
	@SuppressWarnings("unchecked")
	public <T extends Vm> List<T> getVmsCreatedList() {
		return (List<T>) vmsCreatedList;
	}

	/**
	 * Sets the vm list.
	 * 
	 * @param <T> the generic type
	 * @param vmsCreatedList the vms created list
	 */
	protected <T extends Vm> void setVmsCreatedList(List<T> vmsCreatedList) {
		this.vmsCreatedList = vmsCreatedList;
	}

	/**
	 * Gets the vms requested.
	 * 
	 * @return the vms requested
	 */
	protected int getVmsRequested() {
		return vmsRequested;
	}

	/**
	 * Sets the vms requested.
	 * 
	 * @param vmsRequested the new vms requested
	 */
	protected void setVmsRequested(int vmsRequested) {
		this.vmsRequested = vmsRequested;
	}

	/**
	 * Gets the vms acks.
	 * 
	 * @return the vms acks
	 */
	protected int getVmsAcks() {
		return vmsAcks;
	}

	/**
	 * Sets the vms acks.
	 * 
	 * @param vmsAcks the new vms acks
	 */
	protected void setVmsAcks(int vmsAcks) {
		this.vmsAcks = vmsAcks;
	}

	/**
	 * Increment vms acks.
	 */
	protected void incrementVmsAcks() {
		vmsAcks++;
	}

	/**
	 * Gets the vms destroyed.
	 * 
	 * @return the vms destroyed
	 */
	protected int getVmsDestroyed() {
		return vmsDestroyed;
	}

	/**
	 * Sets the vms destroyed.
	 * 
	 * @param vmsDestroyed the new vms destroyed
	 */
	protected void setVmsDestroyed(int vmsDestroyed) {
		this.vmsDestroyed = vmsDestroyed;
	}

	/**
	 * Gets the datacenter ids list.
	 * 
	 * @return the datacenter ids list
	 */
	protected List<Integer> getDatacenterIdsList() {
		return datacenterIdsList;
	}

	/**
	 * Sets the datacenter ids list.
	 * 
	 * @param datacenterIdsList the new datacenter ids list
	 */
	protected void setDatacenterIdsList(List<Integer> datacenterIdsList) {
		this.datacenterIdsList = datacenterIdsList;
	}

	/**
	 * Gets the vms to datacenters map.
	 * 
	 * @return the vms to datacenters map
	 */
	protected Map<Integer, Integer> getVmsToDatacentersMap() {
		return vmsToDatacentersMap;
	}

	/**
	 * Sets the vms to datacenters map.
	 * 
	 * @param vmsToDatacentersMap the vms to datacenters map
	 */
	protected void setVmsToDatacentersMap(Map<Integer, Integer> vmsToDatacentersMap) {
		this.vmsToDatacentersMap = vmsToDatacentersMap;
	}

	/**
	 * Gets the datacenter characteristics list.
	 * 
	 * @return the datacenter characteristics list
	 */
	protected Map<Integer, DatacenterCharacteristics> getDatacenterCharacteristicsList() {
		return datacenterCharacteristicsList;
	}

	/**
	 * Sets the datacenter characteristics list.
	 * 
	 * @param datacenterCharacteristicsList the datacenter characteristics list
	 */
	protected void setDatacenterCharacteristicsList(
			Map<Integer, DatacenterCharacteristics> datacenterCharacteristicsList) {
		this.datacenterCharacteristicsList = datacenterCharacteristicsList;
	}

	/**
	 * Gets the datacenter requested ids list.
	 * 
	 * @return the datacenter requested ids list
	 */
	protected List<Integer> getDatacenterRequestedIdsList() {
		return datacenterRequestedIdsList;
	}

	/**
	 * Sets the datacenter requested ids list.
	 * 
	 * @param datacenterRequestedIdsList the new datacenter requested ids list
	 */
	protected void setDatacenterRequestedIdsList(List<Integer> datacenterRequestedIdsList) {
		this.datacenterRequestedIdsList = datacenterRequestedIdsList;
	}

}
