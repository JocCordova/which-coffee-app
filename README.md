
<!-- ABOUT THE PROJECT -->
## About The Project
Which Coffee App is an Android app that lets you save in a Database Coffee related data.

The three main tables in the Database are:

#### COFFEE
|ID|Origin|Name|Process|Roast Date|
|---|---|---|---|---|
|1|Colombia|El Trébol|Washed|2021-02-06|

The Coffee table is a basic table to store basic information about the coffee.
The Id is the primary key and it's auto generated.

#### JAR
|ID|Amount|CofeeID|
|---|---|---|
|15|15g|1|

The main idea for the Jar table is to store coffee in different numerated jars.
The Id is the primary key, that means that the numbers on the jars must be different.

<br><br>
If you are like me and you preportion your coffee for freezer storage, and whant to have control of *which coffee* is in the jar, ~~this table~~ *this App* just makes sense.


#### REVIEW
|ID|Method|Description|CoffeeID|
|---|---|---|---|
|1|Moccamaster|Chocolate,Mandarine,Mild Body|1|

In the Review table a short "review" can be added. Method is the Method of the coffee brewing and the Description could be the flavor notes.
The Id is the primary key and it's auto generated.




### Built With

* Android Studio
* SQLite


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/JocCordova/which-coffee-app.git
   ```



<!-- USAGE EXAMPLES -->
## Usage




<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


<!-- CONTACT -->
## Contact

Jose Cordova - jacordovah@gmail.com

Project Link: [https://github.com/JocCordova/which-coffee-app](https://github.com/JocCordova/which-coffee-app)


