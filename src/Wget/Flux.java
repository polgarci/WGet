package Wget;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;



 
/**
 * Aquesta classe s'utilitza en cada un dels threads, amb la informació
 * que rep de Wget.java.
 * @author Pol Garcia
 * @author Flavio Jiménez
 */
public class Flux extends Thread {

	private String url;
	private int cont;
	private boolean filtreA;
	private boolean filtreZ;
	private boolean filtreGZ;
	
	/** Aquest és el constructor de la clase, que ens permet generar threads, 
	 * el qual iniciem amb els booleans dels filtres -a, -z i -gz
	 * @param s String que conté la url que ens descarregarem i de la qual obtindrem el nom del fitxer.
	 * @param i Comptador de fitxers que ajuda, , a diferenciar els fitxers anomenats de la
	 * mateixa forma.principalment
	 * @param a Booleà del filtre -a
	 * @param z Booleà del filtre -z
	 * @param gz Booleà del filtre -gz**/
	public Flux(String s, int i, boolean a, boolean z, boolean gz) 
	{
		url=s;
		cont=i;
		filtreA=a;
		filtreZ=z;
		filtreGZ=gz;
		
		
	}
	
	/** El filtre -gz només s'executa individualment quan està activat -gz i no ho està
	 *  -z, independentment de si s'ha activat el filtre -a. La funcio que duu a terme
     * aquest procés és la creació d'un arxiu .gz i el seu guardat dins el fitxer
	 * comprimit amb el codi font de la pàgina. Aquest arxiu sera .asc si a més
	 *  s'ha aplicat el filtre -a. 
	 *  @param OutputZip String que conté el nom del fitxer, que serà diferent cas que s'hagi aplicat
	 *  el filtre -a. **/
	public static void filtreGZ(String OutputZip) 
	{   
		FileOutputStream FileGZipOutput;
		try {
			FileGZipOutput = new FileOutputStream(OutputZip+".gz"); // Creeem el nou fitxer amb extensió .gz
			GZIPOutputStream GzipFile= new GZIPOutputStream(FileGZipOutput);
		
		    FileInputStream auxi= new FileInputStream(OutputZip);
		    
		    byte[]by=new byte[1];
		    while((auxi.read(by))>0)  // Costruim el bucle per tal de escriure el contingut del fitxer descarregat de la url, amb el codi font corresponent
		    {GzipFile.write(by);}
		    // Un cop em escrit dins del nou fitxer tota la informacio necesaria, ens disponem a tancar-ho
		    GzipFile.flush();
		    GzipFile.finish();

		    
			auxi.close();
			   
			GzipFile.close();
			FileGZipOutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("\nAdvertencia: La dirección url introducida \nno existe o es inaccesible.");	
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}



	/** Aquesta funció aplica el filtre -z per crear un nou fitxer .zip, 
	 * però si també el filtre -gz està activat s'encarregarà de crear un fitxer .zip.gz
	 * i fer la doble compressio.
	 * @param nomZip És el string que conté el nom del fitxer original per donar-li nom al nou .zip (i al nou .gz).
	 * @param OutputFile És el string que conté el nom del fitxer per comprimir en .zip (i .gz).
     * @param contingut És el contingut del fitxer indicat per OutputFile.
     * @param filtreGZ Booleà que indica si el filtre -gz s'ha d'aplicar a més del -z o no.**/
	public static void filtreZ(String nomZip,String OutputFile,BufferedReader contingut, boolean filtreGZ) 
	{
		try {
			
			
			
			File tmp =  new File(nomZip+".zip"); // Generem un nou fitxer amb extensio .zip, per duu a terme la compressió del fixter amb el codi font. 
			//@SuppressWarnings("resource")
			FileOutputStream fileOutStreamZ = new FileOutputStream(tmp);
			
			ZipOutputStream outStreamZ = new ZipOutputStream(fileOutStreamZ);
			
			
			
			
			
			ZipEntry ze=new ZipEntry(OutputFile);
			outStreamZ.putNextEntry(ze);
		    FileInputStream inStreamZ= new FileInputStream(OutputFile);
		    
		    byte[] byteZ=new byte[1];
		    byte[] byteGZ=new byte[1];
		    
		    while((inStreamZ.read(byteZ))>0) // Un cop creada la nova entrada per generar un zip, construim aquest bucle per anar escribin byte a byte en el fitxer .zip
			{
				
		    	outStreamZ.write(byteZ,0,1);
				
				
			}
		    
  //Despres d'haber escrit el fitxer amb el codi font de la url, ensa segurem de tancar el tots els OutputStream i InputStreams utilitzats per tal de que el fitxer no quedi danyat 
		    outStreamZ.flush();
		    outStreamZ.closeEntry();				

            
		    inStreamZ.close();
			
		    outStreamZ.finish();
		    outStreamZ.close();
		    fileOutStreamZ.close();
		    
		    
		    // En cas de que el filtre -gz hagi sigut activat, otrnem a repetir el proces de crear un fitxer i escriure dins el contigut. En aquest cas el fitxer sera de tipus .gz i el que escriurem sera la informacio del .zip
		    if(filtreGZ)
			{
		    	
		    	
		    	FileOutputStream fileOutStreamGZ = new FileOutputStream(nomZip+".zip.gz");
			    GZIPOutputStream outStreamGZ = new GZIPOutputStream(fileOutStreamGZ);
			    FileInputStream inStreamGZ= new FileInputStream(tmp);
			    
			    while((inStreamGZ.read(byteGZ))>0) 
			    {outStreamGZ.write(byteGZ);}
			    
			    
			    // Un cop em escrit tot, ens assegurem de tarcar tots els OutputStream i InputStreams anteriorment creats 
			    outStreamGZ.flush();
			    outStreamGZ.finish();

			    
			    inStreamGZ.close();
				   
			    outStreamGZ.close();
			    fileOutStreamGZ.close();
			    // Eliminem el fitxer .zip anteriorment creat perque ja no ens fa falta tenint el nou fitxer .zip.gz
			    tmp.delete();
			}
			
			
		}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
			System.out.println("\nAdvertencia: La dirección url introducida\nno existe o es inaccesible.");	
				//e.printStackTrace();
			}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
			
		}
		
			
		    
		  
	
		    
	
	/** Aquesta funció és l'encarregada d'iniciar el thread i per tant també de segmentar
	 *  la url, i executar els diferents filtres que es volen aplicar per l'usuari:
	 *  tant -a, com -z, com -gz.
	 *  
	 *   Hem establert un missatge d'advertencia pels casos als que es tracti d'aplicar
	 *	 el filtre -a a una imatge.**/
	public void run() {
		
		
		System.out.println(url);
			

		    final String OutputFile;
		    
		    // Spliteamos la url en segmentos
		    String segments[] = url.split("/");
		    
		    // Extraemos el último segmento después de la última "/"
		    String afterSlash = segments[segments.length - 1];
		    
		  
		    
		    //Nos quedamos con el último elemento tras el punto, la extensión
		    String extension = afterSlash.substring(afterSlash.lastIndexOf(".") + 1);
		    
		    
		
		    
		    int punts=0;
		    File auxFileAscii = null;
		    for(int i =0; i < afterSlash.length(); i++)
		        if(afterSlash.charAt(i) == '.')
		            punts++;
		    
		    String nom = null;
		    
		    if ( punts != 1)
		    {
		    	cont++;
		    	nom = afterSlash.split("\\.")[1];
		    	OutputFile = "index"+cont+".html";
		    }
		    else
		    {
		    	cont++;
		    	nom = afterSlash.split("\\.")[0];
		    	OutputFile = nom+cont+"."+extension;
		    }
		    

			try {
				
				// Creacio de la variable URL
				URL urlCon;
				urlCon = new URL(url);
				
				
				
				URLConnection connection;
		       
				
		        connection=urlCon.openConnection();
		        InputStream inStream=connection.getInputStream();
		        OutputStream outStream;
		        if (connection.getContentType() != null)
		        {
		          try {
		        		inStream = connection.getInputStream();
		        		 if(filtreA==true)
		        		 {
		        			 inStream=new Html2AsciiInputStream(inStream);
		        			 //falte el nom per al ascii
		        		 }
				
					     String NomZip = OutputFile;
					     
					     if(filtreZ!=true && filtreGZ!=true)
					     {
					        outStream = new FileOutputStream(OutputFile); 
					    	 
					     }
					     else {
					    	 
					    	 if(filtreZ ==true && filtreGZ ==true)
					    	 {
					    		OutputFile=OutputFile+".zip.gz"; 
					    	    FileOutputStream tmpF=new FileOutputStream(OutputFile); 	 
					    		GZIPOutputStream tmpGZ= new GZIPOutputStream(tmpF);
					    		outStream= tmpGZ;
					    		ZipEntry EntradaZip = new ZipEntry(NomZip);
                                ((ZipOutputStream) outStream).putNextEntry(EntradaZip);			
					    		 
					    	 }
					    	 else { 
					    		    if(filtreZ==true && filtreGZ!=true)
					    		    {
					    		    	OutputFile= OutputFile +".zip";
					    		    	outStream= new ZipOutputStream(new FileOutputStream(OutputFile));
					    		    	ZipEntry Entrada =new ZipEntry(NomZip);
					    		    	((ZipOutputStream) outStream).putNextEntry(Entrada);				
					    		    	
					    		    }
					    		    else 
					    		    {
					    		     OutputFile=OutputFile +".gz";
					    		     outStream=new GZIPOutputStream(new FileOutputStream(OutputFile));
					    		    	
					    		    }
					    	 
					    	     }
					     }
					
					     int bytes;
					     
					     while ((bytes=inStream.read())!= -1)
					     {
					    	 
					    	 outStream.write(bytes);
					    	 
					     }
					     inStream.close();
					     outStream.close();
					
					
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
	}
	
