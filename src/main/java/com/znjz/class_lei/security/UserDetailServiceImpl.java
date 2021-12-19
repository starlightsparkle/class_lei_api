package com.znjz.class_lei.security;


import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.service.TblUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	TblUserService tblUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		TblUser tblUser = tblUserService.getByUsername(username);
		if (tblUser == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		return new AccountUser(tblUser.getUserId(), tblUser.getUsername(), tblUser.getPassword(), null);
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */
//	public List<GrantedAuthority>  getUserAuthority(Long userId){
//
////		// 角色(ROLE_admin)、菜单操作权限 sys:user:list
//		String authority = sysUserService.getUserAuthorityInfo(userId);  // ROLE_admin,ROLE_normal,sys:user:list,....
//		//将上方获取的权限字符串转化为权限列表返回
//		System.out.println(authority);
//		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
//	}
}
