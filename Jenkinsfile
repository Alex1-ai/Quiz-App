
def gv
pipeline {
    agent any

    tools {
        maven "Maven"
    }

    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description:'')


    }

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
                  gv = load "script.groovy"

               }
            }

        }

        stage("test") {

                when {
                     expression {
                         params.executeTests
                     }

                }
         steps {
                script {
                    gv.testApp()

                    sh 'mvn test'

                }

            }
        }


        stage("build") {
            steps {
               script {
                  gv.buildApp()

                  sh 'mvn package'

               }

            }

        }


        stage("build image") {
            steps {
               script {
                  echo "building the docker image..."

                  withCredentials(UsernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')) {
                      sh 'docker build -t chidi123/quiz-app:jma-2.0 .'
                      sh "echo $PASS docker login -u $USER --password-stdin"
                      sh "docker push chidi123/quiz-app:jma-2.0"
                  }




               }

            }

        }


        stage("deploy") {

            input {
               message "Select the environment to deploy to"
               ok "Done"

               parameters {
                  choice(name: 'ENV', choices: ['dev', 'staging', 'prod'], description: '')

               }

            }
            steps {
               script {

                  echo "Deploying version ${params.VERSION} to ${ENV}"
                  gv.deployApp()



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