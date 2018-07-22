package sjtusummerproject.usermicroservice.Service;

import org.springframework.web.bind.annotation.RequestParam;
import sjtusummerproject.usermicroservice.DataModel.Domain.UserEntity;

public interface ManageUserService {
    public UserEntity QueryUserOption(String username);
    public UserEntity QueryUserByIdOption(Long id);
    public UserEntity AddUserOption(String username, String password, String email, String status);
    public boolean UpdateUserOldPassword(Long id, String oldPassword, String newPassword);
    public void DeleteUserOption(String username);
    public void UpdateUserStatusOption(String username, String status);
    public void UpdateUserPasswordOption(String username, String password);
    public void UpdateUserEmailOption(String username,String email);
    public void UpdateUserAuthorityOption(String username,String authority);
}