package Wget;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;




public class Flux extends Thread {

	private String url;
	private int cont;
	private boolean filtreA;
	private boolean filtreZ;
	private boolean filtreGZ;
	
	public Flux(String s, int i, boolean a, boolean z, boolean gz)
	{
		url=s;
		cont=i;
		filtreA=a;
		filtreZ=z;
		filtreGZ=gz;
		
		
	}
	
	
	
	public void run() {
		
		
		System.out.println(url);
			

		    final String OutputFile;
		    
		    // Spliteamos la url en segmentos
		    String segments[] = url.split("/");
		    
		    // Extraemos el último segmento después de la última "/"
		    String afterSlash = segments[segments.length - 1];
		    
		    /*
		      MÉTODO PARA DETERMINAR SI TRATAMOS UNA SUBPÁGINA SIN EXTENSIÓN, ¿OBSOLETO? 
		    
		     boolean subpage=false;
		      
		    for(int i =0; i < afterSlash.length(); i++)
		        if(afterSlash.charAt(i) == '.')
		        {
		        	subpage=false;
		    		break;
		        }
		        else
		        	subpage=true;
		        */
		    
		  //Nos quedamos con el último elemento tras el punto, la extensión
		    String extension = afterSlash.substring(afterSlash.lastIndexOf(".") + 1);
		    
		    //System.out.println(afterSlash);
		    
		    int punts=0;
		    
		    for(int i =0; i < afterSlash.length(); i++)
		        if(afterSlash.charAt(i) == '.')
		            punts++;
		    
		    if ( punts != 1)
		    {
		    	OutputFile = "Page"+cont+".html";
		    }
		    else
		    {
		    	OutputFile = "Page"+cont+"."+extension;
		    }
		    
		    /*Si descomentamos este println observamos como no se puede determinar el orden
		    de creación de los threads: */
		    //System.out.println(OutputFile);

			try {
				URL url1;
				url1 = new URL(url);
				URLConnection connection;
		        InputStream inStream;
				
		        connection=url1.openConnection();
		        
				inStream = connection.getInputStream();
		  
				
					OutputStream outStream=new FileOutputStream(OutputFile);
					
					byte[] buffer=new byte[1];
					

					
					
					while (inStream.read(buffer) != -1)
					{		
					outStream.write(buffer);
					}
					
					

					
					inStream.close();
					outStream.close();
					
					
					if (filtreA)
					{
					BufferedReader contingut = new BufferedReader(new FileReader(OutputFile));	
					if (contingut.readLine() == null) 
					System.out.println("Advertencia: BufferedReader VACÍO");
					Html2AsciiInputStream.filtreA(OutputFile,contingut);
					}
					
					
					if (filtreZ)
					{
						
						
						
					}
					
					if (filtreGZ)
					{
						
					}
					
					
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	        
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

		
	}
	

