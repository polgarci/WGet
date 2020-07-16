package Wget;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

 
/**
 * Aquesta classe té la funcio d'aplicar a l'InputStream el filtre dels tags i, per tant,
 * treure tot alló que estigui entre els tags d'un arxiu .html o similar.
 * @author Pol Garcia
 * @author Flavio Jiménez
 */
public class Html2AsciiInputStream extends FilterInputStream {


		 //static BufferedReader contingut;
		//System.out.println(contingut);
		
		static String line;
		FileWriter fw;
		int contingut;
		boolean tag;
		/** Constructor de la clase Html2AsciiInputStream, la cual necesita una variable InputStream per ser inicialitzada. **/
		protected Html2AsciiInputStream(InputStream in) 
		{

			super(in);
			tag=false;
			
		}
		
		/** Aquesta funció ens aplica el filtre -a, per tant ens permet extreure tots els
		 *  tags html del codi font de la pàgina, els cuals es troben entre els caracters
		 *  "menor que" i "major que"
		 *  @param OutputFile És el string que conté el nom del fitxer per treure les etiquetes.
		 *  @param contingut És el contingut del fitxer indicat per OutputFile.
		 *  @return Retornem 0 per execucions correctes i -1 per execucions amb IOExceptions...**/
		public int read() 
		{

         try {
				 contingut=this.in.read(); 
				 
			/*
			    /*	FileWriter fw = new FileWriter(OutputFile);*/
				boolean tag=false;
				char[] s= new char[1];
				 
			//	while(contingut.read(s)>0)
				//{
				   
					
					//int ascii = (int) s[0];
					if( contingut == 60  && tag==false)
					{
						//fw.write("/n");
						contingut=0;
						tag=true;
					}  
					else {
						if(contingut == 62 && tag == true)
						{
							
							tag=false;
						}
						
					///	else {
							 
						//	 if(tag==false)
									//fw.write(s);
						
					//ç77	}
							}
				//	}
				
				//fw.flush();
				//fw.close();
				//contingut.close();
				
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}
         return contingut;
		}
		
}
		
		
	
			
			
			
			
			
		
		
	
			
		
		





