package com.translineindia.vms.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.translineindia.vms.entity.Login;
import com.translineindia.vms.security.UserPrincipal;

public class AuthUtils {
	public static Login getCurrentVisitor() {
		return ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLogin();
	}
}
