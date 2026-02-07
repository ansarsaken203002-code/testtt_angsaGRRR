package services.interfaces;

import models.Admin;
import java.util.List;

public interface AdminServiceInterface {
    Admin login(String username, String password) throws Exception;
    void createAdmin(String username, String password, String role, Admin currentAdmin) throws Exception;
    List<Admin> getAllAdmins() throws Exception;
}