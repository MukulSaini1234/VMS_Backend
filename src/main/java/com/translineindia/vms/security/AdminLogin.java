//package com.translineindia.vms.security;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class AdminLogin implements UserDetails {
//    private Admin admin;
//
//    public AdminLogin(Admin admin) {
//        this.admin = admin;
//    }
//
//    // Implement UserDetails methods using admin properties
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Return roles/authorities for the admin
//        return Collections.emptyList();
//    }
//
//    @Override
//    public String getPassword() {
//        return admin.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return admin.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return admin.isEnabled();
//    }
//}
