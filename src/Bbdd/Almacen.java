package Bbdd;

/**
 * Identificador, partida de compra, stock libre, stock reservado
 * @author Marco Ant. Quirós
 * @version 1.1 */
public class Almacen extends Actualizador{
	
	int id, stock, idProveedor, albaran;
	String modelo, marca, fecha;

	/**
	 * Constructor nulo para usar las querys de insercion de datos */
	public Almacen(){}
	/**
	 * Instancia el objeto con los siguientes parametros
	 * @param id
	 * @param partidaCompra
	 * @param stockLibre
	 * @param stockReser */
	public Almacen(int id, String marca, String modelo, int stock, int proveedor,
			String fecha, int albaran) {
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.fecha = fecha;
		this.idProveedor = proveedor;
		this.albaran = albaran;
		this.stock = stock;
	}
	
	/**
	 * Inserta un nuevo registro en la tabla Almacen
	 * @param id
	 * @param partidaCompra
	 * @param stockLibre
	 * @param stockReser
	 * @return boolean */
	public boolean insertar(int id, String marca, String modelo, int stock, int proveedor,
			String fecha, int albaran){
		String query = "INSERT INTO Almacen (Id_Producto, Marca, Modelo, Stock, Id_Proveedor, Fecha_Recepcion, Albaran) "
				+ "VALUES ("+id+", '"+marca+"', '"+modelo+"', "+stock+", "+proveedor+", '"+fecha+"', "+albaran+")";
		if(updateQuery(query)){
			return true;
		}
		return false;
	}
	
	/**
	 * Actualiza un registro en la BBDD
	 * @return boolean */
	public boolean actualizar(){
		String query = "UPDATE Almacen SET Marca = "+this.getMarca()+", Modelo = "+this.getModelo()+", Stock = "+this.getStock()+", Id_Proveedor = "+this.getIdProveedor()+", Fecha_Recepcion = '"+this.getFechaEntrada()+"', Albaran = "+this.getAlbaran()+"  WHERE Id_Producto = "+this.getId();
		if(updateQuery(query)){
			return true;
		}
		return false;
	}
	
	/**
	 * Actualiza un registro en la BBDD pasado por parametro
	 * @param a <code>{@link #Almacen}</code>
	 * @return boolean */
	public boolean actualizar(Almacen a){
		String query = "UPDATE Almacen SET Marca = "+a.getMarca()+", Modelo = "+a.getModelo()+", Stock = "+a.getStock()+", Id_Proveedor = "+a.getIdProveedor()+", Fecha_Recepcion = '"+a.getFechaEntrada()+"', Albaran = "+a.getAlbaran()+"  WHERE Id_Producto = "+a.getId();
		if(updateQuery(query)){
			return true;
		}
		return false;
	}
	
	/**
	 * Elimina un registro de la BBDD
	 * @return boolean */
	public boolean eliminar(){
		String query = "DELETE FROM Almacen WHERE Id_Producto = "+this.getId();
		if(updateQuery(query)){
			return true;
		}
		return false;
	}

	/**
	 * @return the id */
	public int getId() {
		return id;
	}

	/**
	 * @return the partidaCompra */
	public String getMarca() {
		return marca;
	}

	/**
	 * @return the stockLibre */
	public int getStock() {
		return stock;
	}

	/**
	 * @return the stockReser */
	public String getModelo() {
		return modelo;
	}
	
	//entrada, proveedor, fecha, albaran
	public String getFechaEntrada(){
		return fecha.substring(0,10);
	}
	
	public int getIdProveedor(){
		return this.idProveedor;
	}
	
	public int getAlbaran(){
		return this.albaran;
	}

	/**
	 * @param id the id to set */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param partidaCompra <code>el parametro a setear</code>
	 * @param update <code>boolean</code> TRUE para actualizar la BBDD */
	public void setMarca(String marca, boolean update) {
		this.marca = marca;
		if(update){
			String query = "UPDATE Almacen SET Marca = '" + marca + "' WHERE Id_Producto = " + this.id;
			updateQuery(query);
		}
	}

	/**
	 * @param stockLibre <code>el parametro a setear</code>
	 * @param update <code>boolean</code> TRUE para actualizar la BBDD */
	public void setStock(int entrada, boolean update) {
		this.stock += entrada;
		if(update){
			String query = "UPDATE Almacen SET Stock = (" + this.stock + "+"+entrada+") WHERE Id_Producto = " + this.id;
			updateQuery(query);
		}
	}

	/**
	 * @param stockReser <code>el parametro a setear</code>
	 * @param update <code>boolean</code> TRUE para actualizar la BBDD */
	public void setModelo(String modelo, boolean update) {
		this.modelo = modelo;
		if(update){
			String query = "UPDATE Almacen SET Modelo = '" + modelo + "' WHERE Id_Producto = " + this.id;
			updateQuery(query);
		}
	}
	
}
