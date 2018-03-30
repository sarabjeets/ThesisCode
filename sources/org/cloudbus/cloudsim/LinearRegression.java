package org.cloudbus.cloudsim;

import java.io.IOException;
import java.util.ArrayList;

import ilog.concert.IloException;

/******************************************************************************
 *  Compilation:  javac LinearRegression.java
 *  Execution:    java  LinearRegression
 *  Dependencies: none
 *  
 *  Compute least squares solution to y = beta * x + alpha.
 *  Simple linear regression.
 *
 ******************************************************************************/


/**
 *  The {@code LinearRegression} class performs a simple linear regression
 *  on an set of <em>n</em> data points (<em>y<sub>i</sub></em>, <em>x<sub>i</sub></em>).
 *  That is, it fits a straight line <em>y</em> = &alpha; + &beta; <em>x</em>,
 *  (where <em>y</em> is the response variable, <em>x</em> is the predictor variable,
 *  &alpha; is the <em>y-intercept</em>, and &beta; is the <em>slope</em>)
 *  that minimizes the sum of squared residuals of the linear regression model.
 *  It also computes associated statistics, including the coefficient of
 *  determination <em>R</em><sup>2</sup> and the standard deviation of the
 *  estimates for the slope and <em>y</em>-intercept.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class LinearRegression {
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
    public LinearRegression(int[] x, ArrayList<Double> y) {
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

    
    
   /* public static void main(String[] args)throws IOException, IloException {
    	
    	int[] X = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    	double[] y = {0.01, 0.30131092209225785, 0.6011229371704857, 0.11488968524161185, 0.4344178930554293};//, 0.11159036102721195, 0.17663107514681314, 0.17712678081037364, 0.18868889787432636, 0.01, 0.44678130029108787, 0.13665750016400607, 0.01, 0.01, 0.5491354833123995, 0.01, 0.021094493359797295, 0.12214857242063909, 0.01, 0.2477979998813022};
    	ArrayList<Double> Y = new ArrayList<Double>();
    	
    	for(int i = 0; i<y.length; i++) {
    		
    		Y.add(y[i]);
    	}
    	//ac .23 pred .45
    	
    	LinearRegression x = new LinearRegression(X,Y);
    	
    double pred = 	x.predict(Y.size()+1);
    
    double r2 = x.intercept
    		();
    
    	System.out.println(pred);
    	System.out.println(Y.size());
    }*/
    	
}