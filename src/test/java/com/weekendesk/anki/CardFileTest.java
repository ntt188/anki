package com.weekendesk.anki;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.weekendesk.anki.CardBuilder.aCard;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CardFileTest {

    private static final String EXPECTED_FILE_CONTENT = "card question|card answer|box\n" +
            "What enzyme breaks down sugars mouth and digestive tract?|Amylase|RED\n" +
            "How is dietary cholesterol transported to target tissues?|In chylomicrons|ORANGE\n" +
            "What is the glucose transporter in the brain and what are its properties?|GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity.|ORANGE";

    @Test
    public void testArtDeckFile() {
        CardFile reader = new CardFile();
        Session session = reader.read();

        assertThat(session.getUnstudiedCards(), hasSize(3));
        CardAssert.assertThat(session.getUnstudiedCards().get(0))
                .hasQuestion("What enzyme breaks down sugars mouth and digestive tract?")
                .hasAnswer("Amylase");
        CardAssert.assertThat(session.getUnstudiedCards().get(1))
                .hasQuestion("How is dietary cholesterol transported to target tissues?")
                .hasAnswer("In chylomicrons");
        CardAssert.assertThat(session.getUnstudiedCards().get(2))
                .hasQuestion("What is the glucose transporter in the brain and what are its properties?")
                .hasAnswer("GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity.");

        assertThat(session.getRedBox().cards(), is(empty()));
        assertThat(session.getOrangeBox().cards(), is(empty()));
        assertThat(session.getGreenBox().cards(), is(empty()));
    }

    @Test
    public void testSaveFile() throws Exception {
        Files.copy(Paths.get(CardFileTest.class.getClassLoader().getResource("save.txt").getFile()),
                Paths.get(CardFile.SAVE_FILE));

        CardFile reader = new CardFile();
        Session session = reader.read();

        assertThat(session.getUnstudiedCards(), is(empty()));

        assertThat(session.getRedBox().cards(), hasSize(1));
        CardAssert.assertThat(session.getRedBox().cardAt(0))
                .hasQuestion("What enzyme breaks down sugars mouth and digestive tract?")
                .hasAnswer("Amylase");

        assertThat(session.getOrangeBox().cards(), hasSize(2));
        CardAssert.assertThat(session.getOrangeBox().cardAt(0))
                .hasQuestion("How is dietary cholesterol transported to target tissues?")
                .hasAnswer("In chylomicrons");
        CardAssert.assertThat(session.getOrangeBox().cardAt(1))
                .hasQuestion("What is the glucose transporter in the brain and what are its properties?")
                .hasAnswer("GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity.");

        assertThat(session.getGreenBox().cards(), is(empty()));

        new File(CardFile.SAVE_FILE).delete();
    }

    @Test
    public void testBuildFileContent() {
        Session session = new Session(new CardStudy());
        session.getRedBox().put(aCard().withQuestion("What enzyme breaks down sugars mouth and digestive tract?")
                .withAnswer("Amylase").build());
        session.getOrangeBox().put(aCard().withQuestion("How is dietary cholesterol transported to target tissues?")
                .withAnswer("In chylomicrons").build());
        session.getOrangeBox().put(aCard().withQuestion("What is the glucose transporter in the brain and what are its properties?")
                .withAnswer("GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity.")
                .build());

        CardFile cardFile = new CardFile();

        assertThat(cardFile.buildFileContent(session), is(equalTo(EXPECTED_FILE_CONTENT)));
    }
}