package org.cloudbus.cloudsim.examples;

import java.text.DecimalFormat;
/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */
import java.util.*;
import java.io.*;
import java.io.PrintWriter;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.LinearRegression;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.User;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.lists.HostList;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import ilog.concert.IloException;

/**
 * A simple example showing how to create a datacenter with one host and run one
 * cloudlet on it.
 */
public class Write {
	
	
	
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;
	//private static List<Cloudlet> cloudletList1;

	/** The vmlist. */
	private static List<Vm> vmlist;
//	private static List<Vm> vmlist1;
	private static List<User> userlist;
	private static List<User> userlist1;
	private static List<Host> hostlist1;
	//private static List<Host> hostlist;//for aazam
	/**
	 * Creates main() to run this example.
	 *
	 * @param args the args
	 * @throws IloException 
	 */
	
	public static void main(String[] args)throws IOException, IloException {
		
		System.out.println("Simulating..." );
		int[] resask = new int[5300];
		int[] timestamp = new int[resask.length];
		//int[] timestamp = {0,3};
		int[] duration = new int[resask.length*2];
		//int[] duration = {4,4};
		int[] expiretime1 = new int[timestamp.length];
		int[] endtime = new int[timestamp.length];
		int[] end1 = new int[timestamp.length];
		

		timestamp[0] = 0;
		int interarrival = 16;  
		ExponentialDistribution exp = new ExponentialDistribution(interarrival);
		//ExponentialDistribution exp1 = new ExponentialDistribution(2.0);
		//ExponentialDistribution exp2 = new ExponentialDistribution(3.0);
		
		
		//for(int i = 1; i <timestamp.length; i++){
		//	timestamp[i] = (int)exp.sample() + 35 + timestamp[i-1];
		//}
		
		 int n = 1;  // scenario 3 and 4
		    double pServer = n*.525;		//Total Energy consumed by servers when fully utilized (in kWh)
		    double elecPrice = .000036;
		   
		    double price = 0.00003; // price of the service j/sec
		    
		    
		    
		
		    ArrayList<Integer> start = new ArrayList<Integer> ();    
		    
		for(int i = 1; i <timestamp.length; i++){
			timestamp[i] = (int)exp.sample()+ 1+timestamp[i-1];
			//timestamp[i] = i+2;
			start.add(timestamp[i]);
		
		}
		
		/**for(int i = 500; i <1500; i++){
			timestamp[i] = (int)exp1.sample() +25 + timestamp[i-1];
			//timestamp[i] = i+2;
			
		
		}
		for(int i = 1500; i <2000; i++){
			timestamp[i] = (int)exp2.sample() +35 + timestamp[i-1];
			//timestamp[i] = i+2;
			
		
		}**/
		
		
		ArrayList<Integer> reqRes = new ArrayList<Integer> ();
		//System.out.println("ftutyj"+timestamp[19]);
				for(int i = 0; i < resask.length; i ++){
			duration[i] = 3600 + (int)(Math.random() * ((14400 - 3600) + 1));//time user asks for max 9 hours min 3 hrs
		//	duration[i] =i+2;
			resask[i] = 500 + (int)(Math.random() * ((600-250) + 1));;// resources userr asks for
			reqRes.add(resask[i]);
		}
				
				    
				
	   ArrayList<Integer> durat = new ArrayList<Integer> ();		
		
		int[] dura = new int[resask.length];
		for (int i = 0; i < resask.length; i++){
			dura[i] = duration[i];
			durat.add(dura[i]);
		//	System.out.println(dura[i]+",");
		}
			

			
		
		
		
		
	
					
		
		double[] aveRP = DreadFile("C:\\Program Files\\cloudplex\\CloudSim\\data\\DreadFile.txt");		
	//	System.out.println(aveRP.length);
		
	//	double[] aveRP = {0.5,0.6,0.3,0.2,0.5,0.5,0.6,0.3,0.2,0.5,0.5,0.6,0.3,0.2,0.5};
		
		
		int tres = 200000;
		//[0, 4, 6, 13, 15, 24, 25, 30, 32, 35, 36, 38, 39, 41, 51, 52, 53, 54, 58, 60]
		
		
		Random r = new Random();
		
		ArrayList<Integer> acpu = new ArrayList<Integer> ();
		ArrayList<Integer> acpu1 = new ArrayList<Integer> ();
		ArrayList<Integer> acpuSj = new ArrayList<Integer> ();
		ArrayList<Integer> acpuRelinq = new ArrayList<Integer> ();
		ArrayList<Integer> acpuNoRelinq = new ArrayList<Integer> ();
		ArrayList<Integer> aram = new ArrayList<Integer> ();
		ArrayList<Integer> aram1 = new ArrayList<Integer> ();
		ArrayList<Long> asto = new ArrayList<Long> ();
		ArrayList<Long> asto1 = new ArrayList<Long> ();
		
		ArrayList<Double> relinp = new ArrayList<Double> ();
		ArrayList<Integer> relinqTime = new ArrayList<Integer> ();
		ArrayList<Integer> end = new ArrayList<Integer> ();
		ArrayList<Double> actual = new ArrayList<Double> ();
		ArrayList<Double> pred = new ArrayList<Double> ();
		ArrayList<Double> preda = new ArrayList<Double> ();
		ArrayList<Double> predQi = new ArrayList<Double> ();
		
		
		HashMap<Integer, Double> uti = new HashMap<Integer,Double>();
		TreeMap<Integer, Double> utireal = new TreeMap<Integer,Double>();
	
		HashMap<Integer, Integer> block = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> blockAazam = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> blockRelinq = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> blockNoRelinq = new HashMap<Integer,Integer>();
		HashMap<Integer, Integer> blockSj = new HashMap<Integer,Integer>();
		double utiliz=0;
		
		double[] overalluti = new double[10000000];
		
		
		
		int[] av= new int[overalluti.length];
		int[] avAazam= new int[overalluti.length];
		int[] avNoRelinq= new int[overalluti.length];
		int[] avRelinq= new int[overalluti.length];
		int[] avSj= new int[overalluti.length];
		int[] avOptimize= new int[overalluti.length];
		
		for (int i=0; i<overalluti.length;i++){
			uti.put(i, (double) 0);
			overalluti[i]=0;
			 av[i]=tres;
			 avAazam[i]=tres;
			 avNoRelinq[i]=tres;
			 avRelinq[i]=tres;
			 avSj[i]=tres;
			 avOptimize[i] = tres;
			// availableRes.put(i, tres);
		}	

		
	    for (int z=0; z<resask.length;z++){
	
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = z; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);
             
			// Second step: Create Datacenters
			// Datacenters are the resource providers in CloudSim. We need at
			// list one of them to run a CloudSim simulation
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
		//	Datacenter datacenter1 = createDatacenter("Datacenter_1");
			//adrian
			userlist = new ArrayList<User>();// will contain the objects of user class in 
			userlist1 = new ArrayList<User>();
			int userid = z;
			User user = new User(userid);
			User user1 = new User(userid);

			userlist.add(user);// add user to user list
			userlist1.add(user1);
			hostlist1 = new ArrayList<Host>();
			//hostlist = new ArrayList<Host>();
			// Third step: Create Broker
			DatacenterBroker broker = createBroker();
		//	DatacenterBroker broker1 = createBroker();
			int brokerId = broker.getId();
			int brokerId1 = broker.getId();
			//here I set the initial value of total profit as 100, which will be fixed after discussion;
		//	double totalProfit = 100;
			Host host = datacenter0.getHostList().get(0);
			//Host host1 = datacenter1.getHostList().get(0);
			hostlist1.add(host);
		//	hostlist.add(host1);
			host = hostlist1.get(0);
			//host1 = hostlist.get(0);
			//host.setTotalProfit(totalProfit);
			
			//bind each list to broker 
			broker.setHostList(hostlist1);
			broker.submitUserList(userlist);
			broker.BindUserToHost(user.getuserId(), host.getId());
		//	broker1.setHostList(hostlist1);
		//	broker1.submitUserList(userlist1);
			//broker1.BindUserToHost(user1.getuserId(), host.getId());
			
			// Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();
		//	vmlist1 = new ArrayList<Vm>();
			// Fifth step: Create one Cloudlet
			cloudletList = new ArrayList<Cloudlet>();
	//		cloudletList1 = new ArrayList<Cloudlet>();
			int pesNumber = 1000; // number of cpus
			// Cloudlet properties
			int cloudletid =z;
			long length = 400000;
			//the unit of this filesize is Byte; so I need to divide it with 1000000000;
			long fileSize = 400000000000L;
			long outputSize = 400;
			//double price = 300;
			//I will set pledge later.
			//double pledgeAmount = price * 1.05;
			UtilizationModel utilizationModel = new UtilizationModelFull();
			
			Cloudlet cloudlet = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet.setbrokerId(brokerId);
			cloudlet.setuser(user);
			//cloudlet.setExecStartTime(0.1);
			//cloudlet.setprice(price);
			
			//user.setCloudletId(cloudlet.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet);

			// submit cloudlet list to the broker
			broker.submitCloudletList(cloudletList);
			
			Cloudlet cloudlet1 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet1.setbrokerId(brokerId1);
			cloudlet1.setuser(user1);
			//cloudlet.setExecStartTime(0.1);
			//cloudlet.setprice(price);			basicprice = user1.getnumCPU() * 0.01 + user1.getnumRAM() * 0.01 + (double)(user1.getnumStorage()*0.001);

			
		//	user1.setCloudletId(cloudlet1.getCloudletId());
			
			// add the cloudlet to the list
			
		//	cloudletList1.add(cloudlet1);

			// submit cloudlet list to the broker
		//	broker1.submitCloudletList(cloudletList1);
			//adrian 
			double numtime = duration[z];
			double starttime = timestamp[z];// sjs - gives start time
			int res = resask[z];//requested resources
			end1[z]=timestamp[z]+duration[z];
			//user.setnumtime(numtime);
			user.submitCloudlet(cloudlet);
			user.submitRequest(user.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
		//	user.submitRequest1(user.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			//user.setnumtime(numtime);
			user.submitCloudlet(cloudlet);
		//	user1.submitRequest1(user1.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			//set user's aop as not null
			host.getmtimes().put(userid, 0);//puts userid as key and 0 as no. of times user returns
			
			relinp.clear();
			
			int randr = r.nextInt(100) + 2;
			//int randr = 20;
			if(aveRP[z] == 0){} //checks whether first element of aveRP is zero, if zero, does not execute the code
			else  if(aveRP[z] > 0){    //nested loop           
				 for(int i = 0 ; i < randr; i++){
					double a = r.nextGaussian() * 0.3 + aveRP[z];
					if (a < 0 ){
						a=0.01;
					}
					else if( a>1){
						a = .99;
					}
					
						relinp.add(a);  // big arraylist of filterd out ave rp 
					
				    
				 }
		//		 System.out.println("avg relinp" + aveRP[z] );
			//	 System.out.println("avg relinp" + relinp );
				 
				 
				
				 
				 ArrayList<Double> se = new ArrayList<Double> ();
				 ArrayList<Double> de = new ArrayList<Double> ();
				 ArrayList<Double> se1 = new ArrayList<Double> ();
				 ArrayList<Double> de1 = new ArrayList<Double> ();
				//se.clear();
			  //  de.clear();
				host.getmaop().put(userid, se);
				 host.getmaop1().put(userid, se1);
				 host.getmsop().put(userid, de);
				 host.getmsop1().put(userid, de1);
				 for(int i = 0; i < randr; i++){
				// for(int i = 0; i < 9 ; i++){	 
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmaop1(userid, relinp.get(i));
					host.addmsop1(userid, relinp.get(i));
					host.addmtimes(userid);
					//System.out.println(relinp.get(i));
				//	host1.addmtimes1(userid);
				 }
			 }
			
			int []X = new int[relinp.size()];
			
			 for(int i = 0; i< relinp.size(); i++ ) {
				 
				 X[i] = i+1;
				 
			 }
		
			 //double  rel =  .6;// actual relinp
			double  rel =  r.nextGaussian() * 0.3 + aveRP[z];// actual relinp
			if (rel < 0 ){
				rel=0.01;
			}
			else if(rel>1){
				rel = .99;
			}
		//	System.out.println(" relinp" + rel );
			//here I set the basic price of the task; 0.01 dollar per hour per CPU, 0.01 dollar per hour per GB of RAM, 1*0.001 dollar per hour per GB of Storage;
			//maybe I need to write a function to calculate the price of the task;
	//		double basicprice = user.getnumCPU() * 0.01 + user.getnumRAM() * 0.01 + (double)(user.getnumStorage()*0.001);
		//	double pledge = basicprice * 1.05;
			
			
		//	System.out.println("user" + user.getuserId() + " asks for " + user.getnumCPU1() + " CPUs and " + user.getnumRAM1() + " GB of RAM and " + user.getnumStorage1() + " GB of storage to host" + host.getId() + "at time" + starttime);
		//	reqRes.add(resask[z]);
			//System.out.println("user" + user.getuserId() + " asks for " + user1.getnumCPU1() + " CPUs and " + user1.getnumRAM1() + " GB of RAM and " + user1.getnumStorage1() + " GB of storage to host" + host.getId() + "at time" + starttime);
			//int num = 0;
			
			
			
			
			
			
			
			int availableNoRelinq =ChkAvailableRes(tres,timestamp[z],avNoRelinq);
		//	System.out.println(availableNoRelinq + "available ideal res at time" +timestamp[z] );
			
	        broker.CalNumOfResIdeal(user.getuserId(), availableNoRelinq);
	       acpuNoRelinq.add(user.getnumCPU2());
			UpAvailableRes(z, acpuNoRelinq,timestamp[z] ,end1[z], avNoRelinq);
			
			blockNoRelinq.put(userid, user.getnumCPU2());
			
			
			
			
			
			
			
			
            int availableRelinq =ChkAvailableRes(tres,timestamp[z],avRelinq);
         //   System.out.println(availableRelinq + "available relinq res at time" +timestamp[z] );
	        broker.CalNumOfResRelinq(user.getuserId(), availableRelinq);
			acpuRelinq.add(user.getnumCPU3());
			
		//	ArrayList<Double> a = HostList.getById(getHostList(),host.getId()).getmaop().get(user.getuserId());
			
			
			
			
			
			double az = 0;
			 int availableAazam =ChkAvailableRes(tres,timestamp[z],avAazam);
			az =  broker.CalNumOfResAzam(user.getuserId(), host.getId(),availableAazam);
				host.addmtimes(userid);
			//	host.addmsop1(userid, relinp.get(0));
			///	host.addmaop1(userid, relinp.get(0));
				acpu1.add(user.getnumCPU1());
				aram1.add(user.getnumRAM1());
				asto1.add(user.getnumStorage1());
			//	System.out.println("For AAzam "+"host" + host.getId() + " will give " + user.getnumCPU1() + " CPUs and " +user.getnumRAM1()+ " GB of RAM and " + user.getnumStorage1() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());
				
			preda.add(az);	
				
				
				
				
				
				int availableSj =ChkAvailableRes(tres,timestamp[z],avSj);
				 broker.calNumOfResSj(user.getuserId(), host.getId(),availableSj);
					host.addmtimes(userid);
					acpuSj.add(user.getnumCPU4());
					
					
					
					
					
					
				utiliz = CalUti(tres,timestamp[z], acpu, uti, expiretime1, utireal);
				// System.out.println(utiliz + "utilization at time" +timestamp[z] );
			    int available =ChkAvailableRes(tres,timestamp[z],av);
			  //  System.out.println(available + "available res at time" +timestamp[z] );
				//double payprice = 0;
				//System.out.println("user" + user.getuserId() + " asks for " + user.getnumCPU() + " CPUs and " + user.getnumRAM() + " GB of RAM and " + user.getnumStorage() + " GB of storage to host" + host.getId() + "at time" + starttime);
			//System.out.println("user" + user.getuserId() + " asks for " + user.getnumCPU() + " CPUs and " + user.getnumRAM() + " GB of RAM and " + user.getnumStorage() + " GB of storage to host" + host.getId() + "at time" + starttime);
		//	System.out.println("user" + user1.getuserId() + " asks for " + user.getnumCPU1() + " CPUs and " + user.getnumRAM1() + " GB of RAM and " + user.getnumStorage1() + " GB of storage to host" + host.getId() + "at time" + starttime);
			if(cloudlet.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
			//	payprice = pledge;
			}
			else{
				 int id = 0;
				  double x = 0;
				   id = DecideModel(utiliz, rel, userid, res,z); 
				  // id=4;
				//   System.out.println("model is" + id);
				//because we already know that it is the first time of the whole process, so we don't need to decide if the uti has nothing;
				   if (host.getmtimes().get(userid) == 0){
						host.addmtimes(userid);
						ArrayList<Double> aaa = new ArrayList<Double> ();
						ArrayList<Double> bbb = new ArrayList<Double> ();
						//here I will set some default values for aop and sop and ...;
						if(host.getmaop().get(userid) == null)
						{System.out.println("calling this function 1");
							
					    broker.CalNumOfRes(user.getuserId(), host.getId(), 0.3, 0.3, id, available);
						//right now we don't consider the pricing model;
						//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
						host.getmaop().put(userid, bbb);
					   }
				   	
					    else {
							System.out.println("calling this function 2");
							 broker.CalNumOfRes(user.getuserId(), host.getId(), 0.3, id);
							//right now we don't consider the pricing model;
							//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId());
					    }
						host.getmsop().put(userid, aaa);
						host.addmsop(userid, rel);
						host.addmaop(userid, rel);
			     }
					else if(host.getmtimes().get(userid) == 1){
						host.addmtimes(userid);
						// will add codes for sop and aop and ...;
						System.out.println("calling this function 3");
						broker.CalNumOfRes1(user.getuserId(), host.getId(), id,available);
						host.addmsop(userid, rel);
						host.addmaop(userid, rel);
						//payprice = broker.CalPrice(cloudlet3.getCloudletId(), host.getId());
					}
					else if(host.getmtimes().get(userid) == 2){
						host.addmtimes(userid);
						// will add codes for sop and aop and ...;
						System.out.println("calling this function 4");
						broker.CalNumOfRes2(user.getuserId(), host.getId(), id,available);
						host.addmsop(userid, rel);
						host.addmaop(userid, rel);
						//payprice = broker.CalPrice(cloudlet3.getCloudletId(), ho+st.getId());
					}
					else if(host.getmtimes().get(userid) > 2){
						x = broker.CalNumOfRes(user.getuserId(), host.getId(), id,available);
				//	System.out.println("calling this function 5");
					//	int a = host.getmtimes().get(userid);
						host.addmtimes(userid);
						host.addmsop(userid, rel);
						host.addmaop(userid, rel);
						
					}
				   predQi.add(x);
				    //broker.predict(user.getuserId(), host.getId());
				  
			}
			
			LinearRegression pre = new LinearRegression(X,relinp);
			double predict = 0;
			
		    predict = 	pre.predict(relinp.size()+1);
		    pred.add(predict);
		//	System.out.println("list sj "+ acpuSj);
			
			
			
			
			
			
			
			//System.out.println("this is the " + utimeh + "th time for user" + user.getuserId() + " to use services of host" + host.getId());
		//	payprice = user.getnumCPU() * 0.01 + user.getnumRAM() * 0.01 + (double)(user.getnumStorage()*0.001);
			
			acpu.add(user.getnumCPU());
			aram.add(user.getnumRAM());
			asto.add(user.getnumStorage());
			
			
			/**PrintWriter writerRes = new PrintWriter ("C:\\New folder\\Required Res .txt", "UTF-8");
		//	PrintWriter writerQi = new PrintWriter ("C:\\New folder\\Allocated Res Qi.txt", "UTF-8");
			//PrintWriter writerAazam = new PrintWriter ("C:\\New folder\\Allocated Res Aazam.txt", "UTF-8");
			PintWriter writerRelinq = new PrintWriter ("C:\\New folder\\Allocated Res Relinq.txt", "UTF-8");
			PrintWriter writerNoRelinq = new PrintWriter ("C:\\New folder\\Allocated Res Not Relinq.txt", "UTF-8");
			//writerRes.println(reqRes);
			writerQi.println(acpu);
			writerAazam.println(acpu1);
			writerRelinq.println(acpuRelinq);
			writerNoRelinq.println(acpuNoRelinq);
			writerAazam.close();
			writerQi.close();
			//writerRes.close();
			writerRelinq.close();
			writerNoRelinq.close();**/
			
						// VM description
						//pesNumber = user.getnumCPU();
						int vmid = z;
						int mips = 1000;
						//long size = 500000; // image size (MB)
						//int ram = 2048; // vm memory (MB)
						long bw = 1000;
						String vmm = "Xen"; // VMM name
						// create VM
						Vm vm = new Vm(vmid, brokerId, mips, user.getnumCPU(), user.getnumRAM(), bw, user.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
						vm.setHost(host);
						vmlist.add(vm);
						//int vmid2 = 1;
						//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
						// add the VM to the vmList
						 endtime[vmid] = timestamp[vmid]+ duration[vmid];
						 end.add(endtime[vmid]);
					//	System.out.println("req duration is "+duration[vmid]);
						//System.out.println("req till" + endtime[vmid]);
						//System.out.println("req duration is "+duration[vmid]);
						duration[z] = (int)(duration[z]*(1-rel));	///duration is set according to the relinp.add(a); everytime list is cleared and a new value is added to first element of list and everytime 1-relinp.get(0) is used
					//	relinqTime.add(duration[z]);//list collects relinquish times of the users
					
						actual.add(rel);
						//	System.out.println("given duration is "+duration[vmid]);
						//	System.out.println("balle " + relinp.get(0));
						expiretime1[vmid]= timestamp[vmid] + duration[vmid]; 
					//	relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid])+ timestamp[vmid] ) + timestamp[vmid];
						//relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) );
					//	System.out.println("ts"+timestamp[vmid]+" expt"+expiretime1[vmid] + "end"+endtime[vmid]);
						//vmlist.add(vm2);
						//expiretime1[z] = timestamp[z] + duration[z];
						relinqTime.add(expiretime1[z]);
				//		System.out.println("host" + host.getId() + " will give " + user.getnumCPU() + " CPUs and " +user.getnumRAM()+ " GB of RAM and " + user.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());			
					
						int maxTime=expiretime1[0];
						for (int counter = 1; counter < expiretime1.length; counter++)
							{
							     if (expiretime1[counter] > maxTime)
							     {
							      maxTime = expiretime1[counter];
						 }
							     }
					
						UpAvailableRes(z,  acpuRelinq,timestamp[z] ,expiretime1[z], avRelinq);
						UpAvailableRes(z,  acpu1,timestamp[z] ,expiretime1[z], avAazam);
						UpAvailableRes(z,  acpuSj,timestamp[z] ,expiretime1[z], avSj);
						CalUtinew(overalluti,z,tres,timestamp[z], expiretime1[z],acpu, uti, utiliz, utireal);
						UpAvailableRes(z, acpu,timestamp[z] ,expiretime1[z], av);
						
						block.put(userid,user.getnumCPU());
						blockAazam.put(userid,user.getnumCPU1());
						blockRelinq.put(userid, user.getnumCPU3());
						blockSj.put(userid, user.getnumCPU4());
						// submit vm list to the broker
						broker.submitVmList(vmlist);
						
						broker.bindCloudletToVm(cloudletid, vmid);
						
	}
//	    System.out.println("relin " + actual);
	//    System.out.println("pred " + pred);
	//    System.out.println("predQi " + predQi);
		
	    
		
	   //optimization model 
	    
	    
	    
	    int batchSize = 5;
	    double[] relinq = new double [timestamp.length];
	    double[]prob = new double [timestamp.length];
	    
	    for (int i = 0; i<prob.length; i++) {
	    	prob[i] = pred.get(i);
	    	relinq[i] = actual.get(i);
	    }
		
		
		// {0.4272155324635558, 0.7316919419020255, 0.702029892176997, 0.49117944013313763, 0.4945952109265612, 0.3814719539117115, 0.6104654974385018, 0.7712400993836594, 0.20497192398039743, 0.11425075612505164};//, 0.20579159468580266, 0.1169987736200625, 0.30685496557827374, 0.36668670879349563, 0.38444252903470577, 0.4382608270160684, 0.6909955739934297, 0.45276623402575034, 0.4090671814837832, 0.7531367378598116};
		int[] startOp = new int [timestamp.length];//{104, 110, 120, 134, 150,160,166,180,191,202};//, 35080, 36016, 36235, 47518, 52234, 57481, 64097, 68129, 72295, 90940, 91446, 93329, 97182, 97826,98343};
		int[] usedDura = new int [timestamp.length];
		
		// {0.4173206147256701, 0.6930842216026064, 0.7282547069494332, 0.5136210383211806, 0.48268858105823474, 0.352380488607993, 0.6794891246701898, 0.8087924430295516, 0.25546497930747412, 0.144947636901526655};//, 0.24422861328524721, 0.12167708909580224, 0.35194967482360099, 0.4220030494830489, 0.3764201772983033, 0.3974959128069639, 0.647332406436446717, 0.42753166095842967, 0.4241038632761484, 0.74404746570013551};//DreadFile("/home/singh/Downloads/papers/prob.txt");
		
		ArrayList<Integer> ncpu= new ArrayList<Integer>();
		ArrayList<Integer> finalcpu= new ArrayList<Integer>();
		ArrayList<Integer> availa= new ArrayList<Integer>();
		
		int[] endOp = new int [startOp.length];
		
		int[] expire = new int [startOp.length];
		
		
		
       
		 
       
       
		
		int fUserofBatch = 0;
		int lUserofBatch = batchSize;
		int avail  = 0;
		
		int nBatches = timestamp.length / batchSize;
		
		
		
		
		 for(int batchNo = 0 ; batchNo < nBatches ; batchNo ++){
			 
			 DatacenterBroker broker1 = createBroker();
			// System.out.println("lUserofBatch   " + lUserofBatch);
			// System.out.println("lUserofBatchtime   " + timestamp[ lUserofBatch-1]);
			 
		 avail = ChkAvailableResBatch(tres,timestamp[ lUserofBatch-1],avOptimize);
		
	//	 System.out.println("avail   " + avail);
		      
		      availa.add(avail);
		      ncpu = broker1.model(batchNo, batchSize, fUserofBatch, lUserofBatch, avail, resask, prob, duration, relinq);
       
		   System.out.println("ncpu   " + ncpu);
		      
		      
		      
		      
		      for (int i = 0; i < batchSize; i++) {
		    	  
		    	  finalcpu.add(ncpu.get(i));
		    	  
		       } 
		      
		//      System.out.println("fcpu   " + finalcpu);
		      
		//      System.out.println("lUserofBatchtime   " +  timestamp[lUserofBatch-1]);
		      
		      for (int j=fUserofBatch; j < lUserofBatch; j++) {
		    	  
		    	  
		    	  startOp[j] = timestamp[lUserofBatch-1];
		    	  endOp [j] = startOp[j]+ dura[j];
		    	  usedDura[j] = (int) (dura[j]*(1-relinq[j]));
		    	  expire[j] = startOp[j]+ usedDura[j];
		    	  
		    	//  System.out.println("start   " +  startOp[j]);

		    	  //System.out.println("end   " +  endOp [j]);
		    	  //System.out.println("used   " +   usedDura[j]);
		    	  //System.out.println("exp   " +  expire[j]);
		    	  
		    	  
		    	  
		    	  
		    	  UpAvailableRes(j,  finalcpu, startOp[j], expire[j],avOptimize);
		    	  
		    	  
		    	
		    	 
		    	  
		     }
		      
		    
	
		      fUserofBatch +=  batchSize;
		      lUserofBatch +=  batchSize;
		      
		//      System.out.println("update   " + lUserofBatch);
		 
		 //     System.out.println(" av update   " + timestamp[ startOp[4989	]]);
		
		
		
		      	
		 
		 
		 }//loop 
		 
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   // CloudSim.startSimulation();
//			/////////////////////////////
			///////////////////////
			////////////////////////
	    
	    
	 //   System.out.println("block Azam " + blockAazam);
	 //   System.out.println("block qi " + block);
	 //   System.out.println("block Relinq " + blockRelinq);
	 //   System.out.println("block no Relinq " + blockNoRelinq);
	    
	  
	    
	    
	    
	   
	    
	    
	    
	    
	    ArrayList<Double> OverallUtiIdeal = new ArrayList<Double> ();
	    ArrayList<Double> OverallUtiRelinq = new ArrayList<Double> ();
	    ArrayList<Double> OverallUtiAazam = new ArrayList<Double> ();
	    ArrayList<Double> OverallUtiQi = new ArrayList<Double> ();
	    ArrayList<Double> OverallUtiSj = new ArrayList<Double> ();
	    ArrayList<Double> OverallUtiOp = new ArrayList<Double> ();
	    
	    
	    
	    
	    ArrayList<Double> npIdeal = new ArrayList<Double> ();
	    ArrayList<Double> npRelinq = new ArrayList<Double> ();
	    ArrayList<Double> npAazam = new ArrayList<Double> ();
	    ArrayList<Double> npQi = new ArrayList<Double> ();
	    ArrayList<Double> npSj = new ArrayList<Double> ();
	    ArrayList<Double> npOp = new ArrayList<Double> ();
	    
	    
	    
	    
		int maxTime=endtime[0];
		for (int counter = 1; counter < endtime.length; counter++)
			{
			     if (endtime[counter] > maxTime)
			     {
			      maxTime = endtime[counter];
		 }
			     }
		System.out.println(finalcpu.size());
	    System.out.println("cpac   " + availa);
	    
	    int totalDuration = maxTime ;//maximum value of duration array
	    
	    int maxTimeOp=endOp[0];
		for (int counter = 1; counter < endOp.length; counter++)
			{
			     if (endOp[counter] > maxTimeOp)
			     {
			      maxTimeOp = endOp[counter];
		 }
			     }
	    
		  int totalDuration1 = maxTimeOp	 ; 
	    
		//int[] starTime = new starTime[nOfusers];
	    int[] starTime= new int[resask.length];
	    int[] endTime= new int [resask.length];
	    int[] relinqtime =new int[resask.length];
	    int[] resAllocIdeal = new int[resask.length];
	    int[] resAllocUserRelinq = new int[resask.length];
	    int[] resAllocAazam = new int[resask.length];
	    int[] resAllocQi = new int[resask.length];
	    int[] resAllocSj = new int[resask.length];
	    int[] resAllocOp = new int[resask.length];
	    
	    
	    
	    for(int b=0; b < resask.length; b++){
	      starTime[b] = timestamp[b];//start time of task
	      relinqtime[b] = relinqTime.get(b) ;//assumed scenario 2,3, 4,5
	      endTime[b] = end1[b];//end time of task
	      resAllocIdeal[b]= acpuNoRelinq.get(b); //scenario 1 resources allocated = res requested
	     
	      resAllocUserRelinq[b]= acpuRelinq.get(b); //scenario 2
	     
	      resAllocAazam[b]= acpu1.get(b); //scenario 3
	      resAllocQi[b]= acpu.get(b); // scenario 4
	      resAllocSj[b]= acpuSj.get(b); // scenario 5
	      
	      resAllocOp[b] = finalcpu.get(b);// OP model
	    }
	//    System.out.println(acpuNoRelinq);
	//    System.out.println(acpuRelinq);
	    
	    int[] resUsedIdeal = new int[totalDuration ];//total resources used at time i
	    int[] resUsedUserRelinq = new int[totalDuration ];
	    int[] resUsedAazam = new int[totalDuration ];
	    int[] resUsedQi = new int[totalDuration ];
	    int[] resUsedSj = new int[totalDuration ];
	    int[] resUsedOp = new int[totalDuration1 ];
	    
	    
	    
	    double[] elecExpenseIdeal = new double[totalDuration ];// electricity expenses at time i
	    double[] elecExpenseUserRelinq = new double[totalDuration ];
	    double[] elecExpenseAazam = new double[totalDuration ];
	    double[] elecExpenseQi = new double[totalDuration ];
	    double[] elecExpenseSj = new double[totalDuration ];
	    double[] elecExpenseOp = new double[totalDuration1 ];
	    
	    
	    float[] overallUtiIdeal = new float[totalDuration ];// overall utilization at time i
	    float[] overallUtiUserRelinq = new float[totalDuration ];
	    float[] overallUtiAazam = new float[totalDuration ];
	    float[] overallUtiQi = new float[totalDuration ];
	    float[] overallUtiSj = new float[totalDuration ];
	    float[] overallUtiOp = new float[totalDuration1 ];
	    
	    
	    
	    double[] incomeIdeal= new double[totalDuration ]; // income at time i
	    double[] incomeUserRelinq= new double[totalDuration ];
	    double[] incomeAazam= new double[totalDuration ];
	    double[] incomeQi= new double[totalDuration ];
	    double[] incomeSj= new double[totalDuration ];
	    double[] incomeOp= new double[totalDuration1 ];
	    
	    
	    
	    double[] relinqLossIdeal = new double[totalDuration ];
	    double[] relinqLossUserRelinq = new double[totalDuration ]; // Relinquishment loss at time i
	    double[] relinqLossAazam = new double[totalDuration ];
	    double[] relinqLossQi = new double[totalDuration ];
	    double[] relinqLossSj = new double[totalDuration ];
	    double[] relinqLossOp = new double[totalDuration1 ];
	    
	    
	    
	    
	    double[] netProfitIdeal = new double[totalDuration ];
	    double[] netProfitUserRelinq = new double[totalDuration ]; // Relinquishment loss at time i
	    double[] netProfitAazam = new double[totalDuration ];
	    double[] netProfitQi = new double[totalDuration ];
	    double[] netProfitSj = new double[totalDuration ];
	    double[] netProfitOp = new double[totalDuration1 ];
	    
	    
	  //  int[] usersAtTimeiIdeal = new int[totalDuration ];
	  //  int[] usersAtTimeiUserRelinq = new int[totalDuration ];
	  //  int[] usersAtTimeiAazam = new int[totalDuration ];
	  //  int[] usersAtTimeiQi = new int[totalDuration ];
	    
	    
	 //   for (int i=0, i<use)
	 //   int n = 4;                 //no. of servers in the cloud for scenario 1
	  //  int n = 3;             // scenario 2
	   
	    
	    
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
	   // ArrayList<Double> relinp = new ArrayList<Double> ();
	  // ArrayList<Double> relinp = new ArrayList<Double> ();	
	    
	    
	    
	  
	  
	    // calculating  Relinquishment loss
	    
	    ArrayList<Integer> a = new ArrayList<Integer> (); 
	   
	   for( int z = 0; z < relinqtime.length; z++){      // for user z 
		   
		   a.clear();
		  
		   
		   int tc = 0;
		   int nextUserArrival = 0;
	       int rr=0;
	       int ee=0;
	       
	       
		   for(int v = 0 ; v < relinqtime.length; v++){
			   
			   a.add(v,0);  
			   
			 if (starTime[v]>relinqtime[z]){
				 
				rr = starTime[v];
				a.add(ee,rr);     // add all values of user start times > relinq time of user z
			     ee++;
			     
			 }
		   }
		  
		   
		  nextUserArrival = a.get(0);                                        // get the first value of list which gives the time of next user arrival after user z relinquies
		                      // list gets clear everytime
		   
		 
		  
		  if (nextUserArrival!= 0 && endTime[z]-relinqtime[z] > nextUserArrival-relinqtime[z] ){      // if duration of between relinq and user arrival is less than relinq and end time of user 
			  tc = nextUserArrival;                                           // loss till next user arrival
		  }
		  else {
			  tc = endTime[z];                                                //else loss till end time
		  }
		  
		  
		  //for (int l = relinqtime[z]; l < endTime[z]; l++ ) {
	      for (int l = relinqtime[z]; l < tc; l++ ){                          // finallly calculate relinq loss 
	    	
	    	relinqLossUserRelinq[l] += (float) (resAllocUserRelinq[z]*price);
	    	relinqLossAazam[l] += (float) (resAllocAazam[z]*price);
	    	relinqLossQi[l] += (float) (resAllocQi[z]*price);
	    	relinqLossSj[l] += (float) (resAllocSj[z]*price);
	  //  	System.out.println(relinqLoss[l]);
	    	//System.out.println(l);
	     
	      }
	       
	   
	   }
	   
 for( int z = 0; z < expire.length; z++){      // for user z 
		   
		   a.clear();
		  
		   
		   int tc1 = 0;
		   int nextUserArrival = 0;
	       int rr1=0;
	       int ee1=0;
	       
	       
		   for(int v = 0 ; v < expire.length; v++){
			   
			   a.add(v,0);  
			   
			 if (startOp[v]>expire[z]){
				 
				rr1 = startOp[v];
				a.add(ee1,rr1);     // add all values of user start times > relinq time of user z
			     ee1++;
			     
			 }
		   }
		  
		   
		  nextUserArrival = a.get(0);                                        // get the first value of list which gives the time of next user arrival after user z relinquies
		                      // list gets clear everytime
		   
		 
		  
		  if (nextUserArrival!= 0 && endOp[z]-expire[z] > nextUserArrival-expire[z] ){      // if duration of between relinq and user arrival is less than relinq and end time of user 
			  tc1 = nextUserArrival;                                           // loss till next user arrival
		  }
		  else {
			  tc1 = endOp[z];                                                //else loss till end time
		  }
		  
		
		//  for (int l = relinqtime[z]; l < endTime[z]; l++ ) {
			   
		  
	      for (int l = expire[z]; l < tc1; l++ ){                          // finallly calculate relinq loss 
	    	
	    	relinqLossOp[l] += (float) (resAllocOp[z]*price);
	    	
	    	//System.out.println(l);
	     
	      }
	       
	   
	   }
	   
	   //calculating the no of users at time i
	   
	  
	   
	/**  if  ( relinqtime[t]<endTime[t] ){
		   for(  t =0; t<starTime.length;t++){
		        for( int k =  starTime[t];  k < relinqtime[t];k++ ){
		        	 usersAtTimei[k]+= 1;
		            //System.out.println( usersAtTimei[k]);
		        }
		   }
		   //for(  t =0; t<usersAtTimei.length;t++){
		 //  System.out.println( usersAtTimei[t]);
	  //}*/
		//   int[] noOfUsersRelinqTimei = new int [starTime.length];
		   
		//   Map<Integer,Integer> counterMap = new HashMap<>();

		    
		   
		
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
         //  System.out.println("entered in relinquish");
		    //  System.out.println(repetitionsR);
		 //  System.out.println("entered in endtime")1;
 	      //    System.out.println(repetitionsE);
		  
		   
	  //  resUsed[0]=0;
	    for(int i=0; i<resUsedIdeal.length;i++){
	    	resUsedIdeal[i]=0;
	    	resUsedUserRelinq[i]=0;
	    	resUsedAazam[i]=0;
	    	resUsedQi[i]=0;
	    	resUsedSj[i]=0;
	    	resUsedOp [i]=0;
	    }
	    
	    
	    
	   
	    //calculating resources used at time i
	    for( int j =0; j<starTime.length;j++){
	    	
	    	for( int k =  starTime[j];  k < endTime[j];k++ ){
	    		resUsedIdeal[k]+=resAllocIdeal[j];
	    	}
	    	
	    	
	    	
	    	
	        for( int k =  starTime[j];  k <relinqtime[j];k++ ){
	           
	        	
		    	resUsedUserRelinq[k]+=resAllocUserRelinq[j];
		    	resUsedAazam[k]+=resAllocAazam[j];
		    	resUsedQi[k]+=resAllocQi[j];
		    	resUsedSj[k]+=resAllocSj[j];
	          //  System.out.println("S"+starTime[j]);
	            //System.out.println("R"+relinqtime[j]);
	            //System.out.println("RU"+resUsedQi[k]);
	        }
	      
	        for( int k =  startOp[j];  k < expire[j];k++ ){
	    		resUsedOp[k]+=resAllocOp[j];
	    	   } 
		    
	    }
	    
	    
	   // Calculating income, electricity expenses, overall utilization at time i
	    double uuuu = .0005;
	    for(int i=0; i<resUsedIdeal.length;i++)
        {
	    	overallUtiIdeal[i]= (float) (resUsedIdeal[i]*uuuu);
	    	overallUtiUserRelinq[i]= (float) (resUsedUserRelinq[i]*uuuu);
	    	overallUtiAazam[i]= (float) (resUsedAazam[i]*uuuu);
	    	overallUtiQi[i]= (float) (resUsedQi[i]*uuuu);
	    	overallUtiSj[i]= (float) (resUsedSj[i]*uuuu);
	    	overallUtiOp[i]= (float) (resUsedOp[i]*uuuu);
	    	//System.out.println(overallUti[i]/100);
	    	
	   
	    	elecExpenseIdeal[i]= elecPrice*     pServer*((overallUtiIdeal[i]/100)        +   ((2/3.0)*(1-overallUtiIdeal[i]/100)));
	    	elecExpenseUserRelinq[i]= elecPrice*pServer/3*((overallUtiUserRelinq[i]/100)+2);//simplified form of equation
	    	elecExpenseAazam[i]= elecPrice*     ((pServer*(overallUtiAazam[i]/100)) +          (pServer*(2/3.0)*(1-overallUtiAazam[i]/100)));
	    	elecExpenseQi[i]= elecPrice*        ((pServer*(overallUtiQi[i]/100)) +             (pServer*(2/3.0)*(1-overallUtiQi[i]/100)));
	    	elecExpenseSj[i]= elecPrice*        ((pServer*(overallUtiSj[i]/100)) +             (pServer*(2/3.0)*(1-overallUtiSj[i]/100)));
	    	elecExpenseOp[i]= elecPrice*        ((pServer*(overallUtiOp[i]/100)) +             (pServer*(2/3.0)*(1-overallUtiOp[i]/100)));
	    //	System.out.println(elecExpense[i]);
	    	//Scanner s = new Scanner(System.in);
	    	//String abcd= s.next();
	    	incomeIdeal[i] = resUsedIdeal[i]*price;
	    	incomeUserRelinq[i] = resUsedUserRelinq[i]*price;
	    	incomeAazam[i] = resUsedAazam[i]*price;
	    	incomeQi[i] = resUsedQi[i]*price;
	    	incomeSj[i] = resUsedSj[i]*price;
	    	incomeOp[i] = resUsedOp[i]*price;
	    	
	    	
	    	
	       DecimalFormat df = new DecimalFormat("#.#####");    
	    	 
	       elecExpenseIdeal[i] = Double.valueOf(df.format(elecExpenseIdeal[i]));
	  	   elecExpenseUserRelinq[i] = Double.valueOf(df.format(elecExpenseUserRelinq[i]));
	  	   elecExpenseAazam[i] = Double.valueOf(df.format(elecExpenseAazam[i]));
	  	   elecExpenseQi[i] = Double.valueOf(df.format(elecExpenseQi[i]));
	  	   elecExpenseSj[i] = Double.valueOf(df.format(elecExpenseSj[i]));
	  	  elecExpenseOp[i] = Double.valueOf(df.format(elecExpenseOp[i]));
	  	   
	  	   
	  	   
	  	   incomeIdeal[i]= Double.valueOf(df.format( incomeIdeal[i]));
	  	   incomeUserRelinq[i]= Double.valueOf(df.format( incomeUserRelinq[i]));
	  	   incomeAazam[i]= Double.valueOf(df.format( incomeAazam[i]));
	  	   incomeQi[i]= Double.valueOf(df.format( incomeQi[i]));
	  	   incomeSj[i]= Double.valueOf(df.format( incomeSj[i]));
	  	   incomeOp[i]= Double.valueOf(df.format( incomeOp[i]));
	  	   
	  	 
	       relinqLossIdeal[i]= Double.valueOf(df.format( relinqLossIdeal[i]));
	  	   relinqLossUserRelinq[i]= Double.valueOf(df.format( relinqLossUserRelinq[i]));
	       relinqLossAazam[i]= Double.valueOf(df.format( relinqLossAazam[i]));
	       relinqLossQi[i]= Double.valueOf(df.format( relinqLossQi[i]));
	       relinqLossSj[i]= Double.valueOf(df.format( relinqLossSj[i]));
	       relinqLossOp[i]= Double.valueOf(df.format( relinqLossOp[i]));
	       
	       
	        netProfitIdeal[i]= (incomeIdeal[i]-(relinqLossIdeal[i]+elecExpenseIdeal[i]));
	    	netProfitUserRelinq[i]= (incomeUserRelinq[i]-(relinqLossUserRelinq[i]+elecExpenseUserRelinq[i]));
	    	netProfitAazam[i]= (incomeAazam[i]-(relinqLossAazam[i]+elecExpenseAazam[i]));
	    	netProfitQi[i]= (incomeQi[i]-(relinqLossQi[i]+elecExpenseQi[i]));
	    	netProfitSj[i]= (incomeSj[i]-(relinqLossSj[i]+elecExpenseSj[i]));
	    	netProfitOp[i]= (incomeOp[i]-(relinqLossOp[i]+elecExpenseOp[i]));
	    	
	    	
	    	OverallUtiIdeal.add((double) overallUtiIdeal[i]);
	    	OverallUtiRelinq.add((double) overallUtiUserRelinq[i]);
	    	OverallUtiAazam.add((double) overallUtiAazam[i]);
	    	OverallUtiQi.add((double) overallUtiQi[i]);
	    	OverallUtiSj.add((double) overallUtiSj[i]);
	    	OverallUtiOp.add((double) overallUtiOp[i]);
	    	
	    	
	    	
	    	npIdeal.add(netProfitIdeal[i]);
	    	npRelinq.add(netProfitUserRelinq[i]);
	    	npAazam.add(netProfitAazam[i]);
		  	npQi.add(netProfitQi[i]);
		  	npSj.add(netProfitSj[i]);
		  	
		  	npOp.add(netProfitSj[i]);
		  	
		  	
		  	
	  	
        }
//	    int[] p1 = new int [25];
	//    int[] p2 = new int [25];
	  //  int[] p3 = new int [25];
	    //int[] p4 = new int [25];
	  
	    System.out.println("ideal");
	    int y=50;
	    
	    for (int i = 0; i <y; i++){
	    	 
	    	 System.out.println( netProfitIdeal[(i)*1800]+",");
	    }
	    
	    
	    System.out.println( "relinq");
	    for (int i = 0; i < y; i++){
	    	 
	    	System.out.println( netProfitUserRelinq[(i)*1800]+",");
	    }
	    	
	    
	    System.out.println( "azam");
	    for (int i = 0; i <y; i++){
	    	System.out.println( netProfitAazam[(i)*1800] + ",");
	    }
	    	
	    
	    System.out.println( "qi");
	    for (int i = 0; i <y; i++){
	    	 System.out.println( netProfitQi[(i)*1800]+","); 	 
	    
	    	 
	    }
	    
	    System.out.println( "sj");
	    for (int i = 0; i <y; i++){
	    	 System.out.println( netProfitSj[(i)*1800]+",");
	    }
	    
	    System.out.println( "Op");
	    for (int i = 0; i <y; i++){
	    	 System.out.println( netProfitOp[(i)*1800]+",");
	    }
	    
	    
	    System.out.println("utiliz");
	    
   	 System.out.println("ideal");
   	for (int i = 0; i <y; i++){
   	   System.out.println( overallUtiIdeal[(i)*1800]+",");
   	}
	    System.out.println( "relinq");
	    for (int i = 0; i <y; i++){
	    	 
	    	System.out.println( overallUtiUserRelinq[(i)*1800]+",");
	    }
	    	System.out.println( "azam");
	    for (int i = 0; i <y; i++){
	    	System.out.println( overallUtiAazam[(i)*1800] + ",");
	    }
	    	System.out.println( "qi");
	    for (int i = 0; i <y; i++){
	    	 System.out.println( overallUtiQi[(i)*1800]+",");
	    	 
	    } 
	    	 
	    
    		//netProfitUserRelinq[i*3600]= p2[i];
    		//netProfitAazam[i*3600]= p3[i];
    		//netProfitQi[i*3600]= p4[}
	    System.out.println( "sj");
	    
	    for (int i = 0; i <y; i++){
	    	System.out.println( overallUtiSj[(i)*1800] + ",");
	    }
	    
	   
	    
	    System.out.println( "Op ");
	    for (int i = 0; i <y; i++){
	    	 System.out.println( overallUtiOp[(i)*1800]+",");
	    	 
	    } 	
	    	 
	    
	    
	    
	    double s1=0,s2=0, s3=0, s4=0, s5=0, s6=0;
	    double a1 =0, a2=0, a3=0, a4=0, a5=0, a6 = 0;
	    for(int i = 0; i < overallUtiIdeal.length; i++){
	    	s1 += overallUtiIdeal[i];
	    	s2 += overallUtiUserRelinq[i];
	    	s3 += overallUtiAazam[i];
	    	s4 += overallUtiQi[i];
	    	s5 += overallUtiSj[i];
	    	s6 += overallUtiOp[i];
	    }
	    
	    a1 = s1/overallUtiIdeal.length;
	    a2 = s2/overallUtiIdeal.length;
	    a3 = s3/overallUtiIdeal.length;
	    a4 = s4/overallUtiIdeal.length;
	    a5 = s5/overallUtiIdeal.length;
	    a6 = s6/overallUtiIdeal.length;
	    
	    System.out.println("Avg uti sc1 "+ a1);
	   // System.out.println(p1);
	    System.out.println("Avg uti sc2 "+ a2);
	   // System.out.println(p2);
	    System.out.println("Avg uti sc3 "+ a3);
	   // System.out.println(p3);
	    System.out.println("Avg uti sc4 "+ a4);
	    //System.out.println(p4);
	    System.out.println("Avg uti sc5 "+ a5);
	    
	    System.out.println("Avg uti sc6 "+ a6);
	    
	    
	    int j=0;int k=0;int l=0; int m=0, nn=0, nnn = 0;
		for (int i= 0 ; i< acpu.size() ;i++){
			
			   if (acpu.get(i)==0){
				   j+=1;
				  
			   }
			   if (acpu1.get(i)==0){
				   k+=1;
				  
			   }
			   if (acpuNoRelinq.get(i)==0){
				   l+=1;
				 
			   }
			   if (acpuRelinq.get(i)==0){
				   m+=1;
				  
			   }
			   if (acpuSj.get(i)==0){
				   nn+=1;
				  
			   }
			   
			   if (finalcpu.get(i)==0){
				   nnn+=1;
				  
			   }
		}
		  System.out.println(l + " block ideal");
		  System.out.println(m + " block relinq");
		  System.out.println(j + " block Qil");
		  System.out.println(k + " block Aazam");
		  System.out.println(nn + " block Sj");
		  System.out.println(nnn + " block Op");
		  
	    for(int i=0; i<resUsedIdeal.length;i++){
	 
	/**  System.out.println( "Time "+  i + " total resources used " +resUsedIdeal[i]+ "  overall Util " 
	//  + overallUtiIdeal[i] + "% " +" Elec Expense  " + "$"+elecExpenseIdeal[i]+ " , income "+"$"+ incomeIdeal[i]+ 
	//  " relinq Loss $"+ relinqLossIdeal[i]+ " net profit " + netProfitIdeal[i] );
	   
	   PrintWriter Oveallutiideal = new PrintWriter ("C:\\New folder\\Oveall Utiliz Ideal .txt", "UTF-8");
	    PrintWriter OveallutiRelin = new PrintWriter ("C:\\New folder\\Oveall Utiliz User Relinquish .txt", "UTF-8");
	    PrintWriter OveallutiAazam = new PrintWriter ("C:\\New folder\\Oveall Utiliz Aazam .txt", "UTF-8");
	    PrintWriter OveallutiQi =    new PrintWriter ("C:\\New folder\\Oveall Utiliz Qi .txt", "UTF-8");
	    
	    Oveallutiideal.println(OverallUtiIdeal);
	    OveallutiRelin.println(OverallUtiRelinq);
	    OveallutiAazam.println(OverallUtiAazam);
	    OveallutiQi.println(OverallUtiQi);
		OveallutiQi.close();
		OveallutiAazam.close();
		OveallutiRelin.close();
		Oveallutiideal.close();
	    
		
		PrintWriter npideal = new PrintWriter ("C:\\New folder\\Net Profit Ideal.txt", "UTF-8");
		PrintWriter nprelin = new PrintWriter ("C:\\New folder\\Net Profit User Relinquish.txt", "UTF-8");
	    PrintWriter npqi = new PrintWriter ("C:\\New folder\\Net Profit Qi.txt", "UTF-8");
		PrintWriter npAzam = new PrintWriter ("C:\\New folder\\Net Profit Aazam.txt", "UTF-8");
		
		npideal.println(npIdeal);
		nprelin.println(npRelinq);
		npAzam.println(npAazam);
		npqi.println(npQi);
		npideal.close();
		nprelin.close();
		npAzam.close();
		npqi.close();**/
        }
	   // System.out.println(  Arrays.toString(resUsed));
	    
	    //calculating net profit 
	    double sumIncomeIdeal = 0;
	    double sumIncomeUserRelinq = 0;
	    double sumIncomeAazam = 0;
	    double sumIncomeQi = 0;
	    double sumIncomeSj = 0;
	    double sumIncomeOp = 0;
	    
	    for (double i : incomeIdeal)
	    	sumIncomeIdeal += i;
	    for (double i : incomeUserRelinq )
	    	sumIncomeUserRelinq  += i;
	    for (double i : incomeAazam)
	    	sumIncomeAazam += i;
	    for (double i : incomeQi)
	    	sumIncomeQi += i;
	    for (double i : incomeSj)
	    	sumIncomeSj += i;
	    for (double i : incomeOp)
	    	sumIncomeOp += i;
	    
	    //System.out.println(sumIncome);
	    
	    double sumElecExpenseIdeal = 0;
	    double sumElecExpenseUserRelinq = 0;
	    double sumElecExpenseAazam = 0;
	    double sumElecExpenseQi = 0;
	    double sumElecExpenseSj = 0;
	    double sumElecExpenseOp = 0;
	    
	    
	    for (double i :elecExpenseIdeal)
	    	sumElecExpenseIdeal += i;
	    for (double i :elecExpenseUserRelinq)
	    	sumElecExpenseUserRelinq += i;
	    for (double i :elecExpenseAazam)
	    	sumElecExpenseAazam += i;
	    for (double i :elecExpenseQi)
	    	sumElecExpenseQi += i;
	    for (double i :elecExpenseSj)
	    	sumElecExpenseSj += i;
	    for (double i :elecExpenseOp)
	    	sumElecExpenseOp += i;
	    
	    
	    
	    //System.out.println(sumElecExpense);
	   double sumRelinqLossIdeal = 0;
	   double sumRelinqLossUserRelinq = 0;
	   double sumRelinqLossAazam = 0;
	   double sumRelinqLossQi = 0;
	   double sumRelinqLossSj = 0;
	   double sumRelinqLossOp = 0;
	   
	   
	    for (double i : relinqLossIdeal)
	    	sumRelinqLossIdeal += i;
	    for (double i : relinqLossUserRelinq)
	    	sumRelinqLossUserRelinq += i;
	    for (double i : relinqLossAazam)
	    	sumRelinqLossAazam += i;
	    for (double i : relinqLossQi)
	    	sumRelinqLossQi += i;
	    for (double i : relinqLossSj)
	    	sumRelinqLossSj += i;
	    for (double i : relinqLossOp)
	    	sumRelinqLossOp += i;
	    
	    
	   // System.out.println(sumRelinqLoss );
	    double NetProfitIdeal = (double) (sumIncomeIdeal-(sumRelinqLossIdeal+sumElecExpenseIdeal));
	    double NetProfitUserRelinq = (double) (sumIncomeUserRelinq-(sumRelinqLossUserRelinq+sumElecExpenseUserRelinq));
	    double NetProfitAazam = (double) (sumIncomeAazam-(sumRelinqLossAazam+sumElecExpenseAazam));
	    double NetProfitQi = (double) (sumIncomeQi-(sumRelinqLossQi+sumElecExpenseQi));
	    double NetProfitSj = (double) (sumIncomeSj-(sumRelinqLossSj+sumElecExpenseSj));
	    double NetProfitOp = (double) (sumIncomeOp-(sumRelinqLossOp+sumElecExpenseOp));
	    
	    
	    
	    DecimalFormat df = new DecimalFormat("#.##");      
	    NetProfitIdeal = Double.valueOf(df.format(NetProfitIdeal));
	    NetProfitUserRelinq = Double.valueOf(df.format(NetProfitUserRelinq));
	    NetProfitAazam = Double.valueOf(df.format(NetProfitAazam));
	    NetProfitQi = Double.valueOf(df.format(NetProfitQi));
	    NetProfitSj = Double.valueOf(df.format(NetProfitSj));
	    NetProfitOp = Double.valueOf(df.format(NetProfitOp));
	    
	    
	     sumElecExpenseIdeal = Double.valueOf(df.format(sumElecExpenseIdeal));
	     sumElecExpenseUserRelinq = Double.valueOf(df.format(sumElecExpenseUserRelinq));
	     sumElecExpenseAazam = Double.valueOf(df.format(sumElecExpenseAazam));
	     sumElecExpenseQi = Double.valueOf(df.format(sumElecExpenseQi));
	     sumElecExpenseSj = Double.valueOf(df.format(sumElecExpenseSj));
	     sumElecExpenseOp = Double.valueOf(df.format(sumElecExpenseOp));
	     
	     
	     
	     sumIncomeIdeal = Double.valueOf(df.format(sumIncomeIdeal));
	     sumIncomeUserRelinq = Double.valueOf(df.format(sumIncomeUserRelinq));
	     sumIncomeAazam = Double.valueOf(df.format(sumIncomeAazam));
	     sumIncomeQi = Double.valueOf(df.format(sumIncomeQi));
	     sumIncomeSj = Double.valueOf(df.format(sumIncomeSj));
	     sumIncomeOp = Double.valueOf(df.format(sumIncomeOp));
	     
	     
	     
	     
	       
	     sumRelinqLossIdeal = Double.valueOf(df.format(sumRelinqLossIdeal));
	     sumRelinqLossUserRelinq = Double.valueOf(df.format(sumRelinqLossUserRelinq));
	     sumRelinqLossAazam = Double.valueOf(df.format(sumRelinqLossAazam));
	     sumRelinqLossQi = Double.valueOf(df.format(sumRelinqLossQi));
	     sumRelinqLossSj = Double.valueOf(df.format(sumRelinqLossSj));
	     sumRelinqLossOp = Double.valueOf(df.format(sumRelinqLossOp));
	     
	    

	     System.out.println("For reliq "+totalDuration +" hours  total income is $" +sumIncomeUserRelinq + 
			  		" ,total electricity expenses $" + sumElecExpenseUserRelinq + " and relinqishment loss is $" + 
			  		sumRelinqLossUserRelinq + ", net profit is $" + NetProfitUserRelinq );
	     
	     System.out.println("For qi "+totalDuration +" hours  total income is $" +sumIncomeQi + 
	  		" ,total electricity expenses $" + sumElecExpenseQi + " and relinqishment loss is $" + 
	  		sumRelinqLossQi + ", net profit is $" + NetProfitQi );
	  
      System.out.println("For aazam "+totalDuration +" hours  total income is $" +sumIncomeAazam + 
		  		" ,total electricity expenses $" + sumElecExpenseAazam + " and relinqishment loss is $" + 
		  		sumRelinqLossAazam + ", net profit is $" + NetProfitAazam );
	 
	  
	  
	  System.out.println("For ideal "+totalDuration +" hours  total income is $" +sumIncomeIdeal + 
		  		" ,total electricity expenses $" + sumElecExpenseIdeal + " and relinqishment loss is $" + 
		  		sumRelinqLossIdeal + ", net profit is $" + NetProfitIdeal );
	  
	  System.out.println("For Sj "+totalDuration +" hours  total income is $" +sumIncomeSj + 
		 		" ,total electricity expenses $" + sumElecExpenseSj + " and relinqishment loss is $" + 
		  		sumRelinqLossSj + ", net profit is $" + NetProfitSj );
	  
	  System.out.println("For Op "+totalDuration +" hours  total income is $" +sumIncomeOp + 
		  		" ,total electricity expenses $" + sumElecExpenseOp + " and relinqishment loss is $" + 
		  		sumRelinqLossOp + ", net profit is $" + NetProfitOp );
	  
	  
	  System.out.println(maxTime + "max");
	    
	  System.out.println("last entered at "+timestamp[ timestamp.length-1] ); 
	  
	//  System.out.println("relin " + actual);
		//   System.out.println("pred " + pred);
		//  System.out.println("predQi " + predQi);
		  
	//	  System.out.println("allocRel" + acpuRelinq);
		  
	//	  System.out.println("allocOP" + finalcpu);
	//	  System.out.println("allocQi" + acpu);
		  
		//		   int ac=0, ar = 0, ac1=0;
			//	long ast = 0;
				//for (Integer record : acpu) {
					//ac += record;
					//System.out.println(record + " Qi Alloc");
			//	}
		
				
				//double sd = broker.calculatevariance(payment);
				//System.out.println("the variance of payment is " + sd);
		//		double summm = 0;
			//	double count = 0;
				//for(int ii = 0;ii < 100;ii++){
					//if(utireal.containsKey(ii)){
						//summm+=utireal.get(ii);
						//count++;
					//}
				//}
			//	System.out.println("THe average I want is "+ summm/count);
				//double paymentAll = 0.0;
				//double scoreAll = 0.0;
				//for (Double record : reimburse) {
					//scoreAll += record;
				//}
				//for (Double aaaa : payment) {
				//	paymentAll += aaaa;
				//}
				
				//double average = scoreAll/reimburse.size();
				//double payave = paymentAll/payment.size();
				
				
		//	System.out.println("start " + start);
			//	System.out.println("req " + reqRes);
				//System.out.println("dura " + durat);
				
				//System.out.println("preda "+preda);
				//System.out.println(payave);
				//System.out.println(String.format("%.2f", payave-average));
				CloudSim.stopSimulation();

				//Final step: Print results when simulation is over
				//List<Cloudlet> newList = broker.getCloudletReceivedList();
				printCloudletList(cloudletList);
				
				Log.printLine("CloudSimExample1 finished!");
				
	  }
			
			
			
		/**
		 * Read the file and input the parameters into the program
		 *
		 * @param the filepath of the parameter(the text file)
		 *
		 * @return the array of each kind of parameter
		 */
		public static final int[] readFile(String filePath) {
	        File f = new File(filePath);
	        if(!f.exists()) {
	            System.err.println("File " + f.getName() + " not found");
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
	        int[] b = new int[aa];
	        for(int i=0;i<a.length;i++){
	        		b[i] = Integer.parseInt(a[i]);
	        }
	        return b;
	    }
		
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
		
		
		public static final double CalUti(int tres, int timestamp, ArrayList<Integer> res, HashMap<Integer, Double> uti, int[] expiretime, TreeMap<Integer, Double> utireal) {
		
			if(timestamp == 0){
				return 0;
			}
			else{
			//	System.out.println(timestamp + "timestamp caluti");
				double utinow= uti.get(timestamp);
				//double utinow=45.5;
				/**int lastentry = uti.size()-1;
			//	System.out.println(lastentry + "last entry");
				double utinow = uti.get(lastentry);// getting the uti till the previous user
				int i = 0;
				while(expiretime[i]!=0){
					System.out.println("timest "+timestamp);
					System.out.println("EXPT "+expiretime[i]);
					if(expiretime[i] <= timestamp && expiretime[i] != -1){
						System.out.println("expt of user " + i + " is  "+expiretime[i]);
						System.out.println("HELLOs "+utinow);
						System.out.println("res at " + i +res.get(i));
						double  n=0 , s=0, u=0; int v=0,t = 0;
						t=(res.get(i));
						v=tres;
						n=(double)t/v;
						s=(double)(n*100);
						System.out.println("s " + s);
						u=(double)(utinow-s);
						System.out.println("u " + u);
						utinow = u;
						utireal.put(expiretime[i], utinow);
						expiretime[i] = -1;
						
					}
					else {
					}
					i++;
				System.out.println("HELLO "+utinow);
				System.out.println("HELLO "+utireal);
				}*/
				return utinow;
				
			}
				
			}
	
		
		//}
			
			
	  
		
		public static final void CalUtinew(double[] overalluti,int z,int tres,int timestamp,int expiretime, ArrayList<Integer> res, HashMap<Integer, Double> uti, double utinow, TreeMap<Integer, Double> utireal) {
			//remember the lastentry of res and expiretime are equal, but different to the lastentry of uti since it is now updated yet.
			
			
			
			//System.out.println(uti );
		//	System.out.println(timestamp + "timestamp calutinew");
			// System.out.println(expiretime + "expiretime calutinew");
			for(int i= timestamp; i<expiretime;i++ ){
			//	System.out.println(res.get(z)+" res.get(z)");
				double j=res.get(z),k=tres;
				double l=(j/k);
			//	System.out.println(l + "l " + j +" m calutinew");
				overalluti[i]+=(double)(l*100);
			//	System.out.println("overalluti[i] is "+overalluti[i]);
				uti.put(i,overalluti[i]);
				
				
			}
			
		//	System.out.println(uti);
			
			
			
		/**	if(timestamp == 0){
				uti.put(0, (double) res.get(0)/tres*100);
				utireal.put(0, (double) res.get(0)/tres*100);
				System.out.println("Utili "+uti);
				System.out.println("timestamp "+utireal);
			} 
			else{
				int lastentry = res.size()-1;
				System.out.println(lastentry + "le");
				int resnow = res.get(lastentry);
				System.out.println("resnow "+resnow + "tres " + tres );
				//double utinow = uti.get(lastentry -1);
				System.out.println(utinow+ " from method");
				double t=0;
				t=(double)resnow/tres;
				utinow = (double) (utinow + t*100);
			//	System.out.println(t*100+ " from method res/tres*100");
				System.out.println(utinow+ " from method2");
				
				uti.put(lastentry, utinow);+duration[i]
				utireal.put(timestamp, utinow);
				System.out.println("Utili "+uti );
				System.out.println("timestamp "+utireal+ resnow);
			}*/
			
		}
		
		public static final int ChkAvailableRes(int tres,int timestamp,int[]av){
			int avail=0;
			if(timestamp == 0){
				return tres;
			}
			else{
				
					 avail = av[timestamp];
					return avail;
					
			}
			//System.out.println();
		}
		
		
		public static final int ChkAvailableResBatch(int tres,int timestamp,int[]av){
			int avail=0;
			if(timestamp == 0 && timestamp == 4){
				return tres;
			}
			else{
				
					 avail = av[timestamp];
					return avail;
					
			}
			//System.out.println();
		}
		
		
		
		public static final void UpAvailableRes(int i, ArrayList<Integer> res,int timestamp,int expiretime,int[]av){
			for (int z=timestamp; z<expiretime; z++){
				av[z]-= (int)res.get(i) ;
				if (av[z]<0)
					av[z]=0;
				//available.put(i,av[z]);
				//System.out.println(" array available update at " +z + av[z]  );
				//System.out.println("available update"+ available  );
			}	
		}

		public static final int DecideModel(double uti, double aveRP, int userid, int res,int z){
			
			if(uti<=25){
				return 2;
			}
			else if(uti>=75 && uti<100){
				return 1;
			}
			else {
				if(uti>25 && uti<75){
					if(aveRP==0){
				}
					return 3;
				}
				else if(uti>25 && uti<75){
					if(0 > aveRP&&aveRP <= 0.5){
				}
					return 4;
				}
				else if(uti>25 && uti<75){
					if(aveRP > 0.5&& aveRP <=1){
				    }
					if(res>22){
						return 3;
					}
					else{
						return 4;
					}
					
					}
				else if(uti>=100){
					return 5;
				}
				
				
				return 0;
			}
		}
		
		
		
		 private final double intercept, slope;
		    private final double r2;
		    private final double svar0, svar1;

		   /**
		     * Performs a linear regression on the data points {@code (y[i], x[i])}.
		     *
		     * @param  x the values of the predictor variable
		     * @param  y the corresponding values of the response variable
		     * @throws IllegalArgumentException if the lengths of the two arrays are not equal
		     */
		    public Write(int[] x, ArrayList<Double> y) {
		     //  if (x.length != y.size()) {
		       //     throw new IllegalArgumentException("array lengths are not equal");
		        //}
		        int n = y.size();

		        // first pass
		        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
		        for (int i = 0; i < n; i++) {
		            sumx  += x[i];
		            sumx2 += x[i]*x[i];
		            sumy  += y.get(i);
		        }
		        double xbar = sumx / n;
		        double ybar = sumy / n;

		        // second pass: compute summary statistics
		        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
		        for (int i = 0; i < n; i++) {
		            xxbar += (x[i] - xbar) * (x[i] - xbar);
		            yybar += (y.get(i) - ybar) * (y.get(i) - ybar);
		            xybar += (x[i] - xbar) * (y.get(i) - ybar);
		        }
		        slope  = xybar / xxbar;
		        intercept = ybar - slope * xbar;

		        // more statistical analysis
		        double rss = 0.0;      // residual sum of squares
		        double ssr = 0.0;      // regression sum of squares
		        for (int i = 0; i < n; i++) {
		            double fit = slope*x[i] + intercept;
		            rss += (fit - y.get(i)) * (fit - y.get(i));
		            ssr += (fit - ybar) * (fit - ybar);
		        }

		        int degreesOfFreedom = n-2;
		        r2    = ssr / yybar;
		        double svar  = rss / degreesOfFreedom;
		        svar1 = svar / xxbar;
		        svar0 = svar/n + xbar*xbar*svar1;
		    }

		   /**
		     * Returns the <em>y</em>-intercept &alpha; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
		     *
		     * @return the <em>y</em>-intercept &alpha; of the best-fit line <em>y = &alpha; + &beta; x</em>
		     */
		    public double intercept() {
		        return intercept;
		    }

		   /**
		     * Returns the slope &beta; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
		     *
		     * @return the slope &beta; of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>
		     */
		    public double slope() {
		        return slope;
		    }

		   /**
		     * Returns the coefficient of determination <em>R</em><sup>2</sup>.
		     *
		     * @return the coefficient of determination <em>R</em><sup>2</sup>,
		     *         which is a real number between 0 and 1
		     */
		    public double R2() {
		        return r2;
		    }

		   /**
		     * Returns the standard error of the estimate for the intercept.
		     *
		     * @return the standard error of the estimate for the intercept
		     */
		    public double interceptStdErr() {
		        return Math.sqrt(svar0);
		    }

		   /**
		     * Returns the standard error of the estimate for the slope.
		     *
		     * @return the standard error of the estimate for the slope
		     */
		    public double slopeStdErr() {
		        return Math.sqrt(svar1);
		    }

		   /**
		     * Returns the expected response {@code y} given the value of the predictor
		     * variable {@code x}.
		     *
		     * @param  x the value of the predictor variable
		     * @return the expected response {@code y} given the value of the predictor
		     *         variable {@code x}
		     */
		    public double predict(double x) {
		        return slope*x + intercept;
		    }

		   /**
		     * Returns a string representation of the simple linear regression model.
		     *
		     * @return a string representation of the simple linear regression model,
		     *         including the best-fit line and the coefficient of determination
		     *         <em>R</em><sup>2</sup>
		     */
		    public String toString() {
		        StringBuilder s = new StringBuilder();
		        s.append(String.format("%.2f n + %.2f", slope(), intercept()));
		        s.append("  (R^2 = " + String.format("%.3f", R2()) + ")");
		        return s.toString();
		    }
	
	     

		/**
		 * Creates the datacenter.
		 *
		 * @param name the name
		 *
		 * @return the datacenter
		 */
		private static Datacenter createDatacenter(String name) {

			// Here are the steps needed to create a PowerDatacenter:
			// 1. We need to create a list to store
			// our machine
			List<Host> hostList = new ArrayList<Host>();

			// 2. A Machine contains one or more PEs or CPUs/Cores.
			// In this example, it will have only one core.
			List<Pe> peList = new ArrayList<Pe>();

			int mips = 1000;

			// 3. Create PEs and add these into a list.
			for (int i = 0; i < 1000; i++){
				//create more pes so that the vm can have the value more than 1 of the pesNumber;
				peList.add(new Pe(i, new PeProvisionerSimple(mips)));
			}
			 // need to store Pe id and MIPS Rating
			
			// 4. Create Host with its id and list of PEs and add them to the list
			// of machines
			int hostId = 0;
			int ram = 2048; // host memory (MB)
			long storage = 200000; // host storage
			int bw = 10000;

			
			Host host = new Host(hostId,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList,
					new VmSchedulerTimeShared(peList));
			host.setTotalProfit(0);
			hostList.add(host); // This is our machine

			// 5. Create a DatacenterCharacteristics object that stores the
			// properties of a data center: architecture, OS, list of
			// Machines, allocation policy: time- or space-shared, time zone
			// and its price (G$/Pe time unit).
			String arch = "x86"; // system architecture
			String os = "Linux"; // operating system
			String vmm = "Xen";
			double time_zone = 10.0; // time zone this resource located
			double cost = 3.0; // the cost of using processing in this resource
			double costPerMem = 0.05; // the cost of using memory in this resource
			double costPerStorage = 0.001; // the cost of using storage in this
											// resource
			double costPerBw = 0.0; // the cost of using bw in this resource
			LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
														// devices by now

			DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
					arch, os, vmm, hostList, time_zone, cost, costPerMem,
					costPerStorage, costPerBw);

			// 6. Finally, we need to create a PowerDatacenter object.
			Datacenter datacenter = null;
			try {
				datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return datacenter;
		}

		// We strongly encourage users to develop their own broker policies, to
		// submit vms and cloudlets according
		// to the specific rules of the simulated scenario
		/**
		 * Creates the broker.
		 *
		 * @return the datacenter broker
		 */
		public static DatacenterBroker createBroker() {
			DatacenterBroker broker = null;
			try {
				broker = new DatacenterBroker("Broker");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return broker;
		}

		/**
		 * Prints the Cloudlet objects.
		 *
		 * @param list list of Cloudlets
		 */
		private static void printCloudletList(List<Cloudlet> list) {
			int size = list.size();
			Cloudlet cloudlet;

			String indent = "    ";
			Log.printLine();
			Log.printLine("========== OUTPUT ==========");
			Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
					+ "Data center ID" + indent + "VM ID" + indent + "Time" + indent
					+ "Start Time" + indent + "Finish Time");

			DecimalFormat dft = new DecimalFormat("###.##");
			for (int i = 0; i < size; i++) {
				cloudlet = list.get(i);
				Log.print(indent + cloudlet.getCloudletId() + indent + indent);

				if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				//	Log.print("SUCCESS");

					
					Log.printLine(indent + indent + cloudlet.getResourceId()
							+ indent + indent + indent + cloudlet.getVmId()
							+ indent + indent
							+ dft.format(cloudlet.getActualCPUTime()) + indent
							+ indent + dft.format(cloudlet.getExecStartTime())
							+ indent + indent
							+ dft.format(cloudlet.getFinishTime()));
				}
			}
			
			
			
		}

	

	}
