package com.adjudicat.helper;

import com.adjudicat.domain.model.security.AdjUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthHelper implements UserDetailsService {

    public static final String FAKE_JWT = "eyJhbGciOiJIUzUxMiJ9"
            + ".eyJpc3MiOiJnZXN0b3AiLCJzdWIiOiJ3cmV3ciIsInVzZXJuYW1lIjoid3Jld3IiLCJhdXRob3JpdGllcyI6IlJPTEVfQURNIiwiem9u"
            + "ZUNvZGUiOiJDQVRCQUwiLCJ6b25lIjoiQ0FUQkFMIiwidXNlcklkIjoiMSIsImZ1bGxOYW1lIjoiRGVidWcgVXNlciIsImVtYWlsIjoi"
            + "ZGVidWdAc2Vuc2VkaS5jb20iLCJ1c3VhcmlvQ29uZmlndXJhZG9yIjp0cnVlLCJpYXQiOjE2MTc3OTI5ODUsImV4cCI6MTYxNzc5NDQ4NX0"
            + ".m4XMdA1AQ9Y1q4s_tWwbrxVeSh9NQazP33ZfybdRtlKbHbWIpf43L3vJjwDyrWaoiAcw1NNimtF8unDU3bEy2Q";

    public static final String FAKE_JWT_ZONE_2 = "eyJhbGciOiJIUzUxMiJ9"
            + ".eyJpc3MiOiJnZXN0b3AiLCJzdWIiOiJ3cmV3ciIsInVzZXJuYW1lIjoid3Jld3IiLCJhdXRob3JpdGllcyI6IlJPTEVfQURNIiwiem9u"
            + "ZUNvZGUiOiJSRVNQIiwiem9uZSI6IlJFU1AiLCJ1c2VySWQiOiIxIiwiZnVsbE5hbWUiOiJEZWJ1ZyBVc2VyIiwiZW1haWwiOiJkZWJ1Z"
            + "0BzZW5zZWRpLmNvbSIsInVzdWFyaW9Db25maWd1cmFkb3IiOnRydWUsImlhdCI6MTYxNzc5Mjk4NSwiZXhwIjoxNjE3Nzk0NDg1fQ"
            + ".m4XMdA1AQ9Y1q4s_tWwbrxVeSh9NQazP33ZfybdRtlKbHbWIpf43L3vJjwDyrWaoiAcw1NNimtF8unDU3bEy2Q";

    public static final String FAKE_JWT_ZONE_3 = "eyJhbGciOiJIUzUxMiJ9"
            + ".eyJpc3MiOiJnZXN0b3AiLCJzdWIiOiJ3cmV3ciIsInVzZXJuYW1lIjoid3Jld3IiLCJhdXRob3JpdGllcyI6IlJPTEVfQURNIiwiem9u"
            + "ZUNvZGUiOiJUT0RBUyIsInpvbmUiOiJUT0RBUyIsInVzZXJJZCI6IjEiLCJmdWxsTmFtZSI6IkRlYnVnIFVzZXIiLCJlbWFpbCI6ImRlY"
            + "nVnQHNlbnNlZGkuY29tIiwidXN1YXJpb0NvbmZpZ3VyYWRvciI6dHJ1ZSwiaWF0IjoxNjE3NzkyOTg1LCJleHAiOjE2MTc3OTQ0ODV9"
            + ".m4XMdA1AQ9Y1q4s_tWwbrxVeSh9NQazP33ZfybdRtlKbHbWIpf43L3vJjwDyrWaoiAcw1NNimtF8unDU3bEy2Q";

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        switch (username) {
            case "test":
                return loadUserByUsername_ZONE1(username);
            case "test2":
                return loadUserByUsername_ZONE2(username);
            case "test3":
                return loadUserByUsername_ZONE_3(username);
            default:
                throw new UsernameNotFoundException("User not found");
        }


    }

    public UserDetails loadUserByUsername_ZONE1(final String username) throws UsernameNotFoundException {
        return AdjUser.builder()
                .username("test")
                .jwtToken(AuthHelper.FAKE_JWT)
                .build();
    }

    public UserDetails loadUserByUsername_ZONE2(final String username) throws UsernameNotFoundException {
        return AdjUser.builder()
                .username("test")
                .jwtToken(AuthHelper.FAKE_JWT_ZONE_2)
                .build();
    }

    public UserDetails loadUserByUsername_ZONE_3(final String username) throws UsernameNotFoundException {
        return AdjUser.builder()
                .username("test")
                .jwtToken(AuthHelper.FAKE_JWT_ZONE_3)
                .build();
    }
}
