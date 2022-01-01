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
        stage("Publish") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                  sh "./gradlew publish -DpublishPassword=$PASSWORD -DpublishName=$USERNAME"
                }
            }
        }
        stage("Build Shadow Jar") {
            steps {
                sh "./gradlew shadowJar"
            }
            post {
                success {
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }
        stage("SSH") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "devserver", passwordVariable: 'password', usernameVariable: 'userName')]) {
                         remote.user = userName
                         remote.password = password
                    }
                }
                sshPut remote: remote, from: 'build/libs/SimpleCloud-Haste-1.0.1.jar', into: "/home/cloud/modules/"
                sshCommand remote: remote, command: "screen -S SimpleCloud -X stuff 'leave\nrl module SimpleCloud-Haste\n'"
            }
        }
    }
}
