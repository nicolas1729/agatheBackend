package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class VersionCommunautaireTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionCommunautaire.class);
        VersionCommunautaire versionCommunautaire1 = new VersionCommunautaire();
        versionCommunautaire1.setId(1L);
        VersionCommunautaire versionCommunautaire2 = new VersionCommunautaire();
        versionCommunautaire2.setId(versionCommunautaire1.getId());
        assertThat(versionCommunautaire1).isEqualTo(versionCommunautaire2);
        versionCommunautaire2.setId(2L);
        assertThat(versionCommunautaire1).isNotEqualTo(versionCommunautaire2);
        versionCommunautaire1.setId(null);
        assertThat(versionCommunautaire1).isNotEqualTo(versionCommunautaire2);
    }
}
