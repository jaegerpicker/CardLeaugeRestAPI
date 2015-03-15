package com.springapp.mvc.repositories;
import com.springapp.mvc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shawn on 3/11/15.
 */

public interface UserRepository extends JpaRepository<User, Long> {

}
