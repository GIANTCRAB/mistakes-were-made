package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @OneToMany(mappedBy = "customer")
    private List<DepositAccount> depositAccountList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId;

    @NotNull
    @Min(1)
    @Max(127)
    @Column(length = 127, nullable = false)
    private String firstName;

    @NotNull
    @Min(1)
    @Max(127)
    @Column(length = 127, nullable = false)
    private String lastName;

    @NotNull
    @Min(1)
    @Max(63)
    @Column(length = 63, nullable = false)
    private String identificationNumber;

    @NotNull
    @Min(1)
    @Max(64)
    @Column(length = 64, nullable = false)
    private String contactNumber;

    @NotNull
    @Min(1)
    @Max(127)
    @Column(length = 127, nullable = false)
    private String addressLine1;

    @Min(1)
    @Max(127)
    @Column(length = 127)
    private String addressLine2;

    @NotNull
    @Min(1)
    @Max(10)
    @Column(length = 10, nullable = false)
    private String postalCode;
}