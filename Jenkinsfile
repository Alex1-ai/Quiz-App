#!/usr/bin/env groovy

library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
   [$class: 'GitSCMSource',
    remote: 'https://github.com/Alex1-ai/jenkins-shared-library',
    credentialsId: 'github-credentials'
   ]
)

def gv

pipeline {
    agent any

    options {
        skipStagesAfterUnstable()
    }

    tools {
        maven "maven-3.9"
    }

    stages {

        stage('Check if skip') {
            steps {
                script {
                    def lastCommit = sh(
                        returnStdout: true,
                        script: 'git log -1 --pretty=%B'
                    ).trim()

                    if (lastCommit.contains('[skip ci]')) {
                        echo "Skipping build - commit contains [skip ci]"
                        currentBuild.result = 'SUCCESS'
                        error('Stopping pipeline - [skip ci] detected')
                    }
                }
            }
        }

        stage("init") {
            steps {
                script {
                    echo "Initializing shared library..."
                    gv = load "script.groovy"
                }
            }
        }

        stage('increment version') {
            when {
                branch 'main'
            }
            steps {
                script {
                    echo "Incrementing app version..."

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
                branch 'main'
            }
            environment {
                DATABASE_URL = credentials('database_url')
            }
            steps {
                script {
                    gv.buildJar()
                }
            }
        }

        stage("build and push image") {
            when {
                branch 'main'
            }
            steps {
                script {
                    gv.buildImage("chidi123/quiz-app:${IMAGE_NAME}")
                    gv.dockerLogin()
                    gv.dockerPush("chidi123/quiz-app:${IMAGE_NAME}")
                }
            }
        }

        stage('deploy to kubernetes') {
            when {
                branch 'main'
            }
            environment {
                AWS_ACCESS_KEY_ID     = credentials('jenkins_aws_access_key_id')
                AWS_SECRET_ACCESS_KEY = credentials('jenkins_aws_secret_access_key')
                APP_NAME = 'quiz-app'
            }
            steps {
                echo 'Deploying docker image to Kubernetes...'
                sh 'envsubst < kubernetes/deployment.yaml | kubectl apply -f -'
                sh 'envsubst < kubernetes/service.yaml | kubectl apply -f -'
            }
        }

        stage("commit version update") {
            when {
                branch 'main'
            }
            steps {
                script {
                    gv.githubCommit()
                }
            }
        }
    }

    post {
        always {
            script {
                def isJenkinsCommit = currentBuild.changeSets.any { cs ->
                    cs.items.any { item ->
                        item.msg.contains('[skip ci]') ||
                        item.author?.fullName == 'jenkins'
                    }
                }

                if (isJenkinsCommit) {
                    echo "Build triggered by Jenkins commit - marking as SUCCESS"
                    currentBuild.result = 'SUCCESS'
                }
            }
        }
    }
}
