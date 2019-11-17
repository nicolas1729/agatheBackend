package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class VersionPrivativeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionPrivative.class);
        VersionPrivative versionPrivative1 = new VersionPrivative();
        versionPrivative1.setId(1L);
        VersionPrivative versionPrivative2 = new VersionPrivative();
        versionPrivative2.setId(versionPrivative1.getId());
        assertThat(versionPrivative1).isEqualTo(versionPrivative2);
        versionPrivative2.setId(2L);
        assertThat(versionPrivative1).isNotEqualTo(versionPrivative2);
        versionPrivative1.setId(null);
        assertThat(versionPrivative1).isNotEqualTo(versionPrivative2);
    }
}
