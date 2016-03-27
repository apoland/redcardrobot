name := "redcardrobot"

version := "0.0.1"

lazy val `redcardrobot` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"
dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value

libraryDependencies ++= Seq( jdbc , cache , ws, specs2 % Test )

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.38"
libraryDependencies += "org.sorm-framework" % "sorm" % "0.3.19"
libraryDependencies += "com.twilio.sdk" % "twilio-java-sdk" % "6.3.0"

libraryDependencies += "org.atteo" % "evo-inflector" % "1.2.1"

scalacOptions += "-feature"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

