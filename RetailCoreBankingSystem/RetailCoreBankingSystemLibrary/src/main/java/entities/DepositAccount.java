package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "atmCardId")
    private AtmCard atmCard;

    @OneToMany(mappedBy = "depositAccount")
    private List<DepositAccountTransaction> depositAccountTransactionList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountId;

    // Format: xxx-xxxx-xxxx-xxxx-xxxx
    @NotNull
    @Size(min = 23, max = 23)
    @Column(length = 32, nullable = false, unique = true)
    private String accountNumber;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private DepositAccountType depositAccountType;

    @NotNull
    @Column
    private BigDecimal availableBalance = BigDecimal.valueOf(0);

    @NotNull
    @Column
    private BigDecimal holdBalance = BigDecimal.valueOf(0);

    @NotNull
    @Column
    private boolean enabled = true;

    @Transient
    public BigDecimal getLedgerBalance() {
        return getAvailableBalance().subtract(getHoldBalance());
    }
}
