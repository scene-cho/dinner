package cf.scenecho.dinner.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @EqualsAndHashCode(of = "id")
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private final Set<AccountRole> roles = new HashSet<>();

    protected Account() {
    }

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        roles.add(AccountRole.USER);
    }

    public void addRole(AccountRole role) {
        roles.add(role);
    }

    public boolean is(Account account) {
        return this.equals(account);
    }
}
