import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "playtomic"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    ( "com.datomic" % "datomic-free" % "0.8.3889" ).exclude( "org.slf4j", "slf4j-nop" )
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "Clojars" at "http://clojars.org/repo"
  )

}
