import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "sofist-scala-play"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "org.reactivemongo" %% "play2-reactivemongo" % "0.9",
    "net.codingwell" % "scala-guice_2.10" % "4.0.0-beta",
    "net.codingwell" %% "scala-guice" % "4.0.0-beta"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )

}
