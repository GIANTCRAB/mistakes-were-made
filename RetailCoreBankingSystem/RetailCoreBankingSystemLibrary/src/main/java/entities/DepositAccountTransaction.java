package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccountTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "depositAccountId", nullable = false)
    private DepositAccount depositAccount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountTransactionId;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime transactionDateTime = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;

    @NotNull
    @Column(nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String reference;

    @NotNull
    @Column
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private TransactionStatus status = TransactionStatus.PENDING;
}
