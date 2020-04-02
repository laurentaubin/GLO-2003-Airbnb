# Air bed and Bug on the Blockchain - GLO2003

Air bed and Bug API - http://glo2003.herokuapp.com/

## Table of content

- [Getting Started](#getting-started)
    - [Build](#build)
    - [Usage](#usage)
- [Main features](#main-features)
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
    
## Main Features

The app is deployed at http://glo2003.herokuapp.com/, but you can run the app by executing `mvn exec:java` in the root directory. The app will then be running at http://localhost:4567.

All endpoints named in the following sections work on both addresses. Make sure to follow the right format when sending your requests. Otherwise, you will receive an error message indicating what went wrong or what is missing.
Also, if you make a request on a bed or booking that doesn't exist, the app will return a `404 not found` error and indicate that the bed or booking is missing. 

*All information provided as example are fictional*

#### GET /beds
You can request every beds that have been publish by owners by making a GET request at `/beds`

#### GET /beds/:bed_uuid
You can get information on a specific bed by making a GET request at `/beds/:bed_uuid`, where `:bed_uuid` is the ID of the bed.

#### POST /beds
You can create a bed as an owner by making a POST request at `/beds`. A valid request must respect the following format
``` Json
{
  "ownerPublicKey": "8F0436A6FB0AA49085B7F19AB73933973BF216276F2EC7D122AC110BB46A3A4E"::string,
  "zipCode": "12345"::string,
  "bedType": "springs"::string,
  "cleaningFrequency": "annual"::string,
  "bloodTypes": ["O-", "O+"]::string[],
  "capacity": 999::int,
  "packages": [
    { 
      "name": "allYouCanDrink"::string,
      "pricePerNight": 45.27::number
    },
    { 
      "name": "bloodthirsty"::string,
      "pricePerNight": 1::number
    }
  ]
}
```

#### GET /beds/:bed_uuid/bookings/:booking_uuid
You can get information about a specific booking on a specific bed by making a GET request at `/beds/:bed_uuid/booking/:booking_uuid`, where `:bed_uuid` and `:booking_uuid` are the IDs of the bed and booking, respectively.

#### POST /beds/:bed_uuid/bookings
You can make a booking as a tenant by making a POST request at `/beds/:bed_uuid/booking`. A valid request must respect the following format
``` JSON
{
  "tenantPublicKey": "8F0436A6FB0AA49085B7F1B73933973BA216276F2EC7D122AC110BB46A3A4E",
  "arrivalDate": "2021-01-19",
  "numberOfNights": 3,
  "package": "bloodthirsty"
}
```

If the booking you are trying to make is in conflict with an existing booking, an error message will be returned to you.

#### POST /beds/:bed_uuid/bookings/:booking_uuid/cancel
You can cancel a specific booking on a specific bed by making a POST request at `/beds/:bed_uuid/bookings/:booking_uuid/cancel`, where `:bed_uuid` and `:booking_uuid` are the IDs of the bed and booking, respectively. 
                       
#### GET /admin/transactions
You can get every transactions made on the app by making a GET request at `/admin/transactions`

#### Filter bed search
You can filter your bed research by:  
- Bed type
- Capacity
- Blood types
- Bed package
- Localisation
- Lodging mode
- Cleaning frequency

A valid query should look something like this:

`GET /beds?package=allYouCanDrink&bedType=memoryFoam&cleaningFreq=never&bloodTypes=O+,A-,B+&minCapacity=350`

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
