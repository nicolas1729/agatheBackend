package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class VersionEnvironnementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionEnvironnement.class);
        VersionEnvironnement versionEnvironnement1 = new VersionEnvironnement();
        versionEnvironnement1.setId(1L);
        VersionEnvironnement versionEnvironnement2 = new VersionEnvironnement();
        versionEnvironnement2.setId(versionEnvironnement1.getId());
        assertThat(versionEnvironnement1).isEqualTo(versionEnvironnement2);
        versionEnvironnement2.setId(2L);
        assertThat(versionEnvironnement1).isNotEqualTo(versionEnvironnement2);
        versionEnvironnement1.setId(null);
        assertThat(versionEnvironnement1).isNotEqualTo(versionEnvironnement2);
    }
}
