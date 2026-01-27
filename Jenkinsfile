
def gv
pipeline {
    agent any

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

                }

            }
        }


        stage("build") {
            steps {
               script {
                  gv.buildApp()

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
                  gv.deployApp()

                  echo "Deploying to ${ENV}"

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