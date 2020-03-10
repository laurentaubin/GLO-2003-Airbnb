# Air bed and Bug on the Blockchain - GLO2003

Air bed and Bug API - http://glo2003.herokuapp.com/

## Table of content

- [Getting Started](#getting-started)
    - [Build](#build)
    - [Usage](#usage)
- [Code of Conduct](#code-of-conduct)
- [Contributing](#contributing)
- [Licence](#licence)
- [Acknowledgement]()


## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine

### Build

To build the project, run ``mvn clean install``  

### Execute

To execute the app, run ``mvn exec:java``

### Running the tests

To run the tests, run ``mvn test``


### Get the code coverage

To get the code coverage, run  

    #Linux
    mvn jacoco:report
    xd-open target/site/jacoco/index.html
    
    #MacOS
    mvn jacoco:report
    open target/site/jacoco/index.html

### Linting

All code is written using [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
The project is configured to be verified and formatted before each commit using [Cosium/maven-git-code-format](https://github.com/Cosium/maven-git-code-format).

Manual code formatting

    mvn git-code-format:format-code -"Dgcf.globPattern=**/*"

Manuel code format validation
    
    mvn git-code-format:validate-code-format -"Dgcf.globPattern=**/*"

## Code of Conduct

See the the Code of Conduct [on this page](https://github.com/glo2003/glo2003-h2020-eq05/blob/master/CODE_OF_CONDUCT.md)

## Contributing

See how to contribute [on this page](https://github.com/glo2003/glo2003-h2020-eq05/blob/master/CONTRIBUTING.md)

## Licence

See the licence [on this page](https://github.com/glo2003/glo2003-h2020-eq05/blob/master/LICENCE.md)


## Contributors
- Laurent Aubin
- Vincent Breault
- Jean-Christophe Drouin
- Toma Gagné
- Benjamin Girard
- Vincent Lambert
- Rémi Lévesque
- Yuxuan Zhao
