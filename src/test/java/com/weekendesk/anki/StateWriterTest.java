package com.weekendesk.anki;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StateWriterTest {

    private static final String EXPECTED_FILE_CONTENT = "card question|card answer|box\n" +
            "What enzyme breaks down sugars mouth and digestive tract?|Amylase|RED\n" +
            "How is dietary cholesterol transported to target tissues?|In chylomicrons|ORANGE\n" +
            "What is the glucose transporter in the brain and what are its properties?|GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity.|ORANGE";

    @Test
    public void testBuildFileContent() {
        Box.clearAll();
        Box.RED.put(new Card("What enzyme breaks down sugars mouth and digestive tract?", "Amylase"));
        Box.ORANGE.put(new Card("How is dietary cholesterol transported to target tissues?",
                "In chylomicrons"));
        Box.ORANGE.put(new Card("What is the glucose transporter in the brain and what are its properties?",
                "GLUT-1 transports glucose across blood-brain barrier, GLUT-3 transports glucose into neurons.  Both are high-affinity."));

        StateWriter writer = new StateWriter();

        assertThat(writer.buildFileContent(), is(equalTo(EXPECTED_FILE_CONTENT)));
    }

}