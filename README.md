# Engine for playing trick-taking games

A final product for semester 2. Functionality includes being able to 
host 12 different trick-taking games or join any trick-taking game that follows
the protocol of Supergroup A (https://gitlab.cs.st-andrews.ac.uk/cs3099groupa/supergroup-code). 
Functionality also includes the supergroup multicast discovery protocol and usernames are also supported.

## Currently supported host games

* Whist 
* Spades
* Bridge
* One Trick Pony
* Smart Aleck
* Oh Hell
* Contract Whist
* Clubs
* Jabberwocky
* Napoleon (Simplified)
* Reverse Spades
* Catch The Ten

## Getting Started

These instructions will get you a copy of the project up and running on 
your local machine for development and testing purposes. 
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
gradle version 5.4.1 (to run tests)
Node version 13.12.0 
npm version 6.14.4
JDK 8+
```

### Installing

A step by step guide on get the program running:

1. Clone the project to your machine:

```
https://gitlab.cs.st-andrews.ac.uk/cs3099groupa-6/project-code.git
```

2. Switch current working directory to **project-code**. 

3. Run:

```
./initInstall.sh
./runAndBuildAll.sh local > /dev/null
```

3. If the above commands are successful, open 4 tabs of a browser and type http://localhost:3000 as a URL and you should be greeted by a welcome page. If not keep refreshing until you are.

4. Host on one tab and join on the others 

5. After you're finished playing run to kill background processes:

```
./killAllProcesses.sh
```

## Running the tests 
```
navigate to inside the back-end directory
gradle test
```
```
navigate to inside the front-end directory
npm test
```

## Honorary mention

A huge thanks goes to our supervisor dr. Edwin Brady for his encouragement and support throughout the process of developing this project.

