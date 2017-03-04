
lazy val root = Project(id = "root", base = file("."), settings = BuildSettings.noPublishSettings)
  .aggregate(bin, emblem, longevity, longevityCassandraDeps, longevityMongoDbDeps, longevitySqliteDeps)

lazy val bin = Project(id = "bin", base = file("bin"), settings = BuildSettings.noPublishSettings)

lazy val longevity = Project(
  id = "longevity",
  base = file("longevity"),
  settings = BuildSettings.buildSettings ++ Seq(

    libraryDependencies += Dependencies.akkaStreamDep      % Optional,
    libraryDependencies += Dependencies.akkaStreamDep      % Test,
    libraryDependencies += Dependencies.cassandraDep       % Optional,
    libraryDependencies += Dependencies.cassandraDep       % Test,
    libraryDependencies += Dependencies.json4sDep          % Optional,
    libraryDependencies += Dependencies.json4sDep          % Test,
    libraryDependencies += Dependencies.kxbmapConfigsDep,
    libraryDependencies += Dependencies.mongodbDep         % Optional,
    libraryDependencies += Dependencies.mongodbDep         % Test,
    libraryDependencies += Dependencies.reflectionsDep,
    libraryDependencies += Dependencies.scalaLoggingDep,
    libraryDependencies += Dependencies.scalaTestDep       % Optional,
    libraryDependencies += Dependencies.slf4jSimpleDep     % Test,
    libraryDependencies += Dependencies.sqliteDep          % Optional,
    libraryDependencies += Dependencies.sqliteDep          % Test,
    libraryDependencies += Dependencies.typesafeConfigDep,

    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra,

    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )
)
.dependsOn(emblem)

lazy val emblem = Project(
  id = "emblem",
  base = file("emblem"),
  settings = BuildSettings.buildSettings ++ Seq(
    libraryDependencies += Dependencies.json4sDep % Optional,
    homepage := Some(url("https://github.com/longevityframework/emblem")),
    pomExtra := (
      <scm>
      <url>git@github.com:longevityframework/emblem.git</url>
      <connection>scm:git:git@github.com:longevityframework/emblem.git</connection>
      </scm>
      <developers>
      <developer>
      <id>sullivan-</id>
      <name>John Sullivan</name>
      <url>https://github.com/sullivan-</url>
      </developer>
      </developers>)
  )
)

lazy val longevityCassandraDeps = Project(
  id = "longevity-cassandra-deps",
  base = file("longevity-cassandra-deps"),
  settings = BuildSettings.publishSettings ++ Seq(
    libraryDependencies += Dependencies.cassandraDep,
    libraryDependencies += Dependencies.json4sDep,
    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra))

lazy val longevityMongoDbDeps = Project(
  id = "longevity-mongodb-deps",
  base = file("longevity-mongodb-deps"),
  settings = BuildSettings.publishSettings ++ Seq(
    libraryDependencies += Dependencies.mongodbDep,
    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra))

lazy val longevitySqliteDeps = Project(
  id = "longevity-sqlite-deps",
  base = file("longevity-sqlite-deps"),
  settings = BuildSettings.publishSettings ++ Seq(
    libraryDependencies += Dependencies.sqliteDep,
    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra))