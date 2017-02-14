package catalog.dao.connection;

public final class DBParameter{
	
	private DBParameter(){}
	
	public static final String DB_DRIVER = "org.gjt.mm.mysql.Driver";
	public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/catalog";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "1234";
	public static final int DB_POOL_SIZE = 5;
}