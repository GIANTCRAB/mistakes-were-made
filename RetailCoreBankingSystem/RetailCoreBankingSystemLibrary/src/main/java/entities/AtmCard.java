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
public class AtmCard implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "atmCard")
    private List<DepositAccount> depositAccountList = new ArrayList<>();

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
