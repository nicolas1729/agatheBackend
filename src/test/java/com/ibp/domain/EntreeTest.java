package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class EntreeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entree.class);
        Entree entree1 = new Entree();
        entree1.setId(1L);
        Entree entree2 = new Entree();
        entree2.setId(entree1.getId());
        assertThat(entree1).isEqualTo(entree2);
        entree2.setId(2L);
        assertThat(entree1).isNotEqualTo(entree2);
        entree1.setId(null);
        assertThat(entree1).isNotEqualTo(entree2);
    }
}
