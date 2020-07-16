package Wget;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;

public class Html2AsciiInputStream extends FilterInputStream {


		 //static BufferedReader contingut;
		//System.out.println(contingut);
		
		static String line;
		FileWriter fw;
		
		
		
		protected Html2AsciiInputStream(String OutputFile, InputStream in)
		{

			super(in);
			
		}
		
		public int read()
		{
			int c=0;
			return c;
		}
		
		public static void filtreA(String OutputFile, BufferedReader contingut) //NO SE HA DE APLICAR A PNGS MIRAR CÓMO SE HACE
		{
		
			try {
				 
				
				FileWriter fw = new FileWriter(OutputFile);
				
				
				while ((line=contingut.readLine())!=null)
				{	
					if (line==null)
						System.out.println("PROTASIA MADRE");
					//System.out.println('\n');
					//System.out.println(line);	
					//fw.write('\n');
					String aux=line.replaceAll("\\<.*?>","kappa"); 
					//System.out.println(line);
					line=aux;
					fw.write(aux);	
					
					

				}
				fw.flush();
				fw.close();
				contingut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		public static void filtreZ(String OutputFile, BufferedReader contingut, OutputStream sile )
		{
			ZipEntry e;
		}
		
		public static void filtreGZ(String OutputFile, BufferedReader contingut)
		{
			
		}
	
	
}

