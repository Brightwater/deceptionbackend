package com.brightwaters.deception.svc;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

import com.brightwaters.deception.model.postgres.DeceptionUser;
import com.brightwaters.deception.repository.postgres.AppUserRepository;

@Service
public class UserDetailsSrv implements UserDetailsService {
    private AppUserRepository userRepos;

    public UserDetailsSrv(AppUserRepository userRepos) {
        this.userRepos = userRepos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DeceptionUser user = userRepos.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}
