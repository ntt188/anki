package com.weekendesk.anki;

import org.junit.Test;

import java.net.URL;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by tungnguyen on 18/06/17.
 */
public class CardFileReaderTest {

    @Test
    public void testReadInputFile() {
        URL url = CardFileReaderTest.class.getClassLoader().getResource("InputFileTest.txt");
        CardFileReader reader = new CardFileReader(url.getFile());
        reader.read();

        assertThat(reader.getUnstudiedCards(), hasSize(3));
        CardAssert.assertThat(reader.getUnstudiedCards().get(0))
                .hasQuestion("What enzyme breaks down sugars mouth and digestive tract?")
                .hasAnswer("Amylase");
        CardAssert.assertThat(reader.getUnstudiedCards().get(1))
                .hasQuestion("How is dietary cholesterol transported to target tissues?")
                .hasAnswer("In chylomicrons");
        CardAssert.assertThat(reader.getUnstudiedCards().get(2))
                .hasQuestion("What is the glucose transporter in the brain and what are its properties?")
                .hasAnswer("GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity.");

        assertThat(Box.RED.cards(), is(empty()));
        assertThat(Box.ORANGE.cards(), is(empty()));
        assertThat(Box.GREEN.cards(), is(empty()));
    }

    @Test
    public void testReadStateFile() {
        URL url = CardFileReaderTest.class.getClassLoader().getResource("StateFileTest.txt");
        CardFileReader reader = new CardFileReader(url.getFile());
        reader.read();

        assertThat(reader.getUnstudiedCards(), is(empty()));

        assertThat(Box.RED.cards(), hasSize(1));
        CardAssert.assertThat(Box.RED.cardAt(0))
                .hasQuestion("What enzyme breaks down sugars mouth and digestive tract?")
                .hasAnswer("Amylase");

        assertThat(Box.ORANGE.cards(), hasSize(2));
        CardAssert.assertThat(Box.ORANGE.cardAt(0))
                .hasQuestion("How is dietary cholesterol transported to target tissues?")
                .hasAnswer("In chylomicrons");
        CardAssert.assertThat(Box.ORANGE.cardAt(1))
                .hasQuestion("What is the glucose transporter in the brain and what are its properties?")
                .hasAnswer("GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity.");

        assertThat(Box.GREEN.cards(), is(empty()));
    }
}