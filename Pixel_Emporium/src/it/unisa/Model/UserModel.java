package it.unisa.Model;

import java.sql.SQLException;
import java.util.Collection;

public interface UserModel {
    public void doSave(UserBean user) throws SQLException;
    
    public boolean doDelete(String email) throws SQLException;
    
    public UserBean doRetrieveByKey(String email) throws SQLException;
    
    public Collection<UserBean> doRetrieveAll(String order) throws SQLException;
    
    public boolean updateUser(UserBean user) throws SQLException;
    
    public boolean emailExists(String email) throws SQLException;
}
