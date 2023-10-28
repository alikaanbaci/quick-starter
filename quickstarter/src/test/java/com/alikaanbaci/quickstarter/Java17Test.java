package com.alikaanbaci.quickstarter;


import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

public class Java17Test {

    @Test
    void givenAccounts_whenAccountsAreDifferent_shouldNotEquals() {
        // prepare
        record Account(String accountId, String accountOwner) {}

        final Account kaanAccount = new Account("1", "Kaan");
        final Account emreAccount = new Account("2", "Emre");
        // verify
        assertThat(kaanAccount).isNotEqualTo(emreAccount);
        assertThat(kaanAccount.equals(emreAccount)).isFalse();
        assertThat(Objects.equals(kaanAccount, emreAccount)).isFalse();
    }

    @Test
    void givenAccounts_whenAccountsAreSame_shouldEquals() {
        // prepare
        record Account(String accountId, String accountOwner) {}

        final Account kaanAccount = new Account("1", "Kaan");
        final Account kaanBackupAccount = new Account("1", "Kaan");
        // verify
        assertThat(kaanAccount).isEqualTo(kaanBackupAccount);
        assertThat(kaanAccount.equals(kaanBackupAccount)).isTrue();
        assertThat(Objects.equals(kaanAccount, kaanBackupAccount)).isTrue();
    }

    @Test
    void givenParameter_whenParameterIsNull_shouldGiveDeclarativeMessage() {
        // prepare
        final Integer num = null;
        final Exception error = catchException(() -> num.doubleValue());
        // verify
        assertThat(error).isInstanceOf(NullPointerException.class);
        assertThat(error.getMessage()).contains("\"num\" is null");
    }
}
