import engine.FHPSimulation;
import engine.ParticlesGenerator;
import engine.TimeFileGenerator;
import org.apache.commons.cli.*;

import java.util.Random;

public class Main {
    private static int numberOfParticles;
    private static String filename;
    private static Long seed = null;
    private static boolean repeats = false;
    private static int numberOfRepetitions = 0;

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
        if(!repeats) {
            ParticlesGenerator particlesGenerator = new ParticlesGenerator(rand, numberOfParticles);
            FHPSimulation fhpSimulation = new FHPSimulation(numberOfParticles, particlesGenerator.getCells(), filename, rand);
            fhpSimulation.simulate();
        }
        else{
            TimeFileGenerator timeFileGenerator = new TimeFileGenerator(filename, numberOfRepetitions);
            for(int i = 0; i < numberOfRepetitions; i++) {
                rand = new Random();
                ParticlesGenerator particlesGenerator = new ParticlesGenerator(rand, numberOfParticles);
                FHPSimulation fhpSimulation = new FHPSimulation(numberOfParticles, particlesGenerator.getCells(), timeFileGenerator, rand);
                fhpSimulation.simulate();
            }
            timeFileGenerator.closeFile();
        }
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

        Option repetitionOption = new Option("r", "repeat", true, "repetition number (optional)");
        repetitionOption.setRequired(false);
        options.addOption(repetitionOption);

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
        if(filename.equals("walls")){
            System.out.println("Invalid filename, cannot be named: wall");
            System.exit(1);
        }

        String numberOfRepetitionsString = cmd.getOptionValue("repeat");
        if(numberOfRepetitionsString != null) {
            try {
                numberOfRepetitions = Integer.parseInt(numberOfRepetitionsString);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument number of repetitions, must be integer");
                System.exit(1);
            }
            if (numberOfRepetitions < 1) {
                System.out.println("Invalid number of repetitions, must be over 1");
                System.exit(1);
            } else {
                repeats = true;
            }
        }

        String aux = cmd.getOptionValue("seed");
        if(aux != null) {
            try {
                seed = Long.parseLong(aux);
            } catch(NumberFormatException e){
                System.out.println("Invalid argument seed, must be long");
                System.exit(1);
            }
        }
        if(repeats && aux != null){
            System.out.println("Invalid arguments, seed cannot be used with repetitions");
            System.exit(1);
        }
    }
}
