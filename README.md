# ss-tp2

## Generate executable
`mvn clean install && mvn clean package`

## Execute command
Required options: n, o

`usage: java -jar target/ss-tp2-1.0.jar`

` -n,--n-particles <arg>   number of particles`

` -o,--output <arg>        output file name`

` -s,--seed <arg>          seed for randomizer (optional)`

## Output
The program will generate two files with the given output file name:

`out/FILENAME.xyz`

`out/FILENAME.csv`

## Usage example
`java -jar target/ss-tp2-1.0.jar -n 500 -o test`

And this will generate:

`out/test.xyz`

`out/test.csv`

## Visual
To run the following python visual charts must install the following libraries:

`pip3 install pandas matplotlib`

For a visual chart of time vs particles using N particles (N = 1000, 2000 and 3000):

`python3 visual/time_vs_particles.py`

For a visual chart of time vs particles using N particles with a determined seed (N = 1000, 2000 and 3000):

`python3 visual/time_vs_particles_seed.py`

For a visual chart of time average with N from 1000 to 8000:

`python3 visual/time_average.py`

## Authors

Tomás Dallas

Tomás Dorado