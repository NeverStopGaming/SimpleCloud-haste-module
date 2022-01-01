def remote = [:]
remote.name = "devserver"
remote.host = "devserver.NeverStopGaming.net"
remote.allowAnyHosts = true
pipeline {
    
    agent any

    tools {
        jdk 'jdk-16'
    }

    stages {
        stage("Clean") {
            steps {
                sh "chmod +x ./gradlew";
                sh "./gradlew clean";
            }
        }
        stage("Build") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh "./gradlew build --refresh-dependencies -DpublishPassword=$PASSWORD -DpublishName=$USERNAME"
                }
            }
        }
    }
}
