package services;

import models.Admin;
import repositories.interfaces.AdminRepositoryInterface;
import services.interfaces.AdminServiceInterface;
import utils.Validator;
import java.util.List;

public class AdminService implements AdminServiceInterface {
    private AdminRepositoryInterface repo;

    public AdminService(AdminRepositoryInterface repo) {
        this.repo = repo;
    }

    public Admin login(String username, String password) throws Exception {
        return repo.getAdminByCredentials(username, password);
    }

    public void createAdmin(String username, String password, String role, Admin currentAdmin) throws Exception {
        if (!Validator.isValidUsername(username)) {
            throw new Exception("Username must be 3-50 characters and contain only letters, numbers, and underscores");
        }

        if (!Validator.isValidPassword(password)) {
            throw new Exception("Password must be at least 4 characters");
        }

        if (!currentAdmin.getRole().equals(role)) {
            throw new Exception("You can only create admins of your own role type (" + currentAdmin.getRole() + ")");
        }

        Admin newAdmin = new Admin(username, password, role);
        repo.createAdmin(newAdmin);
    }

    public List<Admin> getAllAdmins() throws Exception {
        return repo.getAllAdmins();
    }
}