package ru.kpfu.itis.lobanov;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.data.entities.*;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.*;
import ru.kpfu.itis.lobanov.data.services.CurrencyApiService;
import ru.kpfu.itis.lobanov.data.services.CurrencyService;
import ru.kpfu.itis.lobanov.data.services.MessagingService;
import ru.kpfu.itis.lobanov.data.services.TransactionService;
import ru.kpfu.itis.lobanov.dtos.*;
import ru.kpfu.itis.lobanov.exceptions.TransactionMethodNotFoundException;
import ru.kpfu.itis.lobanov.exceptions.TransactionTypeNotFoundException;
import ru.kpfu.itis.lobanov.utils.AccountNumberGenerator;
import ru.kpfu.itis.lobanov.utils.DateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;

import static ru.kpfu.itis.lobanov.utils.ExceptionMessages.NO_SUCH_TRANSACTION_METHOD;
import static ru.kpfu.itis.lobanov.utils.ExceptionMessages.NO_SUCH_TRANSACTION_TYPE;
import static ru.kpfu.itis.lobanov.utils.NamingConstants.*;
import static ru.kpfu.itis.lobanov.utils.NamingConstants.BANK_NAME;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {
    private final CurrencyApiService currencyApiService;
    private final CurrencyRepository currencyRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionMethodRepository transactionMethodRepository;
    private final MessagingService messagingService;
    private final UserRepository userRepository;
    private final DateProvider dateProvider;
    private final PasswordEncoder passwordEncoder;
    private final PassportRepository passportRepository;
    private final Mapper<User, UserDto> userMapper;

    @GetMapping
    public String get() {
        return "AAAAAAAA";
    }

    @GetMapping("/{id}")
    public String getB(@PathVariable int id) {
        return "" + id;
    }

    @GetMapping("/a")
    public String aaa() throws IOException {
        String json = currencyApiService.getAllCurrencies();
        Set<Map.Entry<String, JsonElement>> map = JsonParser.parseString(json).getAsJsonObject().get("data").getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry : map) {
            if (entry.getKey().length() >= 3){
                Currency currency = Currency.builder()
                        .name(entry.getKey())
                        .isoCode2(entry.getKey().substring(0, 2))
                        .isoCode3(entry.getKey().substring(0, 3))
                        .icon("")
                        .build();
                currencyRepository.save(currency);
            }
        }
        return "success";
    }

    // converter
    @GetMapping("/money/{id}")
    public void money(@PathVariable("id") String accountId, @RequestParam("amount") Long a) {
        BigDecimal amount = BigDecimal.valueOf(a);
        final LocalDateTime now = LocalDateTime.now();
        final Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();

        Account account = accountRepository.findById(Long.parseLong(accountId)).orElseThrow(IllegalArgumentException::new);


        TransactionType type = transactionTypeRepository.findByName(TRANSACTION_TYPE_INTEREST).orElseThrow(
                () -> new TransactionTypeNotFoundException(NO_SUCH_TRANSACTION_TYPE)
        );
        TransactionMethod method = transactionMethodRepository.findByName(TRANSACTION_METHOD_TRANSFER)
                .orElseThrow(() -> new TransactionMethodNotFoundException(NO_SUCH_TRANSACTION_METHOD));

        Transaction transaction = Transaction.builder()
                .date(Timestamp.from(instant))
                .initAmount(amount)
                .currency(account.getCurrency())
                .type(type)
                .method(method)
                .from(account.getId())
                .to(account.getId())
                .bankNameFrom(BANK_NAME)
                .bankNameTo(BANK_NAME)
                .terminalIp(null)
                .serviceCompany(BANK_NAME)
                .message(null)
                .commission(BigDecimal.ZERO)
                .cashback(BigDecimal.ZERO)
                .riskIndicator(0)
                .totalAmount(amount)
                .build();

        transaction = transactionRepository.save(transaction);
        messagingService.sendTransactionToChargeCommission(transaction);

        account.setDeposit(account.getDeposit().add(amount));

        account.getTransactions().add(transaction);

        accountRepository.save(account);
    }

    @GetMapping("/admin")
    public ResponseEntity<UserDto> admin() {
        Passport passport = Passport.builder()
                .name("Admin")
                .lastname("Adminov")
                .patronymic("Adminovich")
                .series((short) 1234)
                .number(567890)
                .birthday(dateProvider.parseDate("01.01.2000"))
                .gender('M')
                .departmentCode("660-100")
                .issuedBy("Admin administration")
                .issuedDate(dateProvider.parseDate(""))
                .address("Admin street, Admin town")
                .build();

        passport = passportRepository.save(passport);

        User user = User.builder()
                .passport(passport)
                .email("admin@mail.com")
                .password(passwordEncoder.encode("qwerty123!"))
                .verificationCode(null)
                .role(Role.ADMIN)
                .state(State.ACTIVE)
                .deleted(false)
                .build();

        user = userRepository.save(user);
        return new ResponseEntity<>(userMapper.toResponse(user), HttpStatus.CREATED);
    }
}
