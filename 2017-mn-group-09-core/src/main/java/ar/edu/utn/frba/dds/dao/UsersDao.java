package ar.edu.utn.frba.dds.dao;

import ar.edu.utn.frba.dds.models.users.User;

import java.util.List;

/**
 * Created by TATIANA on 21/9/2017.
 */
public class UsersDao extends BaseDao {
    public List<User> list() { return list(User.class); }
    public User exist(User e) { return exist(e.getUsername()); }
    public User exist(String username) { return getByPropertyValue(User.class, "username", username); }
    public User exist(int id){
        return getById(User.class,id);
    }
    public User addIfDontExist(User user) {
        User e2 = exist(user);
        if (e2 != null){
            return e2;
        }else{
            save(user);
        }
        return user;
    }

}
