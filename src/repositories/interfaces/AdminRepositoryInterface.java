package repositories.interfaces;

import models.Admin;
import java.util.List;

public interface AdminRepositoryInterface {
    Admin getAdminByCredentials(String username, String password) throws Exception;
    Admin getAdminById(int id) throws Exception;
    void createAdmin(Admin admin) throws Exception;
    List<Admin> getAllAdmins() throws Exception;
}
//каммит не работает