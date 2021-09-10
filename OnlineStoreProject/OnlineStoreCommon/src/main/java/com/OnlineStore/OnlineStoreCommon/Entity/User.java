package com.OnlineStore.OnlineStoreCommon.Entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(name = "first_name", length = 45, nullable = false)
    private String userFirstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "enabled")
    private Boolean enabled;

    private String photo;


    @ManyToMany(fetch = FetchType.EAGER) // this relationship is unidirectional
    @JoinTable( name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
                                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    // jointable is intermediate table. This relationship is unidirectional


    public User(){
    }
// email	enabled	first name	last name	password	image
   /* public User(String email, boolean enabled, String firstName,String lastName, String password, String photo) {
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.userFirstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }*/

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.userFirstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return userFirstName;
    }

    public void setFirstName(String firstName) {
        this.userFirstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName(){

        return  this.userFirstName  +  " " + this.lastName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public String photosImagePath = getPhotosImagePath();

    @Transient
    public String getPhotosImagePath(){
        if(id == null || photo == null) return "/images/dafaultusericon1.png";
        return "/user-photos/" + this.id + "/" + this.photo ;

    }

}
