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
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long depositAccountId;

    @NotNull
    @Min(3)
    @Max(127)
    @Column(length = 127, nullable = false, unique = true)
    private String accountNumber;

    @NotNull
    @Column
    private BigDecimal availableBalance = BigDecimal.valueOf(0);

    @NotNull
    @Column
    private BigDecimal holdBalance = BigDecimal.valueOf(0);

    @NotNull
    @Column
    private BigDecimal ledgerBalance = BigDecimal.valueOf(0);

    @NotNull
    @Column
    private boolean enabled = true;
}