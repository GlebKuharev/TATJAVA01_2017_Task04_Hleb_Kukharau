package catalog.dao.impl;

import catalog.dao.NewsDAO;

public final class DAOFactory {
	private static final DAOFactory instance = new DAOFactory(); 

	private final NewsDAO fileNewsImpl = new FileNewsDAO(); 
	
	private DAOFactory(){} 
	
	public static DAOFactory getInstance(){ 
		return instance;
	}
	public NewsDAO getNewsDAO(){ 
		return fileNewsImpl;
	}

}
