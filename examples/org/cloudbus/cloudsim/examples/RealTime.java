package org.cloudbus.cloudsim.examples;

/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */
import java.util.Random;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

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
public class RealTime {
	
	
	
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;
	
	private static List<User> userlist;
	
	private static List<Host> hostlist1;

	/**
	 * Creates main() to run this example.
	 *
	 * @param args the args
	 */
	
	public static void main(String[] args) {
		int[] timestamp = new int[20];
		int[] duration = new int[40];
		int[] resask = new int[20];
		int[] expiretime1 = new int[timestamp.length];
//		int[] timestamp = {0, 2, 6, 9, 12, 25, 26, 27, 29, 41, 43, 46, 55, 57, 72, 74, 90, 96, 101, 105};//new int[20];
//		int[] duration = {26, 14, 22, 30, 32, 39, 8, 9, 9, 18, 26, 6, 6, 18, 16, 38, 48, 12, 27, 13};//new int[20];
//		int[] resask = {74, 50, 53, 112, 45, 100, 34, 104, 135, 143, 111, 118, 133, 141, 144, 54, 88, 95, 36, 69};///new int[20];
		//int[] expiretime = new int[20];
		int[] relinqtime = new int[20];
		timestamp[0] = 0;
		ExponentialDistribution exp = new ExponentialDistribution(4.0);
		for(int i = 1; i < 20; i++){
			timestamp[i] = (int)exp.sample() + 1+timestamp[i-1];
		//	System.out.println("timestamp" + timestamp[i]+ " hhh" + 1+timestamp[i-1] + "ran"+(int)exp.sample());
		}
		for(int i = 0; i < 20; i ++){
			duration[i] = 20 + (int)(Math.random() * ((120 - 10) + 1));//time user asks for
			resask[i] = 20 + (int)(Math.random() * ((250 - 20) + 1));// resources userr asks for
			//System.out.println("duration" +duration[i]);
		}
		int[] dura = new int[20];
		for (int i = 0; i < 20; i++){
			dura[i] = duration[i];
			System.out.println("duration of user " + i + " is " +dura[i]);
					
		}
		
		
		
		double[] aveRP = DreadFile("C:\\Program Files\\cloudsim\\cloudsim-3.0.3\\DreadFile.txt");
		int resources = 1000;
		//[0, 4, 6, 13, 15, 24, 25, 30, 32, 35, 36, 38, 39, 41, 51, 52, 53, 54, 58, 60]
		
		
		Random r = new Random();
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
		

		
		
	
	//	Log.printLine("Starting CloudSimExample1...");
		///I removed "try"
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			// Datacenters are the resource providers in CloudSim. We need at
			// list one of them to run a CloudSim simulation
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			
			//adrian
			userlist = new ArrayList<User>();// will contain the objects of user class in 
			int userid = 0;
			User user = new User(userid);

			userlist.add(user);// add user to user list
			
			hostlist1 = new ArrayList<Host>();

			// Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();
			//here I set the initial value of total profit as 100, which will be fixed after discussion;
			double totalProfit = 100;
			Host host = datacenter0.getHostList().get(0);
			hostlist1.add(host);
			host = hostlist1.get(0);
			host.setTotalProfit(totalProfit);
			
			//bind each list to broker 
			broker.setHostList(hostlist1);
			broker.submitUserList(userlist);
			broker.BindUserToHost(user.getuserId(), host.getId());
			
			// Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			// Fifth step: Create one Cloudlet
			cloudletList = new ArrayList<Cloudlet>();
			
			int pesNumber = 1000; // number of cpus
			// Cloudlet properties
			int cloudletid = 0;
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
			
			//adrian 
			double numtime = duration[0];
			double starttime = timestamp[0];// sjs - gives start time
			int res = resask[0];//requested resources
			user.setnumtime(numtime);
			user.submitCloudlet(cloudlet);
			user.submitRequest(user.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			
			//set user's aop as not null
			host.getmtimes().put(userid, 0);//puts userid as key and 0 as no. of times user returns
			if(aveRP[0] == 0){}
			if(aveRP[0] > 0){
			    for(int i = 0 ; i < 100; i++){
			        double a = r.nextGaussian() * 0.3 + aveRP[0];
			        if( !(a < 0 || a > 1) ){ relinp.add(a);}// add a to relinp list
			    }
			}

			if(aveRP[0] == 0){} //checks whether first element of aveRP is zero, if zero, does not execute the code
			else if(aveRP[0] > 0) //nested loop
			{            
				for(int i = 0 ; i < 100; i++){
					double a = r.nextGaussian() * 0.3 + aveRP[0];
					if (a < 0 || a > 1){}
					else{
						relinp.add(a);
					//	System.out.println(relinp.add(a)+ "relinpadd");
					}
				}
				ArrayList<Double> se = new ArrayList<Double> ();
				ArrayList<Double> de = new ArrayList<Double> ();
				host.getmaop().put(userid, se);
				host.getmsop().put(userid, de);
				for(int i = 1; i < 5; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.6128554864485367);
			
			//here I set the basic price of the task; 0.01 dollar per hour per CPU, 0.01 dollar per hour per GB of RAM, 1*0.001 dollar per hour per GB of Storage;
			//maybe I need to write a function to calculate the price of the task;
			double basicprice = user.getnumCPU() * 0.01 + user.getnumRAM() * 0.01 + (double)(user.getnumStorage()*0.001);
			double pledge = basicprice * 1.05;
			
			System.out.println("user" + user.getuserId() + " asks for " + user.getnumCPU() + " CPUs and " + user.getnumRAM() + " GB of RAM and " + user.getnumStorage() + " GB of storage to host" + host.getId() + "at time" + starttime);
			
			//int num = 0;
			/**broker.CalNumOfResAzam(user.getuserId(), host.getId());
			host.addmtimes(userid);
			host.addmsop(userid, relinp.get(0));
			host.addmaop(userid, relinp.get(0));
			acpu1.add(user.getnumCPU());
			aram1.add(user.getnumRAM());
			asto1.add(user.getnumStorage());
			System.out.println("For AAzam "+"host" + host.getId() + " will give " + user.getnumCPU() + " CPUs and " +user.getnumRAM()+ " GB of RAM and " + user.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());
			*/
			double payprice = 0;
			if(cloudlet.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				int id = 1;//model 1
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
						broker.CalNumOfRes(user.getuserId(), host.getId(), 0.3, id);
						//right now we don't consider the pricing model;
						//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId());
					}
					host.getmsop().put(userid, aaa);//user id is key, aaa is id
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
				else if(host.getmtimes().get(userid) == 1){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker.CalNumOfRes1(user.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker.CalNumOfRes2(user.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					int a = host.getmtimes().get(userid);
					broker.CalNumOfRes(user.getuserId(), host.getId(), id);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//right now we don't consider the pricing model;
				}
			}
			
			
			int utimeh = host.getmtimes().get(userid) ;
			System.out.println("this is the " + utimeh + "th time for user" + user.getuserId() + " to use services of host" + host.getId());
			payprice = user.getnumCPU() * 0.01 + user.getnumRAM() * 0.01 + (double)(user.getnumStorage()*0.001);
			acpu.add(user.getnumCPU());
			aram.add(user.getnumRAM());
			asto.add(user.getnumStorage());
			
						// VM description
						//pesNumber = user.getnumCPU();
						int vmid = 0;
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
						duration[0] = (int)(duration[0]*(1-relinp.get(0))); ///duration is set according to the relinp.add(a); everytime list is cleared and a new value is added to first element of list and everytime 1-relinp.get(0) is used
						System.out.println("given duration is "+duration[vmid]);
						//	System.out.println("balle " + relinp.get(0));
						expiretime1[vmid]= timestamp[vmid] + duration[vmid]; 
						relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid])+ timestamp[vmid] ) + timestamp[vmid];
						//relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) );
						System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
						//vmlist.add(vm2);
						expiretime1[0] = timestamp[0] + duration[0];
						CalUtinew(timestamp[0], acpu, uti, 0.0, utireal);
						
						// submit vm list to the broker
						broker.submitVmList(vmlist);
						
						broker.bindCloudletToVm(cloudletid, vmid);
						payprice = payprice * user.gettime();
						double bb = relinp.get(0);
						//System.out.println("balle " + relinp.get(0));
						System.out.println("the relinquish probability of the user for this time is " + bb);
						
						//although the unit of RAM and Storage is MB is the default configuration, but here I still use GB because of Amazon EC2's pricing model;
						System.out.println("host" + host.getId() + " will give " + user.getnumCPU() + " CPUs and " +user.getnumRAM()+ " GB of RAM and " + user.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());
						
						System.out.println("the user will pay " + payprice + " dollars to the broker.");
						
						payment.add(payprice);
						System.out.println(host.getmtimes().get(userid));
			// Sixth step: Starts the simulation
			CloudSim.startSimulation();
			
			
			///////////////////////////////
			//////////////////////////////
			
			
			
			
			
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter1 = createDatacenter("Datacenter_1");

			DatacenterBroker broker1 = createBroker();
			int broker1Id = broker1.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 1;
			User user1 = new User(userid);
			userlist.add(user1);
			broker1.submitUserList(userlist);
			broker1.BindUserToHost(user1.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker1.setHostList(hostlist1);
			
			cloudletid = 1;
			Cloudlet cloudlet1 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet1.setbrokerId(broker1Id);
			//update the price of cloudlet;
			//price = 200;
			//cloudlet1.setprice(price);
			cloudlet1.setuser(user1);
			user1.setCloudletId(cloudlet1.getCloudletId());
		//System.out.println(user1.setCloudletId(cloudlet1.getCloudletId()));
			// add the cloudlet to the list
			cloudletList.add(cloudlet1);

			// submit cloudlet list to the broker
			broker1.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[1];
			starttime = timestamp[1];
			
			
			res = resask[1];
			user1.submitCloudlet(cloudlet1);
			user1.submitRequest(user1.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			
			relinp.clear();// clears all the elements 
			host.getmtimes().put(userid, 0);
			if(aveRP[1] == 0){}
			else if(aveRP[1] > 0){
				for(int i = 0 ; i < 100; i++){
					double a = r.nextGaussian() * 0.3 + aveRP[1];
					if (a < 0 || a > 1){}
					else{
						relinp.add(a);// adds a fresh element in the lists
					}
				}
				ArrayList<Double> se1 = new ArrayList<Double> ();
				ArrayList<Double> de1 = new ArrayList<Double> ();
				host.getmaop().put(userid, se1);
				host.getmsop().put(userid, de1);
				for(int i = 1; i < 20; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
					host.printmsop(userid);
				}
			}
			relinp.set(0,0.4585833354187909);
			basicprice = user1.getnumCPU() * 0.01 + user1.getnumRAM() * 0.01 + (double)(user1.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user1.getuserId() + " asks for " + user1.getnumCPU() + " CPUs and " + user1.getnumRAM() + " GB of RAM and " + user1.getnumStorage() + " GB of storage to host" + host.getId()+ "at time " 
					+starttime);
			
			double utiliz = CalUti(timestamp[1], acpu, uti, expiretime1, utireal);
		/**	broker.CalNumOfResAzam(user.getuserId(), host.getId());
			host.addmtimes(userid);
			host.addmsop(userid, relinp.get(0));
			host.addmaop(userid, relinp.get(0));
			acpu1.add(user.getnumCPU());
			aram1.add(user.getnumRAM());
			asto1.add(user.getnumStorage());
			System.out.println("For AAzam "+"host" + host.getId() + " will give " + user.getnumCPU() + " CPUs and " +user.getnumRAM()+ " GB of RAM and " + user.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet.getCloudletId());
			*/
			int id = 0;
			
			if(cloudlet1.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				
				id = DecideModel(utiliz, aveRP[1], userid, res);
				System.out.println("model is " + id);
				//System.out.println(utiliz + "UUUUUUUU");
				//id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER1!!!!!!!!! and cloudlet is cloudlet1!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker1.CalNumOfRes(user1.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker1.CalNumOfRes(user1.getuserId(), host.getId(), 0.3, id);
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
					broker1.CalNumOfRes1(user1.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker1.CalPrice(cloudlet1.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker1.CalNumOfRes2(user1.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker1.CalPrice(cloudlet1.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker1.CalNumOfRes(user1.getuserId(), host.getId(), id);
					//int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
				//System.out.println("SOP " + aaa(i) + "AOP " + bbb(i));
			}

			utimeh = host.getmtimes().get(userid) ;
			System.out.println("this is the " + utimeh + "th time for user" + user1.getuserId() + " to use services of host" + host.getId());
			payprice = user1.getnumCPU() * 0.01 + user1.getnumRAM() * 0.01 + (double)(user1.getnumStorage()*0.001);
			vmid = 1;
			acpu.add(user1.getnumCPU());
			aram.add(user1.getnumRAM());
			asto.add(user1.getnumStorage());
			Vm vm1 = new Vm(vmid, broker1.getId(), mips, user1.getnumCPU(), user1.getnumRAM(), bw, user1.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm1.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm1);
			//vmlist.add(vm2);
			
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			
			duration[1] = (int)(duration[1]*(1-relinp.get(0)));
			
			System.out.println("will use for  "+duration[vmid]);
		//	System.out.println("balle " + relinp.get(0));
		//	System.out.println("AAAA"  +  duration[1]);
			expiretime1[1] = timestamp[1] + duration[1];
			
			relinqtime[1] = r.nextInt((expiretime1[1] - timestamp[1]) ) + timestamp[1];
			System.out.println("ts"+timestamp[1]+"rp"+ relinqtime[1] +"expt"+expiretime1[1])	;		
			CalUtinew(timestamp[1], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker1.submitVmList(vmlist);
			
			broker1.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user1.gettime();

			
			bb = relinp.get(0);
			System.out.println("the relinquish probability of the user for this time is " + bb);
			//System.out.println(bb);

			System.out.println("host " + host.getId() + " will give " + user1.getnumCPU() + " CPUs and " +user1.getnumRAM()+ " GB of RAM and " + user1.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet1.getCloudletId());
			System.out.println("the user will pay " + payprice + " dollars to the broker.");
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			CloudSim.startSimulation();
			
			///////////////////////////////////
			///////////////////////////////////
			//////////////////////////////////
			//////////////////////////////////
			//the third time: 
			/////////////////////////////////////
			////////////////////////////////////
			
			
			
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter2 = createDatacenter("Datacenter_1");

			DatacenterBroker broker2 = createBroker();
			int broker2Id = broker2.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 2;
			User user2 = new User(userid);
			userlist.add(user2);
			
			broker2.submitUserList(userlist);
			broker2.BindUserToHost(user2.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker2.setHostList(hostlist1);
			
			cloudletid = 2;
			Cloudlet cloudlet2 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet2.setbrokerId(broker2Id);
			//update the price of cloudlet;
			//price = 200;
			//cloudlet2.setprice(price);
			cloudlet2.setuser(user2);
			user2.setCloudletId(cloudlet2.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet2);

			// submit cloudlet list to the broker
			broker2.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[2];
			starttime = timestamp[2];
			res = resask[2];
			user2.submitCloudlet(cloudlet2);
			user2.submitRequest(user2.getCloudlet(), false, res, res, (long)(res), numtime, starttime );
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[2];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[2] == 0){
				
			}
			else if(aveRP[2] > 0){
				
				ArrayList<Double> se2 = new ArrayList<Double> ();
				ArrayList<Double> de2 = new ArrayList<Double> ();
				host.getmaop().put(userid, se2);
				host.getmsop().put(userid, de2);
				for(int i = 1; i < 22; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.2887622931588689);
			basicprice = user2.getnumCPU() * 0.01 + user2.getnumRAM() * 0.01 + (double)(user2.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user2.getuserId() + " asks for " + user2.getnumCPU() + " CPUs and " + user2.getnumRAM() + " GB of RAM and " + user2.getnumStorage() + " GB of storage to host" + host.getId()+ "at time " + starttime);
			utiliz = CalUti(timestamp[2], acpu, uti, expiretime1, utireal);
			id = 0;
			
			if(cloudlet2.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				
				id = DecideModel(utiliz, aveRP[2], userid, res);
				System.out.println("model is " + id);
				//id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker2.CalNumOfRes(user2.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker2.CalNumOfRes(user2.getuserId(), host.getId(), 0.3, id);
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
					broker2.CalNumOfRes1(user2.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker2.CalNumOfRes2(user2.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker2.CalNumOfRes(user2.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user2.getnumCPU());
			aram.add(user2.getnumRAM());
			asto.add(user2.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("this is the " + utimeh + "th time for user" + user2.getuserId() + " to use services of host" + host.getId());
			payprice = user2.getnumCPU() * 0.01 + user2.getnumRAM() * 0.01 + (double)(user2.getnumStorage()*0.001);
			vmid = 2;
			System.out.println("host " + host.getId() + " will give " + user2.getnumCPU() + " CPUs and " +user2.getnumRAM()+ " GB of RAM and " + user2.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet2.getCloudletId());
			Vm vm2 = new Vm(vmid, broker2Id, mips, user2.getnumCPU(), user2.getnumRAM(), bw, user2.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm2.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm2);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[2] = (int)(duration[2]*(1-relinp.get(0)));
			//vmlist.add(vm2);
			expiretime1[2] = timestamp[2] + duration[2];
			
			
			relinqtime[2] = r.nextInt((expiretime1[2] - timestamp[2]) ) + timestamp[2];
			System.out.println("ts"+timestamp[2]+"rp"+ relinqtime[2] +"expt"+expiretime1[2]);
			CalUtinew(timestamp[2], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker2.submitVmList(vmlist);
			
			broker2.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user2.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			
			
/////////////////////////////////////
////////////////////////////////////
			
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter3 = createDatacenter("Datacenter_3");

			DatacenterBroker broker3 = createBroker();
			int broker3Id = broker3.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 3;
			User user3 = new User(userid);
			userlist.add(user3);
			
			broker3.submitUserList(userlist);
			broker3.BindUserToHost(user3.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker3.setHostList(hostlist1);
			
			cloudletid = 3;
			Cloudlet cloudlet3 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet3.setbrokerId(broker3Id);
			//update the price of cloudlet;
			//price = 200;
			//cloudlet2.setprice(price);
			cloudlet3.setuser(user3);
			user3.setCloudletId(cloudlet3.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet3);

			// submit cloudlet list to the broker
			broker3.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[cloudletid];
			starttime = timestamp[cloudletid];
			res = resask[3];
			user3.submitCloudlet(cloudlet3);
			user3.submitRequest(user3.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[3];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[3] == 0){}
			else if(aveRP[3] > 0){
				
				ArrayList<Double> se3 = new ArrayList<Double> ();
				ArrayList<Double> de3 = new ArrayList<Double> ();
				host.getmaop().put(userid, se3);
				host.getmsop().put(userid, de3);
				for(int i = 1; i < 8; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0, 0.2979604444598067);
			basicprice = user3.getnumCPU() * 0.01 + user3.getnumRAM() * 0.01 + (double)(user3.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user3.getuserId() + " asks for " + user3.getnumCPU() + " CPUs and " + user3.getnumRAM() + " GB of RAM and " + user3.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[3], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet3.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				
				id = DecideModel(utiliz, aveRP[3], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker3.CalNumOfRes(user3.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker3.CalNumOfRes(user3.getuserId(), host.getId(), 0.3, id);
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
					broker3.CalNumOfRes1(user3.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker3.CalPrice(cloudlet3.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker3.CalNumOfRes2(user3.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker3.CalPrice(cloudlet3.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker3.CalNumOfRes(user3.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user3.getnumCPU());
			aram.add(user3.getnumRAM());
			asto.add(user3.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user3.getnumCPU() + " CPUs and " +user3.getnumRAM()+ " GB of RAM and " + user3.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet3.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user3.getuserId() + " to use services of host" + host.getId());
			payprice = user3.getnumCPU() * 0.01 + user3.getnumRAM() * 0.01 + (double)(user3.getnumStorage()*0.001);
			vmid = 3;
			
			Vm vm3 = new Vm(vmid, broker3Id, mips, user3.getnumCPU(), user3.getnumRAM(), bw, user3.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm3.setHost(host);
			
			//int vmid3 = 1;
			//Vm vm3 = new Vm(vmid3, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm3);
			
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			
			duration[3] = (int)(duration[3]*(1-relinp.get(0)));
			expiretime1[3] = timestamp[3] + duration[3];
			//relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) + 1) ;
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) )+ timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[3], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker3.submitVmList(vmlist);
			
			broker3.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user3.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			////////////////////////////////////
			/////////////////////////////////////
			//////////////////////////////////////
			///////////////////////////////////////
			
			
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter4 = createDatacenter("Datacenter_1");

			DatacenterBroker broker4 = createBroker();
			int broker4Id = broker4.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 4;
			User user4 = new User(userid);
			userlist.add(user4);
			
			broker4.submitUserList(userlist);
			broker4.BindUserToHost(user4.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker4.setHostList(hostlist1);
			
			cloudletid = 4;
			Cloudlet cloudlet4 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet4.setbrokerId(broker4Id);
			//update the price of cloudlet;
			//price = 200;
			//cloudlet2.setprice(price);
			cloudlet4.setuser(user4);
			user4.setCloudletId(cloudlet4.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet4);

			// submit cloudlet list to the broker
			broker4.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[4];
			res = resask[4];
			starttime = timestamp[cloudletid];
			user4.submitCloudlet(cloudlet4);
			user4.submitRequest(user4.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[4];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[4] == 0){}
			else if(aveRP[4] > 0){
				
				ArrayList<Double> se4 = new ArrayList<Double> ();
				ArrayList<Double> de4 = new ArrayList<Double> ();
				host.getmaop().put(userid, se4);
				host.getmsop().put(userid, de4);
				for(int i = 1; i < 14; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.3232544084963322);
			basicprice = user4.getnumCPU() * 0.01 + user4.getnumRAM() * 0.01 + (double)(user4.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user4.getuserId() + " asks for " + user4.getnumCPU() + " CPUs and " + user4.getnumRAM() + " GB of RAM and " + user4.getnumStorage() + " GB of storage to host" + host.getId());
			utiliz = CalUti(timestamp[4], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet4.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[4], userid, res);
				System.out.println("model is " + id);
				//id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker4.CalNumOfRes(user4.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker4.CalNumOfRes(user4.getuserId(), host.getId(), 0.3, id);
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
					broker4.CalNumOfRes1(user4.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker4.CalNumOfRes2(user4.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker4.CalNumOfRes(user4.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user4.getnumCPU());
			aram.add(user4.getnumRAM());
			asto.add(user4.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user4.getnumCPU() + " CPUs and " +user4.getnumRAM()+ " GB of RAM and " + user4.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet4.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user4.getuserId() + " to use services of host" + host.getId());
			payprice = user4.getnumCPU() * 0.01 + user4.getnumRAM() * 0.01 + (double)(user4.getnumStorage()*0.001);
			vmid = 4;
			
			Vm vm4 = new Vm(vmid, broker4Id, mips, user4.getnumCPU(), user4.getnumRAM(), bw, user4.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm4.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm4);
			//vmlist.add(vm2);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[4] = (int)(duration[4]*(1-relinp.get(0)));
			expiretime1[4] = timestamp[4] + duration[4];
			//relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) + 1) ;
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) + timestamp[vmid]);
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[4], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker4.submitVmList(vmlist);
			
			broker4.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user4.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			//////////////////////////////////////////
			////////////////////////////////////////////
			//////////////////////////////////////
			////////////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter5 = createDatacenter("Datacenter_1");

			DatacenterBroker broker5 = createBroker();
			int broker5Id = broker5.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 5;
			User user5 = new User(userid);
			userlist.add(user5);
			
			broker5.submitUserList(userlist);
			broker5.BindUserToHost(user5.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker5.setHostList(hostlist1);
			
			cloudletid = 5;
			Cloudlet cloudlet5 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet5.setbrokerId(broker5Id);
			//update the price of cloudlet;
			//price = 200;
			//cloudlet2.setprice(price);
			cloudlet5.setuser(user5);
			user5.setCloudletId(cloudlet5.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet5);

			// submit cloudlet list to the broker
			broker5.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[5];
			res = resask[5];
			starttime = timestamp[cloudletid];
			user5.submitCloudlet(cloudlet5);
			user5.submitRequest(user5.getCloudlet(), false, res, res, (long)(res), numtime, starttime );
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[5];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[5] == 0){}
			else if(aveRP[5] > 0){
				
				ArrayList<Double> se5 = new ArrayList<Double> ();
				ArrayList<Double> de5 = new ArrayList<Double> ();
				host.getmaop().put(userid, se5);
				host.getmsop().put(userid, de5);
				for(int i = 1; i < 11; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.41384016286181785);
			basicprice = user5.getnumCPU() * 0.01 + user5.getnumRAM() * 0.01 + (double)(user5.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user5.getuserId() + " asks for " + user5.getnumCPU() + " CPUs and " + user5.getnumRAM() + " GB of RAM and " + user5.getnumStorage() + " GB of storage to host" + host.getId());
			utiliz = CalUti(timestamp[5], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet5.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[5], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker5.CalNumOfRes(user5.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker5.CalNumOfRes(user5.getuserId(), host.getId(), 0.3, id);
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
					broker5.CalNumOfRes1(user5.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker5.CalNumOfRes2(user5.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker5.CalNumOfRes(user5.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user5.getnumCPU());
			aram.add(user5.getnumRAM());
			asto.add(user5.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user5.getnumCPU() + " CPUs and " +user5.getnumRAM()+ " GB of RAM and " + user5.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet5.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user5.getuserId() + " to use services of host" + host.getId());
			payprice = user5.getnumCPU() * 0.01 + user5.getnumRAM() * 0.01 + (double)(user5.getnumStorage()*0.001);
			vmid = 5;
			
			Vm vm5 = new Vm(vmid, broker5Id, mips, user5.getnumCPU(), user5.getnumRAM(), bw, user5.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm5.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm5);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[5] = (int)(duration[5]*(1-relinp.get(0)));
			expiretime1[5] = timestamp[5] + duration[5];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[5], acpu, uti, utiliz, utireal);

			// submit vm list to the broker
			broker5.submitVmList(vmlist);
			
			broker5.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user5.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			
			///////////////////////////////////
			//////////////////////////////////
			////////////////////////////////
			
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter6 = createDatacenter("Datacenter_1");

			DatacenterBroker broker6 = createBroker();
			int broker6Id = broker6.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 6;
			User user6 = new User(userid);
			userlist.add(user6);
			
			broker6.submitUserList(userlist);
			broker6.BindUserToHost(user6.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker6.setHostList(hostlist1);
			
			cloudletid = 6;
			Cloudlet cloudlet6 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet6.setbrokerId(broker6Id);
			//update the price of cloudlet;
			//price = 600;
			//cloudlet6.setprice(price);
			cloudlet6.setuser(user6);
			user6.setCloudletId(cloudlet6.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet6);

			// submit cloudlet list to the broker
			broker6.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[6];
			res = resask[6];
			starttime = timestamp[cloudletid];
			user6.submitCloudlet(cloudlet6);
			user6.submitRequest(user6.getCloudlet(), false, res, res, (long)(res), numtime, starttime );
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[6];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[6] == 0){}
			else if(aveRP[6] > 0){
				
				ArrayList<Double> se6 = new ArrayList<Double> ();
				ArrayList<Double> de6 = new ArrayList<Double> ();
				host.getmaop().put(userid, se6);
				host.getmsop().put(userid, de6);
				for(int i = 1; i < 20; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.7754216615025256);
			basicprice = user6.getnumCPU() * 0.01 + user6.getnumRAM() * 0.01 + (double)(user6.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user6.getuserId() + " asks for " + user6.getnumCPU() + " CPUs and " + user6.getnumRAM() + " GB of RAM and " + user6.getnumStorage() + " GB of storage to host" + host.getId());
			utiliz = CalUti(timestamp[3], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet6.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[6], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker6.CalNumOfRes(user6.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker6.CalNumOfRes(user6.getuserId(), host.getId(), 0.3, id);
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
					broker6.CalNumOfRes1(user6.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker6.CalNumOfRes2(user6.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker6.CalPrice(cloudlet6.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker6.CalNumOfRes(user6.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user6.getnumCPU());
			aram.add(user6.getnumRAM());
			asto.add(user6.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user6.getnumCPU() + " CPUs and " +user6.getnumRAM()+ " GB of RAM and " + user6.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet6.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user6.getuserId() + " to use services of host" + host.getId());
			payprice = user6.getnumCPU() * 0.01 + user6.getnumRAM() * 0.01 + (double)(user6.getnumStorage()*0.001);
			vmid = 6;
			
			Vm vm6 = new Vm(vmid, broker6Id, mips, user6.getnumCPU(), user6.getnumRAM(), bw, user6.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm6.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm6);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[6] = (int)(duration[6]*(1-relinp.get(0)));
			expiretime1[6] = timestamp[6] + duration[6];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[6], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker6.submitVmList(vmlist);
			
			broker6.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user6.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			/////////////////////////////////
			//////////////////////////////////
			/////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter7 = createDatacenter("Datacenter_1");

			DatacenterBroker broker7 = createBroker();
			int broker7Id = broker7.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 7;
			User user7 = new User(userid);
			userlist.add(user7);
			
			broker7.submitUserList(userlist);
			broker7.BindUserToHost(user7.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker7.setHostList(hostlist1);
			
			cloudletid = 7;
			Cloudlet cloudlet7 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet7.setbrokerId(broker7Id);
			//update the price of cloudlet;
			//price = 700;
			//cloudlet7.setprice(price);
			cloudlet7.setuser(user7);
			user7.setCloudletId(cloudlet7.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet7);

			// submit cloudlet list to the broker
			broker7.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[7];
			res = resask[7];
			starttime = timestamp[cloudletid];
			user7.submitCloudlet(cloudlet7);
			user7.submitRequest(user7.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[7];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[7] == 0){}
			else if(aveRP[7] > 0){
				
				ArrayList<Double> se7 = new ArrayList<Double> ();
				ArrayList<Double> de7 = new ArrayList<Double> ();
				host.getmaop().put(userid, se7);
				host.getmsop().put(userid, de7);
				for(int i = 1; i < 15; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.29205263293591033);
			basicprice = user7.getnumCPU() * 0.01 + user7.getnumRAM() * 0.01 + (double)(user7.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user7.getuserId() + " asks for " + user7.getnumCPU() + " CPUs and " + user7.getnumRAM() + " GB of RAM and " + user7.getnumStorage() + " GB of storage to host" + host.getId());

			//utiliz = CalUti(timestamp[7], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet7.pledge == true){ 
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[7], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker7.CalNumOfRes(user7.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker7.CalNumOfRes(user7.getuserId(), host.getId(), 0.3, id);
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
					broker7.CalNumOfRes1(user7.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker7.CalNumOfRes2(user7.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker7.CalPrice(cloudlet7.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker7.CalNumOfRes(user7.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user7.getnumCPU());
			aram.add(user7.getnumRAM());
			asto.add(user7.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user7.getnumCPU() + " CPUs and " +user7.getnumRAM()+ " GB of RAM and " + user7.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet7.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user7.getuserId() + " to use services of host" + host.getId());
			payprice = user7.getnumCPU() * 0.01 + user7.getnumRAM() * 0.01 + (double)(user7.getnumStorage()*0.001);
			vmid = 7;
			
			Vm vm7 = new Vm(vmid, broker7Id, mips, user7.getnumCPU(), user7.getnumRAM(), bw, user7.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm7.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm7);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[7] = (int)(duration[7]*(1-relinp.get(0)));
			expiretime1[7] = timestamp[7] + duration[7];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[7], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker7.submitVmList(vmlist);
			
			broker7.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user7.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			//////////////////////////////////
			///////////////////////////////
			/////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter8 = createDatacenter("Datacenter_1");

			DatacenterBroker broker8 = createBroker();
			int broker8Id = broker8.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 8;
			User user8 = new User(userid);
			userlist.add(user8);
			
			broker8.submitUserList(userlist);
			broker8.BindUserToHost(user8.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker8.setHostList(hostlist1);
			
			cloudletid = 8;
			Cloudlet cloudlet8 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet8.setbrokerId(broker8Id);
			//update the price of cloudlet;
			//price = 800;
			//cloudlet8.setprice(price);
			cloudlet8.setuser(user8);
			user8.setCloudletId(cloudlet8.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet8);

			// submit cloudlet list to the broker
			broker8.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[8];
			res = resask[8];
			starttime = timestamp[cloudletid];
			user8.submitCloudlet(cloudlet8);
			user8.submitRequest(user8.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[8];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[8] == 0){}
			else if(aveRP[8] > 0){
				
				ArrayList<Double> se8 = new ArrayList<Double> ();
				ArrayList<Double> de8 = new ArrayList<Double> ();
				host.getmaop().put(userid, se8);
				host.getmsop().put(userid, de8);
				for(int i = 1; i < 14; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.7936001340804522);
			basicprice = user8.getnumCPU() * 0.01 + user8.getnumRAM() * 0.01 + (double)(user8.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user8.getuserId() + " asks for " + user8.getnumCPU() + " CPUs and " + user8.getnumRAM() + " GB of RAM and " + user8.getnumStorage() + " GB of storage to host" + host.getId());


			utiliz = CalUti(timestamp[8], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet8.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[8], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker8.CalNumOfRes(user8.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker8.CalNumOfRes(user8.getuserId(), host.getId(), 0.3, id);
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
					broker8.CalNumOfRes1(user8.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker8.CalNumOfRes2(user8.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker8.CalPrice(cloudlet8.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker8.CalNumOfRes(user8.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user8.getnumCPU());
			aram.add(user8.getnumRAM());
			asto.add(user8.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user8.getnumCPU() + " CPUs and " +user8.getnumRAM()+ " GB of RAM and " + user8.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet8.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user8.getuserId() + " to use services of host" + host.getId());
			payprice = user8.getnumCPU() * 0.01 + user8.getnumRAM() * 0.01 + (double)(user8.getnumStorage()*0.001);
			vmid = 8;
			
			Vm vm8 = new Vm(vmid, broker8Id, mips, user8.getnumCPU(), user8.getnumRAM(), bw, user8.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm8.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm8);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[8] = (int)(duration[8]*(1-relinp.get(0)));
			expiretime1[8] = timestamp[8] + duration[8];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[8], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker8.submitVmList(vmlist);
			
			broker8.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user8.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			////////////////////////////////////////
			///////////////////////////////////////
			/////////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter9 = createDatacenter("Datacenter_1");

			DatacenterBroker broker9 = createBroker();
			int broker9Id = broker9.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 9;
			User user9 = new User(userid);
			userlist.add(user9);
			
			broker9.submitUserList(userlist);
			broker9.BindUserToHost(user9.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker9.setHostList(hostlist1);
			
			cloudletid = 9;
			Cloudlet cloudlet9 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet9.setbrokerId(broker9Id);
			//update the price of cloudlet;
			//price = 900;
			//cloudlet9.setprice(price);
			cloudlet9.setuser(user9);
			user9.setCloudletId(cloudlet9.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet9);

			// submit cloudlet list to the broker
			broker9.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[9];
			res = resask[9];
			starttime = timestamp[cloudletid];
			user9.submitCloudlet(cloudlet9);
			user9.submitRequest(user9.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[9];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[9] == 0){}
			else if(aveRP[9] > 0){
				
				ArrayList<Double> se9 = new ArrayList<Double> ();
				ArrayList<Double> de9 = new ArrayList<Double> ();
				host.getmaop().put(userid, se9);
				host.getmsop().put(userid, de9);
				for(int i = 1; i < 22; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.46724986450346023);
			basicprice = user9.getnumCPU() * 0.01 + user9.getnumRAM() * 0.01 + (double)(user9.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user9.getuserId() + " asks for " + user9.getnumCPU() + " CPUs and " + user9.getnumRAM() + " GB of RAM and " + user9.getnumStorage() + " GB of storage to host" + host.getId());


			utiliz = CalUti(timestamp[9], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet9.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[9], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker9.CalNumOfRes(user9.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker9.CalNumOfRes(user9.getuserId(), host.getId(), 0.3, id);
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
					broker9.CalNumOfRes1(user9.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker9.CalNumOfRes2(user9.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker9.CalPrice(cloudlet9.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker9.CalNumOfRes(user9.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user9.getnumCPU());
			aram.add(user9.getnumRAM());
			asto.add(user9.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user9.getnumCPU() + " CPUs and " +user9.getnumRAM()+ " GB of RAM and " + user9.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet9.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user9.getuserId() + " to use services of host" + host.getId());
			payprice = user9.getnumCPU() * 0.01 + user9.getnumRAM() * 0.01 + (double)(user9.getnumStorage()*0.001);
			vmid = 9;
			
			Vm vm9 = new Vm(vmid, broker9Id, mips, user9.getnumCPU(), user9.getnumRAM(), bw, user9.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm9.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm9);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[9] = (int)(duration[9]*(1-relinp.get(0)));
			expiretime1[9] = timestamp[9] + duration[9];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[9], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker9.submitVmList(vmlist);
			
			broker9.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user9.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			/////////////////////////////////////////
			////////////////////////////////////////////
			
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter10 = createDatacenter("Datacenter_1");

			DatacenterBroker broker10 = createBroker();
			int broker10Id = broker10.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 10;
			User user10 = new User(userid);
			userlist.add(user10);
			
			broker10.submitUserList(userlist);
			broker10.BindUserToHost(user10.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker10.setHostList(hostlist1);
			
			cloudletid = 10;
			Cloudlet cloudlet10 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet10.setbrokerId(broker10Id);
			//update the price of cloudlet;
			//price = 1000;
			//cloudlet10.setprice(price);
			cloudlet10.setuser(user10);
			user10.setCloudletId(cloudlet10.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet10);

			// submit cloudlet list to the broker
			broker10.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[10];
			res = resask[10];
			starttime = timestamp[cloudletid];
			user10.submitCloudlet(cloudlet10);
			user10.submitRequest(user10.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[10];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[10] == 0){}
			else if(aveRP[10] > 0){
				
				ArrayList<Double> se10 = new ArrayList<Double> ();
				ArrayList<Double> de10 = new ArrayList<Double> ();
				host.getmaop().put(userid, se10);
				host.getmsop().put(userid, de10);
				for(int i = 1; i < 7; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.13830361655261783);
			basicprice = user10.getnumCPU() * 0.01 + user10.getnumRAM() * 0.01 + (double)(user10.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user10.getuserId() + " asks for " + user10.getnumCPU() + " CPUs and " + user10.getnumRAM() + " GB of RAM and " + user10.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[10], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet10.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[10], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker10.CalNumOfRes(user10.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker10.CalNumOfRes(user10.getuserId(), host.getId(), 0.3, id);
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
					broker10.CalNumOfRes1(user10.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker10.CalNumOfRes2(user10.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker10.CalPrice(cloudlet10.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker10.CalNumOfRes(user10.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user10.getnumCPU());
			aram.add(user10.getnumRAM());
			asto.add(user10.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user10.getnumCPU() + " CPUs and " +user10.getnumRAM()+ " GB of RAM and " + user10.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet10.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user10.getuserId() + " to use services of host" + host.getId());
			payprice = user10.getnumCPU() * 0.01 + user10.getnumRAM() * 0.01 + (double)(user10.getnumStorage()*0.001);
			vmid = 10;
			
			Vm vm10 = new Vm(vmid, broker10Id, mips, user10.getnumCPU(), user10.getnumRAM(), bw, user10.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm10.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm10);
			//vmlist.add(vm2);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[10] = (int)(duration[10]*(1-relinp.get(0)));
			expiretime1[10] = timestamp[10] + duration[10];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[10], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker10.submitVmList(vmlist);
			
			broker10.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user10.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			//////////////////////////////////////////
			////////////////////////////////////////////
			////////////////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter11 = createDatacenter("Datacenter_1");

			DatacenterBroker broker11 = createBroker();
			int broker11Id = broker11.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 11;
			User user11 = new User(userid);
			userlist.add(user11);
			
			broker11.submitUserList(userlist);
			broker11.BindUserToHost(user11.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker11.setHostList(hostlist1);
			
			cloudletid = 11;
			Cloudlet cloudlet11 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet11.setbrokerId(broker11Id);
			//update the price of cloudlet;
			//price = 1100;
			//cloudlet11.setprice(price);
			cloudlet11.setuser(user11);
			user11.setCloudletId(cloudlet11.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet11);

			// submit cloudlet list to the broker
			broker11.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[11];
			res = resask[11];
            starttime = timestamp[cloudletid];
			user11.submitCloudlet(cloudlet11);
			user11.submitRequest(user11.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[11];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[11] == 0){}
			else if(aveRP[11] > 0){
				
				ArrayList<Double> se11 = new ArrayList<Double> ();
				ArrayList<Double> de11 = new ArrayList<Double> ();
				host.getmaop().put(userid, se11);
				host.getmsop().put(userid, de11);
				for(int i = 1; i < 25; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.8914325412955506);
			basicprice = user11.getnumCPU() * 0.01 + user11.getnumRAM() * 0.01 + (double)(user11.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user11.getuserId() + " asks for " + user11.getnumCPU() + " CPUs and " + user11.getnumRAM() + " GB of RAM and " + user11.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[11], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet11.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[11], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker11.CalNumOfRes(user11.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker11.CalNumOfRes(user11.getuserId(), host.getId(), 0.3, id);
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
					broker11.CalNumOfRes1(user11.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker11.CalNumOfRes2(user11.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker11.CalPrice(cloudlet11.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker11.CalNumOfRes(user11.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user11.getnumCPU());
			aram.add(user11.getnumRAM());
			asto.add(user11.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user11.getnumCPU() + " CPUs and " +user11.getnumRAM()+ " GB of RAM and " + user11.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet11.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user11.getuserId() + " to use services of host" + host.getId());
			payprice = user11.getnumCPU() * 0.01 + user11.getnumRAM() * 0.01 + (double)(user11.getnumStorage()*0.001);
			vmid = 11;
			
			Vm vm11 = new Vm(vmid, broker11Id, mips, user11.getnumCPU(), user11.getnumRAM(), bw, user11.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm11.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm11);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[11] = (int)(duration[11]*(1-relinp.get(0)));
			expiretime1[11] = timestamp[11] + duration[11];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[11], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker11.submitVmList(vmlist);
			
			broker11.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user11.gettime();
			
			
			
			
			
			
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			/////////////////////////////////////////////////
			//////////////////////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter12 = createDatacenter("Datacenter_1");

			DatacenterBroker broker12 = createBroker();
			int broker12Id = broker12.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 12;
			User user12 = new User(userid);
			userlist.add(user12);
			
			broker12.submitUserList(userlist);
			broker12.BindUserToHost(user12.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker12.setHostList(hostlist1);
			
			cloudletid = 12;
			Cloudlet cloudlet12 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet12.setbrokerId(broker12Id);
			//update the price of cloudlet;
			//price = 1200;
			//cloudlet12.setprice(price);
			cloudlet12.setuser(user12);
			user12.setCloudletId(cloudlet12.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet12);

			// submit cloudlet list to the broker
			broker12.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[12];
			res = resask[12];
			 starttime = timestamp[cloudletid];
			user12.submitCloudlet(cloudlet12);
			user12.submitRequest(user12.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[12];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[12] == 0){}
			else if(aveRP[12] > 0){
				
				ArrayList<Double> se12 = new ArrayList<Double> ();
				ArrayList<Double> de12 = new ArrayList<Double> ();
				host.getmaop().put(userid, se12);
				host.getmsop().put(userid, de12);
				for(int i = 1; i < 16; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.5342878638709047);
			basicprice = user12.getnumCPU() * 0.01 + user12.getnumRAM() * 0.01 + (double)(user12.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user12.getuserId() + " asks for " + user12.getnumCPU() + " CPUs and " + user12.getnumRAM() + " GB of RAM and " + user12.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[12], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet12.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[12], userid, res);
				System.out.println("model is " + id);
				//id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker12.CalNumOfRes(user12.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker12.CalNumOfRes(user12.getuserId(), host.getId(), 0.3, id);
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
					broker12.CalNumOfRes1(user12.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker12.CalNumOfRes2(user12.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker12.CalPrice(cloudlet12.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker12.CalNumOfRes(user12.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user12.getnumCPU());
			aram.add(user12.getnumRAM());
			asto.add(user12.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user12.getnumCPU() + " CPUs and " +user12.getnumRAM()+ " GB of RAM and " + user12.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet12.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user12.getuserId() + " to use services of host" + host.getId());
			payprice = user12.getnumCPU() * 0.01 + user12.getnumRAM() * 0.01 + (double)(user12.getnumStorage()*0.001);
			vmid = 12;
			
			Vm vm12 = new Vm(vmid, broker12Id, mips, user12.getnumCPU(), user12.getnumRAM(), bw, user12.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm12.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm12);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[12] = (int)(duration[12]*(1-relinp.get(0)));
			expiretime1[12] = timestamp[12] + duration[12];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[12], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker12.submitVmList(vmlist);
			
			broker12.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user12.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			///////////////////////////////////////
			/////////////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter13 = createDatacenter("Datacenter_1");

			DatacenterBroker broker13 = createBroker();
			int broker13Id = broker13.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 13;
			User user13 = new User(userid);
			userlist.add(user13);
			
			broker13.submitUserList(userlist);
			broker13.BindUserToHost(user13.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker13.setHostList(hostlist1);
			
			cloudletid = 13;
			Cloudlet cloudlet13 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet13.setbrokerId(broker13Id);
			//update the price of cloudlet;
			//price = 1300;
			//cloudlet13.setprice(price);
			cloudlet13.setuser(user13);
			user13.setCloudletId(cloudlet13.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet13);

			// submit cloudlet list to the broker
			broker13.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[13];
			res = resask[13];
			 starttime = timestamp[cloudletid];
			user13.submitCloudlet(cloudlet13);
			user13.submitRequest(user13.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[13];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[13] == 0){}
			else if(aveRP[13] > 0){
				
				ArrayList<Double> se13 = new ArrayList<Double> ();
				ArrayList<Double> de13 = new ArrayList<Double> ();
				host.getmaop().put(userid, se13);
				host.getmsop().put(userid, de13);
				for(int i = 1; i < 11; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.5618863601910468);
			basicprice = user13.getnumCPU() * 0.01 + user13.getnumRAM() * 0.01 + (double)(user13.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user13.getuserId() + " asks for " + user13.getnumCPU() + " CPUs and " + user13.getnumRAM() + " GB of RAM and " + user13.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[13], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet13.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[13], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker13.CalNumOfRes(user13.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker13.CalNumOfRes(user13.getuserId(), host.getId(), 0.3, id);
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
					broker13.CalNumOfRes1(user13.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker13.CalNumOfRes2(user13.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker13.CalPrice(cloudlet13.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker13.CalNumOfRes(user13.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user13.getnumCPU());
			aram.add(user13.getnumRAM());
			asto.add(user13.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user13.getnumCPU() + " CPUs and " +user13.getnumRAM()+ " GB of RAM and " + user13.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet13.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user13.getuserId() + " to use services of host" + host.getId());
			payprice = user13.getnumCPU() * 0.01 + user13.getnumRAM() * 0.01 + (double)(user13.getnumStorage()*0.001);
			vmid = 13;
			
			Vm vm13 = new Vm(vmid, broker13Id, mips, user13.getnumCPU(), user13.getnumRAM(), bw, user13.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm13.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm13);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[13] = (int)(duration[13]*(1-relinp.get(0)));
			expiretime1[13] = timestamp[13] + duration[13];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[13], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker13.submitVmList(vmlist);
			
			broker13.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user13.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			////////////////////////////////////
			/////////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter14 = createDatacenter("Datacenter_1");

			DatacenterBroker broker14 = createBroker();
			int broker14Id = broker14.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 14;
			User user14 = new User(userid);
			userlist.add(user14);
			
			broker14.submitUserList(userlist);
			broker14.BindUserToHost(user14.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker14.setHostList(hostlist1);
			
			cloudletid = 14;
			Cloudlet cloudlet14 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet14.setbrokerId(broker14Id);
			//update the price of cloudlet;
			//price = 1400;
			//cloudlet14.setprice(price);
			cloudlet14.setuser(user14);
			user14.setCloudletId(cloudlet14.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet14);

			// submit cloudlet list to the broker
			broker14.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[14];
			res = resask[14];
			 starttime = timestamp[cloudletid];
			user14.submitCloudlet(cloudlet14);
			user14.submitRequest(user14.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[14];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[14] == 0){}
			else if(aveRP[14] > 0){
				
				ArrayList<Double> se14 = new ArrayList<Double> ();
				ArrayList<Double> de14 = new ArrayList<Double> ();
				host.getmaop().put(userid, se14);
				host.getmsop().put(userid, de14);
				for(int i = 1; i < 13; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.6316339619573237);
			basicprice = user14.getnumCPU() * 0.01 + user14.getnumRAM() * 0.01 + (double)(user14.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user14.getuserId() + " asks for " + user14.getnumCPU() + " CPUs and " + user14.getnumRAM() + " GB of RAM and " + user14.getnumStorage() + " GB of storage to host" + host.getId());

			
			utiliz = CalUti(timestamp[14], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet14.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[14], userid, res);
				System.out.println("model is " + id);
				//id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker14.CalNumOfRes(user14.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker14.CalNumOfRes(user14.getuserId(), host.getId(), 0.3, id);
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
					broker14.CalNumOfRes1(user14.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker14.CalNumOfRes2(user14.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker14.CalPrice(cloudlet14.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker14.CalNumOfRes(user14.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user14.getnumCPU());
			aram.add(user14.getnumRAM());
			asto.add(user14.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user14.getnumCPU() + " CPUs and " +user14.getnumRAM()+ " GB of RAM and " + user14.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet14.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user14.getuserId() + " to use services of host" + host.getId());
			payprice = user14.getnumCPU() * 0.01 + user14.getnumRAM() * 0.01 + (double)(user14.getnumStorage()*0.001);
			vmid = 14;
			
			Vm vm14 = new Vm(vmid, broker14Id, mips, user14.getnumCPU(), user14.getnumRAM(), bw, user14.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm14.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			//vmlist.add(vm2);
			duration[14] = (int)(duration[14]*(1-relinp.get(0)));
			expiretime1[14] = timestamp[14] + duration[14];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[14], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker14.submitVmList(vmlist);
			
			broker14.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user14.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			//////////////////////////////////////
			///////////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter15 = createDatacenter("Datacenter_1");

			DatacenterBroker broker15 = createBroker();
			int broker15Id = broker15.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 15;
			User user15 = new User(userid);
			userlist.add(user15);
			
			broker15.submitUserList(userlist);
			broker15.BindUserToHost(user15.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker15.setHostList(hostlist1);
			
			cloudletid = 15;
			Cloudlet cloudlet15 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet15.setbrokerId(broker15Id);
			//update the price of cloudlet;
			//price = 1500;
			//cloudlet15.setprice(price);
			cloudlet15.setuser(user15);
			user15.setCloudletId(cloudlet15.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet15);

			// submit cloudlet list to the broker
			broker15.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[15];
			res = resask[15];
			 starttime = timestamp[cloudletid];
			user15.submitCloudlet(cloudlet15);
			user15.submitRequest(user15.getCloudlet(), false, res, res, (long)(res), starttime,numtime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[15];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[15] == 0){}
			else if(aveRP[15] > 0){
				
				ArrayList<Double> se15 = new ArrayList<Double> ();
				ArrayList<Double> de15 = new ArrayList<Double> ();
				host.getmaop().put(userid, se15);
				host.getmsop().put(userid, de15);
				for(int i = 1; i < 15; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.42142471778390245);
			basicprice = user15.getnumCPU() * 0.01 + user15.getnumRAM() * 0.01 + (double)(user15.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user15.getuserId() + " asks for " + user15.getnumCPU() + " CPUs and " + user15.getnumRAM() + " GB of RAM and " + user15.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[15], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet15.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[15], userid, res);
				System.out.println("model is " + id);
				//id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker15.CalNumOfRes(user15.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker15.CalNumOfRes(user15.getuserId(), host.getId(), 0.3, id);
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
					broker15.CalNumOfRes1(user15.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker15.CalNumOfRes2(user15.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker15.CalPrice(cloudlet15.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker15.CalNumOfRes(user15.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user15.getnumCPU());
			aram.add(user15.getnumRAM());
			asto.add(user15.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user15.getnumCPU() + " CPUs and " +user15.getnumRAM()+ " GB of RAM and " + user15.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet15.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user15.getuserId() + " to use services of host" + host.getId());
			payprice = user15.getnumCPU() * 0.01 + user15.getnumRAM() * 0.01 + (double)(user15.getnumStorage()*0.001);
			vmid = 15;
			
			Vm vm15 = new Vm(vmid, broker15Id, mips, user15.getnumCPU(), user15.getnumRAM(), bw, user15.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm15.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm15);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[15] = (int)(duration[15]*(1-relinp.get(0)));
			expiretime1[15] = timestamp[15] + duration[15];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[15], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker15.submitVmList(vmlist);
			
			broker15.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user15.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			////////////////////////////////
			////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter16 = createDatacenter("Datacenter_1");

			DatacenterBroker broker16 = createBroker();
			int broker16Id = broker16.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 16;
			User user16 = new User(userid);
			userlist.add(user16);
			
			broker16.submitUserList(userlist);
			broker16.BindUserToHost(user16.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker16.setHostList(hostlist1);
			
			cloudletid = 16;
			Cloudlet cloudlet16 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet16.setbrokerId(broker16Id);
			//update the price of cloudlet;
			//price = 1600;
			//cloudlet16.setprice(price);
			cloudlet16.setuser(user16);
			user16.setCloudletId(cloudlet16.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet16);

			// submit cloudlet list to the broker
			broker16.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[16];
			res = resask[16];
			user16.submitCloudlet(cloudlet16);
			 starttime = timestamp[cloudletid];
			user16.submitRequest(user16.getCloudlet(), false, res, res, (long)(res), numtime, starttime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[16];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[16] == 0){}
			else if(aveRP[16] > 0){
				ArrayList<Double> se16 = new ArrayList<Double> ();
				ArrayList<Double> de16 = new ArrayList<Double> ();
				host.getmaop().put(userid, se16);
				host.getmsop().put(userid, de16);
				for(int i = 1; i < 21; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.27795662970055923);
			basicprice = user16.getnumCPU() * 0.01 + user16.getnumRAM() * 0.01 + (double)(user16.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user16.getuserId() + " asks for " + user16.getnumCPU() + " CPUs and " + user16.getnumRAM() + " GB of RAM and " + user16.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[16], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet16.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[16], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker16.CalNumOfRes(user16.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker16.CalNumOfRes(user16.getuserId(), host.getId(), 0.3, id);
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
					broker16.CalNumOfRes1(user16.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker16.CalNumOfRes2(user16.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker16.CalPrice(cloudlet16.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker16.CalNumOfRes(user16.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user16.getnumCPU());
			aram.add(user16.getnumRAM());
			asto.add(user16.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user16.getnumCPU() + " CPUs and " +user16.getnumRAM()+ " GB of RAM and " + user16.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet16.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user16.getuserId() + " to use services of host" + host.getId());
			payprice = user16.getnumCPU() * 0.01 + user16.getnumRAM() * 0.01 + (double)(user16.getnumStorage()*0.001);
			vmid = 16;
			
			Vm vm16 = new Vm(vmid, broker16Id, mips, user16.getnumCPU(), user16.getnumRAM(), bw, user16.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm16.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm16);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[16] = (int)(duration[16]*(1-relinp.get(0)));
			expiretime1[16] = timestamp[16] + duration[16];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[16], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker16.submitVmList(vmlist);
			
			broker16.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user16.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			
			///////////////////////////////////
			///////////////////////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter17 = createDatacenter("Datacenter_1");

			DatacenterBroker broker17 = createBroker();
			int broker17Id = broker17.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 17;
			User user17 = new User(userid);
			userlist.add(user17);
			
			broker17.submitUserList(userlist);
			broker17.BindUserToHost(user17.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker17.setHostList(hostlist1);
			
			cloudletid = 17;
			Cloudlet cloudlet17 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet17.setbrokerId(broker17Id);
			//update the price of cloudlet;
			//price = 1700;
			//cloudlet17.setprice(price);
			cloudlet17.setuser(user17);
			user17.setCloudletId(cloudlet17.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet17);

			// submit cloudlet list to the broker
			broker17.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[17];
			res = resask[17];
			 starttime = timestamp[cloudletid];
			user17.submitCloudlet(cloudlet17);
			user17.submitRequest(user17.getCloudlet(), false, res, res, (long)(res), starttime, numtime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[17];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[17] == 0){}
			else if(aveRP[17] > 0){
				
				ArrayList<Double> se17 = new ArrayList<Double> ();
				ArrayList<Double> de17 = new ArrayList<Double> ();
				host.getmaop().put(userid, se17);
				host.getmsop().put(userid, de17);
				for(int i = 1; i < 24; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}                                                                                                 
			relinp.set(0,0.6855749421501627);
			basicprice = user17.getnumCPU() * 0.01 + user17.getnumRAM() * 0.01 + (double)(user17.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user17.getuserId() + " asks for " + user17.getnumCPU() + " CPUs and " + user17.getnumRAM() + " GB of RAM and " + user17.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[17], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet17.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[17], userid, res);
				System.out.println("model is " + id);
				//id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker17.CalNumOfRes(user17.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker17.CalNumOfRes(user17.getuserId(), host.getId(), 0.3, id);
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
					broker17.CalNumOfRes1(user17.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker17.CalNumOfRes2(user17.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker17.CalPrice(cloudlet17.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker17.CalNumOfRes(user17.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user17.getnumCPU());
			aram.add(user17.getnumRAM());
			asto.add(user17.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user17.getnumCPU() + " CPUs and " +user17.getnumRAM()+ " GB of RAM and " + user17.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet17.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user17.getuserId() + " to use services of host" + host.getId());
			payprice = user17.getnumCPU() * 0.01 + user17.getnumRAM() * 0.01 + (double)(user17.getnumStorage()*0.001);
			vmid = 17;
			
			Vm vm17 = new Vm(vmid, broker17Id, mips, user17.getnumCPU(), user17.getnumRAM(), bw, user17.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm17.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm17);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[17] = (int)(duration[17]*(1-relinp.get(0)));
			expiretime1[17] = timestamp[17] + duration[17];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[17], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker17.submitVmList(vmlist);
			
			broker17.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user17.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			////////////////////
			////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter18 = createDatacenter("Datacenter_1");

			DatacenterBroker broker18 = createBroker();
			int broker18Id = broker18.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 18;
			User user18 = new User(userid);
			userlist.add(user18);
			
			broker18.submitUserList(userlist);
			broker18.BindUserToHost(user18.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker18.setHostList(hostlist1);
			
			cloudletid = 18;
			Cloudlet cloudlet18 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet18.setbrokerId(broker18Id);
			//update the price of cloudlet;
			//price = 1800;
			//cloudlet18.setprice(price);
			cloudlet18.setuser(user18);
			user18.setCloudletId(cloudlet18.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet18);

			// submit cloudlet list to the broker
			broker18.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[18];
			res = resask[18];
			 starttime = timestamp[cloudletid];
			user18.submitCloudlet(cloudlet18);
			user18.submitRequest(user18.getCloudlet(), false, res, res, (long)(res), starttime, numtime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[18];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[18] == 0){}
			else if(aveRP[18] > 0){
				
				ArrayList<Double> se18 = new ArrayList<Double> ();
				ArrayList<Double> de18 = new ArrayList<Double> ();
				host.getmaop().put(userid, se18);
				host.getmsop().put(userid, de18);
				for(int i = 1; i <7; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.63705027280002506);
			basicprice = user18.getnumCPU() * 0.01 + user18.getnumRAM() * 0.01 + (double)(user18.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user18.getuserId() + " asks for " + user18.getnumCPU() + " CPUs and " + user18.getnumRAM() + " GB of RAM and " + user18.getnumStorage() + " GB of storage to host" + host.getId());

			utiliz = CalUti(timestamp[18], acpu, uti, expiretime1, utireal);
			System.out.println("DDD"+utiliz);
			id = 0;
			if(cloudlet18.pledge == true){
				
				
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[18], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker18.CalNumOfRes(user18.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker18.CalNumOfRes(user18.getuserId(), host.getId(), 0.3, id);
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
					broker18.CalNumOfRes1(user18.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker18.CalNumOfRes2(user18.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker18.CalPrice(cloudlet18.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker18.CalNumOfRes(user18.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					
				}
			}

			
			acpu.add(user18.getnumCPU());
			aram.add(user18.getnumRAM());
			asto.add(user18.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user18.getnumCPU() + " CPUs and " +user18.getnumRAM()+ " GB of RAM and " + user18.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet18.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user18.getuserId() + " to use services of host" + host.getId());
			payprice = user18.getnumCPU() * 0.01 + user18.getnumRAM() * 0.01 + (double)(user18.getnumStorage()*0.001);
			vmid = 18;
			
			Vm vm18 = new Vm(vmid, broker18Id, mips, user18.getnumCPU(), user18.getnumRAM(), bw, user18.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm18.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm18);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[18] = (int)(duration[18]*(1-relinp.get(0)));
			expiretime1[18] = timestamp[18] + duration[18];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[18], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker18.submitVmList(vmlist);
			
			broker18.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user18.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);
			
			
			
			
			CloudSim.startSimulation();
			/////////////////////
			///////////////////
			CloudSim.init(num_user, calendar, trace_flag);

			@SuppressWarnings("unused")
			Datacenter datacenter19 = createDatacenter("Datacenter_1");

			DatacenterBroker broker19 = createBroker();
			int broker19Id = broker19.getId();
			
			//update totalProfit;
			totalProfit += payprice;
			userid = 19;
			User user19 = new User(userid);
			userlist.add(user19);
			
			broker19.submitUserList(userlist);
			broker19.BindUserToHost(user19.getuserId(), host.getId());
			
			host.setTotalProfit(totalProfit);
			
			broker19.setHostList(hostlist1);
			
			cloudletid = 19;
			Cloudlet cloudlet19 = new Cloudlet(cloudletid, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudlet19.setbrokerId(broker19Id);
			//update the price of cloudlet;
			//price = 1900;
			//cloudlet19.setprice(price);
			cloudlet19.setuser(user19);
			user19.setCloudletId(cloudlet19.getCloudletId());
			
			// add the cloudlet to the list
			cloudletList.add(cloudlet19);

			// submit cloudlet list to the broker
			broker19.submitCloudletList(cloudletList);
			
			//adrian
			numtime = duration[19];
			res = resask[19];
			 starttime = timestamp[cloudletid];
			user19.submitCloudlet(cloudlet19);
			user19.submitRequest(user19.getCloudlet(), false, res, res, (long)(res), starttime,numtime);
			relinp.clear();
			host.getmtimes().put(userid, 0);
			for(int i = 0 ; i < 100; i++){
				double a = r.nextGaussian() * 0.3 + aveRP[19];
				if (a < 0 || a > 1){}
				else{
					relinp.add(a);
				}
			}
			if(aveRP[19] == 0){}
			else if(aveRP[19] > 0){
				
				ArrayList<Double> se19 = new ArrayList<Double> ();
				ArrayList<Double> de19 = new ArrayList<Double> ();
				host.getmaop().put(userid, se19);
				host.getmsop().put(userid, de19);
				for(int i = 1; i < 19; i++){
					host.addmaop(userid, relinp.get(i));
					host.addmsop(userid, relinp.get(i));
					host.addmtimes(userid);
				}
			}
			relinp.set(0,0.759289226467618);
			basicprice = user19.getnumCPU() * 0.01 + user19.getnumRAM() * 0.01 + (double)(user19.getnumStorage()*0.001);
			pledge = basicprice * 1.05;
			System.out.println("user" + user19.getuserId() + " asks for " + user19.getnumCPU() + " CPUs and " + user19.getnumRAM() + " GB of RAM and " + user19.getnumStorage() + " GB of storage to host" + host.getId()+ "at time" + starttime  );

			utiliz = CalUti(timestamp[19], acpu, uti, expiretime1, utireal);
			id = 0;
			if(cloudlet19.pledge == true){
				//here I didn't consider giving back the extra money if the user will not relinquish any resource;
				payprice = pledge;
			}
			else{
				id = DecideModel(utiliz, aveRP[19], userid, res);
				System.out.println("model is " + id);
			//	id = 1;
				//host.minusmtimes(userid);
				//here remember the BROKER is changing to BROKER2!!!!!!!!! and cloudlet is cloudlet2!!!!!!!!!
				if (host.getmtimes().get(userid) == 0){
					host.addmtimes(userid);
					ArrayList<Double> aaa = new ArrayList<Double> ();
					ArrayList<Double> bbb = new ArrayList<Double> ();
					//here I will set some default values for aop and sop and ...;
					if(host.getmaop().get(userid) == null){
					broker19.CalNumOfRes(user19.getuserId(), host.getId(), 0.3, 0.3, id);
					//right now we don't consider the pricing model;
					//payprice = broker.CalPrice(cloudlet.getCloudletId(), host.getId(), 0.3);
					host.getmaop().put(userid, bbb);
					}
					else {
						broker19.CalNumOfRes(user19.getuserId(), host.getId(), 0.3, id);
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
					broker19.CalNumOfRes1(user19.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker2.CalPrice(cloudlet2.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) == 2){
					host.addmtimes(userid);
					// will add codes for sop and aop and ...;
					broker19.CalNumOfRes2(user19.getuserId(), host.getId(), id);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
					//payprice = broker19.CalPrice(cloudlet19.getCloudletId(), host.getId());
				}
				else if(host.getmtimes().get(userid) > 2){
					broker19.CalNumOfRes(user19.getuserId(), host.getId(), id);
					int a = host.getmtimes().get(userid);
					host.addmtimes(userid);
					host.addmsop(userid, relinp.get(0));
					host.addmaop(userid, relinp.get(0));
				//	host.printmsop(userid);
			     //	host.printmaop(userid);
				}
				
			}

			
			acpu.add(user19.getnumCPU());
			aram.add(user19.getnumRAM());
			asto.add(user19.getnumStorage());
			utimeh = host.getmtimes().get(userid) ;
			System.out.println("host " + host.getId() + " will give " + user19.getnumCPU() + " CPUs and " +user19.getnumRAM()+ " GB of RAM and " + user19.getnumStorage() + " GB of storage to user" + userid + " to finish his cloudlet " + cloudlet19.getCloudletId());
			System.out.println("this is the " + utimeh + "th time for user" + user19.getuserId() + " to use services of host" + host.getId());
			payprice = user19.getnumCPU() * 0.01 + user19.getnumRAM() * 0.01 + (double)(user19.getnumStorage()*0.001);
			vmid = 19;
			
			Vm vm19 = new Vm(vmid, broker19Id, mips, user19.getnumCPU(), user19.getnumRAM(), bw, user19.getnumStorage(), vmm, new CloudletSchedulerTimeShared());
			
			vm19.setHost(host);
			
			//int vmid2 = 1;
			//Vm vm2 = new Vm(vmid2, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			// add the VM to the vmList
			vmlist.add(vm19);
			//vmlist.add(vm2);
			endTime =  timestamp[vmid] + duration[vmid];
			System.out.println("req duration is "+duration[vmid]);
			System.out.println("req till " + endTime);
			duration[19] = (int)(duration[19]*(1-relinp.get(0)));
			expiretime1[19] = timestamp[19] + duration[19];
			relinqtime[vmid] = r.nextInt((expiretime1[vmid] - timestamp[vmid]) ) + timestamp[vmid];
			System.out.println("ts"+timestamp[vmid]+"rp"+ relinqtime[vmid] +"expt"+expiretime1[vmid]);
			CalUtinew(timestamp[19], acpu, uti, utiliz, utireal);
			// submit vm list to the broker
			broker19.submitVmList(vmlist);
			
			broker19.bindCloudletToVm(cloudletid, vmid);
			
			payprice = payprice * user19.gettime();
			
			
			System.out.println(host.getmtimes().get(userid));
			payment.add(payprice);

			CloudSim.startSimulation();
			///////////////////////
			////////////////

			///////////////////////////////////////////
			////////////////////////////////////////////
			



            int ac=0, ar = 0;
			long ast = 0;
			for (Integer record : acpu) {
				ac += record;
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
			System.out.println("THe average I want is "+ summm/count);
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
			List<Cloudlet> newList = broker.getCloudletReceivedList();
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
					utinow = utinow - res.get(i)*0.001;
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
			uti.put(0, res.get(0)*0.001);
			utireal.put(0, res.get(0)*0.001);
		}
		else{
			int lastentry = res.size()-1;
			int resnow = res.get(lastentry);
			//double utinow = uti.get(lastentry -1);
			utinow = utinow + resnow*0.001;
			uti.put(lastentry, utinow);
			utireal.put(timestamp, utinow);
			System.out.println("Utilization "+utireal);
		}
		
	}
	
	
	public static final int DecideModel(double uti, double aveRP, int userid, int res){
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
				if(0 < aveRP&&aveRP <= 0.5){
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

		String indent = "    ";
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
