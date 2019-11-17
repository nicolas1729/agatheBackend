package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class RBUrlTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RBUrl.class);
        RBUrl rBUrl1 = new RBUrl();
        rBUrl1.setId(1L);
        RBUrl rBUrl2 = new RBUrl();
        rBUrl2.setId(rBUrl1.getId());
        assertThat(rBUrl1).isEqualTo(rBUrl2);
        rBUrl2.setId(2L);
        assertThat(rBUrl1).isNotEqualTo(rBUrl2);
        rBUrl1.setId(null);
        assertThat(rBUrl1).isNotEqualTo(rBUrl2);
    }
}
