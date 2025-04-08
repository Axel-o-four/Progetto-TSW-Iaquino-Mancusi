package it.unisa;

import java.sql.SQLException;
import java.util.Collection;

public interface ProductModel {
	public void doSave(ProductBean product) throws SQLException;

	public boolean doDelete(int code, String prefissoID) throws SQLException;

	public ProductBean doRetrieveByKey(int code, String prefissoID) throws SQLException;
	
	public Collection<ProductBean> doRetrieveAll(String order) throws SQLException;
}
