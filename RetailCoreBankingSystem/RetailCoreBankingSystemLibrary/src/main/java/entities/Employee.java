package entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    @NotNull
    @Size(min = 1, max = 127)
    @Column(length = 127, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 127)
    @Column(length = 127, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 3, max = 127)
    @Column(length = 127, nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private EmployeeAccessRight employeeAccessRight = EmployeeAccessRight.TELLER;
}
