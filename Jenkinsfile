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

//     parameters {
//         choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
//         booleanParam(name: 'executeTests', defaultValue: true, description:'')
//
//
//     }

//     tools {
//        maven 'Maven'
//     }
//
//     environment {
//         NEW_VERSION = '1.3.0'
//
//         SERVER_CREDENTIALS = credentials('server-cred')
//
//     }



    stages {

       stage("init") {
            steps {
               script {

                  echo "adding groovy code"
                  gv = load "script.groovy"

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
                   BRANCH_NAME == 'main' || BRANCH_NAME == 'jenkins-shared-lib'
                }

                }
            steps {
               script {
//                   gv.buildJar()
//
//                   sh 'mvn package'
                   buildJar()
               }

            }

        }


        stage("build and push image") {
           when {
                expression {
                   BRANCH_NAME == 'main' || BRANCH_NAME == 'jenkins-shared-lib'
                }

                }

            steps {
               script {

                    buildImage 'chidi123/quiz-app:jma-2.5'
                    dockerLogin()
                    dockerPush 'chidi123/quiz-app:jma-2.5'



//                      gv.buildImage()
//                   echo "building the docker image..."
//
//                   withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
//                       sh 'docker build -t chidi123/quiz-app:jma-2.0 .'
//                       sh "echo $PASS docker login -u $USER --password-stdin"
//                       sh "docker push chidi123/quiz-app:jma-2.0"
//                   }




               }

            }

        }


        stage("deploy") {
           when {
                expression {
                   BRANCH_NAME == 'main'
                }

                }
//             input {
//                message "Select the environment to deploy to"
//                ok "Done"
//
//                parameters {
//                   choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: '')
//
//                }
//
//             }
            steps {
               script {

                  echo "Deploying ......"
//                   gv.deployApp()



               }

            }

        }
    }
//     post {
//         always {
//
//         }
//         success {
//
//         }
//         failure {
//
//         }
//
//
//     }


}