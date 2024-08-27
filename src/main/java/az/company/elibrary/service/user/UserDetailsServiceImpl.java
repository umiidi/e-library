package az.company.elibrary.service.user;

import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CommonGetterService getterService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getterService.getUser(username); // username is email
    }

}
