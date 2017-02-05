package catalog.service.factory;

import catalog.service.CatalogService;
import catalog.service.impl.CatalogServiceImpl;

public final class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory(); 

	private final CatalogService catalogService = new CatalogServiceImpl();

	private ServiceFactory(){

	} 

	public static ServiceFactory getInstance(){ 
		return instance;
	}

	public CatalogService getCatalogService(){ 
		return catalogService;
	}

}
