import engine.FileGenerator;
import engine.ParticlesGenerator;
import org.apache.commons.cli.*;

import java.util.Random;

public class Main {
    private static int n;
    private static String filename;

    public static void main(String[] args){
        parseArguments(args);
        ParticlesGenerator particlesGenerator = new ParticlesGenerator(new Random(), n);
        FileGenerator fileGenerator = new FileGenerator(filename);
        fileGenerator.addCells(particlesGenerator.getCells(), n, 10);
        fileGenerator.closeFile();
    }

    private static void parseArguments(String[] args){
        Options options = new Options();

        Option numberParticles = new Option("n", "n-particles", true, "number of particles");
        numberParticles.setRequired(true);
        options.addOption(numberParticles);

        Option output = new Option("o", "output", true, "output file name");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        n = Integer.parseInt(cmd.getOptionValue("n-particles"));
        if(n < 0 || n > 20000){
            System.out.println("Invalid number of particles");
            System.exit(1);
        }
        filename = cmd.getOptionValue("output");

    }
}
