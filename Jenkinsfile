#!/usr/bin/env groovy

// @Library('jenkins-shared-library')

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
   [$class: 'GitSCMSource',
    remote: 'https://github.com/Alex1-ai/jenkins-shared-library',
    credentialsId: 'github-credentials'
   ]
)

def gv


pipeline {
    agent any

    tools {
        maven "maven-3.9"
    }

    when {
        not {
            expression {
                currentBuild.changeSets.any { change ->
                    change.items.any { it.author.email == "jenkins@example.com" }
                }
            }
        }
    }


    stages {

        stage("init") {
            steps {
                script {
                    echo "adding automatic running"
                    gv = load "script.groovy"
                }
            }
        }

        stage('increment version') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    echo "incrementing app version...."

                    sh """
                    mvn build-helper:parse-version versions:set \
                      -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                      versions:commit
                    """

                    def matcher = readFile('pom.xml') =~ '<version>(.*)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
                }
            }
        }

        stage("test") {
            steps {
                script {
                    gv.testApp()
                }
            }
        }

        stage("build") {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }

            environment {
               DATABASE_URL = credentials('database_url')

            }
            steps {
                script {
                    buildJar()
                }
            }
        }

        stage("build and push image") {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    buildImage "chidi123/quiz-app:${IMAGE_NAME}"
                    dockerLogin()
                    dockerPush "chidi123/quiz-app:${IMAGE_NAME}"
                }
            }
        }

        stage('deploy to kubernetes') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            environment {
                AWS_ACCESS_KEY_ID = credentials('jenkins_aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
                APP_NAME = 'quiz-app'
            }
            steps {
                echo 'deploying docker image...'
//                 sh 'kubectl create deployment nginx-deployment --image=nginx'
                sh 'envsubst < kubernetes/deployment.yaml | kubectl apply -f -'
                sh 'envsubst < kubernetes/service.yaml | kubectl apply -f -'
            }
        }

//         stage("deploy") {
//             when {
//                 expression {
//                     BRANCH_NAME == 'main'
//                 }
//             }
//             steps {
//                 script {
//                     echo "Deploying ......"
//                 }
//             }
//         }

        stage("commit version update") {

            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                script {
                    gv.githubCommit()
                }
            }
        }
    }
}
