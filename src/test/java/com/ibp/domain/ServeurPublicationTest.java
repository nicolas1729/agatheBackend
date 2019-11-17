package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class ServeurPublicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServeurPublication.class);
        ServeurPublication serveurPublication1 = new ServeurPublication();
        serveurPublication1.setId(1L);
        ServeurPublication serveurPublication2 = new ServeurPublication();
        serveurPublication2.setId(serveurPublication1.getId());
        assertThat(serveurPublication1).isEqualTo(serveurPublication2);
        serveurPublication2.setId(2L);
        assertThat(serveurPublication1).isNotEqualTo(serveurPublication2);
        serveurPublication1.setId(null);
        assertThat(serveurPublication1).isNotEqualTo(serveurPublication2);
    }
}
