name := "redcardrobot"

version := "0.0.1"

lazy val `redcardrobot` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq( jdbc , cache , ws, specs2 % Test )

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.38"
libraryDependencies += "com.typesafe.play" %% "anorm" % "2.4.0"
libraryDependencies += "org.bgee.log4jdbc-log4j2" % "log4jdbc-log4j2-jdbc4" % "1.12"

scalacOptions += "-feature"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  