package catalog.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import catalog.bean.News;
import catalog.dao.NewsDAO;
import catalog.dao.exception.DAOException;

public class FileNewsDAO implements NewsDAO {

	@Override
	public String addNews(News news) throws DAOException {
		String csvNews = "\n" + news.getCategory() + "," + news.getName() + "," + news.getReleaseDate() + "," + news.getDescription();
		FileWriter fw = null;
		String response = null;
		try {
			fw = new FileWriter("catalog.csv", true);
			fw.append(csvNews); 
			fw.close();
			response = "Item added to catalog";
		} catch (IOException e) {
			response = "error in addNews DAO, item not added";
			throw new DAOException(e);
		}
		return response;
	}

	@Override
	public String searchNews(String keyword) throws DAOException {
		BufferedReader input;
		String response = null;
		try {
			input = new BufferedReader(new FileReader("catalog.csv"));
			String line = null;
			while (( line = input.readLine()) != null) {
				if (line.toLowerCase().contains(keyword.toLowerCase())) {
					response = response + line + "\n"; 
				}
			}
			input.close(); 
			System.out.println("Searching done");
		} catch (IOException e) {
			throw new DAOException(e);
		} 
		return response;
	}

	@Override
	public ArrayList<News> searchNewsByName(String name) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<News> searchNewsByCategory(String category) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<News> searchNewsByYear(int year) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
