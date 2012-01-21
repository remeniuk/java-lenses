import sbt._
import Keys._

object JavaLensesBuild extends Build {

  lazy val core = Project("core", file("core")) settings (
    libraryDependencies += "com.google.guava" % "guava" % "11.0.1"
    )

  lazy val examples = Project("examples", file("examples")) dependsOn core

  //lazy val root = Project("root", file("."))

  override lazy val projects = Seq(core, examples)

}
