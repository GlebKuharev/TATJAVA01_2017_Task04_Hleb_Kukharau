package catalog.dao.connection;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class ConnectionPool{

	private final static Logger LOG = LogManager.getRootLogger();

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;
	private static ConnectionPool instance;

	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;

	private ConnectionPool() throws ConnectionPoolException{

		this.driverName = DBParameter.DB_DRIVER;
		this.url = DBParameter.DB_URL;
		this.user = DBParameter.DB_USER;
		this.password = DBParameter.DB_PASSWORD;
		this.poolSize = DBParameter.DB_POOL_SIZE;
		this.initPoolData();
	}

	public static ConnectionPool getInstance() throws ConnectionPoolException{
		try{
			if (instance == null){
				instance = new ConnectionPool();
			}
		}catch(ConnectionPoolException e){
			LOG.error("ConnectionPoolException: method getInstance, class ConnectionPool");
			throw e;
		}
		return instance;
	}

	public void initPoolData() throws ConnectionPoolException{

		try{
			Class.forName(driverName);
			givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

			for(int i = 0; i < poolSize; i++){
				Connection conn = DriverManager.getConnection(url, user, password);
				PooledConnection pooledConnection = new PooledConnection(conn);
				connectionQueue.add(pooledConnection);
			}
		}
		catch(SQLException e){
			LOG.error("SQLException: method initPoolData, class ConnectionPool");
			throw new ConnectionPoolException("SQL exception in connection pool", e);
		}
		catch(ClassNotFoundException e){
			LOG.error("ClassNotFoundException: method initPoolData, class ConnectionPool");
			throw new ConnectionPoolException("Can't find database driver class", e);
		}
	}

	public void dispose(){
		clearConnectionQueue();
	}

	private void clearConnectionQueue(){
		try{
			closeConnectionQueue(givenAwayConQueue);
			closeConnectionQueue(connectionQueue);
		}catch(SQLException e){
			LOG.error("SQLException: method clearConnectionQueue, class ConnectionPool");
		}
	}

	public Connection takeConnection() throws ConnectionPoolException{
		Connection conn = null;
		try{
			conn = connectionQueue.take();
			givenAwayConQueue.add(conn);
		}catch(InterruptedException e){
			LOG.error("InterruptedException: method takeConnection, class ConnectionPool");
			throw new ConnectionPoolException("Error connecting to the data source", e);
		}
		return conn;
	}

	public void closeConnection(Connection con, Statement st, ResultSet rs) { 
		try { 
			con.close(); 
		} catch (SQLException e) { 
			LOG.error("Connection isn't returned to the pool.");
		}

		try { 
			rs.close(); 
		} catch (SQLException e) { 
			LOG.error("ResultSet isn't closed."); 
		}

		try { 
			st.close(); 
		} catch (SQLException e) { 
			LOG.error("Statement isn't closed.");
		}
	}

	public void closeConnection(Connection con, Statement st) { 
		try { 
			con.close(); 
		} catch (SQLException e) {
			LOG.error("Connection isn't returned to the pool.");
		}

		try { 
			st.close(); 
		} catch (SQLException e) { 
			LOG.error("Statement isn't closed."); 
		}
	}

	public static void close(PreparedStatement ps){
		try{
			ps.close();
		}catch(SQLException e){
			LOG.error("SQLException: method close, class ConnectionPool");
		}
	}

	public static void close(ResultSet rs){
		try{
			rs.close();
		}catch(SQLException e){
			LOG.error("SQLException: method close, class ConnectionPool");
		}
	}

	public void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException{
		Connection conn;
		while((conn = queue.poll()) != null){
			if(!conn.getAutoCommit()){
				conn.commit();
			}
			((PooledConnection) conn).reallyClose();
		}
	}

	private class PooledConnection implements Connection{

		private Connection connection;

		public PooledConnection(Connection conn) throws SQLException{
			this.connection = conn;
			this.connection.setAutoCommit(true);
		}
		
		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();	
		}

		@Override
		public void close() throws SQLException {
			if (connection.isClosed()){
				throw new SQLException("Closing closed connection!");
			}

			if (connection.isReadOnly()){
				connection.setReadOnly(false);
			}

			if (!givenAwayConQueue.remove(this)) {
				throw new SQLException ("Error deleting connection from the given away connections pool");
			}

			if (!connectionQueue.offer(this)){
				throw new SQLException("Error allocating connection in the pool");
			}
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();	
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback(savepoint);			
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			connection.setClientInfo(properties);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);
		}
	}

}
