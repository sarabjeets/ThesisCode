package org.cloudbus.cloudsim.examples;

import java.util.ArrayList;

public class OctaveScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int[] i2={5,10,20,40,60,100};
		int[] i3={6,11,21,41,61,101};
		String[] file = new String[51];
		String P = "PR";
		String addr = "cd /home/singh/CsimSj/preDataAvg/r";
		String m5 = ";y = load('5.txt');m = length(y);x = [1:m];X = [ones(m, 1), x'];theta = pinv(X'*X)*X'*y;a";
		String n5 = "=[1,6]*theta;";
		String m10 = "y = load('10.txt');m = length(y);x = [1:m];X = [ones(m, 1), x'];theta = pinv(X'*X)*X'*y;b";
		String n10 = "=[1,11]*theta;";
		String m20 = "y = load('20.txt');m = length(y);x = [1:m];X = [ones(m, 1), x'];theta = pinv(X'*X)*X'*y;c";
		String n20 = "=[1,21]*theta;";
		String m40 = "y = load('40.txt');m = length(y);x = [1:m];X = [ones(m, 1), x'];theta = pinv(X'*X)*X'*y;d";
		String n40 = "=[1,41]*theta;";
		String m60 = "y = load('60.txt');m = length(y);x = [1:m];X = [ones(m, 1), x'];theta = pinv(X'*X)*X'*y;e";
		String n60 = "=[1,61]*theta;";
		String m80 = "y = load('80.txt');m = length(y);x = [1:m];X = [ones(m, 1), x'];theta = pinv(X'*X)*X'*y;f";
		String n80 = "=[1,81]*theta;";
		String m100 = "y = load('100.txt');m = length(y);x = [1:m];X = [ones(m, 1), x'];theta = pinv(X'*X)*X'*y;g";
		String n100 = "=[1,101]*theta;";
		
	//	for(int i : i2)
			for(int j=1;j<51;j++) {
				
		      //String file = addr + String.valueOf(j) +"/"+String.valueOf(i2)+".txt";
		          file[j] = addr + String.valueOf(j) +m5+String.valueOf(j)+n5+m10+String.valueOf(j)+n10+m20+String.valueOf(j)+n20+m40+String.valueOf(j)+n40+m60+String.valueOf(j)+n60+m80+String.valueOf(j)+n80+m100+String.valueOf(j)+n100;
			 //     System.out.println(file[j]);
			}
			
			ArrayList<String> a = new ArrayList<String> ();
			ArrayList<String> b = new ArrayList<String> ();
			ArrayList<String> c = new ArrayList<String> ();
			ArrayList<String> d = new ArrayList<String> ();
			ArrayList<String> e = new ArrayList<String> ();
			ArrayList<String> f = new ArrayList<String> ();
			ArrayList<String> g = new ArrayList<String> ();
				
			
			for(int j=1;j<51;j++){
			String A =  "a"+ String.valueOf(j);
			String B =  "b"+ String.valueOf(j);
			String C =  "c"+ String.valueOf(j);
			String D =  "d"+ String.valueOf(j);
			String E =  "e"+ String.valueOf(j);
			String F =  "f"+ String.valueOf(j);
			String G =  "g"+ String.valueOf(j);
			
			a.add(A);
			b.add(B);
			c.add(C);
			d.add(D);
			e.add(E);
			f.add(F);
			g.add(G);
			
			
			}
		   System.out.println(a);
			System.out.println(b);
			System.out.println(c);
		//	System.out.println(d);
		//	System.out.println(e);
		//	System.out.println(f);
		//	System.out.println(g);
			
	}

}
