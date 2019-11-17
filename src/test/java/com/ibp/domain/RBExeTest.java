package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class RBExeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RBExe.class);
        RBExe rBExe1 = new RBExe();
        rBExe1.setId(1L);
        RBExe rBExe2 = new RBExe();
        rBExe2.setId(rBExe1.getId());
        assertThat(rBExe1).isEqualTo(rBExe2);
        rBExe2.setId(2L);
        assertThat(rBExe1).isNotEqualTo(rBExe2);
        rBExe1.setId(null);
        assertThat(rBExe1).isNotEqualTo(rBExe2);
    }
}
