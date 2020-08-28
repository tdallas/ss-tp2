# ss-tp2

## Generate executable
`mvn clean install`

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