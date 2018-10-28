package com.bolsadeideas.springboot.app.models.service;

import com.bolsadeideas.springboot.app.models.dao.IUsuarioDao;
import com.bolsadeideas.springboot.app.models.entity.Role;
import com.bolsadeideas.springboot.app.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario= usuarioDao.findByUsername(s);

        if(usuario == null){
            logger.info("No existe el usuario: ".concat(s));
            throw new UsernameNotFoundException("Usuario no existe: ".concat(s));
        }
        List<GrantedAuthority> authorityList= new ArrayList<GrantedAuthority>();
        for(Role role: usuario.getRoles()){
            authorityList.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if (authorityList.isEmpty()){
            logger.info("No existe roles asignados  el usuario: ".concat(s));
            throw new UsernameNotFoundException("Usuario no  tiene roles  user:  ".concat(s));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(),true,true,true,authorityList);
    }
}
