package com.weekendesk.anki;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CardStudyTest {
    private CardStudy cardStudy = new CardStudy();

    @Test
    public void testUserInput1ShouldReturnColorRed() throws Exception {
        assertThat(this.cardStudy.getColor("1").isPresent(), is(true));
        assertThat(this.cardStudy.getColor("1").get(), is(equalTo(Color.RED)));
    }

    @Test
    public void testUserInput2ShouldReturnColorOrange() throws Exception {
        assertThat(this.cardStudy.getColor("2").isPresent(), is(true));
        assertThat(this.cardStudy.getColor("2").get(), is(equalTo(Color.ORANGE)));
    }

    @Test
    public void testUserInput3ShouldReturnColorGreen() throws Exception {
        assertThat(this.cardStudy.getColor("3").isPresent(), is(true));
        assertThat(this.cardStudy.getColor("3").get(), is(equalTo(Color.GREEN)));
    }

    @Test
    public void testUserInputOtherThan1Or2Or3ShouldReturnEmpty() throws Exception {
        assertThat(this.cardStudy.getColor("").isPresent(), is(false));
        assertThat(this.cardStudy.getColor("0").isPresent(), is(false));
        assertThat(this.cardStudy.getColor("4").isPresent(), is(false));
        assertThat(this.cardStudy.getColor("  dfs ").isPresent(), is(false));
        assertThat(this.cardStudy.getColor("4560").isPresent(), is(false));
    }
}