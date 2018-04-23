package NNProject.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Column(name = "role_id")
    private int id;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
