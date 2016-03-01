package com.KNS.contigs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikolai_Karulin on 2/17/2016.
 */
@RunWith(value = Parameterized.class)
public class SpectrumTest {

    String spectrumFile;
    String amino;
    int score;

    public SpectrumTest(String amino, String spectrumFile, int score) {
        this.amino = amino;
        this.spectrumFile = spectrumFile;
        this.score = score;
    }

    @Test
    public void testScore() throws Exception {

        Spectrum.init();

        List<Integer> specter = new ArrayList<>();
        try (Scanner in = new Scanner(new BufferedReader(new FileReader("Files/" + spectrumFile)))) {
            while (in.hasNextInt()) {
                specter.add(in.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Integer> theor = Spectrum.generate(amino)
                .stream().sorted().collect(Collectors.toList());

        assertEquals(Spectrum.score(theor, specter), score);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                { "NQEL", "spectrum1.txt", 8 },
                { "QGEELLWNCRTGDDYSGGFMLVTYEAFRPMEDKCHHR", "spectrum2.txt", 198 },
                { "ICWTVCKDKSMGGNAGIWLRYYKQRKPYWTFSDKWFQR", "spectrum3.txt", 274 }
        };
        return Arrays.asList(data);
    }
}