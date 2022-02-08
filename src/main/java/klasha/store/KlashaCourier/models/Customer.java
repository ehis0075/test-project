package klasha.store.KlashaCourier.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private BillingDetails billingDetails;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private AppUser appUser;

    @JsonFormat(pattern="dd-MM-yyyy")
    @CreationTimestamp
    private LocalDate dateCreated;

    public void addRole(Role userRole){

        if (roles == null){
            this.roles = new ArrayList<>();
        }
        this.roles.add(userRole);
    }

}
