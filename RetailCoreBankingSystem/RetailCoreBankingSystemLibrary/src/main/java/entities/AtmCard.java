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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtmCard implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @OneToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long atmCardId;

    @NotNull
    @Min(8)
    @Max(19)
    @Column(length = 23, nullable = false, unique = true)
    private String cardNumber;

    @NotNull
    @Min(1)
    @Max(255)
    @Column(nullable = false)
    private String nameOnCard;

    @NotNull
    @Column(nullable = false)
    private boolean enabled = true;

    @NotNull
    @Min(6)
    @Max(10)
    @Column(length = 10, nullable = false)
    private String pin;
}
