package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToOne(mappedBy = "customer")
    private AtmCard atmCard;

    @OneToMany(mappedBy = "customer")
    private List<DepositAccount> depositAccountList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @NotNull
    @Size(min = 1, max = 127)
    @Column(length = 127, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 127)
    @Column(length = 127, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 63)
    @Column(length = 63, nullable = false)
    private String identificationNumber;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(length = 64, nullable = false)
    private String contactNumber;

    @NotNull
    @Size(min = 1, max = 127)
    @Column(length = 127, nullable = false)
    private String addressLine1;

    @Size(min = 1, max = 127)
    @Column(length = 127)
    private String addressLine2;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(length = 10, nullable = false)
    private String postalCode;
}