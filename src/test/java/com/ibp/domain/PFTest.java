package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class PFTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PF.class);
        PF pF1 = new PF();
        pF1.setId(1L);
        PF pF2 = new PF();
        pF2.setId(pF1.getId());
        assertThat(pF1).isEqualTo(pF2);
        pF2.setId(2L);
        assertThat(pF1).isNotEqualTo(pF2);
        pF1.setId(null);
        assertThat(pF1).isNotEqualTo(pF2);
    }
}
