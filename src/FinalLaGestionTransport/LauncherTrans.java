package FinalLaGestionTransport;

import javax.swing.UIManager;


import Bbdd.Disparador;
import Bbdd.Tablas;

public class LauncherTrans {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		 try{
             
	            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); //ponemos lookandfeel
	      
	            }catch(Exception e){
	              e.printStackTrace();
	            } 
	     	
		 
		 	//Crea el Contador
		 	Disparador id = new Disparador("idTransporte");
	     	
		 	//Crea Las tablas
		 	Tablas bbdd = new Tablas(); //instancia una clase con los registros de todas las tablas
		 	
		 	
		 	//Crea el Excel
		 	CrearExcel crearExel = new CrearExcel();

		 	
		 	//Lanaza la APP
		 	InterfaceTrans appTrans =new InterfaceTrans();
	        appTrans.setVisible(true);

	}

}
