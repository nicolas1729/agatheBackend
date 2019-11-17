package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class EnvironnementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Environnement.class);
        Environnement environnement1 = new Environnement();
        environnement1.setId(1L);
        Environnement environnement2 = new Environnement();
        environnement2.setId(environnement1.getId());
        assertThat(environnement1).isEqualTo(environnement2);
        environnement2.setId(2L);
        assertThat(environnement1).isNotEqualTo(environnement2);
        environnement1.setId(null);
        assertThat(environnement1).isNotEqualTo(environnement2);
    }
}
