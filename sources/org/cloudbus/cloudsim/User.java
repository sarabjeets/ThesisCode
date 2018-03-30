package org.cloudbus.cloudsim;

import java.text.DecimalFormat;
import java.util.*;

import org.cloudbus.cloudsim.core.CloudSim;


@SuppressWarnings("unused")
public class User {
	
	private int userid;
	
	public int hostId;
	
	protected int cloudletId;
	
	private double payment;
	
	private StringBuffer history;
	
	private DecimalFormat num;
	
	private Cloudlet cloudlet;
	
	private int numCPU;
	
	private int numRAM;
	
	private long numStorage;
    private int numCPU1;
	
	private int numRAM1;
	
	private long numStorage1;
    private int numCPU2;
	
	private int numRAM2;
	
	private long numStorage2;
	
    private int numCPU3;
	
	private int numRAM3;
	
	private long numStorage3;
	
	 private int numCPU4;
	private double time;
	
	
	private int numRes0 ;
	
	private int numRes1 ;
	
	private int numRes2 ;
	
	private int  numRes3 ;
	
	private int numRes4 ;
	
	
	

	    private String name;

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	
	public void submitCloudlet(Cloudlet cloudlet){
		this.cloudlet = cloudlet;
	}
	
	public Cloudlet getCloudlet(){
		return cloudlet;
	}
	
	public void sethostId(int hostId){
		this.hostId = hostId;
	}
	
	public int gethostId(){
		return hostId;
	}
	//private list<> SOPrecords;
	
	//private ArrayList VMtimes;
	
	private String newline;
	
	
	//private final boolean record;
	
	public User(int userid/*, boolean record*/){
		this.userid = userid;
//		this.record = record;
		cloudletId = -1;
		hostId = -1;
		payment = 0.0;
		
	}
	
	public int getuserId(){		
		return userid;
	}
	
	
	public void submitRequest(Cloudlet cloudlet, boolean i, int x, int y, long z, double time, double time1){
		//cloudlet.setFinishTime(time);
		cloudlet.setpledge(i);
		this.numCPU = x;
		this.numRAM = y;
		this.numStorage = z;
		this.numCPU1 = x;
		this.numRAM1 = y;
		this.numStorage1 = z;
		this.numCPU2 = x;
		this.numRAM2 = y;
		this.numStorage2 = z;
		this.numCPU3 = x;
		this.numRAM3 = y;
		this.numStorage3 = z;
		this.numCPU4 = x;
		this.time = time1;
		
		
		
	}
	public void submitBatchRequest(Cloudlet cloudlet, boolean i, int[] batchDemand, long z, double time, double time1){
        
		this.numRes0 = batchDemand[0];
		
		this.numRes1 = batchDemand[1];
		
		this.numRes2 = batchDemand[2];
		
		this.numRes3 = batchDemand[3];
		
		this.numRes4 = batchDemand[4];
		
	
	}
	
	
	public int getnumCPU(){
		return numCPU;
	}
	
	public int getnumCPU1(){
		return numCPU1;
	}
	public int getnumCPU2(){
		return numCPU2;
	}
	public int getnumCPU3(){
		return numCPU3;
	}
	public int getnumCPU4(){
		return numCPU4;
	}
	
	public void setnumCPU(int numCPU){
		this.numCPU = numCPU;
	}
	public void setnumCPU1(int numCPU){
		this.numCPU1 = numCPU;
	}
	public void setnumCPU2(int numCPU){
		this.numCPU2 = numCPU;
	}
	public void setnumCPU3(int numCPU){
		this.numCPU3 = numCPU;
	}
	public void setnumCPU4(int numCPU){
		this.numCPU4 = numCPU;
	}
	
	public int getnumRAM(){
		return numRAM;
	}	
	public int getnumRAM1(){
		return numRAM1;
	}	
	
	public void setnumRAM(int numRAM){
		this.numRAM = numRAM;
	}
	public void setnumRAM1(int numRAM1){
		this.numRAM1 = numRAM1;
	}
	public long getnumStorage(){
		return numStorage;
	}	
	public long getnumStorage1(){
		return numStorage1;
	}	
	public void setnumStorage(long numStorage){
		this.numStorage = numStorage;
	}
	public void setnumStorage1(long numStorage1){
		this.numStorage1 = numStorage1;
	}
	public void setnumtime(double time){
		this.time = time;
	}
	
	public double gettime(){
		return time;
	}
	
	/*public double calculateSOP(){
		
	}*/
	
	public int getCloudletId(){
		return cloudletId;
	}
	
	public void setCloudletId(int CloudletId){
		this.cloudletId = CloudletId;
	}
	
	public void setPayment (double a ){
		payment = a;
	}
	
	
	public double getPayment(){
		return payment;
	}
	/*protected void write(final String str) {
		if (!record) {
			return;
		}

		if (num == null || history == null) { // Creates the history or
												// transactions of this Cloudlet
			newline = System.getProperty("line.separator");
			num = new DecimalFormat("#0.00#"); // with 3 decimal spaces
			history = new StringBuffer(1000);
			history.append("Time below denotes the simulation time.");
			history.append(System.getProperty("line.separator"));
			history.append("Time (sec)       Description User #" + userid);
			history.append(System.getProperty("line.separator"));
			history.append("------------------------------------------");
			history.append(System.getProperty("line.separator"));
			history.append(num.format(CloudSim.clock()));
			history.append("   Creates User ID #" + userid);
			history.append(System.getProperty("line.separator"));
		}

		history.append(num.format(CloudSim.clock()));
		history.append("   " + str + newline);
	}*/
}
