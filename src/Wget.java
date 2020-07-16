package Wget;

import  java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

//import java.io.InputStreamReader;
//import java.util.stream.Stream;

public class Wget {

	
	
	public static void main( String[] arguments )
	{

		//System.out.println("hello xarxes");
		if ( Objects.equals(arguments[0], "-f") )
		{

		
			final String FILENAME = arguments[1];
		
		try {
			
			int i=0;
			
			boolean a=false;
			boolean z=false;
			boolean gz=false;
			
			int nArgs=2;
			
			while (nArgs<arguments.length)
			{
			

				if ( Objects.equals(arguments[nArgs], "-a") )
				{
					a=true;
				}
			
				if ( Objects.equals(arguments[nArgs], "-z") )
				{
					z=true;
				}
			
				if ( Objects.equals(arguments[nArgs], "-gz") )
				{
					gz=true;
				}
			
			nArgs++;
			
			}

			
			
			//Stream<String> stream = null;
			BufferedReader in = new BufferedReader(new FileReader(FILENAME));
			String s;
			
			while ((s=in.readLine())!=null)
		    {
				
		           //System.out.println(s);
		           	
		           i++;
					Flux t = new Flux(s,i,a,z,gz);
					t.start();
 
		    }
			
			in.close();
			//System.out.println(stream.toString());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
	
	 
	