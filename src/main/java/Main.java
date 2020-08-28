import engine.FHPSimulation;
import engine.ParticlesGenerator;
import org.apache.commons.cli.*;

import java.util.Random;

public class Main {
    private static int numberOfParticles;
    private static String filename;
    private static Long seed = null;

    public static void main(String[] args){
        parseArguments(args);
        //Just in case testing is needed to have the same seed on everything (argument -s is optional)
        Random rand;
        if(seed == null) {
            rand = new Random();
        }
        else{
            rand = new Random(seed);
        }
        ParticlesGenerator particlesGenerator = new ParticlesGenerator(rand, numberOfParticles);
        FHPSimulation fhpSimulation = new FHPSimulation(numberOfParticles, particlesGenerator.getCells(), filename, rand);
        fhpSimulation.simulate();
    }

    private static void parseArguments(String[] args){
        Options options = new Options();

        Option numberParticlesOption = new Option("n", "n-particles", true, "number of particles");
        numberParticlesOption.setRequired(true);
        options.addOption(numberParticlesOption);

        Option outputOption = new Option("o", "output", true, "output file name");
        outputOption.setRequired(true);
        options.addOption(outputOption);

        Option seedOption = new Option("s", "seed", true, "seed for randomizer (optional)");
        seedOption.setRequired(false);
        options.addOption(seedOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar target/ss-tp2-1.0.jar", options);

            System.exit(1);
        }

        try {
            numberOfParticles = Integer.parseInt(cmd.getOptionValue("n-particles"));
        } catch(NumberFormatException e){
            System.out.println("Invalid argument number of particles, must be integer");
            System.exit(1);
        }
        if(numberOfParticles < 0 || numberOfParticles > 10000){
            System.out.println("Invalid number of particles, must be positive lower than 10000");
            System.exit(1);
        }

        filename = cmd.getOptionValue("output");

        String aux = cmd.getOptionValue("seed");
        if(aux != null) {
            try {
                seed = Long.parseLong(aux);
            } catch(NumberFormatException e){
                System.out.println("Invalid argument seed, must be long");
                System.exit(1);
            }
        }
    }
}
