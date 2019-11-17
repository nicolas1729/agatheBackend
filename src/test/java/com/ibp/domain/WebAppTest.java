package com.ibp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ibp.web.rest.TestUtil;

public class WebAppTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebApp.class);
        WebApp webApp1 = new WebApp();
        webApp1.setId(1L);
        WebApp webApp2 = new WebApp();
        webApp2.setId(webApp1.getId());
        assertThat(webApp1).isEqualTo(webApp2);
        webApp2.setId(2L);
        assertThat(webApp1).isNotEqualTo(webApp2);
        webApp1.setId(null);
        assertThat(webApp1).isNotEqualTo(webApp2);
    }
}
