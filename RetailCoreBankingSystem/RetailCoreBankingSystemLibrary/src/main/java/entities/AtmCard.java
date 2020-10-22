package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long atmCardId;

    @NotNull
    @Size(min = 8, max = 19)
    @Column(length = 23, nullable = false, unique = true)
    private String cardNumber;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false)
    private String nameOnCard;

    @NotNull
    @Column(nullable = false)
    private boolean enabled = true;

    @NotNull
    @Size(min = 6, max = 10)
    @Column(length = 10, nullable = false)
    private String pin;
}
