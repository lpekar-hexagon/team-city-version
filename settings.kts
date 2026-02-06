import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetBuild
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubConnection
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2025.11"

project {
    description = "Contains all other projects"

    features {
        buildReportTab {
            id = "PROJECT_EXT_1"
            title = "Code Coverage"
            startPage = "coverage.zip!index.html"
        }
        githubConnection {
            id = "PROJECT_EXT_3"
            displayName = "GitHub.com"
            clientId = "Ov23liUw8QcDdbCh9k56"
            clientSecret = "credentialsJSON:d32ff3fb-a731-4f15-913c-75132076f3b4"
        }
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(VersioningDemo2)
    subProject(VersioningDemo)
}


object VersioningDemo : Project({
    name = "Versioning Demo"
    description = "This project is intended to test the versioning features of TeamCity."

    vcsRoot(VersioningDemo_HttpsGithubComLpekarHexagonTeamCityDemoRefsHeadsMain)

    buildType(VersioningDemo_Build)

    features {
        githubConnection {
            id = "PROJECT_EXT_2"
            displayName = "GitHub.com"
            clientId = "Ov23liUw8QcDdbCh9k56"
            clientSecret = "credentialsJSON:a3540277-2413-4bf1-8e26-391c8efc2767"
        }
    }
})

object VersioningDemo_Build : BuildType({
    name = "Build"

    params {
        checkbox("Parameter_Checkbox", "",
                  checked = "true", unchecked = "custom unchecked value")
        param("Parameter_Text", "Test of text parameter")
    }

    vcs {
        root(VersioningDemo_HttpsGithubComLpekarHexagonTeamCityDemoRefsHeadsMain)
    }

    steps {
        dotnetBuild {
            id = "dotnet"
            projects = "TeamCityDemo/TeamCityDemo.csproj"
            sdk = "10"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object VersioningDemo_HttpsGithubComLpekarHexagonTeamCityDemoRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/lpekar-hexagon/team-city-demo#refs/heads/main"
    url = "https://github.com/lpekar-hexagon/team-city-demo"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "lpekar-hexagon"
        password = "credentialsJSON:43eaff24-2819-489a-be39-69e2fbe5a889"
    }
})


object VersioningDemo2 : Project({
    name = "VersioningDemo2"

    vcsRoot(VersioningDemo2_HttpsGithubComLpekarHexagonTeamCityDemo2refsHeadsMain)

    buildType(VersioningDemo2_Build)
})

object VersioningDemo2_Build : BuildType({
    name = "Build"

    vcs {
        root(VersioningDemo2_HttpsGithubComLpekarHexagonTeamCityDemo2refsHeadsMain)
    }

    steps {
        dotnetBuild {
            name = "build"
            id = "build"
            projects = "TeamCityDemo2.slnx"
            workingDir = "TeamCityDemo2"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object VersioningDemo2_HttpsGithubComLpekarHexagonTeamCityDemo2refsHeadsMain : GitVcsRoot({
    name = "https://github.com/lpekar-hexagon/team-city-demo-2#refs/heads/main"
    url = "https://github.com/lpekar-hexagon/team-city-demo-2"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "lpekar-hexagon"
        password = "credentialsJSON:c42991a1-a1bf-4535-9e0e-ae884f9aaf1c"
    }
})
