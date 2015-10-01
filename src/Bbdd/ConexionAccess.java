package Bbdd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 


import javax.swing.JOptionPane;
 
/** Acciones a realizar una vez el programa est� operativo:
* 1.- En Variable global archivo, cambiar extension de "accdb" a "cos"
* 2.- En Metodo crearBBDD() activar las 3 l�neas para crear el arhicov access en modo oculto
* 
* N.- Borrar estas l�neas de la clase ConexionBBDD
*/

public class ConexionAccess {

	public Connection conexion;
    public Statement stmt;
     
    private java.io.File directorio = new java.io.File( //directorio de destino
            System.getenv("SYSTEMDRIVE")+java.io.File.separator+"LaGestion");
    private java.io.File archivo = new java.io.File( //archivo de destino con ruta directorio
            directorio+java.io.File.separator+"LaGestion.accdb");
     
/**
* Construye la base de datos vacia Si y s�lo si esta no existe previamente
* en la ubicaci�n predeterminada del programa.
*/
    public ConexionAccess(){
        if(!directorio.exists()){ //si el directorio no existe
            if(!directorio.mkdirs()) //si no se puede crear el directorio lo avisa
                JOptionPane.showMessageDialog(null,
                        "No se ha podido crear el directorio."
                                + "\nEl directorio ya existe.",
                                "Operaci�n no permitida.", JOptionPane.ERROR_MESSAGE);
                this.crearBBDD(); //Si si se puede crear el directorio crea el archivo
            }else if(!archivo.exists())//Si el archivo no existe lo copia y lo prepara
                this.crearBBDD();
    }
     
    private void crearBBDD(){
        try {
            java.sql.DriverManager.getConnection("jdbc:ucanaccess://"
                    + archivo.getAbsolutePath()+";newdatabaseversion=V2010");
//          Runtime.getRuntime().exec("attrib +H "+archivo.getAbsolutePath());
//      }   catch (IOException e){
//          JOptionPane.showMessageDialog(null, "crearBBDD() IOException =>"+e.getMessage(),"Operaci�n no permitida.", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "crearBBDD() SQLException =>"+e.getMessage(),"Operaci�n no permitida.", JOptionPane.ERROR_MESSAGE);
        }
        this.creaTablasYRelaciones();
    }
     
    private void creaTablasYRelaciones(){
        if (this.conectar()){
            try {
                stmt=conexion.createStatement();
                stmt.execute("CREATE TABLE Departamentos "
                		+ "(Id_Departamento INTEGER PRIMARY KEY,Nombre TEXT(30),Direccion TEXT(255),"
                		+ "Localidad TEXT(50), Codigo_Postal INTEGER, Descripcion TEXT(255))"); 
                stmt.execute("CREATE TABLE Empleados (Id_Empleado INTEGER PRIMARY KEY,Nombre TEXT(15),"
                		+ "Apellido1 TEXT(20),Apellido2 TEXT(20),DNI TEXT(10),Fecha_Ingreso TEXT(10),"
                		+ "Fecha_Despido TEXT(10),Salario DOUBLE,Comision DOUBLE,Objetivo BOOLEAN,"
                		+ "Password TEXT(32),Id_Departamento INTEGER, "
                		+ "CONSTRAINT FK_Empleados1 FOREIGN KEY (Id_Departamento) "
                		+ "REFERENCES Departamentos(Id_Departamento) ON DELETE CASCADE ON UPDATE CASCADE)");
                stmt.execute("CREATE TABLE Proveedores(Id_Proveedor INTEGER PRIMARY KEY,Razon_Social TEXT(50),"
                		+ "NIF TEXT(9),Direccion TEXT(255),Telefono TEXT(9),Fax TEXT(9),Email TEXT(100))");
                stmt.execute("CREATE TABLE Productos(Id_Producto INTEGER,Partida_Compra INTEGER,"
                		+ "Id_Empleado INTEGER,Id_Proveedor INTEGER,Familia TEXT(50),SubFamilia TEXT(50),"
                		+ "Marca TEXT(30),Modelo TEXT(255),Fecha_Compra TEXT(10),Precio_Compra_Ud DOUBLE,"
                		+ "Unidades INTEGER,CONSTRAINT PK_Productos PRIMARY KEY (Id_Producto,Partida_Compra))");
                stmt.execute("CREATE TABLE Clientes(Id_Cliente INTEGER PRIMARY KEY,Nombre_Razon_Social TEXT(50),"
                		+ "DNI_NIF TEXT(9),Direccion TEXT(255),Telefono TEXT(9),Fax TEXT(9),Email TEXT(100))");
                stmt.execute("CREATE TABLE Almacen(Id_Producto INTEGER PRIMARY KEY,Marca TEXT(50),Modelo TEXT(50),"
                		+ "Stock INTEGER, Id_Proveedor INTEGER, Fecha_Recepcion TEXT(10),"
                		+ "Albaran INTEGER)");
                stmt.execute("CREATE TABLE Pedidos(Id_Pedido INTEGER PRIMARY KEY,Id_Empleado INTEGER,"
                		+ "Id_Cliente INTEGER,Id_Producto INTEGER,Unidades INTEGER,Precio_Venta_Ud DOUBLE,"
                		+ "Fecha_Pedido TEXT(10),Fecha_Entrega TEXT(10))");
                stmt.execute("CREATE TABLE Facturas(Id_Factura INTEGER PRIMARY KEY,Id_Pedido INTEGER,"
                		+ "Fecha_Factura TEXT(10),Tipo_Pago TEXT(20),IVA INTEGER,Descuento INTEGER)");
                stmt.execute("CREATE TABLE Transporte(Id_Transporte INTEGER PRIMARY KEY,Id_Cliente INTEGER,"
                		+ "Id_Producto INTEGER,Tipo_Viaje TEXT(20),Fecha_Entrega TEXT(10),Fecha_Recogida TEXT(10))");
                stmt.execute("CREATE TABLE Reparaciones(Id_Reparacion INTEGER PRIMARY KEY,Id_Cliente INTEGER,"
                		+ "Id_Producto INTEGER,Id_Factura INTEGER,Descripcion_Averia TEXT(50),"
                		+ "Garantia BOOLEAN,Fecha_Entrada TEXT(10),Fecha_Salida TEXT(10),Estado TEXT(50))");
//Alter Table no soportado a�n   stmt.execute("ALTER TABLE Empleados ADD CONSTRAINT FK_Empleados FOREIGN KEY (Id_Departamento) REFERENCES Departamentos (Id_Departamento) ON DELETE CASCADE ON UPDATE ");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "creaTablasYRelaciones() SQLException=> "+e.getMessage(),"Operaci�n no permitida.", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.desconectar();
    }
 
	/**
	* Comprueba si se conecta con la base de datos.
	* @return {@link true}: si y solo si la conexion es activa.
	 */
    public boolean conectar(){
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            conexion=java.sql.DriverManager.getConnection("jdbc:ucanaccess://"+archivo.getAbsolutePath());
            return true;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "conexion() ClassNotFoundException=> "+e.getMessage(),"Operaci�n no permitida.", JOptionPane.ERROR_MESSAGE);
        }   catch (SQLException e){
            JOptionPane.showMessageDialog(null, "conexion() SQLException=> "+e.getMessage(),"Operaci�n no permitida.", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
     
	/**
	* Comprueba el cierre de la conexi�n con la base de datos.
	* @return {@link true}: si y solo si se cierra la conexion.
	*/
    public boolean desconectar(){
        try {
            stmt.close();
            conexion.close();
            System.gc();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "cerrarConexion() SQLException=> "+e.getMessage(),"Operaci�n no permitida.", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public ResultSet selectQuery(String sql){
		try {
			stmt=conexion.createStatement();
			ResultSet rs = this.stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, this.getClass().getSimpleName() 
					+ ".sendQuery("+sql+")\n" + e.getMessage());
		}
		return null;
	}
    
    public int execQuery(String sql){
		try {
			stmt=conexion.createStatement();
			int rs = this.stmt.executeUpdate(sql);
			return rs;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, this.getClass().getSimpleName() 
					+ ".execQuery("+sql+")\n" + e.getMessage());
		}
		return 0;
	}
}
