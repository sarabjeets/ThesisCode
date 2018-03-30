
import org.jopas.*;

public class Example1 {
    
        public Example1() {
        
        Jopas jopas = new Jopas(); //joPAS inicialitation
                
        double a = 6;
        jopas.Load(a,"a");
        
        double b = 2;
        Matrix mb= new Matrix (b,"b");
        jopas.Load(mb);
        
        Matrix A = jopas.Save("a");
        System.out.println(A.getRealAt(0,0));
        
        Matrix B = jopas.Save("b");
        System.out.println(B.getRealAt(0,0)); 
        
        System.exit(0);
    }

public static void main(String av[]) {

        Example1 example1 = new Example1();
        
        
    }
}