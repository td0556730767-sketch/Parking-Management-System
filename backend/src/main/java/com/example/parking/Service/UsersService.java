package com.example.parking.Service;
import com.example.parking.Entities.Users;
import com.example.parking.Reposetories.UsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class UsersService {

    private final UsersRepo usersRepo;

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

//    public UsersService(UsersRepo usersRepo) {
//        this.usersRepo = usersRepo;
//    }

    // הוספת משתמש חדש
    public Users saveUser(Users user) {
       return usersRepo.save(user);
    }

    public Users addNewUser(Users newUser) {
        // במקרה של משתמש עם טלפון זהה, נוכל לחזור על זה או להחזיר שגיאה
        if (usersRepo.existsById(newUser.getUserId())) {
            throw new RuntimeException("משתמש עם מספר טלפון זה כבר קיים");
        }

        // הוספת כוכבים התחלתיים
        newUser.setStars(50);


        // שמירת המשתמש בבסיס הנתונים
        return usersRepo.save(newUser);
    }
    // קבלת כל המשתמשים
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    // מציאת משתמש לפי ID
    public Optional<Users> getUserById(long userId) {
        return usersRepo.findById(userId);
    }

    // עדכון יתרה כספית (למשל לאחר תשלום)
//    public void updateBalance(long userId, double amount) {
//        usersRepo.findById(userId).ifPresent(user -> {
//            user.setBalance(user.getBalance() + amount);
//            usersRepo.save(user);
//        });
//    }
}