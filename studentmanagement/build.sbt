name := """studentmanagement"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += jdbc

libraryDependencies += "org.elasticsearch" % "elasticsearch" % "7.10.0"
libraryDependencies += "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "7.10.0"
//libraryDependencies += "com.typesafe.play" %% "play-java" % "2.8.2"


// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.32"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.11.3"


