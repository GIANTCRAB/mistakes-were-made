package entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeId;

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
    @Min(3)
    @Max(127)
    @Column(length = 127, nullable = false, unique = true)
    private String username;

    @NotNull
    @Min(3)
    @Max(255)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private EmployeeAccessRight employeeAccessRight = EmployeeAccessRight.TELLER;
}
