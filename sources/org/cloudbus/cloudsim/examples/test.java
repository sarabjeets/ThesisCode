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
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * A simple example showing how to create a datacenter with one host and run one
 * cloudlet on it.
 */
public class test {
	
	
	
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;
	private static List<Cloudlet> cloudletList1;

	/** The vmlist. */
	private static List<Vm> vmlist;
	private static List<Vm> vmlist1;
	private static List<User> userlist;
	private static List<User> userlist1;
	private static List<Host> hostlist1;
	//private static List<Host> hostlist;//for aazam
	/**
	 * Creates main() to run this example.
	 *
	 * @param args the args
	 */
	
	public static void main(String[] args)throws IOException {
		int[] resask = new int[2];
		int[] resAlloc = new int [resask.length];
		int[] timestamp = new int[resask.length];
		int[] duration = new int[4];
		//int[] resask = new int[resask.length];
		int[] expiretime1 = new int[timestamp.length];
//		int[] timestamp = {0, 2, 6, 9, 12, 25, 26, 27, 29, 41, 43, 46, 55, 57, 72, 74, 90, 96, 101, 105};//new int[20];
//		int[] duration = {26, 14, 22, 30, 32, 39, 8, 9, 9, 18, 26, 6, 6, 18, 16, 38, 48, 12, 27, 13};//new int[20];
//		int[] resask = {74, 50, 53, 112, 45, 100, 34, 104, 135, 143, 111, 118, 133, 141, 144, 54, 88, 95, 36, 69};///new int[20];
		//int[] expiretime = new int[20];
		int[] relinqtime = new int[resask.length];
		timestamp[0] = 0;
		ExponentialDistribution exp = new ExponentialDistribution(4.0);
		for(int i = 1; i <resask.length; i++){
			timestamp[i] = (int)exp.sample() + 1+timestamp[i-1];
		//	System.out.println("timestamp" + timestamp[i]+ " hhh" + 1+timestamp[i-1] + "ran"+(int)exp.sample());
		}
		for(int i = 0; i < resask.length; i ++){
			duration[i] = 20 + (int)(Math.random() * ((120 - 10) + 1));//time user asks for
			resask[i] = 20 + (int)(Math.random() * ((250 - 20) + 1));// resources userr asks for
			//System.out.println("duration" +duration[i]);
		}
		int[] dura = new int[resask.length];
		for (int i = 0; i < resask.length; i++){
			dura[i] = duration[i];
			//System.out.println("duration of user " + i + " is " +dura[i]);
					
		}
		
		
		
		double[] aveRP = DreadFile("C:\\Program Files\\cloudsim\\cloudsim-3.0.3\\DreadFile.txt");
		int resources = 1000;
		//[0, 4, 6, 13, 15, 24, 25, 30, 32, 35, 36, 38, 39, 41, 51, 52, 53, 54, 58, 60]
		
		
		Random r = new Random();
		ArrayList<Integer> reqRes = new ArrayList<Integer> ();
		ArrayList<Integer> acpu = new ArrayList<Integer> ();
		ArrayList<Integer> acpu1 = new ArrayList<Integer> ();
		ArrayList<Integer> aram = new ArrayList<Integer> ();
		ArrayList<Integer> aram1 = new ArrayList<Integer> ();
		ArrayList<Long> asto = new ArrayList<Long> ();
		ArrayList<Long> asto1 = new ArrayList<Long> ();
		ArrayList<Double> reimburse = new ArrayList<Double> ();
		ArrayList<Double> payment = new ArrayList<Double> ();
		ArrayList<Double> relinp = new ArrayList<Double> ();
		
		HashMap<Integer, Double> uti = new HashMap<Integer,Double>();
		TreeMap<Integer, Double> utireal = new TreeMap<Integer,Double>();
		double utiliz=0;
		double totalUtilize=0;
		
		
	    for (int z=0; z<resask.length;z++){
	//	Log.printLine("Starting CloudSimExample1...");
		///I removed "try"
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
			vmlist1 = new ArrayList<Vm>();
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
			
			user.setCloudletId(cloudlet.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet);

			// submit cloudlet list to the broker
			broker.submitCloudletList(cloudletList);
			
			Cloudlet cloudlet1 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet1.setbrokerId(brokerId1);
			cloudlet1.setuser(user1);
			//cloudlet.setExecStartTime(0.1);
			//cloudlet.setprice(price);
			
			user1.setCloudletId(cloudlet1.getCloudletId());
			
			// add the cloudlet to the list
			
		//	cloudletList1.add(cloudlet1);

			// submit cloudlet list to the broker
		//	broker1.submitCloudletList(cloudletList1);
			//adrian 
			double numtime = duration[z];
			double starttime = timestamp[z];// sjs - gives start time
			int res = resask[z];//requested resources
			
			user.setnumtime(numtime);
			user.submitCloudlet(cloudlet);
			user.submitRequest(user.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
		//	user.submitRequest1(user.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			user.setnumtime(numtime);
			user.submitCloudlet(cloudlet);
		//	user1.submitRequest1(user1.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			//set user's aop as not null
			host.getmtimes().put(userid, 0);//puts userid as key and 0 as no. of times user returns
			/*if(aveRP[z] == 0){}
			if(aveRP[z] > 0){
			    for(int i = 0 ; i < 100; i++){
			        double a = r.nextGaussian() * 0.3 + aveRP[z];
			        if( !(a < 0 || a > 1) ){ relinp.add(a);}// add a to relinp list
			    }
			}*/
			relinp.clear();
			if(aveRP[z] == 0){} //checks whether first element of aveRP is zero, if zero, does not execute the code
			else if(aveRP[z] > 0) //nested loop
			{            
				for(int i = 0 ; i < 1000; i++){
					double a = r.nextGaussian() * 0.3 + aveRP[z];
					if (a < 0 || a > 1){}
					else{
						relinp.add(a);
					//	System.out.println(relinp.add(a)+ "relinpadd");
					}
				}
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
				for(int i = 1; i < r.nextInt(50) + 2; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmaop1(userid, relinp.get(i));
					host.addmsop1(userid, relinp.get(i));
					host.addmtimes(userid);
				//	host1.addmtimes1(userid);
				}
			}
		//	double relinp1 = 1
			relinp.set(   0,Math.random() );
			
			//here I set the basic price of the task; 0.01 dollar per hour per CPU, 0.01 dollar per hour per GB of RAM, 1*0.001 dollar per hour per GB of Storage;
			//maybe I need to write a function to calculate the price of the task;
	//		double basicprice = user.getnumCPU() * 0.01 + user.getnumRAM() * 0.01 + (double)(user.getnumStorage()*0.001);
		//	double pledge = basicprice * 1.05;
			
			
			System.out.println("user" + user.getuserId() + " asks for " + user.getnumCPU1() + " CPUs and " + user.getnumRAM1() + " GB of RAM and " + user.getnumStorage1() + " GB of storage to host" + host.getId() + "at time" + starttime);
			reqRes.add(resask[z]);
			//System.out.println("user" + user.getuserId() + " asks for " + user1.getnumCPU1() + " CPUs and " + user1.getnumRAM1() + " GB of RAM and " + user1.getnumStorage1() + " GB of storage to host" + host.getId() + "at time" + starttime);
			//int num = 0;
			
		

			 broker.CalNumOfResAzam(user.getuserId(), host.getId());
				host.addmtimes(userid);
			//	host.addmsop1(userid, relinp.get(0));
			///	host.addmaop1(userid, relinp.get(0));
				acpu1.add(user.getnumCPU1());
				aram1.add(user.getnumRAM1());
				asto1.add(user.getnumStorage1());
				System.out.println("For AAzam "+"host" + host.getId() + " will give " + user.getnumCPU1() + " CPUs and " +user.getnumRAM1()+ " GB of RAM and " + user.getnumStorage1() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());
		utiliz = CalUti(timestamp[z], acpu, uti, expiretime1, utireal);
			//double payprice = 0;
				System.out.println("user" + user.getuserId() + " asks for " + user.getnumCPU() + " CPUs and " + user.getnumRAM() + " GB of RAM and " + user.getnumStorage() + " GB of storage to host" + host.getId() + "at time" + starttime);
			//System.out.println("user" + user.getuserId() + " asks for " + user.getnumCPU() + " CPUs and " + user.getnumRAM() + " GB of RAM and " + user.getnumStorage() + " GB of storage to host" + host.getId() + "at time" + starttime);
		//	System.out.println("user" + user1.getuserId() + " asks for " + user.getnumCPU1() + " CPUs and " + user.getnumRAM1() + " GB of RAM and " + user.getnumStorage1() + " GB of storage to host" + host.getId() + "at time" + starttime);
			if(cloudlet.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
			//	payprice = pledge;
			}
			else{
				 int id = 0;
				  
				   id = DecideModel(utiliz, aveRP[z], userid, res,z); 
				  // id=4;
				   System.out.println("model is" + id);
				//because we already know that it is the first time of the whole process, so we don't need to decide if the uti has nothing;
				   if (host.getmtimes().get(userid) == 0){
						host.addmtimes(userid);
						ArrayList<Double> aaa = new ArrayList<Double> ();
						ArrayList<Double> bbb = new ArrayList<Double> ();
						//here I will set some default values for aop and sop and ...;
						if(host.getmaop().get(userid) == null){
					   broker.CalNumOfRes(user.getuserId(), host.getId(), 0.3, 0.3, id);
						//right now we don't consider the pricing model;
						//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
						host.getmaop().put(userid, bbb);
						}
						else {
							System.out.println("calling this function");
							broker.CalNumOfRes(user.getuserId(), host.getId(), 0.3, id);
							//right now we don't consider the pricing model;
							//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId());
						}
						host.getmsop().put(userid, aaa);
						host.addmsop(userid, relinp.get(0));
						host.addmaop(userid, relinp.get(0));
					}
					else if(host.getmtimes().get(userid) == 1){
						host.addmtimes(userid);
						// will add codes for sop and aop and ...;
						broker.CalNumOfRes1(user.getuserId(), host.getId(), id);
						host.addmsop(userid, relinp.get(0));
						host.addmaop(userid, relinp.get(0));
						//payprice = broker.CalPrice(cloudlet3.getCloudletId(), host.getId());
					}
					else if(host.getmtimes().get(userid) == 2){
						host.addmtimes(userid);
						// will add codes for sop and aop and ...;
						broker.CalNumOfRes2(user.getuserId(), host.getId(), id);
						host.addmsop(userid, relinp.get(0));
						host.addmaop(userid, relinp.get(0));
						//payprice = broker.CalPrice(cloudlet3.getCloudletId(), host.getId());
					}
					else if(host.getmtimes().get(userid) > 2){
					broker.CalNumOfRes(user.getuserId(), host.getId(), id);
						int a = host.getmtimes().get(userid);
						host.addmtimes(userid);
						host.addmsop(userid, relinp.get(0));
						host.addmaop(userid, relinp.get(0));
						
					}
				}

			 
			
			int utimeh = host.getmtimes().get(userid) ;
			
			System.out.println("this is the " + utimeh + "th time for user" + user.getuserId() + " to use services of host" + host.getId());
		//	payprice = user.getnumCPU() * 0.01 + user.getnumRAM() * 0.01 + (double)(user.getnumStorage()*0.001);
			
			acpu.add(user.getnumCPU());
			aram.add(user.getnumRAM());
			asto.add(user.getnumStorage());
			
			
			PrintWriter writerRes = new PrintWriter ("C:\\New folder\\Required Res .txt", "UTF-8");
			PrintWriter writerQi = new PrintWriter ("C:\\New folder\\Allocated Res Qi.txt", "UTF-8");
			PrintWriter writerAazam = new PrintWriter ("C:\\New folder\\Allocated Res Aazam.txt", "UTF-8");
			writerRes.println(reqRes);
			writerQi.println(acpu);
			writerAazam.println(acpu1);
			writerAazam.close();
			writerQi.close();
			writerRes.close();
			
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
						int endTime = timestamp[vmid]+ duration[vmid];
						System.out.println("req duration is "+duration[vmid]);
						System.out.println("req till" + endTime);
						System.out.println("req duration is "+duration[vmid]);
						duration[z] = (int)(duration[z]*(1-relinp.get(0))); ///duration is set according to the relinp.add(a); everytime list is cleared and a new value is added to first element of list and everytime 1-relinp.get(0) is used
						System.out.println("given duration is "+duration[vmid]);
						//	System.out.println("balle " + relinp.get(0));
						expiretime1[vmid]= timestamp[vmid] + duration[vmid]; 
					//	relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid])+ timestamp[vmid] ) + timestamp[vmid];
						//relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) );
						System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
						//vmlist.add(vm2);
						expiretime1[z] = timestamp[z] + duration[z];
						CalUtinew(timestamp[z], acpu, uti, utiliz, utireal);
						
						 
						
						// submit vm list to the broker
						broker.submitVmList(vmlist);
						
						broker.bindCloudletToVm(cloudletid, vmid);
						//payprice = payprice * user.gettime();
						double bb = relinp.get(0);
						//System.out.println("balle " + relinp.get(0));
						System.out.println("the relinquish probability of the user for this time is " + bb);
						
						
					//although the unit of RAM and Storage is MB is the default configuration, but here I still use GB because of Amazon EC2's pricing model;
						
					System.out.println("host" + host.getId() + " will give " + user.getnumCPU() + " CPUs and " +user.getnumRAM()+ " GB of RAM and " + user.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());
						//System.out.println("host" + host.getId() + " will give " + user.getnumCPU1() + " CPUs and " +user.getnumRAM1()+ " GB of RAM and " + user.getnumStorage1() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());
						//resAlloc[z]= user.getnumCPU();
					//	System.out.println("the user will pay " + payprice + " dollars to the broker.");
						
					//	payment.add(payprice);
						System.out.println(host.getmtimes().get(userid));
			// Sixth step: Starts the simulation
		//	CloudSim.startSimulation();
	}
	   // CloudSim.startSimulation();
//			/////////////////////////////
			///////////////////////
			////////////////////////
	 
				   int ac=0, ar = 0, ac1=0;
				long ast = 0;
				for (Integer record : acpu) {
					ac += record;
					System.out.println(record + " Qi Alloc");
				}
				for (Integer record1 : acpu1) {
				ac1 += record1;
				System.out.println(record1 + " Aazam Alloc");
			}
				for (Integer record : aram) {
					ar += record;
				}
				for (Long record : asto) {
					ast += record;
				}
				int avecpu = ac/acpu.size();
				int averam = ar/aram.size();
				long avesto = ast/asto.size();
				System.out.println("averageCPU " + avecpu + "averageRAM " + averam + "averagesto " + avesto);
				
				//double sd = broker.calculatevariance(payment);
				//System.out.println("the variance of payment is " + sd);
				double summm = 0;
				double count = 0;
				for(int ii = 0;ii < 100;ii++){
					if(utireal.containsKey(ii)){
						summm+=utireal.get(ii);
						count++;
					}
				}
			//	System.out.println("THe average I want is "+ summm/count);
				double paymentAll = 0.0;
				double scoreAll = 0.0;
				for (Double record : reimburse) {
					scoreAll += record;
				}
				for (Double aaaa : payment) {
					paymentAll += aaaa;
				}
				
				double average = scoreAll/reimburse.size();
				double payave = paymentAll/payment.size();
				
				System.out.println("reimburse average is " + average);
				System.out.println("the provider earned " + (payave-average));
				System.out.println(avecpu);
				System.out.println(payave);
				System.out.println(String.format("%.2f", payave-average));
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
	        double[] b = new double[aa];
	        for(int i=0;i<a.length;i++){
	        		b[i] = Double.parseDouble(a[i]);
	        }
	        return b;
	    }
		
		
		public static final double CalUti(int timestamp, ArrayList<Integer> res, HashMap<Integer, Double> uti, int[] expiretime, TreeMap<Integer, Double> utireal) {
			if(timestamp == 0){
				return 0;
			}
			else{
				int lastentry = uti.size()-1;
			//	System.out.println(lastentry + "last entry");
				double utinow = uti.get(lastentry);// getting the uti till the previous user
				int i = 0;
				while(expiretime[i]!=0){
					if(expiretime[i] <= timestamp && expiretime[i] != -1){
						utinow = utinow - res.get(i)*0.0006;
						utireal.put(expiretime[i], utinow);
						expiretime[i] = -1;
						
					}
					else {
					}
					i++;
				//System.out.println("HELLO"+utinow);
				}
				return utinow;
				
			}
		}
		
		public static final void CalUtinew(int timestamp, ArrayList<Integer> res, HashMap<Integer, Double> uti, double utinow, TreeMap<Integer, Double> utireal) {
			//remember the lastentry of res and expiretime are equal, but different to the lastentry of uti since it is now updated yet.
			if(timestamp == 0){
				uti.put(0, res.get(0)*0.0006);
				utireal.put(0, res.get(0)*0.0006);
			} 
			else{
				int lastentry = res.size()-1;
				int resnow = res.get(lastentry);
				//double utinow = uti.get(lastentry -1);
				utinow = utinow + resnow*0.0006;
				uti.put(lastentry, utinow);
				utireal.put(timestamp, utinow);
				System.out.println("Utilization "+utireal);
			}
			
		}
		
	

		public static final int DecideModel(double uti, double aveRP, int userid, int res,int z){
			if (z<1){
				return 1;
			}
			if(uti<=0.25){
				return 2;
			}
			else if(uti>=0.75){
				return 1;
			}
			else{
				if(uti>.25 && uti<.75){
					if(aveRP==0){
				}
					return 3;
				}
				else if(uti>.25 && uti<.75){
					if(0 > aveRP&&aveRP <= 0.5){
				}
					return 4;
				}
				else if(uti>.25 && uti<.75){
					if(aveRP > 0.5&& aveRP <=1){
				}
					if(res>22){
						return 3;
					}
					else{
						return 4;
					}
				}
				return 0;
			}
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
			long storage = 1000000; // host storage
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
		private static DatacenterBroker createBroker() {
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

			//String indent = "    ";
			/**Log.printLine();
			Log.printLine("========== OUTPUT ==========");
			Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
					+ "Data center ID" + indent + "VM ID" + indent + "Time" + indent
					+ "Start Time" + indent + "Finish Time");*/

			DecimalFormat dft = new DecimalFormat("###.##");
			for (int i = 0; i < size; i++) {
				cloudlet = list.get(i);
			//	Log.print(indent + cloudlet.getCloudletId() + indent + indent);

				if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
				//	Log.print("SUCCESS");

					
					/**Log.printLine(indent + indent + cloudlet.getResourceId()
							+ indent + indent + indent + cloudlet.getVmId()
							+ indent + indent
							+ dft.format(cloudlet.getActualCPUTime()) + indent
							+ indent + dft.format(cloudlet.getExecStartTime())
							+ indent + indent
							+ dft.format(cloudlet.getFinishTime()));*/
				}
			}
		}

	

	}
