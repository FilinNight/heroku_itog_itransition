package com.example.collectionsApp.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.*;


@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String username;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 2, message = "Password should be between 2 and 30 characters")
    private String password;
    private Date dataRegistration;
    private String activationCode;
    private String url;
    private Date lastLoginDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CollectionItem> collectionItems;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {return true;}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public void saveLastLoginDate(){
        setLastLoginDate(new Date());
    }

    public String getFormatLastLogin(){

        if(getLastLoginDate() == null)
            return "";

        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy (HH:mm)");
        return formater.format(getLastLoginDate());
    }

    public String getFormatDate(){
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return formater.format(getDataRegistration());
    }

    public String getFormatTime(){
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        return formater.format(getDataRegistration());
    }

    public int sizeItems (){
        int sum = 0;
        for(CollectionItem collection : getCollectionItems())
            sum += collection.sizeItems();
        return sum;
    }

    public String getUserRole(){
        String role = "";
        for(Role rol : getRoles())
            role += rol.toString();

        return role;
    }

    public void changeRole(Role role){
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        setRoles(roles);
    }

    @Override
    public String toString() {
        return "User: " + '\n' +
                "id = " + id + '\n' +
                "username = " + username + '\n' +
                "roles = " + roles + '\n' +
                "email = " + email + '\n' +
                "collectionItems size = " + collectionItems.size() + '\n' +
                "dataRegistration = " + dataRegistration + '\n' +
                "url = " + url;

    }
}