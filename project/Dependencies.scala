import sbt._
import Keys._

object Dependencies {

  val scalaVersionString = "2.12.1"

  val akkaStreamDep:     ModuleID = "com.typesafe.akka"          %% "akka-stream"           % "2.4.17"
  val cassandraDep:      ModuleID = "com.datastax.cassandra"     %  "cassandra-driver-core" % "3.1.4"
  val catsDep:           ModuleID = "org.typelevel"              %% "cats"                  % "0.9.0"
  val catsIterateeDep:   ModuleID = "io.iteratee"                %% "iteratee-core"         % "0.10.0"
  val fs2CoreDep:        ModuleID = "co.fs2"                     %% "fs2-core"              % "0.9.4"
  val json4sDep:         ModuleID = "org.json4s"                 %% "json4s-native"         % "3.5.1"
  val kxbmapConfigsDep:  ModuleID = "com.github.kxbmap"          %% "configs"               % "0.4.4"
  val mongodbDep:        ModuleID = "org.mongodb"                %  "mongodb-driver"        % "3.4.2"
  val nScalaTimeDep:     ModuleID = "com.github.nscala-time"     %% "nscala-time"           % "2.16.0"
  val playIterateeDep:   ModuleID = "com.typesafe.play"          %% "play-iteratees"        % "2.6.1"
  val scalaLoggingDep:   ModuleID = "com.typesafe.scala-logging" %% "scala-logging"         % "3.5.0"
  val scalaTestDep:      ModuleID = "org.scalatest"              %% "scalatest"             % "3.0.1"
  val slf4jSimpleDep:    ModuleID = "org.slf4j"                  %  "slf4j-simple"          % "1.7.25"
  val sqliteDep:         ModuleID = "org.xerial"                 %  "sqlite-jdbc"           % "3.16.1"
  val streamAdapterDep:  ModuleID = "org.longevityframework"     %% "streamadapter"         % "0.0.0"
  val typesafeConfigDep: ModuleID = "com.typesafe"               %  "config"                % "1.3.1"

}
