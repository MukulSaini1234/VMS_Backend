package com.translineindia.vms.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.translineindia.vms.entity.Visitor;
import com.translineindia.vms.security.VisitorLogin;

public class AuthUtils {
	public static Visitor getCurrentVisitor() {
		return ((VisitorLogin)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getVisitor();
	}
}
